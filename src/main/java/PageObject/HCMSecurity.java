package PageObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

import Utilities.AllureReportUtil;
import Utilities.ExcelReader;
import Utilities.ScenarioContext;
import Utilities.TileValidationReportUtil;
import Utilities.Wrapper;

public class HCMSecurity {

    WebDriver driver;

    public HCMSecurity(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    String userName;
    String passWord;

    String EXCEL_PATH = "src/test/resources/TestData/OracleHCMRegressionTestData.xlsx";
    String SHEET_NAME = "Security";
    String KEY_COLUMN_HEADER = "Test Case";

    private String getCurrentTestCaseKey() {
        String key = ScenarioContext.getTestCaseKey();
        if (key == null || key.isBlank()) {
            throw new IllegalStateException("Test case key is not set. Check Hooks @Before.");
        }
        return key;
    }
    String currentScenarioTag = getCurrentTestCaseKey();

    // Accumulates failures across the whole "Approved Menu Options" list for
    // this scenario, so one AssertionError at the end reports everything that
    // was missing, rather than stopping at the first miss.
    private SoftAssert overallSoftAssert;

    private void attachStepEvidence(String stepName) {
        AllureReportUtil.attachScreenshot(driver, "Step screenshot - " + stepName);
    }

    // =========================================================
    // Structured logging - every validation outcome is reported in one
    // consistent, professional format instead of a bare sentence, so log
    // entries read like a test-management/reporting tool's output and can
    // be scanned, filtered, or grepped by status, test case, or section:
    //
    //   [STATUS] Test Case: <tag> | Section: <section> | Menu Item: '<item>' | Detail: <detail>
    // =========================================================

    private static final String LOG_PASS = "PASS";
    private static final String LOG_FAIL = "FAIL";
    private static final String LOG_SKIPPED = "SKIPPED";
    private static final String LOG_INFO = "INFO";

    /**
     * Builds one structured log line for a section-level event (no specific
     * menu item involved) - e.g. a missing tab, a missing "Show More" link,
     * or a section being skipped because no data was configured for it.
     */
    private String buildLogMessage(String status, String section, String detail) {
        return buildLogMessage(status, section, null, detail);
    }

    /**
     * Builds one structured log line for a menu-item-level event, e.g. a
     * single tile/link being found or not found under a given section.
     */
    private String buildLogMessage(String status, String section, String menuItem, String detail) {
        StringBuilder sb = new StringBuilder();
        sb.append('[').append(status).append("] ");
        sb.append("Test Case: ").append(currentScenarioTag);
        sb.append(" | Section: ").append(section);
        if (menuItem != null && !menuItem.isEmpty()) {
            sb.append(" | Menu Item: '").append(menuItem).append("'");
        }
        sb.append(" | Detail: ").append(detail);
        return sb.toString();
    }

    // =========================================================
    // Login Page locators
    // =========================================================

    By xpath_UserName = By.xpath("//input[contains(@id, 'username')]");
    By xpath_Password = By.xpath("//input[contains(@id, 'password')]");
    By xpath_SigninButton = By.xpath("//*[text()='Sign In']");

    /**
     * Login method used for logging into Oracle HCM Application
     * by retrieving credentials from Excel.
     */
    public void enterCredentials() throws Exception {
        try {
            userName = ExcelReader.getCellDataByKey(EXCEL_PATH, SHEET_NAME, KEY_COLUMN_HEADER, currentScenarioTag, "Username");
            passWord = ExcelReader.getCellDataByKey(EXCEL_PATH, SHEET_NAME, KEY_COLUMN_HEADER, currentScenarioTag, "Password");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UserName), userName, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Password), passWord, false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error entering credentials: " + e.getMessage());
        }
    }

    /**
     * Click Sign In and wait for the dashboard to fully load before any
     * validation begins.
     */
    public void openDashboardPage() {
        try {
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SigninButton));
            AllureReportUtil.info("Clicked Sign In. Waiting 10 seconds for the dashboard to load completely.");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            throw new RuntimeException("Error clicking Sign In button: " + e.getMessage());
        }
    }

    // =========================================================
    // Show More locator (the tab locators themselves are now built
    // dynamically in getTabLocatorForSection() below)
    // =========================================================

    // Matches every "Show More" anchor on the page, not scoped to any
    // particular @group attribute - different sections (Benefits, Recruiting,
    // Treasury, etc.) can render this under different @group values, so a
    // narrower locator risks silently finding nothing for some sections.
    // Because more than one such anchor can exist in the DOM at once, callers
    // must pick the currently VISIBLE one (see firstVisible() below) rather
    // than assuming a fixed index/position.
    By xpath_ShowMore = By.xpath("//a[normalize-space(text())='Show More']");

    /**
     * Given a list of same-locator matches (e.g. multiple "Show More"
     * anchors present in the DOM at once, one per section), returns the
     * first one that's actually visible - not just the first in DOM order,
     * since DOM order doesn't necessarily correspond to which section/tab
     * is currently active.
     */
    private Optional<WebElement> firstVisible(List<WebElement> elements) {
        return elements.stream().filter(WebElement::isDisplayed).findFirst();
    }

    // =========================================================
    // Section name -> tab locator / Excel column mapping
    //
    // Fully dynamic - no code change needed to support a new section/tab.
    // In "Approved Menu Options", use the section's exact visible tab text
    // (e.g. "My Client Groups", "HR Connect", "Tools"), or "Me" for the
    // default landing page. Suffix with " Showmore" (case-insensitive) to
    // validate that section's Show More tiles, e.g. "Me, Me Showmore,
    // My Client Groups, My Client Groups Showmore, HR Connect,
    // HR Connect Showmore, Tools, Tools Showmore". Always use the
    // fully-qualified form (section name + "Showmore") - a bare "Showmore"
    // on its own is not supported, since it would be ambiguous about which
    // section it belongs to.
    //
    // Matching Excel columns follow the naming convention:
    //   Tiles_<SectionNameWithNoSpaces>            e.g. Tiles_MyClientGroups
    //   Tiles_<SectionNameWithNoSpaces>_Showmore   e.g. Tiles_MyClientGroups_Showmore
    // =========================================================

    private By getTabLocatorForSection(String sectionName) {
        if (sectionName.trim().equalsIgnoreCase("me")) {
            return null; // default landing section, no tab click needed
        }
        return By.xpath("//a[contains(text(),'" + sectionName.trim() + "')]");
    }

    private String getExcelColumnForSection(String sectionName, boolean isShowMore) {
        String key = "Tiles_" + sectionName.trim().replaceAll("\\s+", "");
        return isShowMore ? key + "_Showmore" : key;
    }

    // =========================================================
    // Shared private helpers
    // =========================================================

    private void forcedWait(String reason) {
        forcedWait(reason, 3000);
    }

    private void forcedWait(String reason, long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sets the page zoom level via JS so more tiles fit within the visible
     * viewport without needing to resize the actual browser window.
     */
    private void setZoomLevel(int zoomPercent) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.style.zoom='" + zoomPercent + "%'");
        } catch (Exception e) {
            AllureReportUtil.info("Could not set zoom level to " + zoomPercent + "%: " + e.getMessage());
        }
    }

    private boolean isPageFullyLoaded() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String readyState = (String) js.executeScript("return document.readyState");
            return "complete".equalsIgnoreCase(readyState);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Confirms the page/DOM has actually finished loading before validation
     * starts. Waits and re-checks a few times; if it still isn't ready,
     * reloads the page as a last resort and waits again.
     * Returns true if a reload was triggered, so the caller can re-navigate
     * back to whatever tab/Show More state was lost by the reload.
     */
    private boolean ensurePageIsFullyLoaded(String sectionName) {
        int attempts = 0;
        int maxAttempts = 3;

        while (!isPageFullyLoaded() && attempts < maxAttempts) {
            AllureReportUtil.info("Page/DOM not fully loaded yet for '" + sectionName + "'. Waiting (attempt "
                    + (attempts + 1) + " of " + maxAttempts + ").");
            forcedWait("page/DOM to finish loading for '" + sectionName + "'", 5000);
            attempts++;
        }

        if (!isPageFullyLoaded()) {
            AllureReportUtil.info("Page/DOM still not loaded for '" + sectionName + "' after " + maxAttempts
                    + " attempts. Reloading the page.");
            driver.navigate().refresh();
            forcedWait("page reload to complete for '" + sectionName + "'", 10000);
            return true;
        }
        return false;
    }

    /**
     * Scrolls the page all the way to the bottom via JS. Several sections
     * (notably "Me") lazy-render their lower content only once scrolled
     * into view, so the "Show More" link can be absent/not-visible until
     * this runs - checking for it without scrolling first is unreliable.
     */
    private void scrollToBottom(String context) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (Exception e) {
            AllureReportUtil.info("Could not scroll to bottom for '" + context + "': " + e.getMessage());
        }
    }

    /**
     * Scrolls down the page in small steps, checking after each step whether
     * any element matching the given locator has become visible - rather
     * than jumping straight to the bottom, which can skip right past an
     * element that only renders when it's somewhere in the middle of the
     * viewport (lazy-rendered content that appears/disappears as it scrolls
     * through view, not just once scrolled all the way down).
     *
     * Starts from the top of the page each time so the scan always covers
     * the full page regardless of where a previous check left the scroll
     * position. Stops early once the element is found, or once scrolling
     * stops making progress (i.e. the bottom of the page has been reached).
     */
    private boolean scrollIncrementallyUntilVisible(By locator, String context) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int stepPixels = 150;
        int maxSteps = 25; // generous ceiling so even very long "Show More" lists are fully covered

        try {
            js.executeScript("window.scrollTo(0, 0)");
        } catch (Exception e) {
            AllureReportUtil.info("Could not scroll to top before incremental scan for '" + context + "': " + e.getMessage());
        }
        forcedWait("resetting to top before incremental scan for '" + context + "'", 300);

        for (int step = 0; step < maxSteps; step++) {
            List<WebElement> matches = Wrapper.findWebElements(locator);
            if (matches.stream().anyMatch(WebElement::isDisplayed)) {
                return true;
            }

            long beforeY;
            try {
                beforeY = ((Number) js.executeScript("return window.pageYOffset || document.documentElement.scrollTop;")).longValue();
                js.executeScript("window.scrollBy(0, arguments[0]);", stepPixels);
            } catch (Exception e) {
                AllureReportUtil.info("Could not scroll incrementally for '" + context + "': " + e.getMessage());
                break;
            }
            forcedWait("incremental scroll step while " + context, 300);

            long afterY = beforeY;
            try {
                afterY = ((Number) js.executeScript("return window.pageYOffset || document.documentElement.scrollTop;")).longValue();
            } catch (Exception e) {
                // If we can't read the new position, just fall through and
                // let the final check below decide.
            }
            if (afterY <= beforeY) {
                // Scrolling made no further progress - already at the bottom.
                break;
            }
        }

        List<WebElement> finalMatches = Wrapper.findWebElements(locator);
        return finalMatches.stream().anyMatch(WebElement::isDisplayed);
    }

    /**
     * Zooms out to 60%, confirms the page/DOM is fully loaded (reloading if
     * necessary), then takes a screenshot at the top of the page and another
     * after scrolling to the bottom - so both screenshots together cover
     * every tile in the section. Exactly two screenshots per section (or per
     * Show More), not one per tile.
     *
     * @param reNavigateIfReloaded callback that re-clicks whatever tab/Show More
     *                             was active before a reload. Pass null if nothing
     *                             needs re-navigating (e.g. the "Me" section).
     */
    private void captureFullSectionScreenshot(String sectionName, Runnable reNavigateIfReloaded) {
        setZoomLevel(60);
        forcedWait("zoom adjustment to settle for '" + sectionName + "'", 3000);

        boolean reloaded = ensurePageIsFullyLoaded(sectionName);
        if (reloaded && reNavigateIfReloaded != null) {
            AllureReportUtil.info("Page was reloaded - re-navigating back to '" + sectionName + "' before continuing.");
            reNavigateIfReloaded.run();
            forcedWait("re-navigation back to '" + sectionName + "' to settle", 5000);
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            js.executeScript("window.scrollTo(0, 0)");
        } catch (Exception e) {
            AllureReportUtil.info("Could not scroll to top for '" + sectionName + "': " + e.getMessage());
        }
        attachStepEvidence("Top view - " + sectionName);

        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (Exception e) {
            AllureReportUtil.info("Could not scroll to bottom for '" + sectionName + "': " + e.getMessage());
        }
        forcedWait("scroll to bottom to settle for '" + sectionName + "'", 3000);
        attachStepEvidence("Bottom view - " + sectionName);

        try {
            js.executeScript("window.scrollTo(0, 0)");
        } catch (Exception e) {
            // ignore - purely cosmetic, doesn't affect tile detection
        }
    }

    /**
     * Checks every expected tile for one section/Show-More against the live
     * page, logs a present/missing message per tile, attaches a screenshot
     * for each missing tile, records one summary row in the tabular report,
     * and fails overallSoftAssert if anything is missing.
     */
    private void validateSectionAndReport(String reportSectionLabel, String excelColumn) throws IOException {
        String tileNamesRaw = ExcelReader.getCellDataByKey(
                EXCEL_PATH, SHEET_NAME, KEY_COLUMN_HEADER, currentScenarioTag, excelColumn);

        if (tileNamesRaw == null || tileNamesRaw.trim().isEmpty()) {
            AllureReportUtil.info(buildLogMessage(LOG_SKIPPED, reportSectionLabel,
                    "No expected menu items are configured in Excel column '" + excelColumn
                            + "' for this test case, so this section was not validated."));
            return;
        }

        List<String> expectedTiles = Arrays.stream(tileNamesRaw.split(","))
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toList());

        List<String> actualPresentTiles = new ArrayList<>();
        List<String> missingTiles = new ArrayList<>();

        for (String tileName : expectedTiles) {
            try {
                forcedWait("checking tile '" + tileName + "' under '" + reportSectionLabel + "'", 300);

                By dynamicXpath = By.xpath("//a[normalize-space(text())='" + tileName + "']");
                List<WebElement> matchingTiles = Wrapper.findWebElements(dynamicXpath);
                boolean isAnyVisible = matchingTiles.stream().anyMatch(WebElement::isDisplayed);

                if (!isAnyVisible) {
                    // Some tiles (e.g. items near the bottom of a long "Show More"
                    // list, like "Year End Documents") only render once scrolled
                    // into view - the same lazy-render behavior already seen with
                    // the "Show More" link itself. A tile could be rendered
                    // anywhere on the page, not just at the very bottom, so scroll
                    // down step by step and check after each step, rather than
                    // jumping straight to the bottom and potentially scrolling
                    // straight past it.
                    isAnyVisible = scrollIncrementallyUntilVisible(dynamicXpath,
                            "checking tile '" + tileName + "' under '" + reportSectionLabel + "'");
                }

                if (isAnyVisible) {
                    AllureReportUtil.info("\"" + tileName + "\" Tile/Link is present in the \"" + reportSectionLabel + "\" section as expected");
                    actualPresentTiles.add(tileName);
                } else {
                    String failMessage = "\"" + tileName + "\" Tile/Link is NOT present in the \"" + reportSectionLabel + "\" section as expected";
                    AllureReportUtil.info(failMessage);
                    attachStepEvidence(failMessage);
                    missingTiles.add(tileName);
                }
            } catch (Exception e) {
                // Any unexpected exception (stale element, timeout, etc.) while checking
                // one tile must not abort validation of the remaining tiles in this
                // section - log it as a failure for this tile only, and move on.
                String exceptionMessage = "\"" + tileName + "\" Tile/Link is NOT present in the \"" + reportSectionLabel
                        + "\" section as expected - an exception occurred while checking it: " + e.getMessage();
                AllureReportUtil.info(exceptionMessage);
                attachStepEvidence(exceptionMessage);
                missingTiles.add(tileName);
            }
        }

        String expectedJoined = String.join(", ", expectedTiles);
        String actualJoined = actualPresentTiles.isEmpty() ? "None" : String.join(", ", actualPresentTiles);

        String comments;
        if (missingTiles.isEmpty()) {
            comments = "All tiles present as expected.";
        } else {
            comments = missingTiles.stream()
                    .map(t -> "'" + t + "' could not be located")
                    .collect(Collectors.joining("; "));
            overallSoftAssert.fail(reportSectionLabel + " - " + comments);
        }

        TileValidationReportUtil.addResult(currentScenarioTag, reportSectionLabel, expectedJoined, actualJoined, comments);
    }

    /**
     * Navigates to a main tab section (or stays on "Me" for the default
     * landing page), then validates its tiles.
     */
    private void navigateAndValidateSection(String sectionName) throws IOException {
        By tabLocator = getTabLocatorForSection(sectionName);

        if (tabLocator != null) {
            Optional<WebElement> visibleTab = firstVisible(Wrapper.findWebElements(tabLocator));
            if (visibleTab.isEmpty()) {
                String comments = "The '" + sectionName + "' tab could not be located on the page.";
                String failMessage = buildLogMessage(LOG_FAIL, sectionName,
                        "Navigation tab is missing or not visible on the dashboard, so no menu items under it could be validated.");
                AllureReportUtil.info(failMessage);
                attachStepEvidence(failMessage);
                overallSoftAssert.fail(comments);
                TileValidationReportUtil.addResult(currentScenarioTag, sectionName, "-", "-", comments);
                return;
            }
            WebElement tabElement = visibleTab.get();
            Wrapper.clickWebElement(tabElement);
            AllureReportUtil.info("\"" + sectionName + "\" tab is clicked.");
            // Wait only for this specific tab element to remain visible after the
            // click - not visibilityOfAllElementsLocatedBy(tabLocator), which
            // would require every element matching the locator on the page to be
            // visible at once (the same issue fixed for the Show More link below).
            //
            // Some apps (Oracle ADF-style partial page rendering) recreate the
            // clicked element's DOM node the moment it's clicked, even though the
            // click itself succeeded and the UI updated correctly - which makes
            // this exact WebElement handle go stale. That's a rendering detail,
            // not a real validation failure, so it must not abort the section -
            // silently continue into the actual tile checks below.
            try {
                Wrapper.getWait().until(ExpectedConditions.visibilityOf(tabElement));
            } catch (Exception e) {
                // Intentionally not logged - a stale/timed-out handle here is
                // expected UI behavior, not a validation problem worth reporting.
            }
            forcedWait(sectionName + " tab");
        } else {
            forcedWait(sectionName + " (dashboard landing section)");
        }

        Runnable reNavigate = (tabLocator == null) ? null
                : () -> firstVisible(Wrapper.findWebElements(tabLocator)).ifPresent(Wrapper::clickWebElement);
        captureFullSectionScreenshot(sectionName, reNavigate);

        String excelColumn = getExcelColumnForSection(sectionName, false);
        if (excelColumn == null) {
            AllureReportUtil.info(buildLogMessage(LOG_SKIPPED, sectionName,
                    "No matching Excel column mapping exists for menu option '" + sectionName + "', so this section was not validated."));
            return;
        }
        validateSectionAndReport(sectionName, excelColumn);
    }

    /**
     * Clicks "Show More" under the given base section (if present) and
     * validates the additional tiles it reveals.
     */
    private void validateShowMoreForSection(String baseSectionName, String reportLabel) throws IOException {
        By tabLocator = getTabLocatorForSection(baseSectionName);

        // The "Show More" link is often below the initial viewport (especially
        // under "Me", which has a long tile list) and some sections only
        // render/reveal it once the page is scrolled down - so check for it
        // only after scrolling to the bottom, and retry once more before
        // concluding it's genuinely missing.
        scrollToBottom(baseSectionName + " - locating 'Show More'");
        forcedWait("scrolling to bottom before locating 'Show More' under '" + baseSectionName + "'", 1000);

        Optional<WebElement> visibleShowMore = firstVisible(Wrapper.findWebElements(xpath_ShowMore));
        if (visibleShowMore.isEmpty()) {
            // Retry once - scroll to bottom again in case the first scroll
            // triggered additional lazy-loaded content that pushed the real
            // bottom (and the Show More link) further down.
            scrollToBottom(baseSectionName + " - retry locating 'Show More'");
            forcedWait("retry scroll before locating 'Show More' under '" + baseSectionName + "'", 1500);
            visibleShowMore = firstVisible(Wrapper.findWebElements(xpath_ShowMore));
        }

        if (visibleShowMore.isEmpty()) {
            String comments = "The 'Show More' link could not be located under the '" + baseSectionName + "' section.";
            String failMessage = buildLogMessage(LOG_FAIL, reportLabel,
                    "'Show More' link is missing or not visible under the '" + baseSectionName
                            + "' section, so its additional menu items could not be validated.");
            AllureReportUtil.info(failMessage);
            attachStepEvidence(failMessage);
            overallSoftAssert.fail(comments);
            TileValidationReportUtil.addResult(currentScenarioTag, reportLabel, "-", "-", comments);
            return;
        }

        WebElement showMoreElement = visibleShowMore.get();
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", showMoreElement);
        } catch (Exception e) {
            AllureReportUtil.info("Could not scroll to the 'Show More' link under '" + baseSectionName + "': " + e.getMessage());
        }
        forcedWait("scrolling to 'Show More' under '" + baseSectionName + "'", 1000);

        Wrapper.clickWebElement(showMoreElement);
        AllureReportUtil.info("\"Show More\" link is clicked under the \"" + baseSectionName + "\" section.");
        // Wait for THIS specific element to still be visible after the click -
        // not visibilityOfAllElementsLocatedBy(xpath_ShowMore), which requires
        // every "Show More" anchor on the page to be visible at once. Since
        // other sections' anchors typically stay hidden while a different tab
        // is active, that condition was essentially never satisfiable and
        // reliably timed out after the full wait duration.
        //
        // This app appears to re-render the DOM node the instant "Show More" is
        // clicked (its label correctly flips to "Show Less" - the click itself
        // works), which invalidates this exact WebElement handle even though
        // nothing actually went wrong. That's a rendering detail, not a real
        // failure, so it must not abort validation of this section's tiles -
        // silently continue; the tile checks below are the real test anyway.
        try {
            Wrapper.getWait().until(ExpectedConditions.visibilityOf(showMoreElement));
        } catch (Exception e) {
            // Intentionally not logged - a stale/timed-out handle here is
            // expected UI behavior, not a validation problem worth reporting.
        }
        forcedWait(baseSectionName + " - Show More");

        // If a reload happens while capturing screenshots, re-click the parent
        // tab (if any) AND re-click Show More, since a reload loses both.
        Runnable reNavigate = () -> {
            if (tabLocator != null) {
                firstVisible(Wrapper.findWebElements(tabLocator)).ifPresent(Wrapper::clickWebElement);
            }
            scrollToBottom(baseSectionName + " - re-locating 'Show More' after reload");
            forcedWait("scrolling to bottom before re-locating 'Show More' under '" + baseSectionName + "'", 2000);
            Optional<WebElement> visibleShowMoreAgain = firstVisible(Wrapper.findWebElements(xpath_ShowMore));
            if (visibleShowMoreAgain.isPresent()) {
                WebElement showMoreAgainElement = visibleShowMoreAgain.get();
                try {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block: 'center'});", showMoreAgainElement);
                } catch (Exception e) {
                    AllureReportUtil.info("Could not scroll to 'Show More' during re-navigation: " + e.getMessage());
                }
                Wrapper.clickWebElement(showMoreAgainElement);
            }
        };
        captureFullSectionScreenshot(reportLabel, reNavigate);

        String excelColumn = getExcelColumnForSection(baseSectionName, true);
        if (excelColumn == null) {
            AllureReportUtil.info(buildLogMessage(LOG_SKIPPED, reportLabel,
                    "No matching Excel column mapping exists for menu option '" + baseSectionName
                            + " Showmore', so this section was not validated."));
            return;
        }
        validateSectionAndReport(reportLabel, excelColumn);
    }

    // =========================================================
    // Orchestrator - reads "Approved Menu Options" and dynamically
    // navigates/validates only the sections listed, in the order listed.
    // =========================================================

    public void Validate_All_Tiles() throws IOException {
        overallSoftAssert = new SoftAssert();

        String approvedMenuOptionsRaw = ExcelReader.getCellDataByKey(
                EXCEL_PATH, SHEET_NAME, KEY_COLUMN_HEADER, currentScenarioTag, "Approved Menu Options");

        if (approvedMenuOptionsRaw == null || approvedMenuOptionsRaw.trim().isEmpty()) {
            AllureReportUtil.info(buildLogMessage(LOG_SKIPPED, "N/A",
                    "'Approved Menu Options' is not configured in Excel for this test case, so no sections were validated."));
            return;
        }

        List<String> menuOptions = Arrays.stream(approvedMenuOptionsRaw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        for (String menuOption : menuOptions) {
            boolean isShowMore = menuOption.toLowerCase().endsWith("showmore");
            String baseSectionName = isShowMore
                    ? menuOption.substring(0, menuOption.length() - "showmore".length()).trim()
                    : menuOption;

            try {
                if (isShowMore) {
                    validateShowMoreForSection(baseSectionName, menuOption);
                } else {
                    navigateAndValidateSection(menuOption);
                }
            } catch (Exception e) {
                // An exception while processing one section (stale tab, navigation
                // timeout, etc.) must not stop the remaining sections in this
                // scenario from being validated - record it and continue.
                String comments = "An exception occurred while validating the '" + menuOption + "' section: " + e.getMessage();
                AllureReportUtil.info(comments);
                attachStepEvidence(comments);
                overallSoftAssert.fail(comments);
                TileValidationReportUtil.addResult(currentScenarioTag, menuOption, "-", "-", comments);
            }
        }

        // Attach this scenario's rows as a table directly in the Allure report,
        // in addition to the standalone TileValidationReport.xlsx covering all
        // scenarios. Attached before assertAll() so it's still visible even
        // when this scenario is about to be marked as failed.
        List<TileValidationReportUtil.ResultRow> thisScenarioRows =
                TileValidationReportUtil.getResultsForTestCase(currentScenarioTag);
        String htmlTable = TileValidationReportUtil.buildHtmlTable(thisScenarioRows);
        AllureReportUtil.attachHtml("Tile Validation Report (HTML) - " + currentScenarioTag, htmlTable);

        String csvTable = TileValidationReportUtil.buildCsvTable(thisScenarioRows);
        AllureReportUtil.attachCsv("Tile Validation Report - " + currentScenarioTag, csvTable);

        overallSoftAssert.assertAll();
    }
}
