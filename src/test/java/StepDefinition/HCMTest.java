package StepDefinition;
import java.time.Duration;

import org.openqa.selenium.WebDriver;

import Base.BaseClass;
import PageObject.HCMCoreHR;
import PageObjectManager.PageObjectManagerHCM;
import Utilities.AllureReportUtil;
import Utilities.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HCMTest extends BaseClass {
    String URL;
    HCMCoreHR hdp;
    PageObjectManagerHCM pageObjectManagerHCM;

    public HCMTest() {
        // Driver is initialized by Hooks @Before. Resolve lazily in steps.
    }

    /**
     * Utility method to get the active WebDriver instance, ensuring it's not null.
     * This method can be called in step definitions to safely retrieve the driver initialized by Hooks.
     * It includes error handling to provide clear feedback if the driver is not available.
     * Scripted By: gaddem [Gadde Madhukar]
     *WebDriver instance for the current scenario execution.
     */
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
            if (this.hdp == null) {
                this.hdp = pageObjectManagerHCM.getHCMCoreHR();
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
    
    /**
     * The employee logs into Oracle HCM as an Employee using credentials from the configuration file
     * Navigates to the dashboard page, and captures a screenshot for reporting.
     * Scripted By: gaddem [Gadde Madhukar]
     */
    @Given("the employee is logging into Oracle HCM as an Employee")
    public void employee_logs_into_oracle_hcm() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        try{
            initializePageObjectsIfNeeded();
            URL=ConfigReader.getProperty("URL");
            WebDriver activeDriver = getActiveDriver();
            
            activeDriver.manage().deleteAllCookies();
            activeDriver.navigate().to(URL);
            activeDriver.manage().window().maximize();

            hdp.enterCredentials();
            AllureReportUtil.info("Navigated to Oracle HCM URL page");
            attachStepEvidence("the employee is logging into Oracle HCM as an Employee");
            hdp.openDashboardPage();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in employee_logs_into_oracle_hcm step: " + e.getMessage(), e);
        }
    }

    //HRA Manager- Update address for employee
    @When("the manager enters the new address details and submits")
    public void the_manager_enters_the_new_address_details_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try {
            hdp.navigateToContactInfoPage();
            hdp.navigateToAddressChangePage();
            hdp.clickAddressPencilEdit();
            hdp.updateAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //HRA Manager- Add Secondary Address for employee
    @When("the manager enters the secondary address details and submits")
    public void the_manager_enters_the_secondary_address_details_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try {
            hdp.navigateToContactInfoPage();
            hdp.navigateToAddressChangePage();
            hdp.addSecondaryAddress();
            hdp.addSecondaryAddressDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //HRA Manager- Enter Job Change for Employee
    @When("the manager enters the job change details and submits")
    public void the_manager_enters_the_job_change_details_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try {
            hdp.navigateToChangeAssignmentPage();
            hdp.searchEmployeeInChangeAssignment();
            hdp.changeAssignmentInfoPage();
            hdp.fillChangeAssignmentWhenAndWhy();
            hdp.fillChangeAssignmentJobDetails();
            hdp.fillChangeAssignmentSalaryDetails();
            hdp.fillChangeAssignmentSeniorityDateDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    //HRA Manager-Terminate an Employee
    @When("the manager enters the termination details and submits")
    public void the_manager_enters_the_termination_details_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToTerminateEmploymentPageHRA();
            hdp.searchEmployeeInTerminateEmploymentPage();
            hdp.fillInfoPage();
            hdp.fillTerminateWhenAndWhyPage();
            hdp.fillWorkRelationshipTerminationInfoPage();
            hdp.fillCommentsAndAttachmentsPage();
            hdp.submitTerminatePage();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //HRA Manager- Modify Existing Emergency Contact for Employee
    @When("the manager modifies the existing emergency contact details and submits")
    public void the_manager_modifies_the_existing_emergency_contact_details_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToModifyExistingEmergencyContactPageHRA();
            hdp.navigateToEditPhoneDetails();
            hdp.editEmergencyContactPhoneDetails();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //HRA Manager-  Edit Employee Phone Number
    @When("the manager edits the employee phone number and submits")
    public void the_manager_edits_the_employee_phone_number_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToContactInfoPage();
            hdp.navigateToAddressChangePage();
            hdp.clickPhonePencilEditAndChangeNumber();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //HRA Manager- Change Marital Status
    @When("the manager changes the marital status for an employee and submits")
    public void the_manager_changes_the_marital_status_for_an_employee_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToPersonalDetailsPageHRA();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Employee- Manage Contact Information - add email address
    @When("the employee adds a new email address and submits")
    public void the_employee_adds_a_new_email_address_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToAddEmailDetailsPage();
            hdp.enterEmailDetails();
            hdp.validateAddedEmailDetails();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Employee- View Organizational Chart under directory
    @When("the employee views the organizational chart under directory")
    public void the_employee_views_the_organizational_chart_under_directory() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToViewOrganizationalChart();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Employee- Add driving licenses
    @When("the employee adds a new driving license and submits")
    public void the_employee_adds_a_new_driving_license_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToAddDrivingLicensePage();
            hdp.upload_files();
            hdp.enterDrivingLicenseDetailsAndSubmit();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //HRA Manager- Correct  Biographical Info of an Employee
    @When("the manager corrects the biographical information of an employee and submits")
    public void the_manager_corrects_the_biographical_information_of_an_employee_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToPersonSpotlightPageHRA();
            hdp.navigateToEditBiographicalInfoPageHRA();
            hdp.editBiographicalInfoAndSubmit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //HRA Manager- Change working hours for an Employee
    @When("the manager changes the working hours for an employee and submits")
    public void the_manager_changes_the_working_hours_for_an_employee_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToPersonSpotlightPageHRA();
            hdp.navigateToChangeWorkingHoursPageHRA();
            hdp.submitWorkingHoursChangeRequestHRA("23/July/2026","Working Hours Change","Standard Hours");
            hdp.WorkingHoursChangeRequestSubmissionHRA();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //HRA Manager- Add a Contingent Worker
    @When("the manager adds a new contingent worker and submits")
    public void the_manager_adds_a_new_contingent_worker_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToAddContingentWorkerPageHRA();
            hdp.fillInfoToIncludePageHRA();
            hdp.fillWhenAndWhyPageHRA();
            hdp.fillPersonalDetailsPageHRA();
            hdp.fillNationalIdentifierDetailsHRA();
            hdp.fillCommunicationInfoDetailsHRA();
            hdp.fillEmailDetailsHRA();
            hdp.fillAssignmentDetailsHRA();
            hdp.fillInCommentsAndAttachmentsHRA();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //HRA Manager- Terminate a Contingent Worker
    @When("the manager terminates a contingent worker and submits")
    public void the_manager_terminates_a_contingent_worker_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToPersonSpotlightPageHRA();
            hdp.navigateToTerminateContingentWorkerPageHRA();
            hdp.fillInfoPage();
            hdp.fillTerminateWhenAndWhyPage();
            hdp.fillWorkRelationshipTerminationInfoPage();
            hdp.fillCommentsAndAttachmentsPage();
            hdp.submitTerminatePage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //HRA Manager- Rehiring a Terminated Employee
    @When("the manager rehires a terminated employee and submits")
    public void the_manager_rehires_a_terminated_employee_and_submits() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToEmploymentInfoPage();
            hdp.navigateToRehireTerminatedEmployeePageHRA();
            hdp.fillInfoToIncludePageRehire();
            hdp.fillWhenAndWhyWorker();
            hdp.fillWhenAndWhyPageHRA();
            hdp.fillPhoneDetailsRehire();
            hdp.fillEmailDetailsRehire();
            hdp.fillAddressDetailsRehire();
            hdp.fillAssignmentDetailsRehire();
            hdp.fillPayrollDetailsRehire();
            hdp.fillSalaryDetailsRehire();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

//hemas

    @Given("is on the Document Records pages")
    public void is_on_the_Document_Records_pages() throws Exception {
        Thread.sleep(5000);
        hdp.navigateToDocumentRecords();
        Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
        
    }
    @When("the employee views or downloads their document records")
    public void the_employee_views_or_downloads_their_document_records() throws InterruptedException {
       hdp.validateDocumentRecordDetails();
    //    AllureReportUtil.info("Viewed or downloaded document records");
    //         attachStepEvidence("the employee has viewed or downloaded their document records");
            hdp.closeDocumentRecordDetails();
           

   

    }

    // Scenario: ESS - My Activity Center
    
    @Given("is on the My Activity Center page")
    public void is_on_the_My_Activity_Center_page() throws Exception {
        hdp.navigateToMyActivityCenter();
        hdp.validateMyActivityCenterDetails();
        //  AllureReportUtil.info("Validated Team Activity Center page");
        //     attachStepEvidence("the manager views and manages team activities");
    }
   
    
    @When("the employee views and manages their activities")
    public void the_employee_views_and_manages_their_activities() throws InterruptedException {
        // hdp.validateMyActivityCenterPage();
        hdp.navigateToAndValidateJourneys();
        //  AllureReportUtil.info("Validating the Journeys page in My Activity Center");
        // attachStepEvidence("Validating the Journeys page in My Activity Center");
        hdp.navigateToAndValidateBenefits();
        //  AllureReportUtil.info("Validating the Benefits page in My Activity Center");
        // attachStepEvidence("Validating the Benefits page in My Activity Center");
        // hdp.navigateToAndValidateCurrentJob();
        //  AllureReportUtil.info("Validating the Current Job page in My Activity Center");
        // attachStepEvidence("Validating the Current Job page in My Activity Center");
        hdp.navigateToAndValidatePersonalDetails();
        //  AllureReportUtil.info("Validating the Personal Details page in My Activity Center");
        // attachStepEvidence("Validating the Personal Details page in My Activity Center");
        hdp.navigateToAndValidateAdditionalPersonInfo();
        //  AllureReportUtil.info("Validating the Additional Person Info page in My Activity Center");
        // attachStepEvidence("Validating the Additional Person Info page in My Activity Center");
        hdp.navigateToAndValidatePersonalIdentifierForExternalApplications();
        //  AllureReportUtil.info("Validating the Personal Identifier for External Applications page in My Activity Center");
        // attachStepEvidence("Validating the Personal Identifier for External Applications page in My Activity Center");
        hdp.navigateToAndValidateContactInfo();
        //  AllureReportUtil.info("Validating the Contact Info page in My Activity Center");
        // attachStepEvidence("Validating the Contact Info page in My Activity Center");
        hdp.navigateToAndValidateFamilyAndEmergencyContacts();
        //  AllureReportUtil.info("Validating the Family and Emergency Contacts page in My Activity Center");
        // attachStepEvidence("Validating the Family and Emergency Contacts page in My Activity Center");
        hdp.navigateToAndValidateDocumentRecords();
        //  AllureReportUtil.info("Validating the Document Records page in My Activity Center");
        // attachStepEvidence("Validating the Document Records page in My Activity Center");
        hdp.validateViewMoreAndListTimelineView();
        //  AllureReportUtil.info("Validating the View More and List Timeline View in My Activity Center");
        // attachStepEvidence("Validating the View More and List Timeline View in My Activity Center");
        // hdp.validateChangeSalaryOption();
        // hdp.clickGoBackButton();
    }


     //1279848 -Scenario:ESS - Name Change - Submit
    @Given("is on the Name Change page")
    public void is_on_the_Name_Change_page() throws InterruptedException {
        hdp.navigateToNameChange();
        //  AllureReportUtil.info("Navigated to Name Change page");
        //     attachStepEvidence("the employee is in the Name Change page");
    }

    @When("the employee submits a name change request with valid name change details")
    public void the_employee_submits_a_name_change_request_with_valid_name_change_details() throws Exception {
         hdp.enterNameChangeDetails("test");
        //  AllureReportUtil.info("Submitted name change request with valid details");
        //     attachStepEvidence("the employee has submitted a name change request with valid details");
        // hdp.validateNameChange();
    }

    // ESS - Name Change - Validate

    @Given("the user opens Personal Details")
    public void the_user_opens_Personal_Details() throws Exception {
          hdp.navigateToPersonalInfo();
        
        // hdp.navigateToPersonalDetails();
       
        // hdp.navigateToNameChange();
    }

     @When("the updated name is displayed")
    public void the_updated_name_is_displayed() throws Exception {
        //  hdp.enterNameChangeDetails("test");
        
        hdp.validateNameChange();
        
    }

    
    //@HRA_Name_Change_Approve_Submit

@Given("the manager opens the transfer from the worklist")
    public void the_manager_opens_the_transfer_from_the_worklist() throws Exception {
           hdp.navigateToWorklist();
        AllureReportUtil.info("Navigated to Worklist to open transfer request");
        attachStepEvidence("the manager has navigated to Worklist to open transfer request");
    }
   

    @When("approves or rejects the transfer")
    public void approves_or_rejects_the_transfer() throws Exception {
        Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
      hdp.approvingNameChange();
      hdp.aproveNameChangeForRequest();

    //    AllureReportUtil.info("Approved the name change request from worklist");
    //      attachStepEvidence("the manager has approved the name change request from worklist");
    //       // Consider replacing with explicit wait for better reliability
    }


    


    //MSS-Transfer-Approve_or_Deny
 @Given("the manager opens the transfer from the worklist MSS")
    public void the_manager_opens_the_transfer_from_the_worklist_MSS() throws Exception {
         hdp.navigateToWorklist();
        AllureReportUtil.info("Navigated to Worklist to open transfer request");
        attachStepEvidence("the manager has navigated to Worklist to open transfer request");
     }

     @When("approves or rejects the transfer MSS")
    public void approves_or_rejects_the_transfer_MSS() throws Exception {
       hdp.approvingNameChangeMss();
      
    hdp.aproveMSSForRequest();
    }


     //HRA-Promote-Initiate
     @Given("User searches for the employee and selects Promote")
    public void User_searches_for_the_employee_and_selects_Promote() throws Exception {
// hdp.navigateToPromotePage("0031810");
    //  hdp.navigateToPromotePage("0029259");0040175
     hdp.navigateToPromotePage("0007875");

            // AllureReportUtil.info("Searched for the employee and selected Promote action");
            //     attachStepEvidence("User has searched for the employee and selected Promote action");



    hdp.selectSalaryToggleButton();
// AllureReportUtil.info("Selected Salary toggle for promotion");
// attachStepEvidence("User has selected Salary toggle for promotion");
 
hdp.verifyWhenAndWhyPageDetailsAndContinue();
// AllureReportUtil.info("Verified details on When and Why page and continued with promotion process");
// attachStepEvidence("User has verified details on When and Why page and continued with promotion process");
        
    }

    @When("User enters promotion details and submits")
    public void User_enters_promotion_details_and_submits() throws Exception {
        hdp.verifyAssignmentPageDetailsAndContinue();
// AllureReportUtil.info("Verified details on Assignment page and continued with promotion process");
// attachStepEvidence("User has verified details on Assignment page and continued with promotion process");

hdp.salaryPageDetailsAndContinue();
    // AllureReportUtil.info("Entered details on Salary page and continued with promotion process");
    // attachStepEvidence("User has entered details on Salary page and continued with promotion process");

hdp.verifySeniorityDatesAndSubmit();
// AllureReportUtil.info("Verified seniority dates and submitted promotion request");
// attachStepEvidence("User has verified seniority dates and submitted promotion request");
       
    }


    //HRA-Non-Worker-Surviving_Spouse-Add
@Given("User searches employee and selects Add Non-Worker")
    public void User_searches_employee_and_selects_Add_Non_Worker() throws Exception {
       
       hdp.navigateToAddNonWorker();
    //    AllureReportUtil.info("Navigated to Add Non-Worker page");
    //    attachStepEvidence("the manager is on the Add Non-Worker page");
       hdp.selectToggleButtonsForNonWorker();
    //    AllureReportUtil.info("Selected toggle buttons for Non-Worker"); 
    //      attachStepEvidence("Selected toggle buttons for Non-Worker");
         hdp.verifyWhenAndWhyPageDetailsForNonWorkerAndContinue();
        //  AllureReportUtil.info("Verified details on When and Why page for Non-Worker and continued to next step");
        //  attachStepEvidence("Verified details on When and Why page for Non-Worker and continued to next step");
    }

    @When("User enters required details and submits")
    public void User_enters_required_details_and_submits() throws Exception {
        hdp.verifyPersonalDetailsPageAndContinue();
        hdp.verifyCommunicationInfoPageAndContinue();
        // AllureReportUtil.info("Verified Personal Details page for Non-Worker and continued to next step");
        // attachStepEvidence("Verified Personal Details page for Non-Worker and continued to next step");
        hdp.verifyAddressesPageAndContinue();
        // AllureReportUtil.info("Verified Addresses page for Non-Worker and continued to next step");
        // attachStepEvidence("Verified Addresses page for Non-Worker and continued to next step");
        hdp.verifyAssignmentPageDetailsForNonWorkerAndContinue();
        // AllureReportUtil.info("Verified Assignment page details for Non-Worker and continued to next step");
        // attachStepEvidence("Verified Assignment page details for Non-Worker and continued to next step");
        hdp.verifyWorkRelationshipInfoPageDetailsForNonWorkerAndContinue();
        // AllureReportUtil.info("Verified Work Relationship Info page details for Non-Worker and continued to next step");
        // attachStepEvidence("Verified Work Relationship Info page details for Non-Worker and continued to next step");
        hdp.verifyPayrollDetailsPageForNonWorkerAndContinue();
        // AllureReportUtil.info("Verified Payroll Details page for Non-Worker and continued to next step");
        // attachStepEvidence("Verified Payroll Details page for Non-Worker and continued to next step");
        hdp.verifySalaryPageForNonWorkerAndSubmit();
        // AllureReportUtil.info("Verified Salary page for Non-Worker and submitted the form");
        // attachStepEvidence("Verified Salary page for Non-Worker and submitted the form");
        hdp.PersonIdentifiersForNonWorkerSpouseAdd("0019187");
    }




    // @1279854  Scenario:HRA - Change Assignment - Management to Union

@Given("I change the assignment for a management employee with reason {string}")
    public void I_change_the_assignment_for_a_management_employee_with_reason(String s) throws Exception{
        hdp.verifyEmploymentAndNavigateToChangeAssignment();
            // AllureReportUtil.info("Navigated to Change Assignment page for management employee");
            //     attachStepEvidence("the manager has navigated to Change Assignment page for management employee");
                hdp.clickEmployee("0085243");
                // AllureReportUtil.info("Clicked on Employee link to view assignment details");
                // attachStepEvidence("the manager has clicked on Employee link to view assignment details");
                // hdp.SelectWayToChangeAssignment();
                    // AllureReportUtil.info("Selected way to change assignment");
                    // attachStepEvidence("the manager has selected way to change assignment");
                    hdp.selectSalaryToggle();
                        // AllureReportUtil.info("Selected Salary toggle for assignment change");
                        // attachStepEvidence("the manager has selected Salary toggle for assignment change");
                       
    }

    @When("the assignment is updated successfully")
    public void the_assignment_is_updated_successfully() throws Exception {
        System.out.println("Inside step definition for assignment change update");
        hdp.firstPage();
        // AllureReportUtil.info("Navigated to first page of assignment change process");
        // attachStepEvidence("the manager has navigated to first page of assignment change process");
        hdp.verifyAndEnterSecondPageDetails();
        // AllureReportUtil.info("Verified and entered details on second page of assignment change process");
        // attachStepEvidence("the manager has verified and entered details on second page of assignment change process");
        hdp.verifyThirdPageDetailsAndSubmit();
        // AllureReportUtil.info("Verified details on third page and submitted assignment change request");
        // attachStepEvidence("the manager has verified details on third page and submitted assignment change request");

        // Write code here that turns the phrase above into concrete actions
    }

@Then("Delete the Created record")
    public void Delete_the_Created_record() throws Exception {
        hdp.deletePersonalRecord();
    }

// MSS - Manager or Supervisor- Change
    @Given("User changes manager for an employee")
    public void user_changes_manager_for_an_employee() throws Exception{
        Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
        hdp.navigateToChangeManager();
        Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
            // AllureReportUtil.info("Navigated to Change Manager page");
            // attachStepEvidence("User has navigated to Change Manager page");        
        //  hdp.clickEmployeeMSS("0084051");
         hdp.clickEmployeeMSS("0022628");
         
        // hdp.clickEmployeeMSS("0023780");
        // Consider replacing with explicit wait for better reliability
        // AllureReportUtil.info("Selected employee for manager change");
        // attachStepEvidence("User has selected employee for manager change");
        // hdp.clickOnTheEmployee();
            // AllureReportUtil.info("Clicked on the employee to change manager");

            // attachStepEvidence("User has clicked on the employee to change manager");
            hdp.toggleDirectReportButton();
            // Consider replacing with explicit wait for better reliability
            // AllureReportUtil.info("Toggled Direct Report button");
            // attachStepEvidence("User has toggled Direct Report button");
    }
    @When("Manager change request is submitted successfully")
    public void Manager_change_request_is_submitted_successfully() throws Exception {
        System.out.println("Inside step definition for manager change request submission");
        hdp.verifyFirstPageDetailsAndContinue();
        // AllureReportUtil.info("Verified details on first page and continued with manager change process");
        // attachStepEvidence("User has verified details on first page and continued with manager change process");
        hdp.changeManagerAndSubmit();
        // AllureReportUtil.info("Changed manager and submitted manager change request");
        // attachStepEvidence("User has changed manager and submitted manager change request");
        
    }

    //Madhus
    //Scenario: ESS - Family and Emergency Contacts
    /**
     * The employee navigates to the Family and Emergency Contacts page, 
       initiates adding a new contact, and captures screenshots for reporting.
     * Scripted By: gaddem [Gadde Madhukar]
     */
       @Given("is on the Family and Emergency Contacts page")
       public void navigate_to_family_and_emergency_contacts_page() throws Exception {
           try{
               hdp.navigateAndValidateFamilyAndEmergencyContacts();
               hdp.clickAddIcon();
               Thread.sleep(3000); // Consider replacing with a more robust wait strategy
               AllureReportUtil.info("Navigated to Family and Emergency Contacts page");
               attachStepEvidence("is on the Family and Emergency Contacts page");
               Thread.sleep(2000); // Consider replacing with a more robust wait strategy
               hdp.clickContinueButton();
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in navigate_to_family_and_emergency_contacts_page step: " + e.getMessage(), e);
           }
       }
   
       /**
        * The employee adds a new family contact by entering valid personal details, relationship information, 
          emergency details, address details, and national identifier details. 
          Each section of the form is filled out using data from Excel, and screenshots are captured 
          after each major step for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @When("the employee adds a new family contact with valid personal details, relationship information, emergency details, address details, and national identifier details")
       public void the_employee_adds_a_new_family_contact_with_valid_personal_details_relationship_information_emergency_details_address_details_and_national_identifier_details() {
           // Write code here that turns the phrase above into concrete actions
            try{
               //Under Basic Info --> Global Name 
               hdp.enterBasicInfo();
               AllureReportUtil.info("Entered basic info for family contact");
               attachStepEvidence("Entered basic info for family contact");
   
               //Under Basic Info --> Relationship
               hdp.enterRelationshipInfo();    
               AllureReportUtil.info("Entered relationship details for family contact");
               attachStepEvidence("Entered relationship details for family contact");
   
               //Under :Basic Info -->Phone Details 
               hdp.enterPhoneDetails();
               AllureReportUtil.info("Entered phone details for family contact");
               attachStepEvidence("Entered phone details for family contact");
   
               //Basic Info --> Email Details
               hdp.enterEmailDetails();
               AllureReportUtil.info("Entered email details for family contact");
               attachStepEvidence("Entered email details for family contact");
   
               //Under Basic Info --> Address
               hdp.enterAddressDetails();
               AllureReportUtil.info("Entered address details for family contact");
               attachStepEvidence("Entered address details for family contact");
   
               //Basic Info --> National Identifiers
               
               hdp.enterNationalIdentifiers();
               AllureReportUtil.info("Added National Identifiers for family contact");
               attachStepEvidence("Added National Identifiers for family contact");
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in add_new_family_contact step: " + e.getMessage(), e);
           }
       }
   
       /**
        * The employee submits the Family and Emergency Contact form after adding a new contact, 
          and captures a screenshot for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @When("Click on the Submit Button-Family and Emergency Contact")
       public void submit_family_and_emergency_contact_form() throws Exception {
           try{
               hdp.submitFamilyAndEmergencyContactForm();
               AllureReportUtil.info("Submitted Family and Emergency Contact form");
               attachStepEvidence("Click on the Submit Button-Family and Emergency Contact");
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in submit_family_and_emergency_contact_form step: " + e.getMessage(), e);
           }
       }
   
       /**
        * The employee adds a coworker as a contact by entering valid details such as 
          relationship start date, coworker name, relationship, and emergency contact notes. 
          Data is retrieved from Excel, and the method handles dropdown selections, date pickers, and 
          search functionality as needed.Screenshots are captured for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @When("the employee adds a coworker as contact with valid details")
       public void the_employee_adds_a_coworker_as_contact_with_valid_details() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           try{
               hdp.clickAddIcon();
               Thread.sleep(2000); // Consider replacing with a more robust wait strategy
               hdp.selectCoworkerOption();
               Thread.sleep(2000); // Consider replacing with a more robust wait strategy
               attachStepEvidence("Clicked Add icon to add coworker as contact");
               Thread.sleep(2000); // Consider replacing with a more robust wait strategy
               hdp.clickContinueButton();
               hdp.enterCoworkerDetails();
               AllureReportUtil.info("Added coworker as contact");
               attachStepEvidence("the employee adds a coworker as as contact with valid details");
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in the_employee_adds_a_coworker_as_contact_with_valid_details step: " + e.getMessage(), e);
           }
       }
       
       /**
        * The employee submits the Family and Emergency Contact form after adding a coworker as a 
          contact, and captures a screenshot for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @When("Clicks on the Submit Button")
       public void Clicks_on_the_Submit_Button() {
           // Write code here that turns the phrase above into concrete actions
           try{
               hdp.submitFamilyAndEmergencyContactForm();
               AllureReportUtil.info("Clicked Submit button");
               attachStepEvidence("Click on the Submit Button");
               Thread.sleep(5000); // Consider replacing with a more robust wait strategy
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in Clicks_on_the_Submit_Button step: " + e.getMessage(), e);
           }
       }
   
       /**
        * The employee clicks on the Employee image and then clicks on the Signout link to log out of 
          Oracle HCM.Screenshots are captured for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @Then("Clicks on Employee image and Clicks on Signout link")
       public void Clicks_on_Employee_image_and_Clicks_on_Signout_link() {
           // Write code here that turns the phrase above into concrete actions
           try{ 
               Thread.sleep(2000); // Consider replacing with a more robust wait strategy
               hdp.signOut();
               AllureReportUtil.info("Signing out from Oracle HCM");
               // attachStepEvidence("Clicks on Employee image and Clicks on Signout link");
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in Clicks_on_Employee_image_and_Clicks_on_Signout_link step: " + e.getMessage(), e);
           }
       }
   
       //Scenario: HRA - Family and Emergency Contacts
       /**
        * The manager navigates to the HRA Family and Emergency Contacts page for a direct report, 
          and captures a screenshot for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @Given("is on the HRA Family and Emergency Contacts page")
       public void is_on_the_HRA_Family_and_Emergency_Contacts_page() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           try{
               hdp.navigateToFamilyAndEmergencyContacts("0031821");
               // AllureReportUtil.info("Navigated to HRA Family and Emergency Contacts page");
               // attachStepEvidence("is on the HRA Family and Emergency Contacts page");
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in is_on_the_HRA_Family_and_Emergency_Contacts_page step: " + e.getMessage(), e);
           }
       }
   
       /**
        * The manager adds a new family contact for a direct report by entering valid personal details, relationship information, 
          emergency details, address details, and national identifier details. Each section of the form is filled out using data from Excel, 
          and screenshots are captured after each major step for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @When("the manager adds a new family contact for a direct report with valid personal details, relationship information, emergency details, address details, and national identifier details")
       public void the_manager_adds_a_new_family_contact_for_a_direct_report_with_valid_personal_details_relationship_information_emergency_details_address_details_and_national_identifier_details() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           try{
               AllureReportUtil.info("Adding new family contact for direct report with valid details");
               hdp.clickAddIcon();
               AllureReportUtil.info("Clicked Add icon to add family contact for direct report");
               Thread.sleep(2000); // Consider replacing with a more robust wait strategy
               attachStepEvidence("Clicked Continue button to proceed with adding family contact");
               Thread.sleep(2000); // Consider replacing with a more robust wait strategy
               hdp.clickContinueButton();
               AllureReportUtil.info("Clicked Continue button to proceed with adding family contact");
           
               //Under Basic Info --> Global Name 
               hdp.enterBasicInfo();
               AllureReportUtil.info("Entered basic info for family contact for direct report");
               attachStepEvidence("Entered basic info for family contact for direct report");
   
               //Under Basic Info --> Relationship
               hdp.enterRelationshipInfo();    
               AllureReportUtil.info("Entered relationship details for family contact");
               attachStepEvidence("Entered relationship details for family contact");
   
               //Under :Basic Info -->Phone Details 
               hdp.enterPhoneDetails();
               AllureReportUtil.info("Entered phone details for family contact");
               attachStepEvidence("Entered phone details for family contact");
   
               //Basic Info --> Email Details
               hdp.enterEmailDetails();
               AllureReportUtil.info("Entered email details for family contact");
               attachStepEvidence("Entered email details for family contact");
   
               //Under Basic Info --> Address
               hdp.enterAddressDetails();
               AllureReportUtil.info("Entered address details for family contact");
               attachStepEvidence("Entered address details for family contact");
   
               //Basic Info --> National Identifiers
               hdp.enterNationalIdentifiers();
               AllureReportUtil.info("Added National Identifiers for family contact");
               attachStepEvidence("Added National Identifiers for family contact");
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in the_manager_adds_a_new_family_contact_for_a_direct_report step: " + e.getMessage(), e);
           } 
       }
   
       /**
        * The manager adds a coworker as a contact for a direct report by entering valid details such as relationship start date, coworker name, relationship, and emergency contact notes. 
          Data is retrieved from Excel, and the method handles dropdown selections, date pickers, and search functionality as needed.Screenshots are captured for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @When("the manager adds a coworker as contact for a direct report with valid details")
       public void the_manager_adds_a_coworker_as_contact_for_a_direct_report_with_valid_details() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           try{
               hdp.clickAddIcon();
               hdp.selectCoworkerOptionHRAdmin();
               Thread.sleep(3000); // Consider replacing with a more robust wait strategy
               attachStepEvidence("Clicked Add icon to add coworker as contact for direct report");
               Thread.sleep(2000); // Consider replacing with a more robust wait strategy
               hdp.clickContinueButton();
               hdp.enterHRACoworkerDetails();
               AllureReportUtil.info("Added coworker as contact");
               attachStepEvidence("the employee adds a coworker as as contact with valid details");
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in the_manager_adds_a_coworker_as_contact_for_a_direct_report_with_valid_details step: " + e.getMessage(), e);
           }
       }
   
       
       // Scenario: ESS - Compensation - View My Compensation
       @Given("is on the View My Compensation page")
       public void is_on_the_View_My_Compensation_page() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           // AllureReportUtil.info("Navigating to View My Compensation page");
           hdp.navigateToCompensation();
           AllureReportUtil.info("Navigated to View My Compensation page");
           // attachStepEvidence("is on the View My Compensation page");
       }
   
       @When("the employee views compensation details")
       public void the_employee_views_compensation_details() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           // AllureReportUtil.info("Validating compensation details");
           hdp.validateCompensationDetails();
           // AllureReportUtil.info("Validated compensation details");
           // attachStepEvidence("the employee views compensation details");
       }
   
       
   
   
       // Scenario: ESS - Retirement or Journey
       @Given("is on the Retirement or Resignation page")
       public void is_on_the_Retirement_or_Resignation_page() {
           // Write code here that turns the phrase above into concrete actions
           // AllureReportUtil.info("Navigating to Retirement or Resignation page");
           hdp.navigateToResignationRetirement();
           AllureReportUtil.info("Navigated to Retirement or Resignation page");
           // attachStepEvidence("is on the Retirement or Resignation page");
       }
   
       @When("the employee submits retirement details with valid retirement date, action, and reason")
       public void the_employee_submits_retirement_details_with_valid_retirement_date_action_and_reason() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           // String ResignationNotificationDate = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,"1279871","Resignation Notification Date");
           // String ResignationRetirementDate = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,"1279871","Resignation Retirement Date");
   
           // AllureReportUtil.info("Entering retirement details with valid date, action, and reason");
           hdp.enterResignationRetirementDetails("23/July/2026");
           hdp.clickContinueOnResignationRetirement();
           hdp.submitResignationRetirement();
           // AllureReportUtil.info("Submitted retirement details");
           // attachStepEvidence("the employee submits retirement details with valid retirement date, action, and reason");
       }
   
   
       // Test CaseID: 1279873	ESS - Resignation or Journey
       @When("the employee submits resignation details with valid resignation date, action, and reason")
       public void the_employee_submits_resignation_details_with_valid_resignation_date_action_and_reason() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           // AllureReportUtil.info("Entering resignation details with valid date, action, and reason");
           hdp.resignFromEmployment("23/July/2026");
           // AllureReportUtil.info("Submitted resignation details");
           // attachStepEvidence("the employee submits resignation details with valid resignation date, action, and reason");
       }
   
       //Test CaseID: 1279847 MSS - Activity Center
       /**
        * The manager logs into Oracle HCM as a Manager using credentials from the configuration file, 
          navigates to the dashboard page, and captures a screenshot for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @Given("the manager is logging into Oracle HCM as a Manager")
       public void the_manager_is_logging_into_Oracle_HCM_as_a_Manager() {
           // Write code here that turns the phrase above into concrete actions
           try{
               initializePageObjectsIfNeeded();
           
               URL=ConfigReader.getProperty("URL");
               WebDriver activeDriver = getActiveDriver();
               
               activeDriver.manage().deleteAllCookies();
               activeDriver.navigate().to(URL);
               activeDriver.manage().window().maximize();
   
               hdp.enterCredentials();
               AllureReportUtil.info("Logging into Oracle HCM as a Manager");
               attachStepEvidence("the manager is logging into Oracle HCM as a Manager");
               hdp.openDashboardPage(); 
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in the_manager_is_logging_into_Oracle_HCM_as_a_Manager step: " + e.getMessage(), e);
           }
       }
   
       /**
        * The manager navigates to the Activity Center page, validates that the page is displayed correctly, 
          and captures screenshots for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @Given("is on the Activity Center page")
       public void is_on_the_Activity_Center_page() throws InterruptedException {
           // Write code here that turns the phrase above into concrete actions
           try{
               hdp.navigateToTeamActivityCenter();
               AllureReportUtil.info("Navigated to Activity Center page");
               
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in is_on_the_Activity_Center_page step: " + e.getMessage(), e);
           }
       }
   
       /**
        * The manager views and manages team activities by validating that the Team Activity Center page is displayed correctly, 
          and captures screenshots for reporting.
        * Scripted By: gaddem [Gadde Madhukar]
        */
       @When("the manager views and manages team activities")
       public void the_manager_views_and_manages_team_activities() throws InterruptedException {
           // Write code here that turns the phrase above into concrete actions
           try{
               hdp.validateTeamActivityCenterPage();
               AllureReportUtil.info("Validated Team Activity Center page");
               attachStepEvidence("the manager views and manages team activities");
           }catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("Error in the_manager_views_and_manages_team_activities step: " + e.getMessage(), e);
           }
       }
   
       //Test CaseID: 1279853	MSS - Location Change
       @Given("is on the Location Change page")
       public void is_on_the_Location_Change_page() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           // AllureReportUtil.info("Navigating to Location Change page");
           hdp.navigateToLocationChangePage();
           Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
           AllureReportUtil.info("Navigated to Location Change page");
           // attachStepEvidence("is on the Location Change page");
       }

       @When("the manager submits a location change request for a direct report with valid details")
       public void the_manager_submits_a_location_change_request_for_a_direct_report_with_valid_details() throws Exception {
           // Write code here that turns the phrase above into concrete actions
           // AllureReportUtil.info("Submitting location change request for direct report with valid details");
           hdp.submitLocationChangeRequest("23/July/2026");
           // AllureReportUtil.info("Submitting location change request for direct report with valid details");
           // hdp.submitLocationChangeRequest("22/May/2026");
           Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
           AllureReportUtil.info("Submitted location change request for direct report with valid details");
           // attachStepEvidence("the manager submits a location change request for a direct report with valid details");
   
       }

       //Test CaseID: 1279856 HRA Manager - Assignment Related Change Delete
    /**
     * The manager logs into Oracle HCM as a HRA using credentials from the Excel file, 
       navigates to the dashboard page, and captures a screenshot for reporting.
     * Scripted By: gaddem [Gadde Madhukar]
     */
    @Given("the manager is logging into Oracle HCM as a HRA")
    public void the_manager_is_logging_into_Oracle_HCM_as_a_HRA() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        try{
            initializePageObjectsIfNeeded();
            
            URL=ConfigReader.getProperty("URL");
            WebDriver activeDriver = getActiveDriver();
            
            activeDriver.manage().deleteAllCookies();
            activeDriver.navigate().to(URL);
            activeDriver.manage().window().maximize();

            hdp.enterCredentials();
            AllureReportUtil.info("Logging into Oracle HCM as a HRA");
            attachStepEvidence("the manager is logging into Oracle HCM as a HRA");
            hdp.openDashboardPage();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in the_manager_is_logging_into_Oracle_HCM_as_a_HRA step: " + e.getMessage(), e);
        }
    }

    /**
     * The manager navigates to the Historical Assignment Related Change page for a direct report, 
       and captures a screenshot for reporting.
     * Scripted By: gaddem [Gadde Madhukar]
     */
    @Given("is on the Historical Assignment Related Change page")
    public void is_on_the_Historical_Assignment_Related_Change_page() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.navigateToEmploymentInfoPage();
            // attachStepEvidence("the manager clicks on Employment info option under My Client Groups");
            hdp.navigateToHistoricalChangePage();
            // attachStepEvidence("the manager is on the Historical Assignment Related Change page");
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in is_on_the_Historical_Assignment_Related_Change_page step: " + e.getMessage(), e);
        }
        
    }
    
    @When("the manager submits a deletion for a direct report with valid details")
    public void the_manager_submits_a_deletion_for_a_direct_report_with_valid_details() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        hdp.navigateToHistoricalAssignmentCorrectPage();
        hdp.deleteHistoricalAssignmentChange();
        // AllureReportUtil.info("Submitted deletion for direct report with valid details");
        // attachStepEvidence("the manager has submitted a deletion for a direct report with valid details");
        
    }

    //Test CaseID: 1279855 HRA Manager - Assignment Related Change Correct
    @When("the manager submits a correction for a direct report with valid details")
    public void the_manager_submits_a_correction_for_a_direct_report_with_valid_details() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        hdp.navigateToHistoricalAssignmentCorrectPage();
        // AllureReportUtil.info("Navigated to Historical Assignment Change Correction page");
        // attachStepEvidence("the manager is on the Historical Assignment Change Correction page");
        hdp.correctHistoricalAssignmentChange("27/April/2026","Assignment Change","Department Change","250000");
        // attachStepEvidence("the manager has submitted a correction for a direct report with valid details");
    }

    
    //1279851-->HRA - Working Hours - Change
    @Given("is on the Working Hours Change page")
    public void is_on_the_Working_Hours_Change_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        hdp.navigateToChangeWorkingHoursPage();
        // AllureReportUtil.info("Navigated to Working Hours Change page");
        hdp.searchEmployeeForWorkingHoursChange();
        // attachStepEvidence("the manager is on the Working Hours Change page");
    }

    @When("the manager submits a working hours change request for a direct report with valid details")
    public void the_manager_submits_a_working_hours_change_request_for_a_direct_report_with_valid_details() {
        // Write code here that turns the phrase above into concrete actions
        try{
            hdp.submitWorkingHoursChangeRequest("23/July/2026","Working Hours Change","Standard Hours");
            // attachStepEvidence("the manager submits a working hours change request for a direct report with valid details");
            // AllureReportUtil.info("Submitted working hours change request for direct report with valid details");
            hdp.WorkingHoursChangeRequestSubmission();
            // attachStepEvidence("the working hours change request has been submitted successfully");
            hdp.validateWorkingHoursChangeRequest();
         } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in validating working hours change request: " + e.getMessage(), e);
        }
    }

     //MSS - Enter Employee Retirement
     @Given("is on the Terminate Employment page")
     public void is_on_the_Terminate_Employment_page() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToTerminateEmploymentPage();
         hdp.searchEmployeeInTerminateEmploymentPage();
         AllureReportUtil.info("Navigated to Terminate Employment page");
     }
 
     @When("the manager submits retirement details for a direct report with valid retirement date, action, and reason")
     public void the_manager_submits_retirement_details_for_a_direct_report_with_valid_retirement_date_action_and_reason() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillInfoPage();
         hdp.fillTerminateWhenAndWhyPage();
         hdp.fillWorkRelationshipTerminationInfoPage();
         hdp.fillCommentsAndAttachmentsPage();
         hdp.submitTerminatePage();
         AllureReportUtil.info("Submitted retirement details for a direct report with valid retirement date, action, and reason");
     }
 
     
     //HRA - Direct Reports - Change
     @Given("is on the Direct Reports page")
     public void is_on_the_Direct_Reports_page() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
         hdp.navigateToDirectReportsPage();
         hdp.searchEmployeeInDirectReportsPage();
         // AllureReportUtil.info("Navigated to Direct Reports page");
     }
 
     @When("the manager changes the reporting manager for a direct report with valid details")
     public void the_manager_changes_the_reporting_manager_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillDirectReportsInfoToIncludePage();
         hdp.fillDirectReportsChangeWhenAndWhyPage();
         hdp.fillDirectReportsInfoChangePage();
         hdp.fillDirectReportsCommentsAndAttachmentsPage();
         AllureReportUtil.info("Changed the reporting manager for a direct report with valid details");
     }
 
     //MSS - Ad Hoc Salary - Approve
     @Given("is on the Ad Hoc Salary page")
     public void is_on_the_Ad_Hoc_Salary_page() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToToolsPage();
         hdp.navigateToWorklistPage();
     }
 
     @When("the manager approves an ad hoc salary request for a direct report with valid details")
     public void the_manager_approves_an_ad_hoc_salary_request_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToAdHocSalaryRequest();
         hdp.approveAdHocSalaryRequest();
         AllureReportUtil.info("Approved ad hoc salary request for a direct report with valid details");
     }
 
     //Compensation - Ad Hoc Salary - Approve
     @Given("the compensation manager is logged into Oracle HCM as a Compensation Manager")
     public void the_compensation_manager_is_logged_into_Oracle_HCM_as_a_Compensation_Manager() {
         // Write code here that turns the phrase above into concrete actions
         try{
             initializePageObjectsIfNeeded();
         
             URL=ConfigReader.getProperty("URL");
             WebDriver activeDriver = getActiveDriver();
             
             activeDriver.manage().deleteAllCookies();
             activeDriver.navigate().to(URL);
             activeDriver.manage().window().maximize();
 
             hdp.enterCredentials();
             AllureReportUtil.info("Logging into Oracle HCM as a Compensation Manager");
             attachStepEvidence("the compensation manager is logged into Oracle HCM as a Compensation Manager");
             hdp.openDashboardPage();
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("Error in the_compensation_manager_is_logged_into_Oracle_HCM_as_a_Compensation_Manager step: " + e.getMessage(), e);
         }
     }
 
     @When("the compensation manager approves an ad hoc salary request for a direct report with valid details")
     public void the_compensation_manager_approves_an_ad_hoc_salary_request_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToAdHocSalaryRequest();
         hdp.claimAdHocSalaryRequest();
         AllureReportUtil.info("Claimed ad hoc salary request for approval");
     }


