package Hooks;


import Base.BaseClass;
import Utilities.AllureReportUtil;
import Utilities.ScenarioContext;
import Utilities.TileValidationReportUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks extends BaseClass {

    private static final String TILE_REPORT_OUTPUT_PATH = "target/reports/TileValidationReport.xlsx";

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        if(BaseClass.getDriver() == null) {
            BaseClass.initializeDriver();
        }

        String key = scenario.getSourceTagNames().stream()
                .filter(t -> t.startsWith("@"))   // add stricter filter if needed
                .map(t -> t.substring(1))
                .findFirst()
                .orElse(scenario.getName().trim()); // fallback
        ScenarioContext.setTestCaseKey(key);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            AllureReportUtil.attachScreenshot(driver, "Failure screenshot - " + scenario.getName());
        }

        // Re-write the consolidated tile validation table after every scenario,
        // not just once at the very end. Each write reflects everything
        // accumulated so far, so a crash mid-run still leaves a valid file
        // covering every scenario that completed up to that point.
        try {
            TileValidationReportUtil.writeReportToExcel(TILE_REPORT_OUTPUT_PATH);
            System.out.println("Tile validation report updated: " + TILE_REPORT_OUTPUT_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (driver != null) {
            driver.quit();
            driver = null; // Important: set to null so the next scenario knows it's gone
        }
        ScenarioContext.clear();
    }
}
