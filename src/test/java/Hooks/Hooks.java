package Hooks;


import Base.BaseClass;
import Utilities.AllureReportUtil;
import Utilities.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks extends BaseClass {

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
        if (driver != null) {
            driver.quit();
            driver = null; // Important: set to null so the next scenario knows it's gone
        }
        ScenarioContext.clear();
    }
}