//sakeths

     
@Given("the manager is logged into Oracle HCM as a HRA")
    public void the_manager_is_logged_into_Oracle_HCM_as_a_HRA() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        try{
            initializePageObjectsIfNeeded();
            
            URL=ConfigReader.getProperty("URL");
            WebDriver activeDriver = getActiveDriver();
            
            activeDriver.manage().deleteAllCookies();
            activeDriver.navigate().to(URL);
            activeDriver.manage().window().maximize();

            hdp.enterCredentials();
            hdp.openDashboardPage();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in the_manager_is_logged_into_Oracle_HCM_as_a_HRA step: " + e.getMessage(), e);
        }
    }	


	 @Given("is on the Hire Employee page")
     public void is_on_the_Hire_Employee_page() {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToHireAnEmployeePage();
         AllureReportUtil.info("Navigated to Hire Employee page");
     }
     
      @When("the manager completes the hire setup page for a new employee and clicks Continue button")
     public void the_manager_completes_the_hire_setup_page_for_a_new_employee_and_clicks_Continue_button() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillInfoToIncludePage();
         AllureReportUtil.info("Completed hire setup page for new employee");
     }
     
     @When("the manager completes the When and Why page for a new employee and clicks Continue button")
     public void the_manager_completes_the_When_and_Why_page_for_a_new_employee_and_clicks_Continue_button() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillWhenAndWhyPage();
         AllureReportUtil.info("Completed When and Why page for new employee");
     }
     
      @When("the manager completes the Personal Details page for a new employee")
     public void the_manager_completes_the_Personal_Details_page_for_a_new_employee() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillPersonalDetailsPage();
         AllureReportUtil.info("Completed Personal Details page for new employee");
     }
     
     @When("the manager completes the National Identifier page for a new employee and clicks Continue button")
     public void the_manager_completes_the_National_Identifier_page_for_a_new_employee_and_clicks_Continue_button() throws Exception {
         // Write code here that turns the phrase above into concrete actionsd
             hdp.fillNationalIdentifierDetails();
             AllureReportUtil.info("Completed National Identifier page for new employee");
     }
     
     @When("the manager completes the Communication Info page with phone and email details and clicks Continue button")
     public void the_manager_completes_the_Communication_Info_page_with_phone_and_email_details_and_clicks_Continue_button() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillCommunicationInfoDetails();
         hdp.fillEmailDetails();
         AllureReportUtil.info("Completed Communication Info page for new employee");
     }
     
     
     
      @When("the manager completes the Address page for a new employee and clicks Continue button")
     public void the_manager_completes_the_Address_page_for_a_new_employee_and_clicks_Continue_button() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillAddressDetails();
         AllureReportUtil.info("Completed Address page for new employee");
     }
     
      @When("the manager completes the Assignment Details page for a new employee and clicks Continue button")
     public void the_manager_completes_the_Assignment_Details_page_for_a_new_employee_and_clicks_Continue_button() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillAssignmentDetails();
         AllureReportUtil.info("Completed Assignment Details page for new employee");
     }
     
      @When("the manager reviews the assignment information and clicks Continue button")
     public void the_manager_reviews_the_assignment_information_and_clicks_Continue_button() {
         // Write code here that turns the phrase above into concrete actions
         hdp.ManagerDetails();
         AllureReportUtil.info("Reviewed assignment information and clicked Continue button");
     }
     
     @When("the manager completes the Payroll page for a new employee and clicks Continue button")
     public void the_manager_completes_the_Payroll_page_for_a_new_employee_and_clicks_Continue_button() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillPayrollDetails();
         AllureReportUtil.info("Completed Payroll page for new employee");
     }
     
     @When("the manager completes the Salary page for a new employee and clicks Submit button")
     public void the_manager_completes_the_Salary_page_for_a_new_employee_and_clicks_Submit_button() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillSalaryDetails();
         AllureReportUtil.info("Completed Salary page for new employee");
     }
     
      
     
     
 
      @Given("is on the MSS View Compensation page")
     public void is_on_MSS_View_Compensation_page() {
         try{
         // Write code here that turns the phrase above into concrete actions
         AllureReportUtil.info("Navigating to View My Compensation page");
         hdp.navigateToMSSMyTeamCompensation();
         AllureReportUtil.info("Navigated to View My Compensation page");
         Thread.sleep(10000);
         attachStepEvidence("is on the View My Compensation page");
         }catch(InterruptedException e){
             e.printStackTrace();
             throw new RuntimeException("Error in is_on_MSS_View_Compensation_page step: " + e.getMessage(), e);
         }
     }
     
      @When("the employee views MSS compensation Info details")
     public void the_employee_views_MSS_compensation_Info_details() {
         try{
         // Write code here that turns the phrase above into concrete actions
         AllureReportUtil.info("Validating compensation details");
         hdp.validateMSSCompensationInfoDetails();
         AllureReportUtil.info("Validated compensation details");
         Thread.sleep(10000);
         attachStepEvidence("the employee views compensation details");
         }catch(InterruptedException e){
             e.printStackTrace();
             throw new RuntimeException("Error in the_employee_views_MSS_compensation_Info_details step: " + e.getMessage(), e);
         }
     }
     
      @Given("the manager is logged into Oracle HCM")
     public void the_manager_is_logged_into_Oracle_HCM() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         try{
             initializePageObjectsIfNeeded();
             
             URL=ConfigReader.getProperty("URL");
             WebDriver activeDriver = getActiveDriver();
             
             activeDriver.manage().deleteAllCookies();
             activeDriver.navigate().to(URL);
             activeDriver.manage().window().maximize();
 
             hdp.enterCredentials();
             Thread.sleep(3000);
             hdp.openDashboardPage();
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("Error in the_manager_is_logged_into_Oracle_HCM_as_a_HRA step: " + e.getMessage(), e);
         }
     }
     
     @Given("is on the Promotion or Deny page")
     public void is_on_the_Promotion_or_Deny_page() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         // hdp.navigateToPromotionOrDenyPage();
         hdp.navigateToToolsPage();
         hdp.navigateToWorklistPage();
         AllureReportUtil.info("Navigated to Promotion or Deny page");
     } 
     
     @When("the manager approves a promotion request for a direct report with valid details")
     public void the_manager_approves_a_promotion_request_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         // hdp.approvePromotionRequest();
         hdp.navigatetoPromoteRequest();
         hdp.approvePromoteRequest();
         Thread.sleep(5000);
         AllureReportUtil.info("Approved a promotion request for a direct report with valid details");
     }  
     
      @Given("is on the Retirement or Resignation page for withdrawal")
     public void is_on_the_Retirement_or_Resignation_page_for_withdrawal() {
         // Write code here that turns the phrase above into concrete actions
         // AllureReportUtil.info("Navigating to Retirement or Resignation page");
         hdp.navigateToResignationRetirementWithdrawal();
         AllureReportUtil.info("Navigated to Retirement or Resignation page");
         // attachStepEvidence("is on the Retirement or Resignation page");
     }
     
     @When("The employee withdrawal the retirement request")
     public void The_employee_withdrawa_the_retirement_request() throws Exception {
      
         AllureReportUtil.info("Entering retirement details with valid date, action, and reason");
         // hdp.enterResignationRetirementDetails("23/June/2026");
         // hdp.clickContinueOnResignationRetirement();
         hdp.withdrawRetirementRequest();
         AllureReportUtil.info("withdrawn retirement");
         // attachStepEvidence("the employee withdraws retirement details with valid reason");
     }
     
     @Given("is on the MSS Direct Reports page")
     public void is_on_the_MSS_Direct_Reports_page() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
         hdp.navigateToMSSDirectReportsPage();
         hdp.searchEmployeeInMSSDirectReportsPage();
         AllureReportUtil.info("Navigated to Direct Reports page");
     }
     
     @When("the manager changes the reporting manager for a MSS direct report with valid details")
     public void the_manager_changes_the_reporting_manager_for_a_MSS_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.fillMSSDirectReportsInfoToIncludePage();
         hdp.fillMSSDirectReportsChangeWhenAndWhyPage();
         hdp.fillMSSDirectReportsInfoChangePage();
         hdp.fillMSSDirectReportsCommentsAndAttachmentsPage();
         AllureReportUtil.info("Changed the reporting manager for a direct report with valid details");
     }
     
      @Given("is on the HRA Location Change page")
     public void is_on_the_HRA_Location_Change_page() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         AllureReportUtil.info("Navigating to Location Change page");
         hdp.navigateToHRALocationChangePage();
         Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
         AllureReportUtil.info("Navigated to Location Change page");
     }
     
     @When("the manager submits a HRA location change request for a direct report with valid details")
     public void the_manager_submits_a_HRA_location_change_request_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         AllureReportUtil.info("Submitting location change request for direct report with valid details");
         hdp.submitHRALocationChangeRequest("22/August/2026");
         // AllureReportUtil.info("Submitting location change request for direct report with valid details");
         // hdp.submitLocationChangeRequest("22/May/2026");
         Thread.sleep(5000); // Consider replacing with explicit wait for better reliability
         AllureReportUtil.info("Submitted location change request for direct report with valid details");
         attachStepEvidence("the manager submits a location change request for a direct report with valid details");
 
     }
     
     @Given("is on the Document Records page")
     public void is_on_the_Document_Records_page() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToDocumentRecordsPage();
         hdp.searchEmployeeInDocumentRecordsPage();
         AllureReportUtil.info("Navigated to Document Records page");
     }
     
      @When("the manager adds a new document record for a direct report with valid details")
     public void the_manager_adds_a_new_document_record_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.addDocumentRecord();
         AllureReportUtil.info("Added a new document record for a direct report with valid details");
     }
     
     @Given("is on the Transfer Initaite page")
     public void is_on_the_Transfer_Initiate_page() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToTransferInitiatePage();
         hdp.searchEmployeeInTransferInitiatePage();
         AllureReportUtil.info("Navigated to Transfer page");
     }
     
     @When("the manager initiates a transfer for a direct report with valid details")
     public void the_manager_initiates_a_transfer_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.initiateTransferPage();
         AllureReportUtil.info("Initiated a transfer for a direct report with valid details");
     }
     
      @Given("is on the Transfer page")
     public void is_on_the_Transfer_page() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToTransferPage();
         hdp.searchEmployeeInTransferPage();
         AllureReportUtil.info("Navigated to Transfer page");
     }
     
      @When("the manager initiates a global transfer for a direct report with valid details")
     public void the_manager_initiates_a_global_transfer_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.initiateGlobalTransfer();
         Thread.sleep(15000);
         AllureReportUtil.info("Initiated a global transfer for a direct report with valid details");
     }
     
     @Given("the compensation Staff is logged into Oracle HCM as a Compensation Staff")
     public void the_compensation_manager_is_logged_into_Oracle_HCM_as_a_Compensation_Manager2() {
         // Write code here that turns the phrase above into concrete actions
         try{
             initializePageObjectsIfNeeded();
         
             URL=ConfigReader.getProperty("URL");
             WebDriver activeDriver = getActiveDriver();
             
             activeDriver.manage().deleteAllCookies();
             activeDriver.navigate().to(URL);
             activeDriver.manage().window().maximize();
 
             hdp.enterParityCredentials();
              hdp.openDashboardPage();
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("Error in the_compensation_manager_is_logged_into_Oracle_HCM_as_a_Compensation_Manager step: " + e.getMessage(), e);
         }
     }
     
       @Given("is on the Compensation Parity page")
     public void is_on_the_Compensation_Parity_for_CompensationInfo() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToInitiateMyClientGroupsPage();
         hdp.navigateTochangeSalaryParityPage();
     }
     
     @When("the compensation Staff adds or views parity details for a direct report with valid details")
     public void the_compensation_manager_adds_or_views_parity_details_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         AllureReportUtil.info("Adding or viewing parity details for direct report with valid details");
         hdp.submitCompensationParityChangeRequest();
         hdp.fillCompensationParityDetailsforInitiate();
 
         AllureReportUtil.info("Added or viewed parity details for direct report with valid details");
         attachStepEvidence("the compensation manager adds or views parity details for a direct report with valid details");
     }
     
      @Given("is on the Ad Hoc Salary page for myteam")
     public void is_on_the_Ad_Hoc_Salary_page_for_myteam() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.navigateToInitiateMyTeamPage();
         hdp.navigateTochangeSalaryPage();
     }
     
     @When("the manager initiates an ad hoc salary change request for a direct report with valid details")
     public void the_manager_submits_an_ad_hoc_salary_change_request_for_a_direct_report_with_valid_details() throws Exception {
         // Write code here that turns the phrase above into concrete actions
         hdp.submitAdHocSalaryChangeRequest();
         hdp.fillAdhocSalaryChangeDetailsforInitiate();
         AllureReportUtil.info("Submitted an ad hoc salary change request for a direct report with valid details");
     }  
     
 
 
 
     
     
 
 
 


}
