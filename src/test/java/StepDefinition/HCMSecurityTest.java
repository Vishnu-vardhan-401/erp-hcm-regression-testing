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

public class HCMSecurityTest {
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

    //CE Time Entry Clerk
    @Given("the user is logged into Oracle HCM as a CE Time Entry Clerk and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        try{
            initializePageObjectsIfNeeded();

            URL=ConfigReader.getProperty("URL");
            WebDriver activeDriver = getActiveDriver();
            
            activeDriver.manage().deleteAllCookies();
            activeDriver.navigate().to(URL);
            activeDriver.manage().window().maximize();

            hcmSecurity.enterCredentials();
            
            AllureReportUtil.info("Navigated to Oracle HCM URL page");
            attachStepEvidence("the employee is logged into Oracle HCM as an Employee");
            hcmSecurity.openDashboardPage();

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error in the_user_logs_into_environment_as_role step: " + e.getMessage(), e);
        }
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Time Entry Clerk role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Time_Entry_Clerk_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.captureMyClientGroupsDashboardItems();
        hcmSecurity.captureHRConnectDashboardItems();
        hcmSecurity.captureToolsDashboardItems();
    }
    
    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Time Entry Clerk role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Time_Entry_Clerk_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.validateDashboardItems();
    }


    //CE Compensation Labor Relations Staff Exclude Retirees
    @Given("the user is logged into Oracle HCM as a CE Compensation Labor Relations Staff Exclude Retirees and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Compensation_Labor_Relations_Staff_Exclude_Retirees_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Compensation Labor Relations Staff Exclude Retirees role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Compensation_Labor_Relations_Staff_Exclude_Retirees_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.captureCompensationLaborRelationsStaffExcludeRetireesMyClientGroupsDashboardItems();
        hcmSecurity.captureCompensationLaborRelationsStaffExcludeRetireesHRConnectDashboardItems();
        hcmSecurity.captureCompensationLaborRelationsStaffExcludeRetireesToolsDashboardItems();
    }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Compensation Labor Relations Staff Exclude Retirees role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Compensation_Labor_Relations_Staff_Exclude_Retirees_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.validateDashboardItems();
    }


    //CE Payroll Staff Full Population
    @Given("the user is logged into Oracle HCM as a CE Payroll Staff Full Population and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Payroll_Staff_Full_Population_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Payroll Staff Full Population role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Payroll_Staff_Full_Population_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.capturePayrollStaffFullPopulationMyClientGroupsDashboardItems();
        hcmSecurity.capturePayrollStaffFullPopulationHRConnectDashboardItems();
        hcmSecurity.capturePayrollStaffFullPopulationToolsDashboardItems();
    }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Payroll Staff Full Population role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Payroll_Staff_Full_Population_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.validateDashboardItems();
    }

    //CE Tech Support View Only Data
    @Given("the user is logged into Oracle HCM as a CE Tech Support View Only Data and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Tech_Support_View_Only_Data_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Tech Support View Only Data role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Tech_Support_View_Only_Data_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.captureTechSupportViewOnlyDataMyClientGroupsDashboardItems();
        hcmSecurity.captureTechSupportViewOnlyDataHRConnectDashboardItems();
        hcmSecurity.captureTechSupportViewOnlyDataToolsDashboardItems();
    }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Tech Support View Only Data role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Tech_Support_View_Only_Data_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.validateDashboardItems();
    }

    //CE Treasury Full Population
    @Given("the user is logged into Oracle HCM as a CE Treasury Full Population and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Treasury_Full_Population_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Treasury Full Population role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Treasury_Full_Population_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.captureTreasuryFullPopulationMyClientGroupsDashboardItems();
        hcmSecurity.captureTreasuryFullPopulationHRConnectDashboardItems();
        hcmSecurity.captureTreasuryFullPopulationToolsDashboardItems();
    }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Treasury Full Population role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Treasury_Full_Population_role() {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.validateDashboardItems();
    }
    
    

    


    














    

    
}
