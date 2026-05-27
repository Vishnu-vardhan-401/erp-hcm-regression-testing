package Utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.BaseClass;
import io.qameta.allure.Allure;

public class Wrapper extends BaseClass {

    public Wrapper(){
        // No-op constructor: all methods resolve driver lazily from BaseClass.
    }
    // Define the base directory for screenshots
    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + "ScreenShots";

    /* Captures a screenshot of the entire visible page. */
    public static String captureFullPage(WebDriver driver, String screenshotName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return saveFile(srcFile, screenshotName);
    }

    /* Captures a screenshot of a specific WebElement.*/
    public static String captureElement(WebElement element, String screenshotName) {
        File srcFile = element.getScreenshotAs(OutputType.FILE);
        return saveFile(srcFile, screenshotName);
    }

    /* Internal helper method to handle the file saving logic safely. */
    private static String saveFile(File srcFile, String baseName) {
        try {
            // 1. Ensure the directory exists
            Path directoryPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // 2. Generate a readable timestamp (e.g., 20231024_153045)
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String finalFileName = baseName + "_" + timestamp + ".png";

            // 3. Define destination path
            Path destinationPath = Paths.get(SCREENSHOT_DIR, finalFileName);

            // 4. Safely copy the file
            Files.copy(srcFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Screenshot saved successfully at: " + destinationPath);
            return destinationPath.toString(); // Return path in case reporters (like ExtentReports) need it

        } catch (IOException e) {
            // Throw an unchecked exception so it fails the test clearly without forcing throws declarations everywhere
            throw new RuntimeException("Failed to save screenshot: " + e.getMessage(), e);
        }
    }

    //Internal Helper to click WebElement
    @SuppressWarnings("CallToPrintStackTrace")
    public static void clickWebElement(WebElement element) {
        try {
            if (element == null) {
                throw new IllegalArgumentException("Cannot click a null WebElement.");
            }
            WebElement clickableElement = waitForElementToBeClickable(element);
            // highlightElement(clickableElement);
            clickableElement.click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error clicking WebElement: " + e.getMessage(), e);
        }
    }

    
    public static void redwoodSync() {
    try {
        Thread.sleep(4000); // debounce

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(d -> ((JavascriptExecutor)d)
                .executeScript("return document.readyState").equals("complete"));

        } catch (Exception ignored) {}
    }


    //Internal Helper to send data to WebElement
    public static void WebElementsendKeys(WebElement element, String text, Boolean pressEnter) {
        if (pressEnter != null && pressEnter) {
            waitForElementToBeVisible(element);
           
            // element.click();
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(Keys.DELETE);
            element.sendKeys(text);
            element.sendKeys(Keys.ENTER);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            System.out.println("Sent keys with ENTER: " + text);
        } else {
            // highlightElement(element);
            element.sendKeys(text);
        }
    }

    public static void WebElementsendKeysWithtab(WebElement element) {
       try{
            waitForElementToBeClickable(element);
            element.click();
            //element.sendKeys(text);
            element.sendKeys(Keys.TAB);
           // System.out.println("Sent keys with ENTER: " + text);
    }catch (Exception e) {
        // e.printStackTrace();
        }
    }

    //Internal helper to get Text from WebElement
    public static String getText(WebElement element) {
        try {
            WebElement visibleElement = waitForElementToBeVisible(element);
            highlightElement(visibleElement);
            String text = visibleElement.getText();
            passTest("Successfully retrieved text from element" + text);
            return text;
        } catch (Exception e) {
            failTest("Failed to retrieve text from element Exception: " + e.getMessage());
            return null;
        }
    }

    //Internal Helper to wait for required seconds
    public static void waitForSeconds(int seconds) {
        try {
            // Thread.sleep(seconds * 1000L);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
        } catch (Exception e) {
            
            failTest("Wait was interrupted: " + e.getMessage());
        }
    }

    //Helper to scrollTo WebElement
    public static void scrollToElement(WebElement element, String elementName) {
        try {
            Thread.sleep(3000);
            JavascriptExecutor js = (JavascriptExecutor) BaseClass.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            // highlightElement(element);
            passTest("Successfully scrolled to " + elementName);
        } catch (InterruptedException e) {
            failTest("Failed to scroll to " + elementName + ". Exception: " + e.getMessage());
        }
    }


    //Helper to moveTo WebElement
    public static void moveToElement(WebElement element, String elementName) {
        try {
            Actions actions = new Actions(BaseClass.getDriver());
            actions.moveToElement(element).perform();
            highlightElement(element);
            passTest("Successfully moved to " + elementName);
        } catch (Exception e) {
            failTest("Failed to move to " + elementName + ". Exception: " + e.getMessage());
        }
    }


    public static WebDriverWait getWait() {
        int timeout = Integer.parseInt(ConfigReader.getProperty("explicitWait"));
        return new WebDriverWait(getDriverOrThrow(), Duration.ofSeconds(timeout));
    }

    public static WebElement waitForElementToBeClickable(WebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot wait for clickability of a null WebElement.");
        }
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForpresenceOfElementLocated(By locator) {
        if (locator == null) {
            throw new IllegalArgumentException("Cannot wait for presence of elements with a null locator.");
        }
        return getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeVisible(WebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot wait for visibility of a null WebElement.");
        }
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void highlightElement(WebElement element) {
        try {
            if (element == null) {
                return;
            }
            JavascriptExecutor js = (JavascriptExecutor) getDriverOrThrow();
            js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
            Thread.sleep(150);
            js.executeScript("arguments[0].setAttribute('style', '');", element);
        } catch (Exception e) {
            // Log warning or ignore if highlighting fails
        }
    }

    public static void passTest(String message) {
        Allure.step(message);
        // Optionally attach screenshot on pass:
        // ScreenshotUtil.takeScreenshot("Pass Screenshot - " + message);
    }

    public static void failTest(String message) {
        WebDriver activeDriver = getDriverOrThrow();
        captureFullPage(activeDriver,"Failed - " + message);
        Allure.step("FAIL: " + message);
//        Assert.fail(message); // Force fail in TestNG
    }

    public static void blockTest(String message) {
        WebDriver activeDriver = getDriverOrThrow();
        captureFullPage(activeDriver,"Blocked - " + message);
        Allure.step("BLOCKED: " + message);
//        Assert.fail(message);
    }

    public static WebElement findWebElement(By locator) {
        WebDriver activeDriver = getDriverOrThrow();
        return activeDriver.findElement(locator);
    }

    public static List<WebElement> findWebElements(By locator) {
        WebDriver activeDriver = getDriverOrThrow();
        return activeDriver.findElements(locator);
    }

    //Internal Helper for Date Selection
    public static void selectDate(WebElement datePicker,String dateValue,By datesLocator,By monthLocator, By yearLocator, By nextButtonLocator)  throws Exception {
        // WebDriver activeDriver = getDriverOrThrow();
        
        // 1. Click the date input to open the calendar widget
        clickWebElement(datePicker);

        // 2. Navigate to correct month & year
        String[] dateParts = dateValue.split("/");
        String targetMonth = dateParts[1];
        // System.out.println("Target Month: " + targetMonth);
        String targetYear = dateParts[2];
        // System.out.println("Target Year: " + targetYear);

        while (true) {
            String currentMonth = findWebElement(monthLocator).getText();
            // System.out.println("Current Month: " + currentMonth);
            String currentYear = findWebElement(yearLocator).getText(); 
            // System.out.println("Current Year: " + currentYear);
            if (currentMonth.equals(targetMonth) && currentYear.equals(targetYear)) {
                break;
            } 
            else {
                findWebElement(nextButtonLocator).click();
                Thread.sleep(2000); // small pause to allow calendar to update - adjust as needed
            }
        }
        
        // 4. Select the day
        List<WebElement> days = findWebElements(datesLocator);
        String targetDay = dateParts[0];
        
        for (WebElement day : days) {
            if (day.getText().equals(targetDay)) {
                clickWebElement(day);
                break;
            }
        }
    }

    private static WebDriver getDriverOrThrow() {
        WebDriver activeDriver = BaseClass.getDriver();
        if (activeDriver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Ensure Hooks @Before runs before using Wrapper methods.");
        }
        return activeDriver;
    }

    // public static void selectFromDropdown(WebElement dropdown, WebElement selectionOption) {
    //     try {
    //         JavascriptExecutor js = (JavascriptExecutor) getDriverOrThrow();
    //         js.executeScript("arguments[0].scrollIntoView(true);", dropdown);
    //         clickWebElement(dropdown);
    //         // Thread.sleep(500);
    //         Actions actions = new Actions(getDriverOrThrow());
    //         actions.moveToElement(selectionOption).perform();
    //         clickWebElement(selectionOption);

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         // failTest("Failed to select '" + "' from dropdown. Exception: " + e.getMessage());
    //     }
    // }


    
    // 5) Replace dropdown method: do NOT wait for visibility of all items
    public static void selectOptionFromCustomDropdown(WebElement dropdown, By optionsLocator, String optionToSelect) {
        final int maxAttempts = 5;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                // open dropdown robustly
                dropdown.click();
                Thread.sleep(5000); // small pause to allow options to render - adjust as needed

                // wait only for presence, not "all visible"
                getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
                List<WebElement> options = findWebElements(optionsLocator);

                for (WebElement option : options) {
                    String txt = option.getText() == null ? "" : option.getText().trim();
                    if (txt.equalsIgnoreCase(optionToSelect.trim())) {
                        ((JavascriptExecutor) getDriverOrThrow()).executeScript("arguments[0].scrollIntoView({block:'nearest'});", option);
                        getWait().until(ExpectedConditions.elementToBeClickable(option)).click();
                        System.out.println("Selected option: " + optionToSelect);
                        return;
                    }
                }
            }

            
            catch (Exception e) {
                if (attempt == maxAttempts) {
                    e.printStackTrace();
                    // failTest("Failed to select '" + optionToSelect + "' from custom dropdown. " + e.getMessage());
                }
            }
            
        }
    }

    public static void keyTab() {
        try {
            Actions actions = new Actions(getDriverOrThrow());

        // Send TAB key without specifying an element
        actions.sendKeys(Keys.TAB).perform();
        } catch (Exception e) {
            failTest("Failed to send TAB key. Exception: " + e.getMessage());
        }
    }

    public static void selectElementFromDropDown(WebElement dropdown,WebElement optionToSelect){
        try {
            clickWebElement(dropdown);
            getWait().until(ExpectedConditions.elementToBeClickable(optionToSelect)).click();
        } catch (Exception e) {
            // e.printStackTrace();
            // failTest("Failed to select '" + optionToSelect.getText() + "' from dropdown. Exception: " + e.getMessage());
        }
    }


    public static void clickUsingJS(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriverOrThrow();
            // js.executeScript("arguments[0].scrollIntoView(true);", element);
            // js.executeScript("arguments[0].scrollIntoView({block:'nearest'});", element);
            js.executeScript("arguments[0].click();", element);
            // js.executeScript("arguments[0].value = arguments[1];", element, inputField);
            // element.sendKeys(Keys.ENTER);
            
        } catch (Exception e) {
            failTest("Failed to click using JavaScript. Exception: " + e.getMessage());
        }
    }


}
