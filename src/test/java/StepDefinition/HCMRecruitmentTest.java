package StepDefinition;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

import Base.BaseClass;
import PageObject.HCMCoreHR;
import PageObject.HCMSecurity;
import PageObjectManager.PageObjectManagerHCM;
import Utilities.AllureReportUtil;
import Utilities.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HCMRecruitmentTest {
    String URL;
    HCMCoreHR hdp;
    HCMSecurity hcmSecurity;
    PageObjectManagerHCM pageObjectManagerHCM;

    private WebDriver getActiveDriver() {
        try {
            WebDriver activeDriver = BaseClass.getDriver();
            if (activeDriver == null) {
                throw new IllegalStateException("WebDriver is null in step execution. Verify Hooks @Before and glue package configuration.");
            }
            return activeDriver;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting active WebDriver: " + e.getMessage(), e);
        }
    }

    /**
     * Initialize page objects if they are not already initialized.
     * This method ensures that the PageObjectManagerHCM and HCMCoreHR instances are created with the active WebDriver.
     * Scripted By: gaddem [Gadde Madhukar]
     * Note: This method can be called at the beginning of step definitions to ensure page objects are ready for use.
     */
    private void initializePageObjectsIfNeeded(){
        try {
            WebDriver activeDriver = getActiveDriver();

            activeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            if (this.pageObjectManagerHCM == null) {
                this.pageObjectManagerHCM = new PageObjectManagerHCM(activeDriver);
            }
            if (this.hcmSecurity == null) {
                this.hcmSecurity = pageObjectManagerHCM.getHCMSecurity();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing page objects: " + e.getMessage(), e);
        }
    }

    /**
     * Utility method to capture and attach a screenshot to Allure report for a given step.
     * This method retrieves the active WebDriver, captures a screenshot, and attaches it to the 
       Allure report with a descriptive name.
     */
    private void attachStepEvidence(String stepName){
        AllureReportUtil.attachScreenshot(getActiveDriver(), "Step screenshot - " + stepName);
    }

}