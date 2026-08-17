package StepDefinition;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;

import Base.BaseClass;
import PageObject.HCMSecurity;
import PageObjectManager.PageObjectManagerHCM;
import Utilities.AllureReportUtil;
import Utilities.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HCMSecurityTest {
    String URL;
    // HCMCoreHR hdp;
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
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }
    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Time Entry Clerk role")
public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Time_Entry_Clerk_role() throws IOException {
    hcmSecurity.Validate_All_Tiles();
}



    //CE Compensation Labor Relations Staff Exclude Retirees
    @Given("the user is logged into Oracle HCM as a CE Compensation Labor Relations Staff Exclude Retirees and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Compensation_Labor_Relations_Staff_Exclude_Retirees_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Compensation Labor Relations Staff Exclude Retirees role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Compensation_Labor_Relations_Staff_Exclude_Retirees_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Compensation Labor Relations Staff Exclude Retirees role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Compensation_Labor_Relations_Staff_Exclude_Retirees_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }


    //CE Payroll Staff Full Population
    @Given("the user is logged into Oracle HCM as a CE Payroll Staff Full Population and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Payroll_Staff_Full_Population_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Payroll Staff Full Population role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Payroll_Staff_Full_Population_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Payroll Staff Full Population role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Payroll_Staff_Full_Population_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Tech Support View Only Data
    @Given("the user is logged into Oracle HCM as a CE Tech Support View Only Data and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Tech_Support_View_Only_Data_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Tech Support View Only Data role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Tech_Support_View_Only_Data_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Tech Support View Only Data role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Tech_Support_View_Only_Data_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Treasury Full Population
    @Given("the user is logged into Oracle HCM as a CE Treasury Full Population and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Treasury_Full_Population_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Treasury Full Population role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Treasury_Full_Population_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Treasury Full Population role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Treasury_Full_Population_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Compensation Staff 
    @Given("the user is logged into Oracle HCM as a CE Compensation Staff and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Compensation_Staff_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Compensation Staff role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Compensation_Staff_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }
    
    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Compensation Staff role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Compensation_Staff_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }
    
    //CE HR Employment Data View Only Excl Exec Retiree LEB
    @Given("the user is logged into Oracle HCM as a CE HR Employment Data View Only Excl Exec Retiree LEB and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_HR_Employment_Data_View_Only_Excl_Exec_Retiree_LEB_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE HR Employment Data View Only Excl Exec Retiree LEB role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_HR_Employment_Data_View_Only_Excl_Exec_Retiree_LEB_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE HR Employment Data View Only Excl Exec Retiree LEB role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_HR_Employment_Data_View_Only_Excl_Exec_Retiree_LEB_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE HR Employment Data View Only Full Population
    @Given("the user is logged into Oracle HCM as a CE HR Employment Data View Only Full Population and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_HR_Employment_Data_View_Only_Full_Population_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }
    
    @When("the user reviews the Quick Action items and tiles displayed for the CE HR Employment Data View Only Full Population role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_HR_Employment_Data_View_Only_Full_Population_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE HR Employment Data View Only Full Population role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_HR_Employment_Data_View_Only_Full_Population_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE HR Director Full Population
    @Given("the user is logged into Oracle HCM as a CE HR Director Full Population and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_HR_Director_Full_Population_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE HR Director Full Population role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_HR_Director_Full_Population_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE HR Director Full Population role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_HR_Director_Full_Population_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE HR Data View Only LI Data Analytics Full Population
    @Given("the user is logged into Oracle HCM as a CE HR Data View Only LI Data Analytics Full Population and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_HR_Data_View_Only_LI_Data_Analytics_Full_Population_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE HR Data View Only LI Data Analytics Full Population role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_HR_Data_View_Only_LI_Data_Analytics_Full_Population_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE HR Data View Only LI Data Analytics Full Population role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_HR_Data_View_Only_LI_Data_Analytics_Full_Population_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }
    
    //CE HR QA Compliance Full Population
    @Given("the user is logged into Oracle HCM as a CE HR QA Compliance Full Population and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_HR_QA_Compliance_Full_Population_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE HR QA Compliance Full Population role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_HR_QA_Compliance_Full_Population_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE HR QA Compliance Full Population role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_HR_QA_Compliance_Full_Population_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //HR Production Support
    @Given("the user is logged into Oracle HCM as a HR Production Support and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_HR_Production_Support_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the HR Production Support role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_HR_Production_Support_role()throws Exception {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the HR Production Support role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_HR_Production_Support_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Benefits Staff Full Population Data
    @Given("the user is logged into Oracle HCM as a CE Benefits Staff Full Population Data and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Benefits_Staff_Full_Population_Data_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Benefits Staff Full Population Data role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Benefits_Staff_Full_Population_Data_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Benefits Staff Full Population Data role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Benefits_Staff_Full_Population_Data_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //HRA Staff
    @Given("the user is logged into Oracle HCM as a HRA Staff and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_HRA_Staff_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the HRA Staff role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_HRA_Staff_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the HRA Staff role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_HRA_Staff_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //HRA Manager
    @Given("the user is logged into Oracle HCM as a HRA Manager and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_HRA_Manager_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the HRA Manager role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_HRA_Manager_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the HRA Manager role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_HRA_Manager_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Recruitment Staff
    @Given("the user is logged into Oracle HCM as a CE Recruitment Staff and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Recruitment_Staff_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Recruitment Staff role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Recruitment_Staff_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Recruitment Staff role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Recruitment_Staff_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Recruitment Manager
    @Given("the user is logged into Oracle HCM as a CE Recruitment Manager and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Recruitment_Manager_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Recruitment Manager role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Recruitment_Manager_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Recruitment Manager role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Recruitment_Manager_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Content Librarian
    @Given("the user is logged into Oracle HCM as a CE Content Librarian and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Content_Librarian_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Content Librarian role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Content_Librarian_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Content Librarian role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Content_Librarian_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Retiree Self Service
    @Given("the user is logged into Oracle HCM as a CE Retiree Self Service and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Retiree_Self_Service_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Retiree Self Service role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Retiree_Self_Service_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Retiree Self Service role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Retiree_Self_Service_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Manager Self Service
    @Given("the user is logged into Oracle HCM as a CE Manager Self Service and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Manager_Self_Service_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Manager Self Service role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Manager_Self_Service_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Manager Self Service role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Manager_Self_Service_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Employee Self Service
    @Given("the user is logged into Oracle HCM as a CE Employee Self Service and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Employee_Self_Service_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Employee Self Service role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Employee_Self_Service_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Employee Self Service role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Employee_Self_Service_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE LI Diversity and Inclusion Exclude Retirees
    @Given("the user is logged into Oracle HCM as a CE LI Diversity and Inclusion Exclude Retirees and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_LI_Diversity_and_Inclusion_Exclude_Retirees_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE LI Diversity and Inclusion Exclude Retirees role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_LI_Diversity_and_Inclusion_Exclude_Retirees_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE LI Diversity and Inclusion Exclude Retirees role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_LI_Diversity_and_Inclusion_Exclude_Retirees_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Workers Compensation Group Excl Exec Retiree
    @Given("the user is logged into Oracle HCM as a CE Workers Compensation Group Excl Exec Retiree and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Workers_Compensation_Group_Excl_Exec_Retiree_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Workers Compensation Group Excl Exec Retiree role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Workers_Compensation_Group_Excl_Exec_Retiree_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Workers Compensation Group Excl Exec Retiree role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Workers_Compensation_Group_Excl_Exec_Retiree_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Absence Management Staff Full Population
    @Given("the user is logged into Oracle HCM as a CE Absence Management Staff Full Population and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Absence_Management_Staff_Full_Population_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Absence Management Staff Full Population role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Absence_Management_Staff_Full_Population_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Absence Management Staff Full Population role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Absence_Management_Staff_Full_Population_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE HR Resource Planning
    @Given("the user is logged into Oracle HCM as a CE HR Resource Planning and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_HR_Resource_Planning_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE HR Resource Planning role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_HR_Resource_Planning_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE HR Resource Planning role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_HR_Resource_Planning_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE HR Business Partner
    @Given("the user is logged into Oracle HCM as a CE HR Business Partner and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_HR_Business_Partner_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE HR Business Partner role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_HR_Business_Partner_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE HR Business Partner role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_HR_Business_Partner_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    //CE Absence Nurse Staff Exclude Retirees
    @Given("the user is logged into Oracle HCM as a CE Absence Nurse Staff Exclude Retirees and on Dashboard")
    public void the_user_is_logged_into_Oracle_HCM_as_a_CE_Absence_Nurse_Staff_Exclude_Retirees_and_on_Dashboard() {
        // Write code here that turns the phrase above into concrete actions
        the_user_is_logged_into_Oracle_HCM_as_a_CE_Time_Entry_Clerk_and_on_Dashboard();
    }

    @When("the user reviews the Quick Action items and tiles displayed for the CE Absence Nurse Staff Exclude Retirees role")
    public void the_user_reviews_the_Quick_Action_items_and_tiles_displayed_for_the_CE_Absence_Nurse_Staff_Exclude_Retirees_role() {
        // Tile capture/count logic removed - all validation now happens in the Then step via Validate_All_Tiles()
        }

    @Then("the user should verify that only the approved tiles and menu items are enabled for the CE Absence Nurse Staff Exclude Retirees role")
    public void the_user_should_verify_that_only_the_approved_tiles_and_menu_items_are_enabled_for_the_CE_Absence_Nurse_Staff_Exclude_Retirees_role() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        hcmSecurity.Validate_All_Tiles();
    }

    

    






}

    
