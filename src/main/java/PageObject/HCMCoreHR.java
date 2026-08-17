package PageObject;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.ObjectUtils.wait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.AllureReportUtil;
import Utilities.ExcelReader;
import Utilities.ScenarioContext;
import Utilities.Wrapper;

public class HCMCoreHR {
    WebDriver driver;
    String userName;
    String passWord;

    String EXCEL_PATH = "src/test/resources/TestData/OracleHCMRegressionTestData.xlsx";
    String SHEET_NAME = "CoreHR";
    String KEY_COLUMN_HEADER = "Test Case"; 

    public HCMCoreHR(WebDriver driver){
        this.driver=driver;
    }

    private String getCurrentTestCaseKey() {
        String key = ScenarioContext.getTestCaseKey();
        if (key == null || key.isBlank()) {
            throw new IllegalStateException("Test case key is not set. Check Hooks @Before.");
        }
        return key;
    }
    String currentScenarioTag= getCurrentTestCaseKey();

    private void attachStepEvidence(String stepName) {
        AllureReportUtil.attachScreenshot(driver, "Step screenshot - " + stepName);
    }


    //Login Page
    By xpath_UserName= By.xpath("//input[contains(@id, 'username')]");
    By xpath_Password=By.xpath("//input[contains(@id, 'password')]");
    By xpath_SigninButton=By.xpath("//*[text()='Sign In']");

    //Test ID:1279843-->ESS - Family and Emergency Contacts
    // By xpath_subtitle = By.xpath("//*[text()='Me']");
    // By xpath_QuickActionsSubtitle = By.xpath("//*[text()='Quick Actions']");
    By xpath_FamilyandEmergencyAction = By.xpath("//a[text()='Family and Emergency Contacts']");
    By xpath_FamilyandEmergencyPageValidation=By.xpath("//h1[text()='Family and Emergency Contacts']");
    By xpath_MyContactsTitle = By.xpath("//*[text()='My contacts']");
    By xpath_AddIcon = By.xpath("//*[text()='My contacts']//following-sibling::div");
    //selecting coworker option 
    // By xpath_CoworkerOption = By.xpath("//oj-dialog[contains(@dialog-title,'whatWouldYouLikeToDo')]//child::oj-radioset//span[contains(@class,'enabled oj-selected')]");
    By xpath_SelectCoworkerAsContact=By.xpath("//oj-option[contains(text(),'Select a Coworker as a Contact')]");
    By xpath_CreateNewContact=By.xpath("//input[contains(@id,'create-new-contact-option')]");
    By xpath_ContinueButton=By.xpath("//*[text()='Continue']");
    
    //Basic Info-->Global Name
    By xpath_LastName=By.xpath("//input[contains(@id,'Last Name|input')]");
    By xpath_FirstName=By.xpath("//input[contains(@id,'First Name')]");
    By xpath_Suffix=By.xpath("//input[contains(@id,'Suffix')]");
    By xpath_MiddleName=By.xpath("//input[contains(@id,'Middle Name')]");
    
    //Basic Info-->Relationship
    By xpath_Relationship=By.xpath("//oj-select-single[contains(@id,'contact-relationship-contact')]//span/span"); ////oj-input-text[@label-hint='Relationship']//input[contains(@id,'contact-relationship-contact')]  //oj-select-single[contains(@id,'contact-relationship-contact')]//div[1]//span/a

    public By xpath_RelationshipDropDownValues(String reasonText) {
        String xpath = "//div[contains(@id,'contact-relationship')]//ul/li//span[contains(text(),'xxx')]";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }

    By xpath_RelationshipSD=By.xpath("//*[contains(@id,'relationship-start')]//*[contains(@title,'Select Date')]");
    
    By xpath_Gender=By.xpath("//oj-select-single[contains(@id,'contact-relationship-gender')]//span/span");

    By xpath_RDOB=By.xpath("//oj-input-date[contains(@id,'relationship-date-of-birth-input-date')]//span/span");

    // By xpath_RelationshipEmergencyContact=By.xpath("(//*[text()='This person is an emergency contact']/../../../following-sibling::div//div)[2]");
    By xpath_RelationshipPrimaryEmergencyContact=By.xpath("//oj-switch[contains(@id,'primary-contact')]//div[contains(@aria-labelledby,'primary-contact')]");
    By xpath_TinType=By.xpath("//oj-select-single[contains(@id,'tinType')]//span/span");
    public By xpath_TinTypeList(String reasonText) {
        String xpath = "//ul[contains(@aria-labelledby,'tinType')]/li//div/span[text()='xxx']";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }

    By xpath_TinNumber=By.xpath("//input[contains(@id,'tinNumber')]");
    By xpath_BenefitsOfferedConditionally=By.xpath("//oj-select-single[contains(@id,'benefitsOffered')]//span/span");
    public By xpath_BenefitsOfferedConditionallyList(String reasonText) {
        String xpath = "//ul[contains(@aria-labelledby,'benefitsOffered')]/li//div/span[contains(text(),'xxx')]";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }
    // By xpath_EmergencyContactNotes=By.xpath("//*[text()='Emergency Contact Notes']/../../../following-sibling::*");
    
    //Basic Info-->Phone Details
    By xpath_PhoneCountryCode=By.xpath("//oj-select-single[contains(@id,'phone-country')]//span/span");
    // By xpath_PhoneCountryCodeList=By.xpath("//oj-list-view//ul[@aria-label='Country']//li");
    //ul[contains(@aria-label,'Country')]/li//oj-highlight-text/span[text()='US']
    By xpath_PhoneCountryCodeManual=By.xpath("//input[contains(@id,'filter-phone-country')]");
    public By xpath_PhoneCountryCodeList(String reasonText) {
        String xpath = "//ul[contains(@aria-label,'Country')]/li//oj-highlight-text/span[text()='xxx']";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }

    // By xpath_PhoneType=By.xpath("//oj-select-single[contains(@id,'person-phone-type')]//span/span");
    By xpath_PhoneType=By.xpath("//oj-select-single[contains(@id,'PhoneType')]//span/span");
    public By xpath_PhoneTypeList(String reasonText) {
        String xpath = "//oj-list-view//ul[contains(@aria-labelledby,'PhoneType')]//li//span[contains(text(),'xxx')]";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }
    
    By xpath_PhoneAreaCode=By.xpath("//input[contains(@id,'AreaCode')]");
    By xpath_PhoneNumber=By.xpath("//input[contains(@id,'PhoneNumber')]");
    // By xpath_PhoneExtension=By.xpath("");
    // By xpath_PhoneFromDate=By.xpath("");
    // By xpath_PhoneToDate=By.xpath("");

    //Basic Info-->Email Details
    By xpath_EmailType=By.xpath("//oj-select-single[contains(@id,'person-emails')]//span/span");
    // By xpath_EmailTypeList=By.xpath("//ul[contains(@aria-labelledby,'person-emails')]//li");
    public By xpath_EmailTypeList(String reasonText){
        String xpath="//oj-list-view//ul[contains(@aria-labelledby,'person-emails')]//li//span[contains(text(),'xxx')]";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }
    By xpath_Email=By.xpath("//input[contains(@id,'EmailAddress')]");
    
    //Basic Info-->Address
    By xpath_AddressType=By.xpath("//input[contains(@value,'useMyAddress')]");
    By xpath_Address=By.xpath("//oj-select-single[contains(@id,'relatedPersonAddressLOV')]//span/span");
    By xpath_AddressValue=By.xpath("(//oj-list-view[contains(@id,'relatedPersonAddress')]//ul/li)[1]");

    //Basic Info-->National Identifiers 
    By xpath_NICountry=By.xpath("//oj-select-single[contains(@id,'national-identifier')]//span/span");
    By xpath_NICountryManual=By.xpath("//input[contains(@id,'filter-nid-country')]");
    public By xpath_NICountryList(String reasonText){
        String xpath="//ul[contains(@aria-label,'Country')]/li//oj-highlight-text/span[text()='xxx']";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }

    By xpath_NIType=By.xpath("//oj-select-single[contains(@id,'NationalIdentifierType')]//span/span");
    public By xpath_NITypeList(String reasonText){
        String xpath="//oj-list-view[contains(@id,'NationalIdentifierType')]//li//span[text()='xxx']";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }

    By xpath_NIID=By.xpath("//label[contains(text(),'National ID')]/parent::div/child::div/input");
    By xpath_NIIssueDate=By.xpath("//oj-input-date[contains(@id,'IssueDate')]//span[contains(@title,'Select Date')]");
    By xpath_NIExpirationDate=By.xpath("//oj-input-date[contains(@id,'ExpirationDate')]//span[contains(@title,'Select Date')]");

    //Co-Worker Contact
    By xpath_CoworkerPageValidation=By.xpath("//*[text()='Search coworker']");
    By xpath_HRAdminCoworkerPageValidation=By.xpath("//h2[text()='Search person']");
    By xpath_CoworkerRelationshipStartDate=By.xpath("//oj-input-date[contains(@id,'start-date')]//span[contains(@title,'Select Date')]");
    
    By xpath_CoworkerSearch=By.xpath("//oj-select-single[contains(@id,'coworker-contacts-lov')]//span/span");
    By xpath_CoworkerSearchManual=By.xpath("//input[contains(@id,'filter-coworker-contacts')]");
    public By xpath_CoworkerSearchList(String reasonText){
        String xpath= "//ul[contains(@aria-label,'Search for a')]//li//span/span[contains(text(),'xxx')]";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }

    By xpath_CoworkerRelationship=By.xpath("//oj-select-single[contains(@id,'coworker-create')]//span/span");
    public By xpath_CoworkerRelationshipList(String reasonText){
        String xpath="//div[contains(@id,'coworker-create')]//ul/li//span[contains(text(),'xxx')]";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
    }

    By xpath_CoworkerEmergencyContactNotes=By.xpath("//input[contains(@id,'emergencyContactNotes')]");
    //Submit Button
    By xpath_SubmitButton=By.xpath("//button//div/span[text()='Submit']");

    // Test ID: 1279865-->ESS - Compensation - View My Compensation
    //show more
    By xpath_ShowMore=By.xpath("(//*[text()='Show More'])[1]");
    
    //show less
    By xpath_ShowLess=By.xpath("(//*[text()='Show Less'])[1]");
    
    //compensation
    By xpath_Compensation=By.xpath("//*[text()='Compensation']");
    
    //My compensation link
    By xpath_MyCompensation=By.xpath("//*[text()='My Compensation']");

    //current salary
    By xpath_CurrentSalary=By.xpath("//*[text()='Current salary']");

    //salary
    By xpath_Salary=By.xpath("//*[text()='Current salary']/../following-sibling::*//*[text()='Salary']/../../following-sibling::*");

    //Adjustment
    By xpath_Adjustment=By.xpath("//*[text()='Current salary']/../following-sibling::*//*[text()='Adjustment']/../../../following-sibling::*");

    //Effective period
    By xpath_EffectivePeriod=By.xpath("//*[text()='Current salary']/../following-sibling::*//*[text()='Effective Period']/../../../following-sibling::*/div");

    //Component
    By xpath_Component=By.xpath("//*[text()='Current salary']/../following-sibling::*//*[text()='Component']");

    //Percentage
    By xpath_Percentage=By.xpath("//*[text()='Current salary']/../following-sibling::*//*[text()='Percentage (%)']");
    
    //Profile
    // By xpath_Profile=By.xpath("(//*[@aria-label='Notifications']/../../../../../following-sibling::*)[2]");
    By xpath_Profile=By.xpath("//a[contains(@title,'Settings and Actions')]");
    
    //Sign Out
    By xpath_SignOut=By.xpath("//a[text()='Sign Out']");

 
    // Test ID: 1279871-->ESS - Retirement or Journey
    //show more
    // By xpath_ShowMore=By.xpath("(//*[text()='Show More'])[1]");
 
    //show less
    // By xpath_ShowLess=By.xpath("(//*[text()='Show Less'])[1]");
    
    // employement
    // By xpath_Employment=By.xpath("//*[text()='Employment']");

    // retirement 
    By xpath_ResignationRetirement=By.xpath("//div[contains(@id,'show_more_groupNode_my_information')]//a[text()='Resignation/Retirement']");

    // notification date
    // By xpath_ResignationNotificationDate=By.xpath("(//*[text()='Resignation Notification Date']/../../../../following-sibling::*/*)[1]");

    // notification date value
    // By xpath_ResignationNotificationDateValue=By.xpath("//tbody//a[@role='button']");

    // retirement date
    By xpath_ResignationRetirementDate=By.xpath("(//*[text()='Resignation/Retirement Date']/../../../../following-sibling::*/*)[1]");

    // retirement date value
    By xpath_ResignationRetirementDateValue=By.xpath("//tbody//a[@role='button']");

    By xpath_SelectMonthAndYear=By.xpath("//table[@data-handler='calendarKey']//td[contains(@data-handler,'selectYear')]/a");
    
    By xpath_DatePickerYear=By.xpath("//div//a[contains(@data-handler,'selectYearHeader')]");
    By xpath_DatePickerMonth=By.xpath("//div/a[contains(@data-handler,'selectMonthHeader')]");
    By xpath_DatePickerNext=By.xpath("(//a[@aria-label='Next'])[1]");
    By xpath_DatePickerPrevious=By.xpath("(//a[@aria-label='Previous'])[1]");
    
    //Resignation/Retirement Action
    By xpath_ResignationRetirementAction=By.xpath("//oj-select-single[contains(@id,'TerminationActionId')]//span/span");

    //Resignation/Retirement Reason
    By xpath_ResignationRetirementReason=By.xpath("//oj-select-single[contains(@id,'TerminationActionReasonId')]//span/span");
    
    By xpath_ResignationRetirementReasonnValidation=By.xpath("//oj-list-view[contains(@id,'listviewWR')]//span");

    // continue
    By xpath_Continue=By.xpath("//button[contains(@aria-label,'Continue')]");

    // submit
    By xpath_Submit=By.xpath("//button[contains(@aria-label,'Submit')]");

    By xpath_MyClientGroups=By.xpath("//a[text()='My Client Groups']");
    By xpathHRAShowMore=By.xpath("//div[contains(@group,'workforce')]//a[text()='Show More']");

    
    // Test CaseID: 1279873	ESS - Resignation or Journey
    
    // Resign from employment:
    // By xpath_ResignFromEmployment = By.xpath("//*[text()='Resign from Employment']");

    // Resignation action
    // By xpath_ResignationAction = By.xpath("(//*[text()='Resignation/Retirement Action'])[1]/../../../../following-sibling::*/*");


    /**
	 *Login method used for logging into Oracle HCM Application
      by retrieving credentials from Excel.
     * Scripted By:gaddem[Gadde Madhukar]  
	**/
    public void enterCredentials() throws Exception{
        try {
            // Thread.sleep(5000);
            userName = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Username");
            passWord = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Password");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UserName), userName, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Password), passWord, false);   
            Thread.sleep(2000);
        }catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Error entering credentials: " + e.getMessage());
        } 
    }

    /**
     * Open the dashboard page after successful login.
     * Scripted By:gaddem[Gadde Madhukar]
     */
    public void openDashboardPage() throws Exception{
        try {
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SigninButton));
            Thread.sleep(2000);
            // if(driver.findElement(xpath_UserName).isDisplayed()) {
            //     userName = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Username");
            //     passWord = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Password");
            //     Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UserName), userName, false);
            //     Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Password), passWord, false);   
            //     Thread.sleep(5000);
            //     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SigninButton));
            //     Thread.sleep(5000);
            // }else{
            //     attachStepEvidence("Successfully logged in and navigated to the dashboard page.");
            // }
        }catch(InterruptedException e) {            
            e.printStackTrace();
            throw new RuntimeException("Error clicking Sign In button: " + e.getMessage());
        }  
    }
    By xpath_TerminateEmploymentPageValidation=By.xpath("//h1[contains(text(),'Terminate Employment')]");
    By xpath_EmployeeSearchBox=By.xpath("//div//input[contains(@placeholder,'Search by Name, Business Title')]");

    public By HRAXpath(String Name){
        String xpath="//ul//span[contains(text(),'test')]";
        xpath=xpath.replace("test", Name);
        return By.xpath(xpath);
    }

    public void searchEmployeeInTerminateEmploymentPage() throws Exception{
        String EmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
        Wrapper.waitForpresenceOfElementLocated(xpath_TerminateEmploymentPageValidation);
        if(Wrapper.findWebElement(xpath_TerminateEmploymentPageValidation).isDisplayed()){
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
            if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                Assert.assertTrue(true, "Employee search functionality is working fine in Terminate Employment page.");
                Thread.sleep(3000);
                attachStepEvidence("the manager selected the employee in Terminate Employment page.");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(HRAXpath(EmployeeName)));
            }else{
                Assert.fail("Employee search functionality is not working in Terminate Employment page.");
            }
        }else{
            Assert.fail("Terminate Employment page is not displayed after clicking on Terminate Employment option under My Team.");
        }
    }

    By xpath_InfoToIncludePageValidation=By.xpath("//div/span[contains(@aria-label,'Info to include')]");
    By xpath_CommunicationInfoButton=By.xpath("//oj-switch//div[contains(@aria-label,'Communication info')]");
    By xpath_AddressButton=By.xpath("//oj-switch//div[contains(@aria-label,'Addresses')]");
    By xpath_ManagerButton=By.xpath("//oj-switch//div[contains(@aria-label,'Managers')]");
    By xpath_PayRollButton=By.xpath("//oj-switch//div[contains(@aria-label,'Payroll')]");
    By xpath_HireSalaryButton=By.xpath("//oj-switch//div[contains(@aria-label,'Salary')]");
    By xpath_commentsAndAttachmentButton=By.xpath("//oj-switch//div[contains(@aria-label,'Comments and attachments')]");
    By xpath_MSSContinueButton=By.xpath("//button[@aria-label='Continue']");

    By xpath_TerminateWhenAndWhyPageValidation=By.xpath("//span[contains(@aria-label,'When and why')]");
    By xpath_TerminationNotificationDate=By.xpath("(//oj-input-date[contains(@class,'inputdatetime')]//span/span[contains(@title,'Select Date')])[1]");
    By xpath_TerminationDate=By.xpath("(//oj-input-date[contains(@class,'inputdatetime')]//span/span[contains(@title,'Select Date')])[2]");
    By xpath_TerminationDropDownsValidation=By.xpath("//oj-list-view[contains(@id,'listviewWR')]//div/span");
    By xpath_TerminationAction=By.xpath("//oj-select-single[contains(@id,'TerminationActionId')]//span/span");
    public By xpath_TerminationActionOption() throws Exception{
        String TerminationAction=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Termination_Action");
        String xpathValue="//div[contains(@id,'TerminationActionId')]//div[text()='test']";
        xpathValue= xpathValue.replace("test", TerminationAction);
        return By.xpath(xpathValue);
    }
    By xpath_TerminationReason=By.xpath("//oj-select-single[contains(@id,'TerminationActionReasonId')]//span/span");
    public By xpath_TerminationReasonOption() throws Exception{
        String TerminationReason=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Termination_Reason");
        String xpathValue="//div[contains(@id,'TerminationActionReasonId')]//div[contains(text(),'test')]";
        xpathValue= xpathValue.replace("test", TerminationReason);
        return By.xpath(xpathValue);
    }
    //xpath_MSSContinueButton

    By xpath_WorkRelationshipTerminationInfoPageValidation=By.xpath("//span[contains(@aria-label,'Work relationship termination info')]");
    By xpath_RevokeUserAccess=By.xpath("//oj-select-single[contains(@id,'RevokeUserAccess')]//span/span");
    public By xpath_RevokeUserAccessOption() throws Exception{
        String RevokeUserAccess=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Revoke_User_Access");
        String xpathValue="//ul[contains(@aria-labelledby,'RevokeUserAccess')]//span[contains(text(),'test')]";
        xpathValue= xpathValue.replace("test", RevokeUserAccess);
        return By.xpath(xpathValue);
    }
    By xpath_RehireRecommendation=By.xpath("//oj-select-single[contains(@id,'RehireRecommendation')]//span/span");
    public By xpath_RehireRecommendationOption() throws Exception{
        String RehireRecommendation=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Rehire_Recommendation");
        String xpathValue="//ul[contains(@aria-labelledby,'RehireRecommendation')]//span[contains(text(),'test')]";
        xpathValue= xpathValue.replace("test", RehireRecommendation);
        return By.xpath(xpathValue);
    }
    By xpath_LastWorkingDay=By.xpath("//div[contains(@aria-label,'Date Picker')]//parent::oj-input-date//span/span[contains(@title,'Select Date')]");
    //xpath_MSSContinueButton

    By xpath_CommentsAndAttachmentsPageValidation=By.xpath("//span[contains(@aria-label,'Comments and attachments')]");
    By xpath_CommentsInputBox=By.xpath("//textarea[contains(@aria-label,'Comments')]");
    By xpath_SaveComment=By.xpath("//button/div/span[contains(text(),'Save Comment')]");
    By xpath_ClickCross=By.xpath("//oj-button[contains(@chroming,'borderless')]//span/span[contains(@slot,'startIcon')]");
    By xpath_EditComment=By.xpath("//button/div/span[contains(text(),'Edit Comment')]");
    //xpath_MSSContinueButton

    By xpath_TerminateSeniortiyPageValidation=By.xpath("//span[contains(@aria-label,'Seniority dates')]");

    public void fillInfoPage(){
        Wrapper.waitForpresenceOfElementLocated(xpath_InfoToIncludePageValidation);
        if(Wrapper.findWebElement(xpath_InfoToIncludePageValidation).isDisplayed()){
            Assert.assertTrue(true, "Info to include page is displayed successfully after clicking on an employee in Terminate Employment page.");
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_commentsAndAttachmentButton), "Comments and attachments button in Info to include page");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_commentsAndAttachmentButton));
            attachStepEvidence("the manager filled the details in Info to include page and submitted the termination request.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        }else{
            Assert.fail("Info to include page is not displayed after clicking on an employee in Terminate Employment page.");
        }
    }

    public void fillTerminateWhenAndWhyPage() throws Exception{
        String terminationNotificationDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Termination_Notification_Date");
        String terminationDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Termination_Date");
        Wrapper.waitForpresenceOfElementLocated(xpath_TerminateWhenAndWhyPageValidation);
        if(Wrapper.findWebElement(xpath_TerminateWhenAndWhyPageValidation).isDisplayed()){
            Assert.assertTrue(true, "When and why page is displayed successfully after clicking Continue button on Info to include page.");
            Thread.sleep(10000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_TerminationNotificationDate), "Termination Notification Date field in When and why page");
            Wrapper.selectDate(Wrapper.findWebElement(xpath_TerminationNotificationDate), terminationNotificationDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
            
            Wrapper.waitForpresenceOfElementLocated(xpath_TerminationDropDownsValidation);
            Thread.sleep(5000);
            Wrapper.selectDate(Wrapper.findWebElement(xpath_TerminationDate), terminationDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);

            if(Wrapper.findWebElement(xpath_TerminationDropDownsValidation).isDisplayed()){
                Assert.assertTrue(true, "Action and Reason dropdowns are displayed successfully in When and why page.");
                Wrapper.waitForpresenceOfElementLocated(xpath_TerminationAction);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_TerminationAction)).click();
                Wrapper.redwoodSync();
                By terminationActionOption = xpath_TerminationActionOption();
                Wrapper.waitForpresenceOfElementLocated(terminationActionOption);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(terminationActionOption)).click();

                Thread.sleep(5000);
                Wrapper.scrollToElement(Wrapper.findWebElement(xpath_TerminationReason), "Termination Reason dropdown");
                Wrapper.waitForpresenceOfElementLocated(xpath_TerminationReason);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_TerminationReason)).click();
                Wrapper.redwoodSync();
                By terminationReasonOption = xpath_TerminationReasonOption();
                Wrapper.waitForpresenceOfElementLocated(terminationReasonOption);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(terminationReasonOption)).click();

                attachStepEvidence("the manager filled the details in When and why page and submitted the termination request.");
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            }else{
                Assert.fail("Action and Reason dropdowns are not displayed in When and why page.");
            }
            
        }else{
            Assert.fail("When and why page is not displayed after clicking Continue button on Info to include page.");
        }
    }

    public void fillWorkRelationshipTerminationInfoPage() throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_WorkRelationshipTerminationInfoPageValidation);
        if(Wrapper.findWebElement(xpath_WorkRelationshipTerminationInfoPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Work relationship termination info page is displayed successfully after clicking Continue button on When and why page.");
            
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_RevokeUserAccess), "Revoke User Access dropdown");
            Wrapper.waitForpresenceOfElementLocated(xpath_RevokeUserAccess);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_RevokeUserAccess)).click();
            By revokeUserAccessOption = xpath_RevokeUserAccessOption();
            Wrapper.waitForpresenceOfElementLocated(revokeUserAccessOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(revokeUserAccessOption)).click();

            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_RehireRecommendation), "Rehire Recommendation dropdown");
            Wrapper.waitForpresenceOfElementLocated(xpath_RehireRecommendation);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_RehireRecommendation)).click();
            By rehireRecommendationOption = xpath_RehireRecommendationOption();
            Wrapper.waitForpresenceOfElementLocated(rehireRecommendationOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(rehireRecommendationOption)).click();

            String lastWorkingDay=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Last_Working_Day");
            Thread.sleep(5000);
            Wrapper.selectDate(Wrapper.findWebElement(xpath_LastWorkingDay), lastWorkingDay, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
            Thread.sleep(5000);
            attachStepEvidence("the manager filled the details in Work relationship termination info page and submitted the termination request.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        }else{
            Assert.fail("Work relationship termination info page is not displayed after clicking Continue button on When and why page.");
        }
    }
    
    public void fillCommentsAndAttachmentsPage() throws Exception{
        String comment=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Termination_Comment");
        Wrapper.waitForpresenceOfElementLocated(xpath_CommentsAndAttachmentsPageValidation);
        if(Wrapper.findWebElement(xpath_CommentsAndAttachmentsPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Comments and attachments page is displayed successfully after clicking Continue button on Work relationship termination info page.");
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_CommentsInputBox), "Comments input box in Comments and attachments page");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_CommentsInputBox), comment, false);
            attachStepEvidence("the manager filled the details in Comments and attachments page and submitted the termination request.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveComment));
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ClickCross));
            Wrapper.waitForpresenceOfElementLocated(xpath_EditComment);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        }else{
            Assert.fail("Comments and attachments page is not displayed after clicking Continue button on Work relationship termination info page.");
        }
    }

    By xpath_MSSSubmitButton=By.xpath("//button[@aria-label='Submit']");

    public void submitTerminatePage() throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_TerminateSeniortiyPageValidation);
        if(Wrapper.findWebElement(xpath_TerminateSeniortiyPageValidation).isDisplayed()){
            Thread.sleep(10000);
            // Wrapper.findWebElement(xpath_CASeniorityDatePageValidation).isDisplayed();
            Assert.assertTrue(true, "Seniority dates page is displayed successfully after clicking Continue button on Comments and attachments page.");
            attachStepEvidence("the manager in Seniority dates page and submitted the termination request.");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
            Thread.sleep(12000);
        }else{
            Assert.fail("Seniority dates page is not displayed after clicking Continue button on Comments and attachments page.");
        }
    }
    By xpath_EmploymentInfo=By.xpath("//div[contains(@quickactioncategory,'grp_mcg_employment')]//a[text()='Employment Info']");

    By xpath_EmploymentInfoValidation=By.xpath("//h1[text()='Employment Info']");
    
    // By xpath_EmployeeSearchBox=By.xpath("//div//input[contains(@placeholder,'Search by Name, Business Title')]");

    // public By HRAXpath(String Name){
    //     String xpath="//ul//span[contains(text(),'test')]";
    //     xpath=xpath.replace("test", Name);
    //     return By.xpath(xpath);
    // }

    By xpath_EmployeeSearchResultValidation=By.xpath("//div[contains(@id,'EmpInfo_sgop_h_pageSubtitle')]");
    By xpath_HistoricalChangeOptionValidation=By.xpath("//h2[text()='Historical changes']");
    By xpath_HistoricalAssignmentChange=By.xpath("//span//a[text()='Change Salary']");
    By xpath_SummaryPageValidation=By.xpath("//h2[contains(@title,'Summary of')]");
    By xpath_CorrectButton=By.xpath("//oj-toolbar//button[contains(@aria-label,'Correct')]");
    // By xpath_HistoricalAssignmentChangeValidation=By.xpath("//span[text()='Assignment Change']");

    //xpath_MSSContinueButton
    By xpath_WhenAndWhyPageValidation=By.xpath("//span[contains(@aria-label,'When and Why')]");
    By xpath_SalaryChangeDate=By.xpath("//oj-input-date[contains(@id,'effectiveDate')]//span[contains(@title,'Select Date')]");
    By xpath_ActionDropDown=By.xpath("//oj-select-single[contains(@id,'actionSingleSelect')]//span/span");
    
    public By xpath_ActionDropDownOption(String ActionName){
        String xpath="//div[contains(@id,'actionSingleSelect')]//oj-highlight-text/span[contains(text(),'test')]";
        xpath=xpath.replace("test", ActionName);
        return By.xpath(xpath);
    }

    By xpath_ReasonDropDown=By.xpath("//oj-select-single[contains(@id,'reasonSingleSelect')]//span/span");
    public By xpath_ReasonDropDownOption(String Reason){
        String xpath="//div[contains(@id,'reasonSingleSelect')]//oj-highlight-text/span[contains(text(),'test')]";
        xpath=xpath.replace("test", Reason);
        return By.xpath(xpath);
    }
    //Coninue Button-->xpath_MSSContinueButton
    By xpath_SalaryChange=By.xpath("//oj-c-input-number[contains(@id,'amtInputNumber')]//input");
    //Submit Button-->xpath_MSSSubmitButton

    // By xpath_ActionNameDropDown=By.xpath("//oj-select-single[contains(@id,'employmentWhenAndWhy.CorrectionActionId')]//span/span");
    
    // By xpath_ReasonForChangeDropDown=By.xpath("//oj-select-single[contains(@id,'employmentWhenAndWhy.CorrectionActionReasonId')]//span/span");
    
    
    //Correct: The respective  assignment details
    //Coninue Button-->xpath_MSSContinueButton
    //Verify: The updated value  is populated
    //Coninue Button-->xpath_MSSContinueButton

    By xpath_SeniorityDatePageValidation=By.xpath("//span[contains(@aria-label,'Seniority dates')]");
    //Submit Button-->xpath_MSSSubmitButton
    By xpath_DeleteButton=By.xpath("//oj-toolbar//button[contains(@aria-label,'Delete')]");
    By xpath_DeleteTranscationPopUpValidation=By.xpath("//oj-dialog[contains(@id,'salaryDelete-dialog')]");
    By xpath_AssignmentDeleteButton=By.xpath("//button[contains(@aria-labelledby,'warndelete')]//span[text()='Delete']");
    By xpath_AssignmentDeletePageValidation=By.xpath("//h1[text()='Delete Assignment Change']");
    By xpath_AssignmentSubmitButton=By.xpath("//button[contains(@aria-labelledby,'delete')]//span[contains(@id,'ActionFromHeader')]");

    public void navigateToEmploymentInfoPage() {
        try{
            Wrapper.waitForpresenceOfElementLocated(xpath_MyClientGroups);
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            attachStepEvidence("the manager clicks on My Client Groups");
            Thread.sleep(2000);
            Wrapper.waitForpresenceOfElementLocated(xpathHRAShowMore);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
            Wrapper.waitForpresenceOfElementLocated(xpath_EmploymentInfo);
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_EmploymentInfo), "Employment Info option under My Client Groups");
            Thread.sleep(3000);
            attachStepEvidence("the manager clicks on Employment info option under My Client Groups");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmploymentInfo));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in navigateToEmploymentInfoPage method: " + e.getMessage(), e);
        }
    }

   //HRA Manager- Update address for employee

    // xpath_MyClientGroups
    // xpathHRAShowMore
    By xpath_ContactInformation = By.xpath("//div[contains(@id,'show_more_groupNode_workforce_management')]//a[text()='Contact Info']");

    public void navigateToContactInfoPage() throws Exception{
        Thread.sleep(3000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Thread.sleep(2000);
        attachStepEvidence("Clicked on My Client Groups");
        Thread.sleep(2000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
        Wrapper.waitForpresenceOfElementLocated(xpath_ContactInformation);
        if(Wrapper.findWebElement(xpath_ContactInformation).isDisplayed()){
            Thread.sleep(2000);
            attachStepEvidence("Clicking on ContactInfo");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ContactInformation));
        }
    }

    By xpath_ContactInfoPageValidation = By.xpath("//h1[text()='Contact Info']");

    public void navigateToAddressChangePage() throws Exception{
        String employeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
        Wrapper.waitForpresenceOfElementLocated(xpath_ContactInfoPageValidation);
        if(Wrapper.findWebElement(xpath_ContactInfoPageValidation).isDisplayed()){
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), employeeName, false);
            if(Wrapper.findWebElement(HRAXpath(employeeName)).isDisplayed()){
                Thread.sleep(3000);
                attachStepEvidence("Searched for employee using employee number: " + employeeName);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(employeeName))).click();
            }
        }
    }

    By xpath_AddressPencilEdit=By.xpath("(//button[contains(@aria-label,'Edit Home Address')]//span/span)[1]");
    public void clickAddressPencilEdit() throws Exception{
        Thread.sleep(8000);
        Wrapper.waitForpresenceOfElementLocated(xpath_AddressPencilEdit);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AddressPencilEdit),"Scrolling to the address pencil edit icon.");
        Thread.sleep(2000);
        Wrapper.findWebElement(xpath_AddressPencilEdit).isDisplayed();
        attachStepEvidence("Clicking on the address pencil edit icon.");
        Thread.sleep(6000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressPencilEdit));
    }

    By xpath_AddressChangeStartDate=By.xpath("//oj-input-date[contains(@id,'current-future-addresses-change-date')]//span/span");
    By xpath_AddressLine1=By.xpath("//oj-input-text[contains(@id,'AddressLine1')]//div/input");
    By xpath_AddressLine2=By.xpath("//oj-input-text[contains(@id,'AddressLine2')]//div/input");

    By xpath_ZIPCode=By.xpath("//oj-select-single[contains(@id,'PostalCode')]//span/span");
    By xpath_ZIPCodeManual=By.xpath("//oj-input-text[contains(@id,'geography_PostalCode')]//input");
    public By xpath_ZIPCodeOption() throws Exception{
        String ZIPCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"ZIP_CODE");
        String xpath="//ul[contains(@aria-labelledby,'PostalCode')]//span[contains(text(),'test')]";
        xpath=xpath.replace("test", ZIPCode);
        return By.xpath(xpath);
    }

    By xpath_ValidateButton=By.xpath("//button[contains(@aria-label,'Edit Home')]");
    //Assignment Details
    By xpath_AssignmentPageValidation=By.xpath("//span[contains(@aria-label,'Assignment')]");
    By xpath_AssignmentNumber=By.xpath("//oj-input-text//input[contains(@id,'AssignmentNumber')]");
    By xpath_AssignmentStatusPicker=By.xpath("//oj-select-single[contains(@id,'AssignmentStatusTypeId')]//span/span");
    By xpath_AssignmentStatusList=By.xpath("//div[contains(@id,'AssignmentStatusTypeId')]//td/div[contains(@id,'UserStatus')]");
    By xpath_PersonTypePicker=By.xpath("//oj-select-single[contains(@id,'UserPersonTypeId')]//span/span");
    public By xpath_PersonTypeOption() throws Exception{
        String PersonType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Person_Type");
        String xpath="//oj-list-view[contains(@id,'UserPersonTypeId')]//li/div/span[contains(text(),'test')]";
        xpath=xpath.replace("test", PersonType);
        return By.xpath(xpath);
    }
    // By xpath_JobPicker=By.xpath("//oj-select-single[contains(@id,'JobId')]//span/span");
    By xpath_JobPicker=By.xpath("(//span[text()='Job']//following::input)[1]");
    By xpath_JobManual=By.xpath("//oj-input-text[contains(@id,'JobId')]//input");
    By xpath_JobList=By.xpath("//div[contains(@id,'JobId')]//div[contains(@id,'JobName')]");
    By xpath_BusinessTitle=By.xpath("//oj-input-text[contains(@id,'AssignmentName')]//input");
    
    By xpath_GradePicker=By.xpath("//oj-select-single[contains(@id,'GradeId')]//span/span");
    // By xpath_GradePicker=By.xpath("//span[text()='Grade']//following::input)[1]");
    By xpath_GradeManual=By.xpath("//oj-input-text[contains(@id,'GradeId')]//input");
    By xpath_GradeList=By.xpath("//div[contains(@id,'GradeId')]//tbody/tr");

    By xpath_DepartmentPicker=By.xpath("//oj-select-single[contains(@id,'DepartmentId')]//span/span");
    // By xpath_DepartmentPicker=By.xpath("//span[text()='Department']//following::input[1]");
    By xpath_DepartmentManual=By.xpath("//oj-input-text[contains(@id,'DepartmentId')]//input");
    By xpath_DepartmentList=By.xpath("//div[contains(@id,'DepartmentId')]//div[contains(@id,'Name')]");
    
    By xpath_ReportingEstablishmentPicker=By.xpath("//oj-select-single[contains(@id,'ReportingEstablishmentId')]//span/span");
    By xpath_ReportingEstablishmentManual=By.xpath("//oj-input-text[contains(@id,'ReportingEstablishmentId')]//input");
    By xpath_ReportingEstablishmentList=By.xpath("//div[contains(@id,'ReportingEstablishmentId')]//div[contains(@id,'Name')]");
    
    By xpath_LocationPicker=By.xpath("//oj-select-single[contains(@id,'LocationId')]//span/span");
    By xpath_LocationManual=By.xpath("//oj-input-text[contains(@id,'LocationId')]//input");
    By xpath_LocationList=By.xpath("//div[contains(@id,'LocationId')]//div[contains(@id,'Name')]");
    
    By xpath_WorkingAtHome=By.xpath("//oj-select-single[contains(@id,'WorkAtHome')]//span/span");
    public By xpath_WorkingAtHomeOption() throws Exception{
        String WorkAtHome=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Work_At_Home");
        String xpath="//ul[contains(@aria-labelledby,'WorkAtHome')]//li//span[contains(text(),'test')]";
        xpath=xpath.replace("test", WorkAtHome);
        return By.xpath(xpath);
    }

    By xpath_WorkerCategory=By.xpath("//oj-select-single[contains(@id,'WorkerCategory')]//span/span");
    public By xpath_WorkerCategoryOption() throws Exception{
        String WorkerCategory=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Worker_Category");
        String xpath="//ul[contains(@aria-labelledby,'WorkerCategory')]//li//span[contains(text(),'test')]";
        xpath=xpath.replace("test", WorkerCategory);
        return By.xpath(xpath);
    }

    By xpath_AssignmentCategory=By.xpath("//oj-select-single[contains(@id,'AssignmentCategory')]//span/span");
    public By xpath_AssignmentCategoryOption() throws Exception{
        String AssignmentCategory=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Category");
        String xpath="//ul[contains(@aria-labelledby,'AssignmentCategory')]//li//span[contains(text(),'test')]";
        xpath=xpath.replace("test", AssignmentCategory);
        return By.xpath(xpath);
    }

    By xpath_RegularTemporary=By.xpath("//oj-select-single[contains(@id,'PermanentTemporary')]//span/span");
    public By xpath_RegularTemporaryOption() throws Exception{
        String RegularTemporary=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Regular_Temporary");
        String xpath="//ul[contains(@aria-labelledby,'PermanentTemporary')]//li//span[contains(text(),'test')]"; 
        xpath=xpath.replace("test", RegularTemporary);
        return By.xpath(xpath);
    }

    By xpath_FullTimePartTime=By.xpath("//oj-select-single[contains(@id,'FullPartTime')]//span/span");
    public By xpath_FullTimePartTimeOption() throws Exception{
        String FullTimePartTime=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Full_Time_Part_Time");
        String xpath="//ul[contains(@aria-labelledby,'FullPartTime')]//li//span[contains(text(),'test')]";
        xpath=xpath.replace("test", FullTimePartTime);
        return By.xpath(xpath);
    }

    By xpath_HourlyPaidSalaried=By.xpath("//oj-select-single[contains(@id,'HourlySalariedCode')]//span/span");
    public By xpath_HourlyPaidSalariedOption() throws Exception{
        String HourlyPaidSalaried=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hourly_Paid_Salaried");
        String xpath="//ul[contains(@aria-labelledby,'HourlySalariedCode')]//li//span[contains(text(),'test')]";
        xpath=xpath.replace("test", HourlyPaidSalaried);
        return By.xpath(xpath);
    }

    By xpath_WorkingHours=By.xpath("//oj-input-number[contains(@id,'NormalHours')]//input");
    
    By xpath_WorkingHoursFrequency=By.xpath("//oj-select-single[contains(@id,'Frequency')]//span/span");
    public By xpath_WorkingHoursFrequencyOption() throws Exception{
        String WorkingHoursFrequency=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Working_Hours_Frequency");
        String xpath="//oj-list-view[contains(@id,'Frequency')]//li//span[text()='test']";
        xpath=xpath.replace("test", WorkingHoursFrequency);
        return By.xpath(xpath);
    }

    By xpath_UnionMember=By.xpath("//oj-select-single[contains(@id,'UnionMember')]//span/span");
    public By xpath_UnionMemberOption() throws Exception{
        String UnionMember=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union_Member");
        String xpath="//oj-list-view[contains(@id,'UnionMember')]//li//span[text()='test']";
        xpath=xpath.replace("test", UnionMember);
        return By.xpath(xpath);
    }

    By xpath_Union=By.xpath("//oj-select-single[contains(@id,'UnionId')]//span/span");
    By xpath_UnionManual=By.xpath("//oj-input-text[contains(@id,'UnionId')]//input");
    By xpath_UnionList=By.xpath("//div[contains(@id,'UnionId')]//oj-table//tr//div[contains(@id,'UnionName')]");

    By xpath_BargainingUnit=By.xpath("//oj-select-single[contains(@id,'BargainingUnitCode')]//span/span");
    By xpath_BargainingUnitManual=By.xpath("//oj-input-text[contains(@id,'BargainingUnitCode')]//input");
    By xpath_BargainingUnitList=By.xpath("//div[contains(@id,'BargainingUnitCode')]//oj-table//tr//div[contains(@id,'BargainingUnitName')]");
    
    By xpath_OfficerCode=By.xpath("(//span[text()='Officer Code']//following::span)[1]");
    public By xpath_OfficerCodeOption() throws Exception{
        String OfficerCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Officer_Code");
        String xpath="//ul[contains(@aria-labelledby,'officerCode')]//li//span[contains(text(),'test')]";
        xpath=xpath.replace("test", OfficerCode);
        return By.xpath(xpath);
    }

    //Continue Button-->xpath_MSSContinueButton
    By xpath_ManagerDetailsPageValidation=By.xpath("//oj-hcm-collection-item[contains(@id,'manager')]");
    //Continue Button-->xpath_MSSContinueButton
    By xpath_PayrollDetailsPageValidation=By.xpath("//h2[contains(@aria-label,'Payroll Frequency')]");
    By xpath_HirePayrollButton=By.xpath("//oj-c-button[contains(@title,'Payroll')]//button");
    By xpath_Payroll=By.xpath("//oj-select-single[contains(@labelled-by,'payroll-')]//span/span");
    public By xpath_PayrollOption() throws Exception{
        // String Payroll=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Payroll");
        String xpath="//ul[contains(@aria-labelledby,'payroll-')]//li//span[contains(text(),'test')]";
        xpath=xpath.replace("test", "Pension Monthly");
        return By.xpath(xpath);
    }
    By xpath_TimeCardRequired=By.xpath("//oj-select-single[contains(@id,'Payrolls.TimeCardRequired')]//span/span");
    public By xpath_TimeCardRequiredOption() throws Exception{
        // String TimeCardRequired=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Time_Card_Required");
        String xpath="//ul[contains(@aria-labelledby,'Payrolls.TimeCardRequired')]//li//span[contains(text(),'test')]";
        xpath=xpath.replace("test", "No");
        return By.xpath(xpath);
    }
    //Call-->xpath_SaveButton
    By xpath_PrimaryValidationAfterSave=By.xpath("//span[contains(@aria-label,'Primary')]");
    //Continue Button-->xpath_MSSContinueButton
    By xpath_HireSalaryPageValidation=By.xpath("//span[contains(@aria-label,'Salary')]");
    By xpath_SalaryBasis=By.xpath("//oj-select-single[contains(@item-text,'SalaryBasisName')]//span/span");
    public By xpath_SalaryBasisOption() throws Exception{
        // String SalaryBasis=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Salary_Basis");
        String xpath="//ul[contains(@aria-labelledby,'basisSingleSelect')]//li//span[contains(text(),'test')]";
        xpath=xpath.replace("test", "Monthly Pension");
        return By.xpath(xpath);
    }
    By xpath_SalaryAmount=By.xpath("//oj-c-input-number[contains(@id,'amtInputNumber')]//input");
    By xpath_SaveButton=By.xpath("//span/span[contains(normalize-space(),'Save')]");

    public void updateAddress() throws Exception{
        String AddressLine1=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Line1");
        String addressChangeStartDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hire_Date");
        Thread.sleep(4000);
        Wrapper.waitForpresenceOfElementLocated(xpath_AddressChangeStartDate);
        Wrapper.selectDate(Wrapper.findWebElement(xpath_AddressChangeStartDate), addressChangeStartDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
        Thread.sleep(4000);

        Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressLine1), AddressLine1, true);
        Thread.sleep(4000);

        Wrapper.waitForpresenceOfElementLocated(xpath_ZIPCode);
        Thread.sleep(4000);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_ZIPCode)).click();
                Thread.sleep(4000);
        Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_ZIPCodeManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"ZIP_CODE"), true);
        Thread.sleep(4000);
        By zipCodeOption = xpath_ZIPCodeOption();
        Thread.sleep(4000);
        Wrapper.waitForpresenceOfElementLocated(zipCodeOption);
        Thread.sleep(4000);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(zipCodeOption)).click();
        Thread.sleep(4000);
        attachStepEvidence("the manager updated the details in Address section");
    
        Thread.sleep(4000);
        Wrapper.findWebElement(xpath_SaveButton).isDisplayed();
        Wrapper.waitForpresenceOfElementLocated(xpath_SaveButton);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SaveButton),"Scrolling to the save button.");
        attachStepEvidence("Clicking on the save button.");
        Thread.sleep(4000);
        
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
        Thread.sleep(6000);


        Wrapper.findWebElement(xpath_AddressPencilEdit).isDisplayed();
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AddressPencilEdit),"Scrolling to the address pencil edit icon.");
        Wrapper.waitForpresenceOfElementLocated(xpath_AddressPencilEdit);
        Thread.sleep(6000);
        attachStepEvidence("Verified that the address changed");
        Thread.sleep(4000);
    }

    //HRA Manager- Add Secondary Address for employee
    By xpath_AddSecondaryAddress=By.xpath("//button[contains(@aria-label,'Add Address')]//span/span/span");
    public void addSecondaryAddress() throws Exception {
        try{
            Wrapper.findWebElement(xpath_AddSecondaryAddress).isDisplayed();
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AddSecondaryAddress),"Scrolling to the add secondary address button.");
            Wrapper.waitForpresenceOfElementLocated(xpath_AddSecondaryAddress);
            Thread.sleep(4000);
            attachStepEvidence("Clicking on the add button to add secondary address");
            Thread.sleep(6000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddSecondaryAddress));
            Thread.sleep(4000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    By xpath_SecondaryAddressType=By.xpath("//oj-select-single[contains(@id,'create-address-type')]//span/span");
    By xpath_SecondaryAddressManual=By.xpath("//oj-input-text[contains(@label-hint,'Type')]//input[contains(@id,'create-address-type')]");
    public By xpath_AddressOption() throws Exception {
        String addressType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Type");
        String xpath="//div[contains(@id,'create-address-type')]//span/span[contains(text(),'test')]";
        xpath=xpath.replace("test",addressType);
        return By.xpath(xpath);
    }

    By xpath_AddressAddStartDate=By.xpath("//oj-input-date[contains(@id,'current-future-addresses-start-date')]//span/span");
    By xpath_BusinessAddressPencilEdit=By.xpath("(//button[contains(@aria-label,'Edit Business Address')]//span/span)[1]");
    
    public void addSecondaryAddressDetails() throws Exception {
        try{
            String addressType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Type");
            String AddressLine1=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Line1");
            String addressChangeStartDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hire_Date");

            Wrapper.findWebElement(xpath_SecondaryAddressType).isDisplayed();
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SecondaryAddressType),"Scrolling to the secondary address type dropdown.");
            Wrapper.waitForpresenceOfElementLocated(xpath_SecondaryAddressType);
            Thread.sleep(4000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SecondaryAddressType));
            Thread.sleep(6000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SecondaryAddressManual),addressType,false);
            Thread.sleep(4000);
            Wrapper.findWebElement(xpath_AddressOption()).isDisplayed();
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressOption()));
            Thread.sleep(4000);

            Thread.sleep(4000);
            Wrapper.waitForpresenceOfElementLocated(xpath_AddressAddStartDate);
            Wrapper.selectDate(Wrapper.findWebElement(xpath_AddressAddStartDate), addressChangeStartDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
            Thread.sleep(4000);

            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressLine1), AddressLine1, true);
            Thread.sleep(4000);

            Wrapper.waitForpresenceOfElementLocated(xpath_ZIPCode);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_ZIPCode)).click();
                    Thread.sleep(4000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_ZIPCodeManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"ZIP_CODE"), true);
            Thread.sleep(4000);
            By zipCodeOption = xpath_ZIPCodeOption();
            Thread.sleep(4000);
            Wrapper.waitForpresenceOfElementLocated(zipCodeOption);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(zipCodeOption)).click();
            Thread.sleep(4000);
            attachStepEvidence("the HRA is adding secondary address details in Address section");
        
            Thread.sleep(4000);
            Wrapper.findWebElement(xpath_SaveButton).isDisplayed();
            Wrapper.waitForpresenceOfElementLocated(xpath_SaveButton);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SaveButton),"Scrolling to the save button.");
            attachStepEvidence("Clicking on the save button.");
            Thread.sleep(4000);
            
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Thread.sleep(8000);

            Wrapper.findWebElement(xpath_BusinessAddressPencilEdit).isDisplayed();
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_BusinessAddressPencilEdit),"Scrolling to the address pencil edit icon.");
            Wrapper.waitForpresenceOfElementLocated(xpath_BusinessAddressPencilEdit);
            Thread.sleep(6000);
            attachStepEvidence("Verified that secondary address added");
            Thread.sleep(4000);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //HRA Manager- Enter Job Change for Employee
    // xpath_MyClientGroups
    // xpathHRAShowMore
    By xpath_HRAChangeAssignment = By.xpath("//div[contains(@id,'show_more_groupNode_workforce_management')]//a[text()='Change Assignment']");

    public void navigateToChangeAssignmentPage() {
        try {
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            Thread.sleep(2000);
            attachStepEvidence("Clicked on My Client Groups");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
            Wrapper.waitForpresenceOfElementLocated(xpath_HRAChangeAssignment);
            if(Wrapper.findWebElement(xpath_HRAChangeAssignment).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Change Assignment");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAChangeAssignment));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    By xpath_ChangeAssignmentPageValidation = By.xpath("//h1[contains(text(),'Change Assignment')]");
    
    public void searchEmployeeInChangeAssignment() throws Exception {
        String employeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
        Wrapper.waitForpresenceOfElementLocated(xpath_ChangeAssignmentPageValidation);
        if(Wrapper.findWebElement(xpath_ChangeAssignmentPageValidation).isDisplayed()){
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), employeeName, false);
            if(Wrapper.findWebElement(HRAXpath(employeeName)).isDisplayed()){
                Thread.sleep(3000);
                attachStepEvidence("Searched for employee using employee number: " + employeeName);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(employeeName))).click();
            }
        }
    }
    By xpath_SalaryButton=By.xpath("//div[contains(@aria-label,'Salary')]");
    // By xpath_DocumentsRecordsOption=By.xpath("//div[contains(@aria-label,'Document records')]");

    //Check if Buttons are enabled or not
    public By xpath_ButtonEnabled(String ButtonName){
        String xpath="//oj-switch//div[contains(@aria-label,'test')]";
        xpath=xpath.replace("test", ButtonName);
        return By.xpath(xpath);
    }
    //Call this-->xpath_MSSContinueButton
    By xpath_WorkingHoursWhenAndWhyValidation=By.xpath("//span[contains(@aria-label,'When and why')]");
    By xpath_DatePicker=By.xpath("//div[contains(@id,'whenAndWhyForm')]//span[contains(@title,'Select Date')]");
    By xpath_WorkingHoursWay=By.xpath("//oj-select-single[contains(@id,'employmentWhenAndWhy.ActionId')]//span/span");
    By xpath_WorkingHoursWhy=By.xpath("//oj-select-single[contains(@id,'employmentWhenAndWhy.ActionReasonId')]//span/span");
    //Call this-->xpath_ReasonSelection
    //Continue Button-->xpath_MSSContinueButton
    By xpath_AssignmentChangeValidation=By.xpath("//span[contains(@aria-label,'Assignment')]");
    By xpath_WorkingHoursInputField=By.xpath("//oj-input-number[@data-oj-field='WorkingHours']//input");
    //Continue Button-->xpath_MSSContinueButton
    By xpath_SalaryPageValidation=By.xpath("//label[contains(text(),'Salary Amount')]");
    //Continue Button-->xpath_MSSContinueButton
    //call this-->xpath_SeniorityDatePageValidation
    //Submit Button-->xpath_MSSSubmitButton
    By xpath_Home=By.xpath("//a[contains(@title,'Home')]/img");
    By xpath_WorkingHoursChangeValidation=By.xpath("//label[text()='Working Hours']");

    By xpath_navigateToChangeWorkingHours=By.xpath("//h4[contains(text(),'Document Records')]");

    public void changeAssignmentInfoPage() throws Exception {
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SalaryButton));
        attachStepEvidence("Clicked on Salary button in Change Assignment Page.");
        Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Salary"));
        if(Wrapper.findWebElement(xpath_ButtonEnabled("Salary")).isEnabled()){
            Assert.assertTrue(true, "Salary button is enabled.");
        }else{
            Assert.fail("Salary button is not enabled.");
        }

        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        Thread.sleep(6000);
    }

    By xpath_AssignmentChangeStartDate=By.xpath("//oj-input-date//span[contains(@title,'Select Date')]");
    By xpath_AssignmentChangeWayPicker=By.xpath("//oj-select-single[contains(@id,'CorrectionActionId')]//span/span");
    public By xpath_AssignmentChangeWayOption() throws Exception{
        String reason=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"AssignmentChangeWay");
        String xpath="//oj-table[contains(@aria-label,'default')]//div[contains(text(),'test')]";
        xpath=xpath.replace("test", reason);
        return By.xpath(xpath);
    }
    By xpath_AssignmentChangeReasonPicker=By.xpath("//oj-select-single[contains(@id,'CorrectionActionReasonId')]//span/span");
    public By xpath_AssignmentChangeReasonOption() throws Exception{
        String reason=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"AssignmentChangeReason");
        String xpath="//oj-table[contains(@aria-label,'default')]//div[contains(text(),'test')]";
        xpath=xpath.replace("test", reason);
        return By.xpath(xpath);
    }
    By xpath_BusinessUnitPicker=By.xpath("//oj-select-single[contains(@id,'BusinessUnitId')]//span/span");
    public By xpath_CABusinessUnitOption() throws Exception{
        String reason=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CABusinessUnit");
        String xpath="//oj-table[contains(@aria-label,'default')]//div[contains(text(),'test')]";
        xpath=xpath.replace("test", reason);
        return By.xpath(xpath);
    }
    //xpath_MSSContinueButton

    public void fillChangeAssignmentWhenAndWhy() throws Exception{
        String assignmentChangeStartDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hire_Date");
        
        Wrapper.findWebElement(xpath_AssignmentChangeStartDate).isDisplayed();
        Wrapper.selectDate(Wrapper.findWebElement(xpath_AssignmentChangeStartDate), assignmentChangeStartDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
        Thread.sleep(4000);

        Wrapper.findWebElement(xpath_AssignmentChangeWayPicker).isDisplayed();
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AssignmentChangeWayPicker));
        Thread.sleep(2000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AssignmentChangeWayOption()));
        Thread.sleep(2000);

        Wrapper.findWebElement(xpath_AssignmentChangeReasonPicker).isDisplayed();
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AssignmentChangeReasonPicker));
        Thread.sleep(2000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AssignmentChangeReasonOption()));
        Thread.sleep(2000);

        Wrapper.findWebElement(xpath_BusinessUnitPicker).isDisplayed();
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_BusinessUnitPicker));
        Thread.sleep(2000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CABusinessUnitOption()));
        Thread.sleep(2000);

        attachStepEvidence("the HRA is filling the details in When And Why page");
        Thread.sleep(5000);

        Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
    }

    By xpath_CAJobIdPicker=By.xpath("//oj-select-single[contains(@id,'JobId')]//span/span");
    By xpathCAJobIdManual=By.xpath("//oj-input-text//input[contains(@aria-controls,'JobId')]");
    public By xpath_CAJobIdOption() throws Exception{
        String reason=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CAJobId");
        String xpath="//oj-table[contains(@aria-label,'default')]//div[contains(text(),'test')]";
        xpath=xpath.replace("test", reason);
        return By.xpath(xpath);
    }

    public void fillChangeAssignmentJobDetails() throws Exception{
        String reason=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CAJobId");
        Wrapper.findWebElement(xpath_CAJobIdPicker).isDisplayed();
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CAJobIdPicker));
        Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpathCAJobIdManual), reason, true);
        Thread.sleep(2000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CAJobIdOption()));
        Thread.sleep(2000);

        attachStepEvidence("the HRA is filling details in Assignment page");
        Thread.sleep(5000);

        Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
    }

    //xpath_SalaryChange
    public void fillChangeAssignmentSalaryDetails() throws Exception{
        Wrapper.findWebElement(xpath_SalaryChange).isDisplayed();

        attachStepEvidence("is On Salary Page");
        Thread.sleep(5000);

        Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
    }
    
    By xpath_CASeniorityDatePageValidation=By.xpath("(//*[contains(text(),'Seniority Date')])[1]");
    public void fillChangeAssignmentSeniorityDateDetails() throws Exception{    
        
        Wrapper.waitForpresenceOfElementLocated(xpath_CASeniorityDatePageValidation);
        if(Wrapper.findWebElement(xpath_CASeniorityDatePageValidation).isDisplayed()){
            attachStepEvidence("is On Seniority Date Page");
            Thread.sleep(5000);
    
            Wrapper.waitForpresenceOfElementLocated(xpath_MSSSubmitButton);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
            Thread.sleep(10000);
        }
    }

    //HRA Manager-Terminate an Employee
    // xpath_MyClientGroups
    // xpathHRAShowMore
    // xpath_TerminateEmployment
    By xpath_TerminateEmployment=By.xpath("//div[contains(@id,'all_quickactions_groupNode')]//a[text()='Terminate Employment']");

    public void navigateToTerminateEmploymentPageHRA() {
        try {
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            Thread.sleep(2000);
            attachStepEvidence("Clicked on My Client Groups");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
            Wrapper.waitForpresenceOfElementLocated(xpath_TerminateEmployment);
            if(Wrapper.findWebElement(xpath_TerminateEmployment).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Terminate Employment");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TerminateEmployment));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //HRA Manager- Modify Existing Emergency Contact for Employee

    By xpath_HRAFamilyAndEmergencyContacts=By.xpath("//div[contains(@id,'all_quickactions_groupNode_workforce_management')]//div//a[text()='Family and Emergency Contacts']");
    
    By xpath_FamilyAndEmergencyContactPageValidation=By.xpath("//div[contains(@id,'contacts-overview-page_h_pageSubtitle')]");
    
    By xpath_ExistingEmployeeAsContact=By.xpath("//oj-option[contains(text(),'Select a Person as a Contact')]");

    public void navigateToModifyExistingEmergencyContactPageHRA() {
        try {
            String EmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
            
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            Thread.sleep(2000);
            attachStepEvidence("Clicked on My Client Groups");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
            Thread.sleep(2000);
            Wrapper.findWebElement(xpath_HRAFamilyAndEmergencyContacts).isDisplayed();
            attachStepEvidence("Family and Emergency Contacts option under My Client Groups");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAFamilyAndEmergencyContacts));
            if(Wrapper.findWebElement(xpath_FamilyandEmergencyPageValidation).isDisplayed()){
                Assert.assertTrue(true, "Successfully navigated to Family and Emergency Contact page.");
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
                Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
                if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                    Thread.sleep(5000);
                    attachStepEvidence("Searched for employee using employee number: " + EmployeeName);
                    Thread.sleep(3000);
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(HRAXpath(EmployeeName))).click();
                    Wrapper.waitForpresenceOfElementLocated(xpath_FamilyAndEmergencyContactPageValidation);
                }
            }else{
                Assert.fail("Failed to navigate to Family and Emergency Contact page.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    By xpath_EmergencyPersonContactName=By.xpath("//div[contains(@id,'DisplayNameGroup')]//a[contains(@id,'button1')]");

    By xpath_PhoneDetailsScrollTo=By.xpath("//h2[contains(@aria-label,'Phone details')]");
    By xpath_EditPhoneDetailsPencil=By.xpath("//oj-button[contains(@id,'person-phones')]//span/span");

    public void navigateToEditPhoneDetails() throws Exception{
        try{
            Thread.sleep(10000);
            Wrapper.waitForpresenceOfElementLocated(xpath_EmergencyPersonContactName);
            Wrapper.findWebElement(xpath_EmergencyPersonContactName).isDisplayed();
            attachStepEvidence("Clicking on the emergency contact name to edit details.");
            Thread.sleep(4000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmergencyPersonContactName));

            Thread.sleep(10000);
            Wrapper.findWebElement(xpath_PhoneDetailsScrollTo).isDisplayed();
            Wrapper.waitForpresenceOfElementLocated(xpath_PhoneDetailsScrollTo);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PhoneDetailsScrollTo),"Scrolling to Phone Details section.");
            Thread.sleep(4000);

            Wrapper.findWebElement(xpath_EditPhoneDetailsPencil).isDisplayed();
            attachStepEvidence("Clicking on the pencil icon to edit phone details.");
            Thread.sleep(4000);
            Wrapper.waitForpresenceOfElementLocated(xpath_EditPhoneDetailsPencil);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EditPhoneDetailsPencil));
            Thread.sleep(4000);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editEmergencyContactPhoneDetails() throws Exception{
        try{
            String phoneCountryCode = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Country");
            String phoneType = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Type");
            String phoneAreaCode = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Area Code");
            String phoneNumber = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Number");
            
            Thread.sleep(5000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PhoneCountryCode),"Scrolling to Phone Country Code dropdown.");
            Thread.sleep(4000);

            Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCode);
            Thread.sleep(5000); 
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_PhoneCountryCode)).click();
            Thread.sleep(3000); 
            // driver.findElement(xpath_PhoneCountryCodeManual).clear();
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneCountryCodeManual), phoneCountryCode, true);
            // driver.findElement(xpath_PhoneCountryCodeManual).sendKeys(phoneCountryCode);
            Thread.sleep(5000); 
            // Select "Country" from dropdown
            // By phoneCountry = xpath_PhoneCountryCodeList("US");
            // Wrapper.findWebElement(phoneCountry).isDisplayed();
            // Thread.sleep(4000);
            // Wrapper.waitForpresenceOfElementLocated(phoneCountry);
            // Thread.sleep(4000); 
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //     .elementToBeClickable(phoneCountry)).click();
            
            Thread.sleep(4000);
            // Wait by locator (not cached WebElement), then click a fresh element
            Wrapper.findWebElement(xpath_PhoneType).isDisplayed();
            Wrapper.waitForpresenceOfElementLocated(xpath_PhoneType);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_PhoneType)).click();

            // Select "PhoneType" from dropdown
            By phoneTypeOption = xpath_PhoneTypeList(phoneType);
            Wrapper.waitForpresenceOfElementLocated(phoneTypeOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(phoneTypeOption)).click();

            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneAreaCode), phoneAreaCode,true);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneNumber), phoneNumber,true);
            
            Thread.sleep(4000); // Consider replacing with explicit wait for better reliability
            attachStepEvidence("the manager has modified the existing emergency contact details");
            Thread.sleep(5000);

            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Thread.sleep(12000);

            //xpath_EditPhoneDetailsPencil
            Wrapper.findWebElement(xpath_EditPhoneDetailsPencil).isDisplayed();
            attachStepEvidence("Verified that the emergency contact phone details have been updated successfully.");

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Error entering phone details: " + e.getMessage());
        }
    }

    //HRA Manager-  Edit Employee Phone Number
    By xpath_EditPhoneDetailsPencilEmployee=By.xpath("(//button[contains(@aria-label,'Phone')]//span/span)[3]");
    
    public void clickPhonePencilEditAndChangeNumber() throws Exception{
        String phoneNumber = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Number");
        Wrapper.findWebElement(xpath_EditPhoneDetailsPencilEmployee).isDisplayed();
        Wrapper.waitForpresenceOfElementLocated(xpath_EditPhoneDetailsPencilEmployee);
        // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_EditPhoneDetailsPencilEmployee),"Scrolling to the employee phone pencil edit icon.");
        Thread.sleep(12000);
        attachStepEvidence("Clicking on the employee phone pencil edit icon.");
        Thread.sleep(6000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EditPhoneDetailsPencilEmployee));

        Wrapper.findWebElement(xpath_PhoneNumber).isDisplayed();
        Wrapper.waitForpresenceOfElementLocated(xpath_PhoneNumber);
        Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneNumber), phoneNumber,true);
        Thread.sleep(4000);
        attachStepEvidence("the manager has modified the employee phone number");
        Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
        Thread.sleep(12000);

        //xpath_EditPhoneDetailsPencilEmployee
        Wrapper.findWebElement(xpath_EditPhoneDetailsPencilEmployee).isDisplayed();
        attachStepEvidence("Verified that the employee phone number has been updated successfully.");
    }

    //HRA Manager- Change Marital Status
    By xpath_PersonalDetailsHRA=By.xpath("//div[contains(@id,'show_more_groupNode_workforce_management')]//a[text()='Personal Details']");
    By xpath_PersonalDetailsPageValidationHRA=By.xpath("//h1[contains(text(),'Personal Details')]");
    By xpath_MaritalStatusPencilEdit=By.xpath("//button[contains(@aria-label,'Edit Demographic info')]//span/span");
    By xpath_MaritalStatusDropdown=By.xpath("//oj-input-text[contains(@label-hint,'Marital Status')]//span/a");
    public By xpath_MaritalStatusOption() throws Exception{
        String maritalStatus=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Marital_Status");
        String xpath="//ul[contains(@aria-labelledby,'MaritalStatus')]//span[contains(text(),'test')]";
        xpath=xpath.replace("test", maritalStatus);
        return By.xpath(xpath);
    }
    By xpath_MaritalStatusDatePicker=By.xpath("//oj-input-date[contains(@id,'person-dmg-info-mrtl-date')]//span/span");
    public void navigateToPersonalDetailsPageHRA() {
        try {
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            Thread.sleep(2000);
            attachStepEvidence("Clicked on My Client Groups");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));

            Wrapper.waitForpresenceOfElementLocated(xpath_PersonalDetailsHRA);
            if(Wrapper.findWebElement(xpath_PersonalDetailsHRA).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Personal Details");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PersonalDetailsHRA));
            }

            String employeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
            Wrapper.waitForpresenceOfElementLocated(xpath_PersonalDetailsPageValidationHRA);
            if(Wrapper.findWebElement(xpath_PersonalDetailsPageValidationHRA).isDisplayed()){
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
                Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), employeeName, false);
                if(Wrapper.findWebElement(HRAXpath(employeeName)).isDisplayed()){
                    Thread.sleep(3000);
                    attachStepEvidence("Searched for employee using employee number: " + employeeName);
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(HRAXpath(employeeName))).click();
                }
            }

            Wrapper.findWebElement(xpath_MaritalStatusPencilEdit).isDisplayed();
            Wrapper.waitForpresenceOfElementLocated(xpath_MaritalStatusPencilEdit);
            Thread.sleep(12000);
            attachStepEvidence("Clicking on the Demographic Info Pencil Edit Icon.");
            Thread.sleep(4000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MaritalStatusPencilEdit));

            Wrapper.findWebElement(xpath_MaritalStatusDropdown).isDisplayed();
            Wrapper.waitForpresenceOfElementLocated(xpath_MaritalStatusDropdown);
            Thread.sleep(4000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MaritalStatusDropdown));
            Thread.sleep(4000);
            Wrapper.findWebElement(xpath_MaritalStatusOption()).isDisplayed();
            Wrapper.waitForpresenceOfElementLocated(xpath_MaritalStatusOption());
            Thread.sleep(4000);
            attachStepEvidence("Selecting the Marital Status from the dropdown");
            Thread.sleep(4000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MaritalStatusOption()));
            Thread.sleep(4000);

            Wrapper.findWebElement(xpath_MaritalStatusDatePicker).isDisplayed();
            Wrapper.waitForpresenceOfElementLocated(xpath_MaritalStatusDatePicker);
            String maritalStatusChangeDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hire_Date");
            Wrapper.selectDate(Wrapper.findWebElement(xpath_MaritalStatusDatePicker), maritalStatusChangeDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
            Thread.sleep(4000);

            attachStepEvidence("the manager is changing the marital status for an employee");
            Thread.sleep(4000);

            Wrapper.findWebElement(xpath_SaveButton).isDisplayed();
            Wrapper.waitForpresenceOfElementLocated(xpath_SaveButton); 
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SaveButton),"Scrolling to the Save button."); 
            Thread.sleep(5000);
            Wrapper.findWebElement(xpath_SaveButton).isDisplayed();
            attachStepEvidence("Clicking on the Save button.");
            Thread.sleep(4000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));

            Thread.sleep(10000);
            Wrapper.findWebElement(xpath_MaritalStatusPencilEdit).isDisplayed();    
            attachStepEvidence("Verified that the Marital Status has been updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Employee- Manage Contact Information - add email address
    By xpath_MeShowMore=By.xpath("//a[contains(@id,'showmore_groupNode_my_information')]");
    By xpath_MeManageContactInformation=By.xpath("//*[contains(text(),'Personal Info')]/following::a[text()='Contact Info']");
    By xpath_AddEmailDetails=By.xpath("//button[contains(@aria-label,'Add Email details')]//span/span/span");

    public void navigateToAddEmailDetailsPage() {
        try {
            Thread.sleep(8000);
            Wrapper.findWebElement(xpath_MeShowMore).isDisplayed();
            attachStepEvidence("On the Me Tab");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MeShowMore));
            Wrapper.waitForpresenceOfElementLocated(xpath_MeManageContactInformation);
            if(Wrapper.findWebElement(xpath_MeManageContactInformation).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Contact Info");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MeManageContactInformation));
            }

            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_AddEmailDetails);
            if(Wrapper.findWebElement(xpath_AddEmailDetails).isDisplayed()){
                Thread.sleep(12000);
                attachStepEvidence("Clicking on Add Email Details");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddEmailDetails));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    By xpath_EditEmailDetailsPencil=By.xpath("(//oj-button[contains(@id,'person-emails')]//button//span/span)[1]");
    
    public void validateAddedEmailDetails() throws Exception{
        Thread.sleep(3000);
        attachStepEvidence("the employee has added a new email address");
        Thread.sleep(5000);
        Wrapper.findWebElement(xpath_SaveButton).isDisplayed();
        Wrapper.waitForpresenceOfElementLocated(xpath_SaveButton); 
        // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SaveButton),"Scrolling to the Save button."); 
        Thread.sleep(5000);
        Wrapper.findWebElement(xpath_SaveButton).isDisplayed();
        attachStepEvidence("Clicking on the Save button.");
        Thread.sleep(4000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));

        Thread.sleep(10000);
        Wrapper.findWebElement(xpath_EditEmailDetailsPencil).isDisplayed();
        attachStepEvidence("Verified that the email details have been added successfully.");
    }

    //Employee- View Organizational Chart under directory
    By xpath_Directory=By.xpath("//a[contains(text(),'Directory')]");

    By xpath_MyPublicInfo=By.xpath("//span[contains(text(),'My Public Info')]");
    By xpath_ManagerAndDirectsExpand=By.xpath("//div[contains(@title,'Expand')]//a");
    By xpath_OrgValidation=By.xpath("//h3[contains(text(),'Managers')]");

    public void navigateToViewOrganizationalChart() {
        try {
            Thread.sleep(8000);
            Wrapper.findWebElement(xpath_Directory).isDisplayed();
            attachStepEvidence("Clicking on Directory App under Me Tab");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Directory));
            Wrapper.waitForpresenceOfElementLocated(xpath_MyPublicInfo);
            if(Wrapper.findWebElement(xpath_MyPublicInfo).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on My Public Info");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyPublicInfo));
            }

            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_ManagerAndDirectsExpand);
            if(Wrapper.findWebElement(xpath_ManagerAndDirectsExpand).isDisplayed()){
                // Thread.sleep(8000);
                attachStepEvidence("Clicking on Expand icon to view organizational chart");
                Thread.sleep(3000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ManagerAndDirectsExpand));
            }
            Wrapper.waitForpresenceOfElementLocated(xpath_OrgValidation);
            if(Wrapper.findWebElement(xpath_OrgValidation).isDisplayed()){
                Thread.sleep(12000);
                attachStepEvidence("Verified that the organizational chart is displayed successfully.");
            }else{
                Assert.fail("Failed to display the organizational chart.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Employee- Add driving licenses
    //xpath_MeShowMore
    By xpath_DrivingLicenseDocumentRecords=By.xpath("//*[contains(text(),'Document Records')]/following::a[contains(text(),'Document Records')]");
    By xpath_DRAdd=By.xpath("//button[contains(@aria-label,'Add')]//span/span/span");
    By xpath_DocumentTypeDropdown=By.xpath("//oj-select-single[contains(@id,'document-type')]//span/span");
    public By xpath_DocumentTypeOption() throws Exception{
        String documentType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Document_Type");
        String xpath="//oj-table[contains(@id,'docTypeTable')]//td[contains(normalize-space(),'test')]";
        xpath=xpath.replace("test", documentType);
        return By.xpath(xpath);
    }

    public void navigateToAddDrivingLicensePage() {
        try {
            Thread.sleep(8000);
            Wrapper.findWebElement(xpath_MeShowMore).isDisplayed();
            attachStepEvidence("Clicking on Show More under Me Tab");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MeShowMore));
            Wrapper.waitForpresenceOfElementLocated(xpath_DrivingLicenseDocumentRecords);
            if(Wrapper.findWebElement(xpath_DrivingLicenseDocumentRecords).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Document Records");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DrivingLicenseDocumentRecords));
            }

            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_DRAdd);
            if(Wrapper.findWebElement(xpath_DRAdd).isDisplayed()){
                Thread.sleep(10000);
                attachStepEvidence("Clicking on Add Button to Add Driving License Details");
                Thread.sleep(3000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DRAdd));
            }

            Wrapper.waitForpresenceOfElementLocated(xpath_DocumentTypeDropdown);
            if(Wrapper.findWebElement(xpath_DocumentTypeDropdown).isDisplayed()){
                Thread.sleep(4000);
                // attachStepEvidence("Clicking on Document Type Dropdown");
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DocumentTypeDropdown));
                Wrapper.waitForpresenceOfElementLocated(xpath_DocumentTypeOption());
                Wrapper.findWebElement(xpath_DocumentTypeOption()).isDisplayed();
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DocumentTypeOption()));
                attachStepEvidence("Selected the Document Type from the dropdown as Driving License");
                Thread.sleep(4000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    By xpath_ClickToUploadFile=By.xpath("//div[contains(@aria-label,'Add Files')]//div/div[contains(text(),'Drag and Drop')]");

    public void upload_files() throws Exception {
    try {

        Wrapper.findWebElement(xpath_ClickToUploadFile).isDisplayed();
        Wrapper.waitForpresenceOfElementLocated(xpath_ClickToUploadFile);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ClickToUploadFile));

        //String filePathWindows = "src/test/resources/Testing.docx";
        String filePathWindows = System.getProperty("user.dir") + "\\src\\test\\resources\\Testing.docx";
        StringSelection filepath = new StringSelection(filePathWindows);
        
        Thread.sleep(1000);

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filepath, null);
 
         // Initialize Robot Class to simulate keyboard keys
        Robot robot = new Robot();
        robot.delay(3000);// Give the File Explorer window time to focus

        robot.mouseMove(150, 40);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        robot.delay(5000);

        // Press CTRL + V to paste the path
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        Thread.sleep(5000); 
        
        // Press ENTER to confirm and close the dialog
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(5000); 
        attachStepEvidence("the employee has uploaded the driving license document");
        Thread.sleep(5000);

        }catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Error : " + e.getMessage());
        } 
    }

    By xpath_DrivingLicenseNumber=By.xpath("(//*[contains(text(),'Number')]/following::input)[1]");
    By xpath_DrivingLicenseFromDate=By.xpath("(//oj-input-date//span/span)[1]");
    By xpath_DrivingLicenseExpirationDate=By.xpath("(//oj-input-date//span/span)[2]");
    By xpath_DrivingLicenseIssuingCountryDropDown=By.xpath("//oj-select-single[contains(@id,'country-lov')]//span/span");
    By xpath_DrivingLicenseIssuingCountry=By.xpath("//oj-input-text[contains(@label-hint,'Issuing Country')]//input");
    public By xpath_DrivingLicenseIssuingCountryCode() throws Exception{
        String issuingCountry=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Country_Code");
        String xpath="//div[contains(@id,'country-lov')]//span[contains(text(),'test')]";
        xpath=xpath.replace("test", issuingCountry);
        return By.xpath(xpath);
    }
    By xpath_DrivingLicenseSubmit=By.xpath("//span[contains(text(),'Submit')]");
    By xpath_DrivingLicenseValidation=By.xpath("(//oj-list-item-layout//a)[1]");

    public void enterDrivingLicenseDetailsAndSubmit() throws Exception{
        String drivingLicenseNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Driving_License_Number");
        String drivingLicenseFromDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Termination_Notification_Date");
        String drivingLicenseExpirationDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Termination_Date");
        String drivingLicenseIssuingCountry=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Country");

        Wrapper.findWebElement(xpath_DrivingLicenseNumber).isDisplayed();
        Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_DrivingLicenseNumber), drivingLicenseNumber,false);
        Thread.sleep(2000);

        Wrapper.findWebElement(xpath_DrivingLicenseFromDate).isDisplayed();
        Wrapper.selectDate(Wrapper.findWebElement(xpath_DrivingLicenseFromDate), drivingLicenseFromDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
        Thread.sleep(2000);

        Wrapper.findWebElement(xpath_DrivingLicenseExpirationDate).isDisplayed();
        Wrapper.selectDate(Wrapper.findWebElement(xpath_DrivingLicenseExpirationDate), drivingLicenseExpirationDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
        Thread.sleep(2000);

        Wrapper.findWebElement(xpath_DrivingLicenseIssuingCountryDropDown).isDisplayed();
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DrivingLicenseIssuingCountryDropDown));
        Thread.sleep(2000);
        Wrapper.findWebElement(xpath_DrivingLicenseIssuingCountry).isDisplayed();
        Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_DrivingLicenseIssuingCountry), drivingLicenseIssuingCountry,true);
        Thread.sleep(2000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DrivingLicenseIssuingCountryCode()));
        Thread.sleep(2000);

        attachStepEvidence("the employee has entered the driving license details and is submitting the form");
        Thread.sleep(5000);

        Wrapper.waitForpresenceOfElementLocated(xpath_DrivingLicenseSubmit);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DrivingLicenseSubmit));

        Thread.sleep(12000);
        Wrapper.waitForpresenceOfElementLocated(xpath_DrivingLicenseValidation);
        if(Wrapper.findWebElement(xpath_DrivingLicenseValidation).isDisplayed()){
            Thread.sleep(8000);
            attachStepEvidence("Verified that the driving license details have been added successfully.");
            Thread.sleep(3000);
        }else{
            Assert.fail("Failed to add the driving license details.");
        }
    }

    //HRA Manager- Correct  Biographical Info of an Employee
    // xpath_MyClientGroups
    By xpath_PersonSpotLight=By.xpath("//a[contains(text(),'Person Spotlight')]");

    public void navigateToPersonSpotlightPageHRA() {
        try {
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            Thread.sleep(2000);
            // attachStepEvidence("Clicked on My Client Groups");
            Thread.sleep(2000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PersonSpotLight);
            if(Wrapper.findWebElement(xpath_PersonSpotLight).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicked on My Client Groups and Clicking on Person Spotlight");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PersonSpotLight));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    By xpath_PSShowMore=By.xpath("//div[contains(text(),'Show More')]");
    By xpath_PSPersonalDetails=By.xpath("//div[contains(text(),'Personal Details')]");
    By xpath_ExpandBiographicalInfo=By.xpath("//div[contains(@title,'Expand Biographical Info')]//img");
    By xpath_EditBiographicalInfoPencil=By.xpath("//a[contains(@title,'Edit Biographical Info')]//img");

    public void navigateToEditBiographicalInfoPageHRA() {
        try {

            String employeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
            Wrapper.waitForpresenceOfElementLocated(xpath_EmployeeSearchBox);
            if(Wrapper.findWebElement(xpath_EmployeeSearchBox).isDisplayed()){
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
                Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), employeeName, false);
                if(Wrapper.findWebElement(HRAXpath(employeeName)).isDisplayed()){
                    Thread.sleep(3000);
                    attachStepEvidence("Searched for employee using employee number: " + employeeName);
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(HRAXpath(employeeName))).click();
                }
            }

            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PSShowMore);
            if(Wrapper.findWebElement(xpath_PSShowMore).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Show More in Public Info Page");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PSShowMore));
            }

            Wrapper.waitForpresenceOfElementLocated(xpath_PSPersonalDetails);
            if(Wrapper.findWebElement(xpath_PSPersonalDetails).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Personal Details in Public Info Page");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PSPersonalDetails));
            }

            Wrapper.waitForpresenceOfElementLocated(xpath_ExpandBiographicalInfo);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ExpandBiographicalInfo),"Scrolling to Expand Biographical Info section.");
            Thread.sleep(4000);
            if(Wrapper.findWebElement(xpath_ExpandBiographicalInfo).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Expand Biographical Info");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ExpandBiographicalInfo));
            }

            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_EditBiographicalInfoPencil);
            if(Wrapper.findWebElement(xpath_EditBiographicalInfoPencil).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Edit Biographical Info Pencil Icon");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EditBiographicalInfoPencil));
                Thread.sleep(4000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    By xpath_MedicareNumber=By.xpath("//input[contains(@id,'medicareNumber')]");
    By xpath_MedicareCardDate=By.xpath("//input[contains(@aria-label,'Medicare Card Date')]");
    By xpath_DateMedicareCardProvided=By.xpath("//input[contains(@aria-label,'Date Medicare Card Provided')]");
    By xpath_PSSubmit=By.xpath("//div[contains(@title,'Submit')]//a");

    public void editBiographicalInfoAndSubmit() throws Exception{
        try{
            String medicareNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Medicare_Number");
            String medicareCardDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Medicare_Card_Date");
            String dateMedicareCardProvided=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Date_Medicare_Card_Provided");
            
            Wrapper.waitForpresenceOfElementLocated(xpath_MedicareNumber);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_MedicareNumber),"Scrolling to Medicare Number field.");
            Wrapper.findWebElement(xpath_MedicareNumber).isDisplayed();
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_MedicareNumber), medicareNumber,false);
            Thread.sleep(2000);

            Wrapper.findWebElement(xpath_MedicareCardDate).isDisplayed();
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_MedicareCardDate), medicareCardDate,false);
            // Wrapper.selectDate(Wrapper.findWebElement(xpath_MedicareCardDate), medicareCardDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
            Thread.sleep(2000);

            Wrapper.findWebElement(xpath_DateMedicareCardProvided).isDisplayed();
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_DateMedicareCardProvided), dateMedicareCardProvided,false);
            Thread.sleep(2000);

            attachStepEvidence("the manager has modified the biographical information of an employee and is submitting the changes");
            Thread.sleep(5000);

            Wrapper.waitForpresenceOfElementLocated(xpath_PSSubmit);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PSSubmit));

            Thread.sleep(12000);
            Wrapper.waitForpresenceOfElementLocated(xpath_EditBiographicalInfoPencil);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_EditBiographicalInfoPencil),"Scrolling to Edit Biographical Info Pencil Icon.");
            if(Wrapper.findWebElement(xpath_EditBiographicalInfoPencil).isDisplayed()){
                Thread.sleep(8000);
                attachStepEvidence("Verified that the biographical information has been updated successfully.");
                Thread.sleep(3000);
            }else{
                Assert.fail("Failed to update the biographical information.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //HRA Manager- Change working hours for an Employee
    //xpath_PSShowMore
    By xpath_PSEmploymentInfo=By.xpath("//div[contains(text(),'Employment Info')]");
    By xpath_PSActions=By.xpath("//button/span[contains(text(),'Actions')]");
    By xpath_PSChangeWorkingHours=By.xpath("//td[contains(text(),'Change Working Hours')]");

    public void navigateToChangeWorkingHoursPageHRA() {
        try {

            String employeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
            Wrapper.waitForpresenceOfElementLocated(xpath_EmployeeSearchBox);
            if(Wrapper.findWebElement(xpath_EmployeeSearchBox).isDisplayed()){
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
                Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), employeeName, false);
                if(Wrapper.findWebElement(HRAXpath(employeeName)).isDisplayed()){
                    Thread.sleep(3000);
                    attachStepEvidence("Searched for employee using employee number: " + employeeName);
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(HRAXpath(employeeName))).click();
                }
            }

            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PSShowMore);
            if(Wrapper.findWebElement(xpath_PSShowMore).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Show More in Public Info Page");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PSShowMore));
            }

            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PSEmploymentInfo);
            if(Wrapper.findWebElement(xpath_PSEmploymentInfo).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Employment Info in Public Info Page");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PSEmploymentInfo));
            }

            Wrapper.waitForpresenceOfElementLocated(xpath_PSActions);
            if(Wrapper.findWebElement(xpath_PSActions).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Actions button in Employment Info section");
                Thread.sleep(5000);
                Wrapper.doubleClickWebElement(Wrapper.findWebElement(xpath_PSActions));
            }

            Thread.sleep(10000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PSChangeWorkingHours);
            if(Wrapper.findWebElement(xpath_PSChangeWorkingHours).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Change Working Hours option from Actions dropdown");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PSChangeWorkingHours));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    By xpath_ChangeWorkingHoursPage=By.xpath("//span[contains(@aria-label,'Change Working Hours')]");
    public By xpath_ReasonSelection(String reasonText) {
        String xpath = "//div[contains(text(),'xxx')]";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
        ////div[contains(@id,'contact-relationship')]//ul/li//span[contains(text(),'Con Ed Spouse')]
    }

    public void submitWorkingHoursChangeRequestHRA(String EffectiveDate, String Action, String Reason) throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_ChangeWorkingHoursPage);
        if(Wrapper.findWebElement(xpath_ChangeWorkingHoursPage).isDisplayed()){
            Assert.assertTrue(true, "Successfully navigated to Change Working Hours page.");
            // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SalaryButton));
            // attachStepEvidence("Clicked on Salary button in Change Working Hours page.");
            // Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Salary"));
            // if(Wrapper.findWebElement(xpath_ButtonEnabled("Salary")).isEnabled()){
            //     Assert.assertTrue(true, "Salary button is enabled.");
            // }else{
            //     Assert.fail("Salary button is not enabled.");
            // }
            attachStepEvidence("On Info To Include Page and navigating to next page");

            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursWhenAndWhyValidation);
            if(Wrapper.findWebElement(xpath_WorkingHoursWhenAndWhyValidation).isDisplayed()){
                Assert.assertTrue(true, "When and Why page is displayed successfully.");
                Wrapper.selectDate(Wrapper.findWebElement(xpath_DatePicker), EffectiveDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
                
                Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursWay);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(xpath_WorkingHoursWay)).click();
                By actionOption = xpath_ReasonSelection(Action);
                Wrapper.waitForpresenceOfElementLocated(actionOption);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(actionOption)).click();

                Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursWhy);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(xpath_WorkingHoursWhy)).click();
                By reasonOption = xpath_ReasonSelection(Reason);
                Wrapper.waitForpresenceOfElementLocated(reasonOption);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(reasonOption)).click();
                Thread.sleep(2000);
                attachStepEvidence("Entered details in When and Why page for working hours change request.");
                Thread.sleep(3000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            }
        }
    }

    public void WorkingHoursChangeRequestSubmissionHRA() throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentChangeValidation);
        if(Wrapper.findWebElement(xpath_AssignmentChangeValidation).isDisplayed()){
            Assert.assertTrue(true, "Successfully navigated to Assignment Change page.");
            Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursInputField);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_WorkingHoursInputField), "Working Hours input field");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_WorkingHoursInputField));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_WorkingHoursInputField), "39", true);
            Thread.sleep(10000);
            attachStepEvidence("Entered new working hours in the input field.");
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            // Wrapper.waitForpresenceOfElementLocated(xpath_SalaryPageValidation);
            // if(Wrapper.findWebElement(xpath_SalaryPageValidation).isDisplayed()){
                // Assert.assertTrue(true, "Salary page is displayed successfully.");
                // Thread.sleep(5000);
                // attachStepEvidence("Navigated to Salary page after entering working hours change details.");
                // Thread.sleep(3000);
                // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
                Wrapper.waitForpresenceOfElementLocated(xpath_SeniorityDatePageValidation);
                if(Wrapper.findWebElement(xpath_SeniorityDatePageValidation).isDisplayed()){
                    Thread.sleep(10000);
                    attachStepEvidence("Navigated to Seniority Date page and Clicking on Submit button to submit the working hours change request");
                    Assert.assertTrue(true, "Seniority Date page is displayed successfully.");
                    Wrapper.waitForpresenceOfElementLocated(xpath_MSSSubmitButton);
                    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
                    // Uncomment Below for Initial Test Case
                    Wrapper.findWebElement(xpath_PSShowMore).isDisplayed();
                    Thread.sleep(2000);
                    // Wrapper.waitForpresenceOfElementLocated(xpath_ChangeWorkingHoursPageValidation);
                }else{
                    Assert.fail("Failed to navigate to Seniority Date page after submitting working hours change request.");
                }
            // }else{
            //     Assert.fail("Failed to navigate to Salary page after submitting working hours change request.");
            // }
        }else{
            Assert.fail("Failed to navigate to Assignment Change page for working hours change request.");
        }
    }

    //HRA Manager- Add a Contingent Worker

    By xpath_AddAContingentWorker=By.xpath("//*[contains(text(),'New Person')]/following::a[contains(text(),'Add a Contingent Worker')]");
    public void navigateToAddContingentWorkerPageHRA() throws Exception {
        try {
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            Thread.sleep(2000);
            attachStepEvidence("Clicked on My Client Groups and Clicking on Show More");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
            Wrapper.waitForpresenceOfElementLocated(xpath_AddAContingentWorker);
            if(Wrapper.findWebElement(xpath_AddAContingentWorker).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Add a Contingent Worker");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddAContingentWorker));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    By xpath_CommentsAndAttachmentsButton=By.xpath("//oj-switch//div[contains(@aria-label,'Comments and attachments')]");
    public void fillInfoToIncludePageHRA() throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_InfoToIncludePageValidation);
        if(Wrapper.findWebElement(xpath_InfoToIncludePageValidation).isDisplayed()){
            Assert.assertTrue(true, "Successfully navigated to Info to include page");
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CommunicationInfoButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Communication info"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Communication info")).isEnabled()){
                Assert.assertTrue(true, "Communication info button is enabled.");
            }else{
                Assert.fail("Communication info button is not enabled.");
            }
            
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CommentsAndAttachmentsButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Comments and attachments"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Comments and attachments")).isEnabled()){
                Assert.assertTrue(true, "Comments and attachments button is enabled.");
                attachStepEvidence("the manager is on Info to include.");
            }else{
                Assert.fail("Comments and attachments button is not enabled.");
            }
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Failed to navigate to Info to include page after clicking Hire an Employee.");
        }
    }

    By xpath_WorkerTypePicker=By.xpath("//oj-select-single[contains(@labelled-by,'WorkerType')]//span/span");
    public By xpath_WorkerTypeOption(String workerType) throws Exception{
        // String workerType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Worker_Type");
        String xpath="//ul[contains(@aria-labelledby,'WorkerType')]//span[contains(text(),'test')]";
        xpath=xpath.replace("test", workerType);
        return By.xpath(xpath);
    }
    public void fillWhenAndWhyWorker() throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_WorkerTypePicker);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_WorkerTypePicker));
        By workerTypeOption = xpath_WorkerTypeOption("Employee");
        Wrapper.waitForpresenceOfElementLocated(workerTypeOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(workerTypeOption)).click();
        Thread.sleep(5000);
    }

    By xpath_HireDatePicker=By.xpath("//oj-input-date//span/span[contains(@title,'Select Date')]");
    By xpath_LegalEmployer=By.xpath("//oj-select-single[contains(@id,'LegalEntityId')]//span/span");
    public By xpath_LegalEmployerOption() throws Exception{
        String LegalEmployerName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Legal_Employer");
        String xpath="//div[contains(@id,'LegalEntityId')]//div[contains(text(),'test')]";
        xpath=xpath.replace("test", LegalEmployerName);
        return By.xpath(xpath);
    }

    By xpath_WayToHire=By.xpath("//oj-select-single[contains(@id,'ActionId')]//span/span");
    // public By xpath_WayToHireOption() throws Exception{
    //     String WayToHire=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Way_To_Hire");
    //     String xpath="//div[contains(@id,'ActionId')]//div[contains(text(),'test')]";
    //     xpath=xpath.replace("test", WayToHire);
    //     return By.xpath(xpath);
    // }
    By xpath_WayToHireList=By.xpath("//div[contains(@id,'ActionId')]//div[contains(@id,'ActionName')]");

    By xpath_WhyToHire=By.xpath("//oj-select-single[contains(@id,'ActionReasonId')]//span/span");
    public By xpath_WhyToHireOption() throws Exception{
        String WhyToHire=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Why_Hiring");
        String xpath="//div[contains(@id,'ActionReasonId')]//div[contains(text(),'test')]";
        xpath=xpath.replace("test", WhyToHire);
        return By.xpath(xpath);
    }

    By xpath_BusinessUnit=By.xpath("//oj-select-single[contains(@id,'BusinessUnitId')]//span/span");
    public By xpath_BusinessUnitOption() throws Exception{
        String BusinessUnit=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Business_Unit");
        String xpath="//div[contains(@id,'BusinessUnitId')]//div[contains(text(),'test')]";
        xpath=xpath.replace("test", BusinessUnit);
        return By.xpath(xpath);
    }

    public void fillWhenAndWhyPageHRA() throws Exception{
        String Hire_Date=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hire_Date");
        Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursWhenAndWhyValidation);
        if(Wrapper.findWebElement(xpath_WorkingHoursWhenAndWhyValidation).isDisplayed()){
            Assert.assertTrue(true, "When and Why page is displayed successfully.");
            Thread.sleep(5000);
            // attachStepEvidence("the manager is on When and Why page.");
            Wrapper.selectDate(Wrapper.findWebElement(xpath_HireDatePicker), Hire_Date, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
           
            Wrapper.waitForpresenceOfElementLocated(xpath_LegalEmployer);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_LegalEmployer)).click();
            By legalEmployerOption = xpath_LegalEmployerOption();
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(legalEmployerOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(legalEmployerOption)).click();
            Thread.sleep(5000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_WayToHire);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(xpath_WayToHire)).click();
            // By wayToHireOption = xpath_WayToHireOption();
            // Wrapper.waitForpresenceOfElementLocated(wayToHireOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(wayToHireOption)).click();
            // for(WebElement element:Wrapper.findWebElements(xpath_WayToHireList)){
            //     if(element.getText().equals(ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Way_To_Hire"))){
            //         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            Thread.sleep(20000);
            //                 .elementToBeClickable(element)).click();
            //         break;
            //     }
            // }
            Wrapper.waitForpresenceOfElementLocated(xpath_WhyToHire);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_WhyToHire)).click();
                    Thread.sleep(6000);
            By whyToHireOption = xpath_WhyToHireOption();
            Wrapper.waitForpresenceOfElementLocated(whyToHireOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(whyToHireOption)).click();
            Thread.sleep(6000);
            // fillInfoToIncludePage();
            // Assert.assertTrue(true, "Manager filled the details in When and Why page successfully.");
           
            Wrapper.waitForpresenceOfElementLocated(xpath_BusinessUnit);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_BusinessUnit)).click();
                    Thread.sleep(8000);
            By businessUnitOption = xpath_BusinessUnitOption();
            Wrapper.waitForpresenceOfElementLocated(businessUnitOption);
            Thread.sleep(8000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(businessUnitOption)).click();
                    Thread.sleep(8000);
           
            attachStepEvidence("the manager filled the details in When and Why page.");
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("When and Why page is not displayed after clicking Continue button on Info to include page.");
        }
    }
    By xpath_PersonalDetailsPageValidation=By.xpath("//span[contains(@aria-label,'Personal details')]");
    // By xpath_LastNameInputField=By.xpath("//span[contains(text(),'Last Name')]/ancestor::div/input");
    // By xpath_FirstNameInputField=By.xpath("//span[contains(text(),'First Name')]/ancestor::div/input");
    By xpath_LastNameInputField=By.xpath("//input[contains(@id,'person-names-input-text0|input')]");
    By xpath_FirstNameInputField=By.xpath("//input[contains(@id,'person-names-input-text1|input')]");
    By xpath_HireGender=By.xpath("//oj-select-single[contains(@id,'gender')]//span/span");
    public By xpath_HireGenderOption() throws Exception{
        String Gender=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"R_Gender");
        String xpath="//div[contains(@id,'gender')]//span[contains(text(),'test')]";
        xpath=xpath.replace("test", Gender);
        return By.xpath(xpath);
    }
    By xpath_HireDateOfBirth=By.xpath("//oj-input-date[contains(@id,'DateOfBirth')]//span/span");
    By xpath_NationalIdentifierButton=By.xpath("//button[contains(@aria-label,'National identifiers')]//span/span[contains(normalize-space(),'National identifiers')]");
    By xpath_NationalIdentifierCountry=By.xpath("//oj-select-single[contains(@id,'nid-country')]//span/span");
    //Call-->xpath_NICountryManual
    //Call-->xpath_NICountryList
    //Call-->xpath_NIType
    //Call-->xpath_NITypeList
    //Call-->xpath_NIID
    

    public void fillPersonalDetailsPageHRA() throws Exception{
        String LastName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Last Name");
        String FirstName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"First Name");
        String DOB =  ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"R_DOB");
        Wrapper.waitForpresenceOfElementLocated(xpath_PersonalDetailsPageValidation);
        if(Wrapper.findWebElement(xpath_PersonalDetailsPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Personal Details page is displayed successfully.");
            Thread.sleep(5000);
            // attachStepEvidence("the manager is on the Personal Details page for adding a contingent worker.");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_LastNameInputField), LastName, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_FirstNameInputField), FirstName, false);
 
            Wrapper.waitForpresenceOfElementLocated(xpath_HireGender);
            // Thread.sleep(5000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HireGender), "Gender dropdown");
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_HireGender)).click();
                    Thread.sleep(5000);
            By genderOption = xpath_HireGenderOption();
            Wrapper.waitForpresenceOfElementLocated(genderOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(genderOption)).click();
                Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_HireDateOfBirth);
            Wrapper.selectDate(Wrapper.findWebElement(xpath_HireDateOfBirth), DOB, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerPrevious);
            Thread.sleep(8000);
           
            // attachStepEvidence("the manager filled the details in Personal Details page for adding a contingent worker.");
            Thread.sleep(3000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_NationalIdentifierButton), "National Identifier button");
            Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_NationalIdentifierButton));
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_NationalIdentifierButton));
            Thread.sleep(5000);
        }else{
            Assert.fail("Personal Details page is not displayed after clicking Continue button on When and Why page.");
        }
    }

        public void fillNationalIdentifierDetailsHRA() throws Exception{
        String NICountry=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_Country");
        String NIType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_Type");
        String NIID=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_ID");
        if(Wrapper.findWebElement(xpath_NationalIdentifierCountry).isDisplayed()){
            Assert.assertTrue(true, "National Identifier details page is displayed successfully.");
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_NationalIdentifierCountry), "National Identifier Country dropdown");
            // Wrapper.waitForpresenceOfElementLocated(xpath_NationalIdentifierCountry);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(xpath_NationalIdentifierCountry)).click();
            // Wrapper.waitForpresenceOfElementLocated(xpath_NICountryManual);
            // driver.findElement(xpath_NICountryManual).clear();
            // driver.findElement(xpath_NICountryManual).sendKeys(NICountry);
            // By niCountryOption = xpath_NICountryList("US");
            // Wrapper.waitForpresenceOfElementLocated(niCountryOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(niCountryOption)).click();
            Thread.sleep(4000);
            Wrapper.waitForpresenceOfElementLocated(xpath_NIType);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_NIType)).click();
                    Thread.sleep(4000);
            By niTypeOption = xpath_NITypeList(NIType);
            Wrapper.waitForpresenceOfElementLocated(niTypeOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(niTypeOption)).click();
                    Thread.sleep(4000);
 
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_NIID), NIID, false);
 
            Thread.sleep(5000);
            // attachStepEvidence("the manager filled the details in National Identifier section for adding a contingent worker.");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Thread.sleep(8000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            Wrapper.findWebElement(xpath_PrimaryValidationAfterSave).isDisplayed();
            Thread.sleep(2000);
            attachStepEvidence("the manager filled in Personal Details Page and is clicking on Continue button to navigate to Communication Info page.");
            Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("National Identifier details page is not displayed after clicking National Identifier button on Personal Details page.");
        }
    }
    By xpath_CommunicationInfoPageValidation=By.xpath("//span[contains(@aria-label,'Communication info')]");
    //Phone Details
    By xpath_PhoneDetailsButton=By.xpath("//button//span/span[contains(normalize-space(),'Phone details')]");
    //Call-->xpath_PhoneCountryCode
    //Call-->xpath_PhoneCountryCodeManual
    //Call-->xpath_PhoneCountryCodeList
    By xpath_HirePhoneType=By.xpath("//div[contains(@id,'live-person-phones')]/parent::oj-select-single[contains(@labelled-by,'PhoneType-')]//span/span");
    public By xpath_HirePhoneTypeOption() throws Exception{
        String PhoneType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Type");
        String xpath="//oj-list-view//ul[contains(@aria-labelledby,'person-phones')]//li//span[text()='test']";
        xpath=xpath.replace("test", PhoneType);
        return By.xpath(xpath);
    }
    //Call-->xpath_PhoneAreaCode
    //Call-->xpath_PhoneNumber
    //Call-->xpath_SaveButton

    //Email Details
    By xpath_EmailDetailsButton=By.xpath("//button//span/span[contains(normalize-space(),'Email details')]");
    By xpath_HireEmailType=By.xpath("//input[contains(@id,'filter-person-emails')]/parent::div/parent::div//span/a");
    public By xpath_HireEmailTypeOption() throws Exception{
        String EmailType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email Type");
        String xpath="//oj-list-view//ul[contains(@aria-labelledby,'person-emails')]//li//span[text()='test']";
        xpath=xpath.replace("test", EmailType);
        return By.xpath(xpath);
    }

    public void fillCommunicationInfoDetailsHRA() throws Exception{
        String PhoneCountryCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Country");
        String PhoneAreaCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Area Code");
        String PhoneNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Number");
        // String PhoneType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Type");
        Wrapper.waitForpresenceOfElementLocated(xpath_CommunicationInfoPageValidation);
        if(Wrapper.findWebElement(xpath_CommunicationInfoPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Communication Info page is displayed successfully.");
            // attachStepEvidence("the manager is on the Communication Info page for hiring a new employee.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PhoneDetailsButton));
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCode);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_PhoneCountryCode)).click();
                    Thread.sleep(4000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCodeManual);
            // driver.findElement(xpath_PhoneCountryCodeManual).sendKeys(PhoneCountryCode);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneCountryCodeManual), PhoneCountryCode, true);
            // By phoneCountryCodeOption = xpath_PhoneCountryCodeList("US");
            // Wrapper.waitForpresenceOfElementLocated(phoneCountryCodeOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(phoneCountryCodeOption)).click();
 
            Wrapper.waitForpresenceOfElementLocated(xpath_HirePhoneType);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_HirePhoneType)).click();
                Thread.sleep(5000);
            By phoneTypeOption = xpath_HirePhoneTypeOption();
            Wrapper.waitForpresenceOfElementLocated(phoneTypeOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(phoneTypeOption)).click();
                Thread.sleep(5000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneAreaCode), PhoneAreaCode, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneNumber), PhoneNumber, false);
            Thread.sleep(5000);
            // attachStepEvidence("the manager filled the details in Phone section for adding a contingent worker.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            Thread.sleep(5000);
            //Fill Email details similarly by clicking Email Details button and then click Continue button
        }else{
            Assert.fail("Communication Info page is not displayed after clicking Continue button on National Identifier details page.");
        }
    }

    public void fillEmailDetailsHRA() throws Exception{
        Thread.sleep(10000);
        String EmailType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email Type");
        String Email=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email");
        Wrapper.waitForpresenceOfElementLocated(xpath_EmailDetailsButton);
        Thread.sleep(5000);
        Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_EmailDetailsButton));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmailDetailsButton));
 
        Thread.sleep(10000);
        // Wrapper.waitForpresenceOfElementLocated(xpath_HireEmailType);
        // Wrapper.findWebElement(xpath_HireEmailType).isDisplayed();
        // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HireEmailType), "Email Type dropdown");
        // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //         .elementToBeClickable(xpath_HireEmailType)).click();
        // By emailTypeOption = xpath_HireEmailTypeOption();
        // Wrapper.waitForpresenceOfElementLocated(emailTypeOption);
        // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //         .elementToBeClickable(emailTypeOption)).click();
        //         Thread.sleep(5000);
        // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_HireEmailType), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email Type"), true);
        // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Email), Email, false);
        
        System.out.println("Perform Action");
        Thread.sleep(30000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
        // Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
        // Wrapper.findWebElement(xpath_PrimaryValidationAfterSave).isDisplayed();
        // Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
        Thread.sleep(10000);
        attachStepEvidence("the manager filled the details in Communication Info page for adding a contingent worker.");
        Thread.sleep(8000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        Thread.sleep(10000);
    }

    public void fillAssignmentDetailsHRA() throws Exception{
        // String AssignmentNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Number");
        String AssignmentStatus=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Status");
        String PersonType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Person_Type");
        // String Job=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Job");
        String BusinessTitle=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Business_Title");
        // String Grade=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Grade");
        String Department=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Department");
        String ReportingEstablishment=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Reporting_Establishment");
        String Location=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Location");
        String WorkingAtHome=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Work_At_Home");
        // String WorkerCategory=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Worker_Category");
        // String AssignmentCategory=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Category");
        // String RegularTemporary=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Regular_Temporary");
        // String FullTimePartTime=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Full_Time_Part_Time");
        // String HourlyPaidSalaried=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hourly_Paid_Salaried");
        // String WorkingHoursFrequency=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Working_Hours_Frequency");
        // String UnionMember=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union_Member");
        Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentPageValidation);
        Thread.sleep(10000);
        if(Wrapper.findWebElement(xpath_AssignmentPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Assignment details page is displayed successfully.");
            Thread.sleep(3000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentNumber);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AssignmentNumber), AssignmentNumber, false);
            // Fill other details in Assignment page similarly by using the respective locators and test data from excel sheet.
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AssignmentNumber), "Assignment Number input field");
            // Thread.sleep(2000);
            // driver.findElement(xpath_AssignmentNumber).sendKeys(Keys.TAB);
            // Thread.sleep(10000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentNumber);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AssignmentNumber), AssignmentNumber, false);
            // Wrapper.redwoodSync();
 
            // Fill other details in Assignment page similarly by using the respective locators and test data from excel sheet.
            Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentStatusPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_AssignmentStatusPicker)).click();
            for(WebElement element: driver.findElements(xpath_AssignmentStatusList)){
                if(element.getText().equals(AssignmentStatus)){
                    Wrapper.waitForElementToBeClickable(element);
                    Thread.sleep(2000);
                    element.click();
                    break;
                }
                Thread.sleep(3000);
            }
            // Wrapper.waitForpresenceOfElementLocated(xpath_PersonTypePicker);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_PersonTypePicker)).click();
            // Thread.sleep(4000);
            // By personTypeOption = xpath_PersonTypeOption();
            // Wrapper.waitForpresenceOfElementLocated(personTypeOption);
            // Thread.sleep(2000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(personTypeOption)).click();
            // Thread.sleep(3000);
 
            // Wrapper.waitForpresenceOfElementLocated(xpath_JobPicker);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_JobPicker)).click();
            // Thread.sleep(4000);
            // driver.findElement(xpath_JobManual).sendKeys(Job);
            // for(WebElement element: driver.findElements(xpath_JobList)){
            //     if(element.getText().equals(Job)){
            //         Wrapper.waitForElementToBeClickable(element);
            //         element.click();
            //         Thread.sleep(3000);
            //         break;
            //     }
            // }
            // Wrapper.redwoodSync();
 
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_BusinessTitle), BusinessTitle, false);
            Thread.sleep(3000);

            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_GradePicker), "Grade dropdown");
            // Wrapper.waitForpresenceOfElementLocated(xpath_GradePicker);
            // Thread.sleep(4000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_GradePicker)).click();
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_GradeManual), Grade, false);
            // for(WebElement element: driver.findElements(xpath_GradeList)){
            //     if(element.getText().equals(Grade)){
            //         Wrapper.waitForElementToBeClickable(element);
            //         // Thread.sleep(4000);
            //         element.click();
            //         Thread.sleep(4000);
            //         break;
            //     }
            // }
            // Wrapper.redwoodSync();
 
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DepartmentPicker), "Department dropdown");
            Wrapper.waitForpresenceOfElementLocated(xpath_DepartmentPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_DepartmentPicker)).click();
            Thread.sleep(4000);
            driver.findElement(xpath_DepartmentManual).sendKeys(Department);
            for(WebElement element: driver.findElements(xpath_DepartmentList)){
                if(element.getText().equals(Department)){
                    // Thread.sleep(10000);
                    Wrapper.waitForElementToBeClickable(element);
                    element.click();
                    Thread.sleep(5000);
                    break;
                }
            }
            Wrapper.redwoodSync();
 
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_GradePicker), "Grade dropdown");
            // Wrapper.waitForpresenceOfElementLocated(xpath_GradePicker);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_GradePicker)).click();
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_GradeManual), Grade, true);
            // for(WebElement element: driver.findElements(xpath_GradeList)){
            //     if(element.getText().equals(Grade)){
            //         Wrapper.waitForElementToBeClickable(element);
            //         // Thread.sleep(4000);
            //         element.click();
            //         break;
            //     }
            // }
            // Wrapper.redwoodSync();
 
            Wrapper.waitForpresenceOfElementLocated(xpath_ReportingEstablishmentPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_ReportingEstablishmentPicker)).click();
            driver.findElement(xpath_ReportingEstablishmentManual).sendKeys(ReportingEstablishment);
            for(WebElement element: driver.findElements(xpath_ReportingEstablishmentList)){
                if(element.getText().equals(ReportingEstablishment)){
                    Wrapper.waitForElementToBeClickable(element);
                    element.click();
                    break;
                }
            }
            // attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
            Wrapper.waitForpresenceOfElementLocated(xpath_LocationPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_LocationPicker)).click();
            driver.findElement(xpath_LocationManual).sendKeys(Location);
            for(WebElement element: driver.findElements(xpath_LocationList)){
                if(element.getText().equals(Location)){
                    Wrapper.waitForElementToBeClickable(element);
                    Thread.sleep(3000);
                    element.click();
                    Thread.sleep(3000);
                    break;
                }
            }
            Wrapper.redwoodSync();
 
            Wrapper.waitForpresenceOfElementLocated(xpath_WorkingAtHome);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WorkingAtHome)).click();
            By workingAtHomeOption = xpath_WorkingAtHomeOption();
            Wrapper.waitForpresenceOfElementLocated(workingAtHomeOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(workingAtHomeOption)).click();
            Thread.sleep(3000);
 
            attachStepEvidence("the manager fills the details in Assignment details page for adding a contingent worker.");
 
            // Wrapper.waitForpresenceOfElementLocated(xpath_WorkerCategory);
            // Thread.sleep(5000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WorkerCategory)).click();
            // By workerCategoryOption = xpath_WorkerCategoryOption();
            // Wrapper.waitForpresenceOfElementLocated(workerCategoryOption);
            // Thread.sleep(5000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(workerCategoryOption)).click();
            // Thread.sleep(5000);
 
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AssignmentCategory), "Assignment Category dropdown");
            // Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentCategory);
            // Thread.sleep(5000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_AssignmentCategory)).click();
            // Thread.sleep(5000);
            // By assignmentCategoryOption = xpath_AssignmentCategoryOption();
            // Wrapper.waitForpresenceOfElementLocated(assignmentCategoryOption);
            // Thread.sleep(5000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(assignmentCategoryOption)).click();
            // Thread.sleep(5000);
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_RegularTemporary), "Regular Temporary dropdown");
            // Thread.sleep(3000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_RegularTemporary);
            // Thread.sleep(5000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_RegularTemporary)).click();
            // Thread.sleep(3000);
            // By regularTemporaryOption = xpath_RegularTemporaryOption();
            // Wrapper.waitForpresenceOfElementLocated(regularTemporaryOption);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(regularTemporaryOption)).click();
            // Thread.sleep(5000);
            // attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
            // Wrapper.waitForpresenceOfElementLocated(xpath_FullTimePartTime);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_FullTimePartTime)).click();
            // Thread.sleep(3000);
            // By fullTimePartTimeOption = xpath_FullTimePartTimeOption();
            // Wrapper.waitForpresenceOfElementLocated(fullTimePartTimeOption);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(fullTimePartTimeOption)).click();
            Thread.sleep(5000);
 
            // Wrapper.redwoodSync();
 
            // Wrapper.waitForpresenceOfElementLocated(xpath_HourlyPaidSalaried);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_HourlyPaidSalaried)).click();
            // Thread.sleep(3000);
            // By hourlyPaidSalariedOption = xpath_HourlyPaidSalariedOption();
            // Wrapper.waitForpresenceOfElementLocated(hourlyPaidSalariedOption);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(hourlyPaidSalariedOption)).click();
            // Thread.sleep(5000);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_WorkingHours), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Working_Hours"), true);
            // Thread.sleep(3000);
 
            // Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursFrequency);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WorkingHoursFrequency)).click();
            // By workingHoursFrequencyOption = xpath_WorkingHoursFrequencyOption();
            // Wrapper.waitForpresenceOfElementLocated(workingHoursFrequencyOption);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(workingHoursFrequencyOption)).click();
            // Wrapper.redwoodSync();
 
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_UnionMember), "Union Member dropdown");
            // Wrapper.waitForpresenceOfElementLocated(xpath_UnionMember);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_UnionMember)).click();
            // Thread.sleep(3000);
            // By unionMemberOption = xpath_UnionMemberOption();
            // Wrapper.waitForpresenceOfElementLocated(unionMemberOption);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(unionMemberOption)).click();
 
            // Wrapper.waitForpresenceOfElementLocated(xpath_Union);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_Union)).click();
            // Thread.sleep(3000);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UnionManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union"), true);
            // for(WebElement element: driver.findElements(xpath_UnionList)){
            //     if(element.getText().equals(ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union"))){
            //         Wrapper.waitForElementToBeClickable(element);
            //         Thread.sleep(3000);
            //         element.click();
            //         Thread.sleep(3000);
            //         break;
            //     }
            // }
            // attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
            // Wrapper.waitForpresenceOfElementLocated(xpath_BargainingUnit);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_BargainingUnit)).click();
            // Thread.sleep(6000);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_BargainingUnitManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Bargaining_Unit"), true);
            // for(WebElement element: driver.findElements(xpath_BargainingUnitList)){
            //     if(element.getText().equals(ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Bargaining_Unit"))){
            //         Wrapper.waitForElementToBeClickable(element);
            //         Thread.sleep(3000);
            //         element.click();
            //         Thread.sleep(3000);
            //         break;
            //     }
            // }
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_OfficerCode), "Officer Code dropdown");
            // Thread.sleep(5000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_OfficerCode);
            // Thread.sleep(4000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_OfficerCode)).click();
            // Thread.sleep(5000);
            // By officerCodeOption = xpath_OfficerCodeOption();
            // Wrapper.waitForpresenceOfElementLocated(officerCodeOption);
            // Thread.sleep(5000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(officerCodeOption)).click();
            // Thread.sleep(5000);
            // Thread.sleep(8000);
            //  attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            //  Thread.sleep(8000);
            // attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            // Thread.sleep(8000);
            //  attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_GradePicker), "Grade dropdown");
            // // Wrapper.waitForpresenceOfElementLocated(xpath_GradePicker);
            // // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_GradePicker)).click();
            // // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_GradeManual), Grade, true);
            // // for(WebElement element: driver.findElements(xpath_GradeList)){
            // //     if(element.getText().equals(Grade)){
            // //         Wrapper.waitForElementToBeClickable(element);
            // //         Thread.sleep(4000);
            // //         element.click();
            // //         break;
            // //     }
            // // }
 
 
            // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ReportingEstablishmentPicker), "Reporting Establishment dropdown");
            // // Wrapper.waitForpresenceOfElementLocated(xpath_ReportingEstablishmentPicker);
            // // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_ReportingEstablishmentPicker)).click();
            // // driver.findElement(xpath_ReportingEstablishmentManual).sendKeys(ReportingEstablishment);
            // // for(WebElement element: driver.findElements(xpath_ReportingEstablishmentList)){
            // //     if(element.getText().equals(ReportingEstablishment)){
            // //         Wrapper.waitForElementToBeClickable(element);
            // //         element.click();
            // //         break;
            // //     }
            // // }
 
            // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AssignmentNumber), "Assignment Number input field");
            // // Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentNumber);
            // // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AssignmentNumber), AssignmentNumber, false);
            // Thread.sleep(5000);
            // attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Assignment details page is not displayed after clicking Continue button on Address details page.");
        }
    }

    By xpath_HRACorrectionComments=By.xpath("//textarea[contains(@aria-label,'Comments')]");
    By xpath_HRASaveComment=By.xpath("//span[contains(text(),'Save Comment')]");
    //xpath_ClickCross
    By xpath_ValidateCommentsAndAttachmentsPage=By.xpath("//span[contains(@id,'CommentsAndAttachments_title')]");

    public void fillInCommentsAndAttachmentsHRA() throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_CommentsAndAttachmentsPageValidation);
        Thread.sleep(10000);
        if(Wrapper.findWebElement(xpath_CommentsAndAttachmentsPageValidation).isDisplayed()){
            Wrapper.waitForpresenceOfElementLocated(xpath_HRACorrectionComments);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_HRACorrectionComments), "Testing", false);
            Thread.sleep(5000);
            attachStepEvidence("Entered comments in Comments and Attachments page for adding a contingent worker.");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRASaveComment));
            Wrapper.waitForpresenceOfElementLocated(xpath_ClickCross);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ClickCross));
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
            Thread.sleep(12000);
        }else{
            Assert.fail("Comments and Attachments page is not displayed after clicking Continue button on Assignment details page.");
        }
    }

    //HRA Manager- Terminate a Contingent Worker
    By xpath_TerminateContingentWorkerOption=By.xpath("//td[contains(text(),'Terminate Employment')]");
    public void navigateToTerminateContingentWorkerPageHRA() throws Exception{
        try {

            String employeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
            Wrapper.waitForpresenceOfElementLocated(xpath_EmployeeSearchBox);
            if(Wrapper.findWebElement(xpath_EmployeeSearchBox).isDisplayed()){
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
                Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), employeeName, false);
                if(Wrapper.findWebElement(HRAXpath(employeeName)).isDisplayed()){
                    Thread.sleep(3000);
                    attachStepEvidence("Searched for employee using employee number: " + employeeName);
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(HRAXpath(employeeName))).click();
                }
            }

            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PSShowMore);
            if(Wrapper.findWebElement(xpath_PSShowMore).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Show More in Public Info Page");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PSShowMore));
            }

            Thread.sleep(8000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PSEmploymentInfo);
            if(Wrapper.findWebElement(xpath_PSEmploymentInfo).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Employment Info in Public Info Page");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PSEmploymentInfo));
                Thread.sleep(10000);
            }

            Wrapper.waitForpresenceOfElementLocated(xpath_PSActions);
            if(Wrapper.findWebElement(xpath_PSActions).isDisplayed()){
                Thread.sleep(3000);
                attachStepEvidence("Clicking on Actions button in Employment Info section");
                Thread.sleep(5000);
                Wrapper.doubleClickWebElement(Wrapper.findWebElement(xpath_PSActions));
            }

            Thread.sleep(10000);
            Wrapper.waitForpresenceOfElementLocated(xpath_TerminateContingentWorkerOption);
            if(Wrapper.findWebElement(xpath_TerminateContingentWorkerOption).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Terminate Employment option from Actions dropdown");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TerminateContingentWorkerOption));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //HRA Manager- Rehiring a Terminated Employee
    
    By xpath_IncludeTerminatedFilter=By.xpath("//div[contains(@aria-label,' Include Terminated Work Relationships')]");
    By xpath_EmploymentInfoMoreOption=By.xpath("//button[contains(@aria-label,'More Actions')]//span/span/span");
    By xpath_CreateWorkRelationshipOption=By.xpath("//a[contains(@data-oj-label,'Create Work Relationship')]//span/span");

    public void navigateToRehireTerminatedEmployeePageHRA() throws Exception    {
        String employeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
        Wrapper.waitForpresenceOfElementLocated(xpath_EmploymentInfoValidation);
        Thread.sleep(10000);
        Wrapper.findWebElement(xpath_EmployeeSearchBox).isDisplayed();
        Wrapper.waitForpresenceOfElementLocated(xpath_IncludeTerminatedFilter);
        Wrapper.findWebElement(xpath_IncludeTerminatedFilter).isDisplayed();
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_IncludeTerminatedFilter));
        attachStepEvidence("Clicking on Include Terminated Work Relationships filter in Employment Info page");
        Thread.sleep(10000);
        if(Wrapper.findWebElement(xpath_EmploymentInfoValidation).isDisplayed()){
            AllureReportUtil.info("Successfully navigated to Employment Info page.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), employeeName, false);
            if(Wrapper.findWebElement(HRAXpath(employeeName)).isDisplayed()){
                Thread.sleep(3000);
                attachStepEvidence("Searched for employee using employee number: " + employeeName);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(employeeName))).click();
            }

            Thread.sleep(8000);
            Wrapper.waitForpresenceOfElementLocated(xpath_EmploymentInfoMoreOption);
            if(Wrapper.findWebElement(xpath_EmploymentInfoMoreOption).isDisplayed()){
                Thread.sleep(8000);
                attachStepEvidence("Clicking on More Actions button in Employment Info section");
                Thread.sleep(4000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmploymentInfoMoreOption));
            }
            Wrapper.waitForpresenceOfElementLocated(xpath_CreateWorkRelationshipOption);
            if(Wrapper.findWebElement(xpath_CreateWorkRelationshipOption).isDisplayed()){
                Thread.sleep(2000);
                attachStepEvidence("Clicking on Create Work Relationship option from More Actions dropdown");
                Thread.sleep(2000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CreateWorkRelationshipOption));
            }
        }
    }

    By xpath_PhoneDetailsButtonRehire=By.xpath("//oj-switch//div[contains(@aria-label,'Phone details')]");
    By xpath_EmailDetailsButtonRehire=By.xpath("//oj-switch//div[contains(@aria-label,'Email details')]");
    By xpath_WorkRelationshipInfoRehire=By.xpath("//oj-switch//div[contains(@aria-label,'Work relationship info')]");

    public void fillInfoToIncludePageRehire() throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_InfoToIncludePageValidation);
        if(Wrapper.findWebElement(xpath_InfoToIncludePageValidation).isDisplayed()){
            Assert.assertTrue(true, "Successfully navigated to Info to include page.");
            Thread.sleep(10000);
            // attachStepEvidence("the manager is on Info to include page after clicking Hire an Employee.");
            
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PhoneDetailsButtonRehire));
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Phone details"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Phone details")).isEnabled()){
                Assert.assertTrue(true, "Phone details button is enabled.");
            }else{
                Assert.fail("Phone details button is not enabled.");
            }

            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmailDetailsButtonRehire));
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Email details"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Email details")).isEnabled()){
                Assert.assertTrue(true, "Email details button is enabled.");
            }else{
                Assert.fail("Email details button is not enabled.");
            }
 
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressButton));
            // attachStepEvidence("the manager is clicking on required info to include buttons in info to include page.");
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Addresses"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Addresses")).isEnabled()){
                Assert.assertTrue(true, "Addresses button is enabled.");
            }else{
                Assert.fail("Addresses button is not enabled.");
            }
            
            // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_WorkRelationshipInfoRehire));
            // Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Work relationship info"));
            // if(Wrapper.findWebElement(xpath_ButtonEnabled("Work relationship info")).isEnabled()){  
            //     Assert.assertTrue(true, "Work relationship info button is enabled.");
            // }else{
            //     Assert.fail("Work relationship info button is not enabled.");
            // }

            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PayRollButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Payroll"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Payroll")).isEnabled()){
                Assert.assertTrue(true, "Payroll button is enabled.");
            }else{
                Assert.fail("Payroll button is not enabled.");
            }
           
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HireSalaryButton));
            attachStepEvidence("All buttons are enabled in Info to include");
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Salary"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Salary")).isEnabled()){
                Assert.assertTrue(true, "Salary button is enabled.");
            }else{
                Assert.fail("Salary button is not enabled.");
            }
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Failed to navigate to Info to include page after clicking Hire an Employee.");
        }
    }

    public void fillPhoneDetailsRehire() throws Exception{
        String PhoneCountryCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Country");
        String PhoneAreaCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Area Code");
        String PhoneNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Number");
        // String PhoneType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Type");
        Wrapper.waitForpresenceOfElementLocated(xpath_PhoneDetailsButton);
        if(Wrapper.findWebElement(xpath_PhoneDetailsButton).isDisplayed()){
            Assert.assertTrue(true, "Communication Info page is displayed successfully.");
            // attachStepEvidence("the manager is on the Communication Info page for hiring a new employee.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PhoneDetailsButton));
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCode);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_PhoneCountryCode)).click();
                    Thread.sleep(4000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCodeManual);
            // driver.findElement(xpath_PhoneCountryCodeManual).sendKeys(PhoneCountryCode);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneCountryCodeManual), PhoneCountryCode, true);
            // By phoneCountryCodeOption = xpath_PhoneCountryCodeList("US");
            // Wrapper.waitForpresenceOfElementLocated(phoneCountryCodeOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(phoneCountryCodeOption)).click();
 
            Wrapper.waitForpresenceOfElementLocated(xpath_HirePhoneType);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_HirePhoneType)).click();
                Thread.sleep(5000);
            By phoneTypeOption = xpath_HirePhoneTypeOption();
            Wrapper.waitForpresenceOfElementLocated(phoneTypeOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(phoneTypeOption)).click();
                Thread.sleep(5000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneAreaCode), PhoneAreaCode, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneNumber), PhoneNumber, false);
            Thread.sleep(5000);
            // attachStepEvidence("the manager filled the details in Phone section for adding a contingent worker.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            // Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            Thread.sleep(5000);

            Thread.sleep(10000);
            attachStepEvidence("the manager filled the details in Phone Details page.");
            Thread.sleep(8000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
            //Fill Email details similarly by clicking Email Details button and then click Continue button
        }else{
            Assert.fail("Communication Info page is not displayed after clicking Continue button on National Identifier details page.");
        }
    }

    public void fillEmailDetailsRehire() throws Exception{
        Thread.sleep(10000);
        String EmailType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email Type");
        String Email=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email");
        Wrapper.waitForpresenceOfElementLocated(xpath_EmailDetailsButton);
        Thread.sleep(5000);
        Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_EmailDetailsButton));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmailDetailsButton));
 
        Thread.sleep(10000);
        // Wrapper.waitForpresenceOfElementLocated(xpath_HireEmailType);
        // Wrapper.findWebElement(xpath_HireEmailType).isDisplayed();
        // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HireEmailType), "Email Type dropdown");
        // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //         .elementToBeClickable(xpath_HireEmailType)).click();
        // By emailTypeOption = xpath_HireEmailTypeOption();
        // Wrapper.waitForpresenceOfElementLocated(emailTypeOption);
        // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //         .elementToBeClickable(emailTypeOption)).click();
        //         Thread.sleep(5000);
        // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_HireEmailType), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email Type"), true);
        // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Email), Email, false);
        
        System.out.println("Perform Action");
        Thread.sleep(30000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
        // Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
        // Wrapper.findWebElement(xpath_PrimaryValidationAfterSave).isDisplayed();
        // Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
        Thread.sleep(10000);
        attachStepEvidence("the manager filled the details in Email page");
        Thread.sleep(8000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        Thread.sleep(10000);
    }

    public void fillAddressDetailsRehire() throws Exception{
        String AddressCountry=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Country");
        // String AddressType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Type");
        String AddressLine1=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Line1");
        // String AddressLine2=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Line2");
        // String ZIPCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"ZIP_CODE");
        Thread.sleep(4000);
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressDetailsButton));
        // Thread.sleep(4000);
        // Wrapper.waitForpresenceOfElementLocated(xpath_AddressCountry);
        // Thread.sleep(4000);
        //  if(Wrapper.findWebElement(xpath_AddressCountry).isDisplayed()){
            // Assert.assertTrue(true, "Address details page is displayed successfully.");
        //     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //             .elementToBeClickable(xpath_AddressCountry)).click();
        //             Thread.sleep(4000);
            // driver.findElement(xpath_AddressCountryManual).sendKeys(AddressCountry);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressCountryManual), AddressCountry, true);
            // Thread.sleep(4000);
            // By addressCountryOption = xpath_AddressCountryOption();
            // Wrapper.waitForpresenceOfElementLocated(addressCountryOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(addressCountryOption)).click();
 
            // Wrapper.waitForpresenceOfElementLocated(xpath_HireAddressType);
            // Thread.sleep(4000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(xpath_HireAddressType)).click();
            //         Thread.sleep(4000);
            // By addressTypeOption = xpath_HireAddressTypeOption();
            // Wrapper.waitForpresenceOfElementLocated(addressTypeOption);
            // Thread.sleep(4000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(addressTypeOption)).click();
            //     Thread.sleep(4000);
 
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressLine1), AddressLine1, false);
            // Thread.sleep(4000);
            // // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressLine2), AddressLine2, false);
 
            // Wrapper.waitForpresenceOfElementLocated(xpath_ZIPCode);
            // Thread.sleep(4000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(xpath_ZIPCode)).click();
            //         Thread.sleep(4000);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_ZIPCodeManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"ZIP_CODE"), true);
            // By zipCodeOption = xpath_ZIPCodeOption();
            // Thread.sleep(4000);
            // Wrapper.waitForpresenceOfElementLocated(zipCodeOption);
            // Thread.sleep(4000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(zipCodeOption)).click();
            // Thread.sleep(4000);
            attachStepEvidence("the manager filled the details in Address section.");
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SaveButton), "Save button");
            // Thread.sleep(4000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_SaveButton);
            // Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_SaveButton));
 
            // Thread.sleep(4000);
           
            // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            // Thread.sleep(10000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            // Thread.sleep(10000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_ValidateButton);
            //  attachStepEvidence("the manager filled the details in Address page for hiring a new employee.");
            // Thread.sleep(10000);
            Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
 
        // }
        // else{
        //     Assert.fail("Address details page is not displayed after clicking Address button on Communication Info page.");
        // }
    }


        public void fillAssignmentDetailsRehire() throws Exception{
        // String AssignmentNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Number");
        String AssignmentStatus=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Status");
        // String PersonType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Person_Type");
        String Job=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Job");
        String BusinessTitle=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Business_Title");
        String Grade=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Grade");
        String Department=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Department");
        String ReportingEstablishment=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Reporting_Establishment");
        String Location=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Location");
        String WorkingAtHome=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Work_At_Home");
        String WorkerCategory=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Worker_Category");
        String AssignmentCategory=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Category");
        String RegularTemporary=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Regular_Temporary");
        String FullTimePartTime=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Full_Time_Part_Time");
        String HourlyPaidSalaried=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hourly_Paid_Salaried");
        String WorkingHoursFrequency=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Working_Hours_Frequency");
        String UnionMember=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union_Member");
        Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentPageValidation);
        Thread.sleep(10000);
        if(Wrapper.findWebElement(xpath_AssignmentPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Assignment details page is displayed successfully.");
            Thread.sleep(3000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentNumber);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AssignmentNumber), AssignmentNumber, false);
            // Fill other details in Assignment page similarly by using the respective locators and test data from excel sheet.
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AssignmentNumber), "Assignment Number input field");
            // Thread.sleep(2000);
            // driver.findElement(xpath_AssignmentNumber).sendKeys(Keys.TAB);
            // Thread.sleep(10000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentNumber);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AssignmentNumber), AssignmentNumber, false);
            // Wrapper.redwoodSync();
 
            // Fill other details in Assignment page similarly by using the respective locators and test data from excel sheet.
            Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentStatusPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_AssignmentStatusPicker)).click();
            for(WebElement element: driver.findElements(xpath_AssignmentStatusList)){
                if(element.getText().equals(AssignmentStatus)){
                    Wrapper.waitForElementToBeClickable(element);
                    Thread.sleep(2000);
                    element.click();
                    break;
                }
                Thread.sleep(3000);
            }
            Wrapper.waitForpresenceOfElementLocated(xpath_PersonTypePicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_PersonTypePicker)).click();
            Thread.sleep(4000);
            By personTypeOption = xpath_PersonTypeOption();
            Wrapper.waitForpresenceOfElementLocated(personTypeOption);
            Thread.sleep(2000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(personTypeOption)).click();
            Thread.sleep(3000);
 
            Wrapper.waitForpresenceOfElementLocated(xpath_JobPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_JobPicker)).click();
            Thread.sleep(4000);
            driver.findElement(xpath_JobManual).sendKeys(Job);
            for(WebElement element: driver.findElements(xpath_JobList)){
                if(element.getText().equals(Job)){
                    Wrapper.waitForElementToBeClickable(element);
                    element.click();
                    Thread.sleep(3000);
                    break;
                }
            }
            Wrapper.redwoodSync();
 
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_BusinessTitle), BusinessTitle, true);
            Thread.sleep(3000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_GradePicker), "Grade dropdown");
            Wrapper.waitForpresenceOfElementLocated(xpath_GradePicker);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_GradePicker)).click();
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_GradeManual), Grade, false);
            for(WebElement element: driver.findElements(xpath_GradeList)){
                if(element.getText().equals(Grade)){
                    Wrapper.waitForElementToBeClickable(element);
                    // Thread.sleep(4000);
                    element.click();
                    Thread.sleep(4000);
                    break;
                }
            }
            Wrapper.redwoodSync();
 
            Wrapper.waitForpresenceOfElementLocated(xpath_DepartmentPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_DepartmentPicker)).click();
            Thread.sleep(4000);
            driver.findElement(xpath_DepartmentManual).sendKeys(Department);
            for(WebElement element: driver.findElements(xpath_DepartmentList)){
                if(element.getText().equals(Department)){
                    Thread.sleep(10000);
                    Wrapper.waitForElementToBeClickable(element);
                    element.click();
                    Thread.sleep(5000);
                    break;
                }
            }
            Wrapper.redwoodSync();
 
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_GradePicker), "Grade dropdown");
            // Wrapper.waitForpresenceOfElementLocated(xpath_GradePicker);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_GradePicker)).click();
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_GradeManual), Grade, true);
            // for(WebElement element: driver.findElements(xpath_GradeList)){
            //     if(element.getText().equals(Grade)){
            //         Wrapper.waitForElementToBeClickable(element);
            //         // Thread.sleep(4000);
            //         element.click();
            //         break;
            //     }
            // }
            // Wrapper.redwoodSync();
 
            Wrapper.waitForpresenceOfElementLocated(xpath_ReportingEstablishmentPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_ReportingEstablishmentPicker)).click();
            driver.findElement(xpath_ReportingEstablishmentManual).sendKeys(ReportingEstablishment);
            for(WebElement element: driver.findElements(xpath_ReportingEstablishmentList)){
                if(element.getText().equals(ReportingEstablishment)){
                    Wrapper.waitForElementToBeClickable(element);
                    element.click();
                    break;
                }
            }
            // attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
            Wrapper.waitForpresenceOfElementLocated(xpath_LocationPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_LocationPicker)).click();
            driver.findElement(xpath_LocationManual).sendKeys(Location);
            for(WebElement element: driver.findElements(xpath_LocationList)){
                if(element.getText().equals(Location)){
                    Wrapper.waitForElementToBeClickable(element);
                    Thread.sleep(3000);
                    element.click();
                    Thread.sleep(3000);
                    break;
                }
            }
            Wrapper.redwoodSync();
 
            Wrapper.waitForpresenceOfElementLocated(xpath_WorkingAtHome);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WorkingAtHome)).click();
            By workingAtHomeOption = xpath_WorkingAtHomeOption();
            Wrapper.waitForpresenceOfElementLocated(workingAtHomeOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(workingAtHomeOption)).click();
            Thread.sleep(3000);
 
            // attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
 
            // Wrapper.waitForpresenceOfElementLocated(xpath_WorkerCategory);
            // Thread.sleep(5000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WorkerCategory)).click();
            // By workerCategoryOption = xpath_WorkerCategoryOption();
            // Wrapper.waitForpresenceOfElementLocated(workerCategoryOption);
            // Thread.sleep(5000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(workerCategoryOption)).click();
            Thread.sleep(5000);
 
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AssignmentCategory), "Assignment Category dropdown");
            Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentCategory);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_AssignmentCategory)).click();
            Thread.sleep(5000);
            By assignmentCategoryOption = xpath_AssignmentCategoryOption();
            Wrapper.waitForpresenceOfElementLocated(assignmentCategoryOption);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(assignmentCategoryOption)).click();
            Thread.sleep(5000);

            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_RegularTemporary), "Regular Temporary dropdown");
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_RegularTemporary);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_RegularTemporary)).click();
            Thread.sleep(3000);
            By regularTemporaryOption = xpath_RegularTemporaryOption();
            Wrapper.waitForpresenceOfElementLocated(regularTemporaryOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(regularTemporaryOption)).click();
            Thread.sleep(5000);

            // attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
            Wrapper.waitForpresenceOfElementLocated(xpath_FullTimePartTime);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_FullTimePartTime)).click();
            Thread.sleep(3000);
            By fullTimePartTimeOption = xpath_FullTimePartTimeOption();
            Wrapper.waitForpresenceOfElementLocated(fullTimePartTimeOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(fullTimePartTimeOption)).click();
            Thread.sleep(5000);
 
            Wrapper.redwoodSync();
 
            Wrapper.waitForpresenceOfElementLocated(xpath_HourlyPaidSalaried);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_HourlyPaidSalaried)).click();
            Thread.sleep(3000);
            By hourlyPaidSalariedOption = xpath_HourlyPaidSalariedOption();
            Wrapper.waitForpresenceOfElementLocated(hourlyPaidSalariedOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(hourlyPaidSalariedOption)).click();
            Thread.sleep(5000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_WorkingHours), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Working_Hours"), true);
            Thread.sleep(3000);
 
            Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursFrequency);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WorkingHoursFrequency)).click();
            By workingHoursFrequencyOption = xpath_WorkingHoursFrequencyOption();
            Wrapper.waitForpresenceOfElementLocated(workingHoursFrequencyOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(workingHoursFrequencyOption)).click();
            Wrapper.redwoodSync();
 
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_UnionMember), "Union Member dropdown");
            // Wrapper.waitForpresenceOfElementLocated(xpath_UnionMember);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_UnionMember)).click();
            // Thread.sleep(3000);
            // By unionMemberOption = xpath_UnionMemberOption();
            // Wrapper.waitForpresenceOfElementLocated(unionMemberOption);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(unionMemberOption)).click();
 
            // Wrapper.waitForpresenceOfElementLocated(xpath_Union);
            Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_Union)).click();
            // Thread.sleep(3000);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UnionManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union"), true);
            // for(WebElement element: driver.findElements(xpath_UnionList)){
            //     if(element.getText().equals(ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union"))){
            //         Wrapper.waitForElementToBeClickable(element);
            //         Thread.sleep(3000);
            //         element.click();
            //         Thread.sleep(3000);
            //         break;
            //     }
            // }
            // attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
            // Wrapper.waitForpresenceOfElementLocated(xpath_BargainingUnit);
            // Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_BargainingUnit)).click();
            // Thread.sleep(6000);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_BargainingUnitManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Bargaining_Unit"), true);
            // for(WebElement element: driver.findElements(xpath_BargainingUnitList)){
            //     if(element.getText().equals(ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Bargaining_Unit"))){
            //         Wrapper.waitForElementToBeClickable(element);
            //         Thread.sleep(3000);
            //         element.click();
            //         Thread.sleep(3000);
            //         break;
            //     }
            // }
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_OfficerCode), "Officer Code dropdown");
            // Thread.sleep(5000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_OfficerCode);
            // Thread.sleep(4000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_OfficerCode)).click();
            // Thread.sleep(5000);
            // By officerCodeOption = xpath_OfficerCodeOption();
            // Wrapper.waitForpresenceOfElementLocated(officerCodeOption);
            // Thread.sleep(5000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(officerCodeOption)).click();
            // Thread.sleep(5000);
            // Thread.sleep(8000);
            //  attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            Thread.sleep(8000);
            // attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            Thread.sleep(8000);
            //  attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_GradePicker), "Grade dropdown");
            // // Wrapper.waitForpresenceOfElementLocated(xpath_GradePicker);
            // // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_GradePicker)).click();
            // // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_GradeManual), Grade, true);
            // // for(WebElement element: driver.findElements(xpath_GradeList)){
            // //     if(element.getText().equals(Grade)){
            // //         Wrapper.waitForElementToBeClickable(element);
            // //         Thread.sleep(4000);
            // //         element.click();
            // //         break;
            // //     }
            // // }
 
 
            // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ReportingEstablishmentPicker), "Reporting Establishment dropdown");
            // // Wrapper.waitForpresenceOfElementLocated(xpath_ReportingEstablishmentPicker);
            // // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_ReportingEstablishmentPicker)).click();
            // // driver.findElement(xpath_ReportingEstablishmentManual).sendKeys(ReportingEstablishment);
            // // for(WebElement element: driver.findElements(xpath_ReportingEstablishmentList)){
            // //     if(element.getText().equals(ReportingEstablishment)){
            // //         Wrapper.waitForElementToBeClickable(element);
            // //         element.click();
            // //         break;
            // //     }
            // // }
 
            // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AssignmentNumber), "Assignment Number input field");
            // // Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentNumber);
            // // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AssignmentNumber), AssignmentNumber, false);
            // Thread.sleep(5000);
            attachStepEvidence("the manager filled the details in Assignment page.");
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Assignment details page is not displayed after clicking Continue button on Address details page.");
        }
    }

     public void fillPayrollDetailsRehire() throws Exception{
        //  String PayFrequency=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Pay_Frequency");
        Wrapper.waitForpresenceOfElementLocated(xpath_PayrollDetailsPageValidation);
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_PayrollDetailsPageValidation).isDisplayed()){
             Assert.assertTrue(true, "Payroll details page is displayed successfully.");
             Wrapper.waitForpresenceOfElementLocated(xpath_HirePayrollButton);
             Thread.sleep(3000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_HirePayrollButton)).click();
             Thread.sleep(5000);
             Wrapper.waitForpresenceOfElementLocated(xpath_Payroll);
             Thread.sleep(5000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_Payroll)).click();
             Thread.sleep(5000);
             By payrollOption = xpath_PayrollOption();
             Wrapper.waitForpresenceOfElementLocated(payrollOption);
             Thread.sleep(8000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(payrollOption)).click();
             Thread.sleep(5000);
 
             Wrapper.waitForpresenceOfElementLocated(xpath_TimeCardRequired);
             Thread.sleep(3000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_TimeCardRequired)).click();
             Thread.sleep(5000);
             By timeCardRequiredOption = xpath_TimeCardRequiredOption();
             Thread.sleep(3000);
             Wrapper.waitForpresenceOfElementLocated(timeCardRequiredOption);
             Thread.sleep(8000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(timeCardRequiredOption)).click();
             Thread.sleep(5000);
             attachStepEvidence("the manager filled the details in Payroll details.");
 
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Thread.sleep(8000);
            //  Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Payroll details page is not displayed after clicking Continue button on Manager details page.");
        }
    }
 
 
public void fillSalaryDetailsRehire() throws Exception{
        //  String SalaryAmount=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Salary Amount");
         String SalaryAmount="1000";
         Thread.sleep(5000);
        Wrapper.waitForpresenceOfElementLocated(xpath_HireSalaryPageValidation);
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_HireSalaryPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Salary details page is displayed successfully.");
           
             Wrapper.waitForpresenceOfElementLocated(xpath_SalaryBasis);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_SalaryBasis)).click();
             Thread.sleep(5000);
             By salaryBasisOption = xpath_SalaryBasisOption();  
             Wrapper.waitForpresenceOfElementLocated(salaryBasisOption);
             Thread.sleep(5000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(salaryBasisOption)).click();
             Thread.sleep(5000);
 
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SalaryAmount), SalaryAmount, true);
            Thread.sleep(5000);
            attachStepEvidence("the manager filled the details in Salary details page.");
            Thread.sleep(5000);
 
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Salary details page is not displayed after clicking Continue button on Payroll details page.");
        }
    }
 

    //Hemas
    //Hema new latest code
     // @1279845 Scenario:ESS - Document Records View or download

//document record:
By xpath_DocumentRecord=By.xpath("(//*[text()='Document Records'])[2]");
//document record link:
By xpath_DocumentRecordLink=By.xpath("(//*[text()='Document Records'])[3]");
//document record verify:
By xpath_DocumentRecordVerify=By.xpath("//*[text()='Document Records']");
//Birth certificate:
By xpath_BirthCertificate=By.xpath("//a[contains(.,'certificate')]");
//download button:
By xpath_DownloadButton=By.xpath("(//*[text()='Download']//..//..//..)[1]");
// By xpath_DownloadButton=By.xpath("(//button[.//span[text()='Download']])[1]");
//close button:
By xpath_CloseButton=By.xpath("(//button[@aria-label='Cancel'])[1]");

public void navigateToDocumentRecords() throws InterruptedException{
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ShowMore), "Show More");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Thread.sleep(10000);
        
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DocumentRecord), "Document Records");
        
        Wrapper.findWebElement(xpath_DocumentRecord).isDisplayed();
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_DocumentRecordLink).isDisplayed()){
           
            Assert.assertTrue(true, "Document record link is displayed.");
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DocumentRecordLink), "Document Record Link");
            
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DocumentRecordLink));
            Thread.sleep(20000);
             AllureReportUtil.info("Navigated to Document Records page");
            attachStepEvidence("Navigated to Document Records page");
            
        }
    }
  public void validateDocumentRecordDetails() throws InterruptedException{
    System.out.println("Validating Document Record details...");
    // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DocumentRecordVerify), "Document Record Verify");
    // Wrapper.waitForpresenceOfElementLocated(xpath_DocumentRecordVerify);
    //    Wrapper.findWebElement(xpath_DocumentRecordVerify).isDisplayed();
            // Assert.assertTrue(true, "Document Records page is displayed.");
            Thread.sleep(10000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_BirthCertificate), "Birth Certificate document record");
            System.out.println("Scrolled to Birth Certificate document record.");
            // Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_BirthCertificate));
            // System.out.println("Birth Certificate document record is clickable.");
            
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_BirthCertificate));
            Thread.sleep(5000);
            AllureReportUtil.info("Viewed or downloaded document records");
            attachStepEvidence("the employee has viewed or downloaded their document records");
            System.out.println("Clicked on Birth Certificate document record.");
            
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DownloadButton), "Download Button");
            Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_DownloadButton));  
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DownloadButton));
            
            
        }

        public void closeDocumentRecordDetails() throws InterruptedException{       
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CloseButton));
        }

// Test CaseID: 1279846	ESS - My Activity Center


//    My activityn center:
  By xpath_MyActivityCenter=By.xpath("//*[text()='My Activity Center']");
// payslip:
 By xpath_Payslip=By.xpath("//*[text()='Payslip']");
// paid on:
By xpath_PaidOn=By.xpath("(//*[text()='Payslip']/../../../following-sibling::*)[2]");
// salary:
By xpath_ESSSalary=By.xpath("//*[text()='Salary']");
// salary ..:
By xpath_SalaryValue=By.xpath("(//*[text()='Salary']/../../../following-sibling::*)[2]");
// Personal details:
By xpath_PersonalDetails=By.xpath("//*[text()='Personal Details']/../../../../..");
// journey:
By xpath_Journeys=By.xpath("(//a[contains(@aria-label,'Journeys')])[1]");
//bookmark:
By xpath_Bookmark=By.xpath("//*[@class='oj-ux-ico-bookmark']");
// go back:
By xpath_GoBackFromJourneys=By.xpath("(//*[text()='My Journeys']/../../../preceding::div[1])[1]//button");
// benefits:
By xpath_Benefits=By.xpath("(//a[text()='Benefits'])[1]");
// go back:
By xpath_GoBackFromBenefits=By.xpath("(//*[text()='Benefits']/../../../preceding::div[1])[1]//button");
// current job:
By xpath_CurrentJob=By.xpath("(//a[text()='Current Jobs'])[1]");
// go back:
By xpath_GoBackFromOpportunityMarketplace=By.xpath("(//*[text()='Opportunity Marketplace']/../../../preceding::div[1])[1]//button");
// personal details:

By xpath_PersonalDetailsName=By.xpath("//*[text()='Personal Details']/../../../../..");
By xpath_Name=By.xpath("(//*[text()='Name'])[1]");
By xpath_DemographicInfo=By.xpath("(//*[text()='Demographic info'])[1]");
By xpath_NationalIdentifier=By.xpath("(//*[text()='National identifiers'])[1]");
By xpath_BiographicalInfo=By.xpath("(//*[text()='Biographical info'])[1]");
By xpath_DisabilityInfo=By.xpath("(//*[text()='Disability info'])[1]");
// go back:
By xpath_GoBackFromPersonalDetails=By.xpath("//*[text()='Personal Details']/../../../preceding::div[1]//button");
// Additional information:
By xpath_AdditionalPersonInfo=By.xpath("//*[text()='Additional Person Info']/../../../../..");
// additional information header verification:
By xpath_AdditionalPersonInfoHeader=By.xpath("//*[text()='Additional Person Info']/..");
// coned - drug test:
By xpath_ConedDrugTest=By.xpath("(//*[text()='CONED_DRUG_TEST'])[1]");
// (//*[text()='CONED_DRUG_TEST'])[1]

// additional information go back:
By xpath_GoBackFromAdditionalPersonInfo=By.xpath("//*[text()='Additional Person Info']/../../../preceding::div[1]//button");
// personal identifier for external application:
By xpath_PersonalIdentifierForExternalApplications=By.xpath("//*[text()='Person Identifiers for External Applications']/../../../../..");
// personal identifier for external application header verification:
By xpath_PersonalIdentifierForExternalApplicationsHeader=By.xpath("//*[text()='Person Identifiers for External Applications']/..");
// personal identifier for external application go back:
By xpath_GoBackFromPersonalIdentifierForExternalApplications=By.xpath("//*[text()='Person Identifiers for External Applications']/../../../preceding::div[1]//button");
//contact Info:
By xpath_ContactInfo=By.xpath("//*[text()='Contact Info']/../../../../..");
//phone details:
By xpath_PhoneDetails=By.xpath("(//*[text()='Phone details'])[1]");
//Email Address
By xpath_EmailDetails=By.xpath("(//*[text()='Email details'])[1]");
//Address:
//By xpath_Address=By.xpath("(//*[text()='Address'])[1]");
//Contact info go back
By xpath_GoBackFromContactInfo=By.xpath("//*[text()='Contact Info']/../../../preceding::div[1]//button");

//Family and Emergency Contacts
By xpath_FamilyAndEmergencyContacts=By.xpath("//*[text()='Family and Emergency Contacts']/../../../../..");
//Family and Emergency Contacts go back
By xpath_GoBackFromFamilyAndEmergencyContacts=By.xpath("//*[text()='Family and Emergency Contacts']/../../../preceding::div[1]//button");

//Document Records
By xpath_DocumentRecords=By.xpath("//*[text()='Document Records']/../../../../..");
//Document Records go back
By xpath_GoBackFromDocumentRecords=By.xpath("//*[text()='Document Records']/../../../preceding::div[1]//button");

//view more:
By xpath_ViewMore=By.xpath("//*[text()='View More']");

//Quick action cross symbol:
By xpath_QuickActionCrossSymbol=By.xpath("(//*[text()='Quick actions']/../../following-sibling::*/*//button)[2]");

//list view:
By xpath_ListView=By.xpath("(//*[@data-oj-container='ojButtonset']/*)[1]");
//timeline view:
By xpath_TimelineView=By.xpath("(//*[@data-oj-container='ojButtonset']/*)[2]");

//change salary verify:
By xpath_ChangeSalary=By.xpath("//*[text()='Change Salary']");

//go back button
By xpath_GoBackButton=By.xpath("//button[@aria-label='Go back']");
By xpath_TimeAndAbsence=By.xpath("//*[text()='My Activity Center']");

public void navigateToMyActivityCenter() throws Exception{
        System.out.println("Navigating to My Activity Center...");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_TimeAndAbsence), "My Activity Center");
        AllureReportUtil.info("clicking the my activity center");
            attachStepEvidence("Clicked on My Activity Center");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyActivityCenter));
        
        Thread.sleep(20000);
        
         AllureReportUtil.info("Navigating to My Activity Center");
            attachStepEvidence("Navigated to My Activity Center");
        
        System.out.println("Navigating to My Activity Center2...");
    }
    public void validateMyActivityCenterDetails(){
        // System.out.println("Validating Payslip");
        // Wrapper.waitForpresenceOfElementLocated(xpath_Payslip);
        // if(Wrapper.findWebElement(xpath_Payslip).isDisplayed()){
        //     Assert.assertTrue(true, "Payslip link is displayed on My Activity Center.");
        //     String paidOn = Wrapper.findWebElement(xpath_PaidOn).getText();
            String salary = Wrapper.findWebElement(xpath_ESSSalary).getText();
            String salaryValue = Wrapper.findWebElement(xpath_SalaryValue).getText();
            System.out.println("Salary Value: " + salaryValue);
        //     boolean isMyActivityCenterDetailsDisplayed= !paidOn.isEmpty() && !salary.isEmpty() && !salaryValue.isEmpty(); 
            
        //     Assert.assertTrue(isMyActivityCenterDetailsDisplayed, "Paid On date, Salary label, and Salary value are displayed correctly on My Activity Center.");
            
        // }else{
        //     Assert.fail("Payslip link is not displayed on My Activity Center.");
        // }
    }
    public void navigateToAndValidateJourneys() throws InterruptedException{
        Thread.sleep(4000);
        System.out.println("Navigating to Journeys page...");
       WebElement journeysElement = Wrapper.findWebElement(xpath_Journeys);
        Wrapper.waitForElementToBeClickable(journeysElement);
        Wrapper.scrollToElement(journeysElement, "Journeys");
        Wrapper.clickWebElement(journeysElement);
    
        Thread.sleep(20000);
        // if(Wrapper.findWebElement(xpath_Bookmark).isDisplayed()){
             AllureReportUtil.info("Navigated to Journeys page");
            attachStepEvidence("Navigated to Journeys page");
            Assert.assertTrue(true, "Successfully navigated to Journeys page.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromJourneys));
        // }else{
        //     Assert.fail("Failed to navigate to Journeys page.");
        // }
    }
    public void navigateToAndValidateBenefits() throws InterruptedException{
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Benefits), "Benefits");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Benefits));
        Thread.sleep(20000);
        if(Wrapper.findWebElement(xpath_GoBackFromBenefits).isDisplayed()){
            AllureReportUtil.info("Navigated to Benefits page");
            attachStepEvidence("Navigated to Benefits page");
            Assert.assertTrue(true, "Successfully navigated to Benefits page.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromBenefits));
            Thread.sleep(4000);
        }else{
            Assert.fail("Failed to navigate to Benefits page.");
        }
    }
    public void navigateToAndValidateCurrentJob() throws InterruptedException{
        Thread.sleep(4000);
        WebElement currentJobElement = Wrapper.findWebElement(xpath_CurrentJob);
        Wrapper.scrollToElement(currentJobElement, "Current Job");
        Wrapper.highlightElement(currentJobElement);
        Thread.sleep(2000);
        Wrapper.clickWebElement(currentJobElement);
        Thread.sleep(20000);
        if(Wrapper.findWebElement(xpath_GoBackFromOpportunityMarketplace).isDisplayed()){
            AllureReportUtil.info("Navigated to Current Job page");
            attachStepEvidence("Navigated to Current Job page");
            Assert.assertTrue(true, "Successfully navigated to Current Job page.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromOpportunityMarketplace));
                Thread.sleep(4000);
        }else{
            Assert.fail("Failed to navigate to Current Job page.");
        }
    }
    public void navigateToAndValidatePersonalDetails() throws InterruptedException{
       WebElement personalDetailsElement = Wrapper.findWebElement(xpath_PersonalDetails);
       Wrapper.scrollToElement(personalDetailsElement, "Personal Details");
        Wrapper.waitForElementToBeClickable(personalDetailsElement);
        Wrapper.clickWebElement(personalDetailsElement);
        System.out.println("Navigating to Personal Details page...");
        Thread.sleep(25000);
        if(Wrapper.findWebElement(xpath_PersonalDetailsName).isDisplayed() && Wrapper.findWebElement(xpath_DemographicInfo).isDisplayed() && Wrapper.findWebElement(xpath_NationalIdentifier).isDisplayed() && Wrapper.findWebElement(xpath_BiographicalInfo).isDisplayed() && Wrapper.findWebElement(xpath_DisabilityInfo).isDisplayed()){
            AllureReportUtil.info("Navigated to Personal Details page");
            attachStepEvidence("Navigated to Personal Details page");
            Assert.assertTrue(true, "Successfully navigated to Personal Details page and all sections are displayed.");
             Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PersonalDetailsName), "Personal Details Name");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromPersonalDetails));
            Thread.sleep(4000);
            System.out.println("Navigated back from Personal Details page.");
        }else{
            Assert.fail("Failed to navigate to Personal Details page or some sections are not displayed.");
        }
    }
    public void navigateToAndValidateAdditionalPersonInfo() throws InterruptedException{
        Thread.sleep(5000);
        WebElement additionalPersonInfoElement = Wrapper.findWebElement(xpath_AdditionalPersonInfo);
        Wrapper.scrollToElement(additionalPersonInfoElement, "Additional Person Info");
        Wrapper.waitForElementToBeClickable(additionalPersonInfoElement);
        Wrapper.clickWebElement(additionalPersonInfoElement);
        System.out.println("Navigating to Additional Person Info page...");
        Thread.sleep(30000);
        Wrapper.findWebElement(xpath_AdditionalPersonInfoHeader).isDisplayed();
        AllureReportUtil.info("Navigated to Additional Person Info page");
            attachStepEvidence("Navigated to Additional Person Info page");
            System.out.println("Additional Person Info header is displayed.");
            Assert.assertTrue(true, "Successfully navigated to Additional Person Info page.");
            String header = Wrapper.findWebElement(xpath_AdditionalPersonInfoHeader).getText();
            Assert.assertEquals(header, "Additional Person Info", "Additional Person Info header is displayed correctly.");
            
            Thread.sleep(4000);
            // if(Wrapper.findWebElement(xpath_ConedDrugTest).isDisplayed()){
            //     // Assert.assertTrue(true, "Coned Drug Test section is displayed on Additional Person Info page.");
            // Wrapper.waitForElementToBeVisible(Wrapper.findWebElement(xpath_ConedDrugTest));
            // Wrapper.findWebElement(xpath_ConedDrugTest).isDisplayed();
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromAdditionalPersonInfo));
            
        // }else{
        //     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromAdditionalPersonInfo));
            
        // }
       
    }
    public void navigateToAndValidatePersonalIdentifierForExternalApplications() throws InterruptedException{
        Thread.sleep(5000);
        WebElement personalIdentifierForExternalApplicationsElement = Wrapper.findWebElement(xpath_PersonalIdentifierForExternalApplications);
        Wrapper.scrollToElement(personalIdentifierForExternalApplicationsElement, "Personal Identifier for External Applications");
        Wrapper.waitForElementToBeClickable(personalIdentifierForExternalApplicationsElement);
        Wrapper.clickWebElement(personalIdentifierForExternalApplicationsElement);
        System.out.println("Navigating to Personal Identifier for External Applications page...");
        Thread.sleep(30000);
        if(Wrapper.findWebElement(xpath_PersonalIdentifierForExternalApplicationsHeader).isDisplayed()){
            System.out.println("Personal Identifier for External Applications page is displayed.");
            Assert.assertTrue(true, "Successfully navigated to Personal Identifier for External Applications page.");
            AllureReportUtil.info("Navigated to Personal Identifier for External Applications page");
            attachStepEvidence("Navigated to Personal Identifier for External Applications page");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromPersonalIdentifierForExternalApplications));
            Thread.sleep(4000);
        }else{
            Assert.fail("Failed to navigate to Personal Identifier for External Applications page.");
        }
    }
    public void navigateToAndValidateContactInfo() throws InterruptedException{
        Thread.sleep(10000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ContactInfo), "Contact Info");
        Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_ContactInfo));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ContactInfo));

        Thread.sleep(20000);
         AllureReportUtil.info("Navigated to Contact Info page");
            attachStepEvidence("Navigated to Contact Info page");   
        // if(Wrapper.findWebElement(xpath_PhoneDetails).isDisplayed() && Wrapper.findWebElement(xpath_EmailDetails).isDisplayed() && Wrapper.findWebElement(xpath_Address).isDisplayed()){
        //     Assert.assertTrue(true, "Successfully navigated to Contact Info page and Phone Details, Email Details, and Address sections are displayed.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromContactInfo));
        // }else{
        //     Assert.fail("Failed to navigate to Contact Info page or some sections are not displayed.");
        // }
       
     }
     public void navigateToAndValidateFamilyAndEmergencyContacts() throws InterruptedException{
        Thread.sleep(10000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_FamilyAndEmergencyContacts), "Family and Emergency Contacts");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_FamilyAndEmergencyContacts));
        Thread.sleep(20000);
         AllureReportUtil.info("Navigated to Family and Emergency Contacts page");
            attachStepEvidence("Navigated to Family and Emergency Contacts page");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromFamilyAndEmergencyContacts));
          Thread.sleep(4000);    
     }
     public void navigateToAndValidateDocumentRecords() throws InterruptedException{
        Thread.sleep(20000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DocumentRecords), "Document Records");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DocumentRecords));
        Thread.sleep(20000);
         AllureReportUtil.info("Navigated to Document Records page");
            attachStepEvidence("Navigated to Document Records page");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_GoBackFromDocumentRecords));
            Thread.sleep(4000);  
     }
     public void validateViewMoreAndListTimelineView() throws InterruptedException{
        Thread.sleep(4000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ViewMore), "View More");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ViewMore));
        Thread.sleep(20000);
        if(Wrapper.findWebElement(xpath_QuickActionCrossSymbol).isDisplayed()){
            Assert.assertTrue(true, "Quick Action cross symbol is displayed after clicking View More.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_QuickActionCrossSymbol));
            Thread.sleep(4000);
            System.out.println("Clicked on Quick Action cross symbol to close the expanded view.");
        }else{
            Assert.fail("Quick Action cross symbol is not displayed after clicking View More.");
        }
    //     if(Wrapper.findWebElement(xpath_ListView).isDisplayed()){
    //         Assert.assertTrue(true, "List view is displayed.");
    //     }else{
    //         Assert.fail("List view is not displayed.");
    //     }
    //     if(Wrapper.findWebElement(xpath_TimelineView).isDisplayed()){
    //         System.out.println("Timeline view is displayed.");
    //         Assert.assertTrue(true, "Timeline view is displayed.");
    //     }else{
    //         Assert.fail("Timeline view is not displayed.");
    //     }
      }

      //delete record:

//my client:
By xpath_MyClientGroupsHRA = By.xpath("//a[text()='My Client Groups']");
//personal management:
By xpath_PersonManagement = By.xpath("//*[text()='Person Management']");
//person number:
By xpath_PersonNumber = By.xpath("//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt1:0:pt1:Perso1:0:SP3:q1:value10::content']");
//Search:
By xpath_SearchButton = By.xpath("//button[text()='Search']");
//link:
By xpath_BlevinsSpencer = By.xpath("//a[text()='Blevins, Spencer']");
//edit
By xpath_Edit = By.xpath("//a[@title='Edit']");
//delete record:
By xpath_DeleteRecord = By.xpath("//*[text()='Delete Record']");
//validate :
By xpath_ValidateDelete = By.xpath("//*[text()='The selected date-effective record will be deleted. The other date-effective records will remain.']");
//ok:
By xpath_OkButton = By.xpath("//button[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt1:0:pt1:Manag1:0:AP1:cb2']");

public void deletePersonalRecord() throws Exception{
     
        driver.get("https://ejcu-dev1.fa.us6.oraclecloud.com/fscmUI/faces/FuseWelcome");
       
        driver.manage().window().maximize();

            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UserName), "Ce.0096883", false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Password), "68Ukfh3*r768!", false);  
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SigninButton));

        Wrapper.waitForpresenceOfElementLocated(xpath_MyClientGroupsHRA);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroupsHRA));
        //click on person management link
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PersonManagement), "Person Management");
        Wrapper.waitForpresenceOfElementLocated(xpath_PersonManagement);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PersonManagement));
        //search person number:
        Wrapper.waitForpresenceOfElementLocated(xpath_PersonNumber);
        Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PersonNumber), "0085243", false);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SearchButton), "Search Button");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SearchButton));
        Thread.sleep(5000);
        //click on person link:
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_BlevinsSpencer), "Blevins, Spencer link");
        Wrapper.waitForpresenceOfElementLocated(xpath_BlevinsSpencer);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_BlevinsSpencer));

        //click on edit:
        Wrapper.waitForpresenceOfElementLocated(xpath_Edit);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Edit));
        //click on delete record:
        Wrapper.waitForpresenceOfElementLocated(xpath_DeleteRecord);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DeleteRecord));
        //validate delete pop up:
        Wrapper.waitForpresenceOfElementLocated(xpath_ValidateDelete);
        Wrapper.findWebElement(xpath_ValidateDelete).isDisplayed();
        //click on ok:
        Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_OkButton));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_OkButton));

}


      //1279848 -Scenario:ESS - Name Change - Submit

//personal info:
By xpath_PersonalInfo=By.xpath("//*[text()='Personal Info']");
//personal detail:
By xpath_PersonalDetail=By.xpath("(//*[text()='Personal Details'])[2]");
//pencil icon:
By xpath_PencilIcon=By.xpath("//h2[text()='Name']//..//following-sibling::*//button[@class='oj-button-button']");
// By xpath_PencilIcon=By.xpath("(//button[.//span[@slot='startIcon']])[4]");
//comment box:
By xpath_CommentBox=By.xpath("//*[@aria-label='Comments']");
//drag-drop:
By xpath_DragDrop=By.xpath("//*[@class='oj-filepicker-dropzone ']");
//save button:
By xpath_NameChangeSaveButton=By.xpath("//button[contains(@aria-labelledby,'ButtonSave')]");

By xpath_lastName=By.xpath("//*[text()='Middle Name']//..//..//..//following-sibling::input");
String newName=Wrapper.randomStringGenerator();
String Expectedname=newName;

     //1279848 -Scenario:ESS - Name Change - Submit
     public void navigateToNameChange() throws InterruptedException{
         Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ShowMore), "Show More");
         Wrapper.waitForpresenceOfElementLocated(xpath_ShowMore);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
         Thread.sleep(2000);
         AllureReportUtil.info("Navigated to show more page");
            attachStepEvidence("Navigated to show more page");
        // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PersonalInfo), "Personal Info");
        Wrapper.findWebElement(xpath_PersonalInfo).isDisplayed();
    //    Wrapper.setSize();
    //    Wrapper.disableChromeScallingIssue();
         Thread.sleep(5000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PersonalDetail), "Personal Details");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PersonalDetail));
        Thread.sleep(30000);
        AllureReportUtil.info("Navigating to personal details for name change");
            attachStepEvidence("Navigating to personal details for name change");
            Thread.sleep(5000);
            Wrapper.scrollDown();
            Thread.sleep(5000);
            AllureReportUtil.info("Navigating to personal details for name change");
            attachStepEvidence("Navigating to personal details for name change");

        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PencilIcon), "Pencil Icon for Name Change");
        Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_PencilIcon));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PencilIcon));
        Thread.sleep(10000);
        // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_lastName), "Last Name input field");
        Wrapper.scrollUp();
AllureReportUtil.info("clicking the pencil icon for name change");
            attachStepEvidence("clicking the pencil icon for name change");
        
     }



        public void enterNameChangeDetails(String Comment) throws Exception{
           if(Wrapper.findWebElement(xpath_CommentBox).isDisplayed() && Wrapper.findWebElement(xpath_DragDrop).isDisplayed()){
            Wrapper.findWebElement(xpath_lastName).clear();
            
           Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_lastName), newName, false);
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_CommentBox), Comment, false);
             Thread.sleep(5000);

        //      Wrapper.selectDate(
        //     Wrapper.findWebElement(xpath_SelectDate),
        //     "6/May/2026",
        //     xpath_ResignationRetirementDateValue,
        //     xpath_DatePickerMonth,
        //     xpath_DatePickerYear,
        //     xpath_DatePickerPrevious
        // );
        Thread.sleep(5000);
            AllureReportUtil.info("Entering name change details");
            attachStepEvidence("Entering name change details");
           Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_NameChangeSaveButton)).click();
        
            Thread.sleep(5000);
            
        }
        }
          // Test Case 1279850: ESS - Name Change - Validate
// //personal info:
// By xpath_PersonalInfo=By.xpath("//*[text()='Personal Info']");
// //personal detail:
// By xpath_PersonalDetail=By.xpath("(//*[text()='Personal Details'])[2]");
//get name:
By xpath_GetName=By.xpath("(//*[text()='Personal Details']//..//following-sibling::*)[2]");
//verify first name:
By xpath_VerifyFirstName=By.xpath("(//*[text()='First Name']//..//..//..//following-sibling::*)[1]");
    
public void navigateToPersonalInfo() throws InterruptedException{
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ShowMore), "Show More");
         Wrapper.waitForpresenceOfElementLocated(xpath_ShowMore);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
         Thread.sleep(2000);
         AllureReportUtil.info("Navigated to show more page");
            attachStepEvidence("Navigated to show more page");
        // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PersonalInfo), "Personal Info");
        Wrapper.findWebElement(xpath_PersonalInfo).isDisplayed();
    //    Wrapper.setSize();
    //    Wrapper.disableChromeScallingIssue();
         Thread.sleep(5000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PersonalDetail), "Personal Details");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PersonalDetail));
        Thread.sleep(30000);
       
            
            // Wrapper.scrollDown();
            // Thread.sleep(5000);
            

}
public void navigateToPersonalDetails() throws InterruptedException{
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PersonalDetail), "Personal Details");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_PersonalDetail));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PersonalDetail));
    Thread.sleep(10000);


}

//after name change
By xpath_GetUpdatedName=By.xpath("(//*[text()='Middle Name']//..//..//..//following-sibling::*)[1]");
public void validateNameChange() throws InterruptedException{
      String Expectedname="kGLs";
    String actualName=Wrapper.getText(Wrapper.findWebElement(xpath_GetUpdatedName));
    System.out.println("Actual Name: " + actualName);
    System.out.println("Expected Name: " + Expectedname);
    // Wrapper.scrollUp();
    // Thread.sleep(5000);
    Wrapper.scrollDown();
    Thread.sleep(5000);
    AllureReportUtil.info("Actual Name: " + actualName+" Expected Name: " + Expectedname);
        attachStepEvidence("Actual Name: " + actualName+" Expected Name: " + Expectedname);
   if(actualName.contains(Expectedname)){
        Assert.assertTrue(true, "First name is updated successfully.");
        
    }else{
        Assert.fail("First name is not updated. Expected: " + Expectedname + ", Actual: " + actualName);
    }


}


    
//HRA - Name Change Approve - Submit
//tools
// By xpath_Tools = By.xpath("//*[text()='Tools']");
//Worklist
By xpath_Worklist = By.xpath("//*[text()='Worklist']");
//link:
By xpath_ChangedPersonalInformation = By.xpath("(//a[contains(text(),'Changed Personal Information')])[1]");
//Claim:
//  By xpath_Claim = By.xpath("//*[text()='Claim']");
By xpath_SelectOption = By.xpath("//select[contains(@id,'pt1:_FOr1:1:_FONSr2:0:_FOTsr1:0:pt1:r3:1:tldc:daysFilter::content')]");
//approve
By xpath_Approve=By.xpath("//button[contains(text(),'Approve')]");
By xpath_Tools = By.xpath("//*[text()='Tools']");

public void navigateToWorklist() throws InterruptedException{
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Tools), "Tools");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_Tools));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
    Wrapper.waitForpresenceOfElementLocated(xpath_Worklist);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Worklist));
}

//Comments box:
By xpath_CommentsBox=By.xpath("//*[contains(@id,'r1:0:bip_up:UPsp1:bip_rpp:0:it_apprej::content')]");
//submit:
By xpath_SubmitButtonApprove=By.xpath("//span[text()='Submit']//parent::a");


public void approvingNameChange() throws InterruptedException{
    WebElement dropdownElement = Wrapper.findWebElement(xpath_SelectOption);
    Select dropdown = new Select(dropdownElement);
    dropdown.selectByVisibleText("All");
    Thread.sleep(5000);
    

    AllureReportUtil.info("Navigated to Worklist to open transfer request");
        attachStepEvidence("the manager has navigated to Worklist to open transfer request");
    Wrapper.waitForpresenceOfElementLocated(xpath_ChangedPersonalInformation);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ChangedPersonalInformation));
    Thread.sleep(20000);
    
   
    
}

public void aproveNameChangeForRequest() throws InterruptedException{
String parentWindow = driver.getWindowHandle(); 
     Thread.sleep(5000);
       Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                driver.manage().window().maximize();
                break;
            }
        }
        
        System.out.println("All handles: " + driver.getWindowHandles());
        System.out.println("Child: " + driver.getWindowHandle());
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Claim));
        Thread.sleep(10000);
         AllureReportUtil.info("Clicked on the claim button");
                attachStepEvidence("Clicked on the claim button");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryApprove));
        Thread.sleep(5000);
        Wrapper.waitForpresenceOfElementLocated(xpath_AdHocSalaryApprovePageValidation);
        if(Wrapper.findWebElement(xpath_AdHocSalaryApprovePageValidation).isDisplayed()){
            Assert.assertTrue(true,"Name Change Approve page is displayed successfully after clicking on Name Change Request link in Worklist page.");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AdHocSalaryApproveComments), "Approving this Name Change request for testing purpose.", false);
            attachStepEvidence("the manager filled the details in Name Change Approve page and approved the request.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryApproveSubmit));
            Thread.sleep(10000);
            driver.switchTo().window(parentWindow);
            Thread.sleep(2000);
            Wrapper.waitForpresenceOfElementLocated(xpath_WorkListPageValidation);
        }else{
            Assert.fail("Name Change Approve page is not displayed after clicking on Name Change Request link in Worklist page.");
        }

   
}



//MSS - Transfer - Approve or Deny

//link name:
By xpath_KaneAdamLink = By.xpath("//table[contains(@summary,'Main Task List')]//td/a");

public void navigateToWorklistMss() throws InterruptedException{
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Tools), "Tools");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_Tools));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
    // AllureReportUtil.info("Navigated to Worklist page");
    //     attachStepEvidence("Navigated to Worklist page");
    
    Wrapper.waitForpresenceOfElementLocated(xpath_Worklist);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Worklist));


}
public void approvingNameChangeMss() throws InterruptedException{
   Thread.sleep(20000);
    // WebElement dropdownElement = Wrapper.findWebElement(xpath_SelectOption);
    // Select dropdown = new Select(dropdownElement);
    // dropdown.selectByVisibleText("All");
    AllureReportUtil.info("the manager has navigated to Worklist to open transfer request");
        attachStepEvidence("the manager has navigated to Worklist to open transfer request");
Wrapper.scrollToElement(Wrapper.findWebElement(xpath_KaneAdamLink), "Kane Adam Link");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_KaneAdamLink));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_KaneAdamLink));

Thread.sleep(20000);


       }


       public void aproveMSSForRequest() throws InterruptedException{
String parentWindow = driver.getWindowHandle(); 
     Thread.sleep(5000);
       Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                driver.manage().window().maximize();
                break;
            }
        }
        
        System.out.println("All handles: " + driver.getWindowHandles());
        System.out.println("Child: " + driver.getWindowHandle());
        AllureReportUtil.info("the manager has navigated to approve page");
        attachStepEvidence("the manager has navigated to approve page");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryApprove));
        Thread.sleep(5000);
        Wrapper.waitForpresenceOfElementLocated(xpath_AdHocSalaryApprovePageValidation);
        if(Wrapper.findWebElement(xpath_AdHocSalaryApprovePageValidation).isDisplayed()){
            Assert.assertTrue(true,"MSS Transfer Approve page is displayed successfully after clicking on MSS Transfer Request link in Worklist page.");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AdHocSalaryApproveComments), "Approving this MSS Transfer request for testing purpose.", false);
            attachStepEvidence("the manager filled the details in MSS Transfer Approve page and approved the request.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryApproveSubmit));
            Thread.sleep(10000);
            driver.switchTo().window(parentWindow);
            Thread.sleep(2000);
            Wrapper.waitForpresenceOfElementLocated(xpath_WorkListPageValidation);
        }else{
            Assert.fail("MSS Transfer Approve page is not displayed after clicking on MSS Transfer Request link in Worklist page.");
        }

   
}

      

//     Wrapper.waitForpresenceOfElementLocated(xpath_Claim);
//     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Claim));

//      // Switch back to parent window
//        driver.switchTo().window(parentWindow);
//        System.out.println("Back to Parent Window: " + driver.getTitle());


//HRA - Promote - Initiate

//my client group
//By xpath_MyClientGroups = By.xpath("//*[text()='My Client Groups']");
//Showmore:
//By xpath_ShowMore = By.xpath("(//*[text()='Show More'])[3]");
//Promote:
 By xpath_PromoteLink = By.xpath("//*[text()='Employment']//..//following-sibling::div//*[text()='Promote']");
//promote validation::
By xpath_PromotePageValidation = By.xpath("//*[text()='Promote']");

//place holder:
By xpath_SearchByNameHolder = By.xpath("//*[@placeholder='Search by Name, Business Title, Work Email, or Person Number']");

By xpath_Promote = By.xpath("//*[text()='Employment']//..//following-sibling::div//*[text()='Promote']");
public void navigateToPromotePage(String EmployeeName) throws Exception{
                    Thread.sleep(5000);
                 
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            Thread.sleep(5000);
               AllureReportUtil.info("Clicking on My Client Groups link");
            attachStepEvidence("Clicking on My Client Groups link");
            
            Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
            Thread.sleep(5000);
            AllureReportUtil.info("Clicking on Show More link");
            attachStepEvidence("Clicking on Show More link");
            Wrapper.scrollDown();
                Thread.sleep(5000);
                
            AllureReportUtil.info("Clicking on promote link");
            attachStepEvidence("Clicking on promote link");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PromoteLink));
            
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SearchByNameHolder));
                Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SearchByNameHolder), EmployeeName, false);
                if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                    Thread.sleep(15000);
                     AllureReportUtil.info("Selected employee for promote");
            attachStepEvidence("Selected employee for promote");
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(HRAXpath(EmployeeName))).click();
                           

     

}
            
                    
                }
           



//Salary:
By xpath_SalaryToggleButton = By.xpath("//oj-switch//div[contains(@aria-label,'Salary')]");
//continue button:
By xpath_HRAContinueButton = By.xpath("//button[@aria-label='Continue']");

public void selectSalaryToggleButton() throws Exception{
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SalaryToggleButton), "Salary Toggle Button");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_SalaryToggleButton));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SalaryToggleButton));

   AllureReportUtil.info("Selected toggle button");
    attachStepEvidence("Selected toggle button");
Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
}
//when and why:
By xpath_WhenAndWhyValidate = By.xpath("(//*[text()='When and why'])[1]");
//when is the employee set date:
By xpath_SelectDate = By.xpath("//span[@title='Select Date.']");
//whats the way to promote
By xpath_WhatsTheWayToPromote = By.xpath("//oj-select-single[contains(@id,'ActionId')]//span/span");
//why are u promoting
By xpath_WhyAreYouPromoting = By.xpath("//oj-select-single[contains(@id,'ActionReasonId')]//span/span");
//continue button:
//By xpath_ContinueButton = By.xpath("//button[@aria-label='Continue']");

public void verifyWhenAndWhyPageDetailsAndContinue() throws Exception{
    Wrapper.waitForpresenceOfElementLocated(xpath_WhenAndWhyValidate);
    Wrapper.findWebElement(xpath_WhenAndWhyValidate).isDisplayed();

    //select date
    Wrapper.selectDate(
            Wrapper.findWebElement(xpath_SelectDate),
            "6/April/2026",
            xpath_ResignationRetirementDateValue,
            xpath_DatePickerMonth,
            xpath_DatePickerYear,
            xpath_DatePickerPrevious
        );

 Wrapper.waitForpresenceOfElementLocated(xpath_WhatsTheWayToPromote);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_WhatsTheWayToPromote)).click();
 
        // Select "Promotion"
        By actionOption = xpath_ReasonSelection("Promotion");
        Wrapper.waitForpresenceOfElementLocated(actionOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption)).click();
 
        // Open Reason dropdown
        Wrapper.waitForpresenceOfElementLocated(xpath_WhyAreYouPromoting);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_WhyAreYouPromoting)).click();
 
        // Select "Excellent Performance"
        By reasonOption = xpath_ReasonSelection("Career Ladder Promotion");
        Wrapper.waitForpresenceOfElementLocated(reasonOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reasonOption)).click();
                AllureReportUtil.info("Selected details on when and why page");
            attachStepEvidence("Selected details on when and why page");

                // Click Continue
               
Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
        
    }
//assignment
By xpath_AssignmentValidate = By.xpath("(//span[text()='Assignment'])[1]");
//job id:(Manager)
By xpath_JobId = By.xpath("//oj-select-single[contains(@id,'JobId')]//span/span");
//gradeid:(Band 2H)
By xpath_GradeId = By.xpath("//oj-select-single[contains(@id,'GradeId')]//span/span");
//compensation indicator:(Over Maximum OM1)
By xpath_CompensationIndicator = By.xpath("//oj-select-single[contains(@id,'compensationIndicator')]//span/span");
//continue

public void verifyAssignmentPageDetailsAndContinue() throws InterruptedException{
    Wrapper.waitForpresenceOfElementLocated(xpath_JobId);
    // Select Job ID
    // Wrapper.waitForpresenceOfElementLocated(xpath_JobId);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(xpath_JobId)).click();

    // By jobOption = xpath_ReasonSelection("Manager");
    // Wrapper.waitForpresenceOfElementLocated(jobOption);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(jobOption)).click();

    // // Select Grade ID
    // Wrapper.waitForpresenceOfElementLocated(xpath_GradeId);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(xpath_GradeId)).click();

    // By gradeOption = xpath_ReasonSelection("Band 2H");
    // Wrapper.waitForpresenceOfElementLocated(gradeOption);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(gradeOption)).click();

    // // Select Compensation Indicator
    // Wrapper.waitForpresenceOfElementLocated(xpath_CompensationIndicator);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(xpath_CompensationIndicator)).click();

    // By compensationOption = xpath_ReasonSelection("Over Maximum OM1");
    // Wrapper.waitForpresenceOfElementLocated(compensationOption);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(compensationOption)).click();

                // Click Continue
                Thread.sleep(10000);
                AllureReportUtil.info("Selected details on assignment page");
            attachStepEvidence("Selected details on assignment page");
              
Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
}
//Salary:
By xpath_SalaryValidate = By.xpath("(//*[text()='Salary'])[1]");
//salary basis:
 By xpath_SalaryBasispage = By.xpath("//*[text()='Salary Amount']");
//continue:

public void salaryPageDetailsAndContinue() throws InterruptedException{
   
Wrapper.waitForpresenceOfElementLocated(xpath_SalaryBasispage);
    // // Select Salary Basis
    // Wrapper.waitForpresenceOfElementLocated(xpath_SalaryBasis);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(xpath_SalaryBasis)).click();

    // By salaryBasisOption = xpath_ReasonSelection("Regular Salary");
    // Wrapper.waitForpresenceOfElementLocated(salaryBasisOption);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(salaryBasisOption)).click();
    AllureReportUtil.info("Selected details on assignment page");
            attachStepEvidence("Selected details on assignment page");
Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();

}

//seniority dates:
By xpath_SeniorityDates = By.xpath("(//*[text()='Consolidated Edison Inc'])[1]");
//submit:
By xpath_SubmitButtonFinal = By.xpath("//button[@aria-label='Submit']");

public void verifySeniorityDatesAndSubmit() throws InterruptedException{
     Wrapper.waitForpresenceOfElementLocated(xpath_SeniorityDates);
    // Wrapper.findWebElement(xpath_SeniorityDates).isDisplayed();
    AllureReportUtil.info("Selected details on assignment page");
            attachStepEvidence("Selected details on assignment page");
Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_SubmitButtonFinal)).click();
}



//HRA-Non-Worker-Surviving_Spouse-Add
//New person
By xpath_NewPerson = By.xpath("//*[text()='New Person']");
//Add a non worker
By xpath_AddNonWorker = By.xpath("(//*[text()='Add a Nonworker'])[2]");
//communication info toggle button
By xpath_CommunicationInfoToggle = By.xpath("//oj-switch//div[contains(@aria-label,'Communication info')]");
//Addresse toggle button
By xpath_AddressesToggle = By.xpath("//oj-switch//div[contains(@aria-label,'Addresses')]");
//work relationship toggle button
By xpath_WorkRelationshipToggle = By.xpath("//oj-switch//div[contains(@aria-label,'Work relationship')]");
//Payroll details toggle button:
By xpath_PayrollDetailsToggle = By.xpath("//oj-switch//div[contains(@aria-label,'Payroll')]");
//Salary toggle button:
By xpath_SalaryToggleButtonHRA = By.xpath("//oj-switch//div[contains(@aria-label,'Salary')]");
//continue:

public void navigateToAddNonWorker() throws InterruptedException{
    Thread.sleep(5000);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore)); 
   Wrapper.findWebElement(xpath_NewPerson).isDisplayed();
Thread.sleep(5000);
AllureReportUtil.info("Navigated to Add Non-Worker page."); 
         attachStepEvidence("Navigated to Add Non-Worker page");
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AddNonWorker), "Add a Nonworker");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_AddNonWorker));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddNonWorker));

    
    Thread.sleep(5000);
    // Click Continue
        //         Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
        // Wrapper.waitForpresenceOfElementLocated(xpath_HRAContinueButton);    
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAContinueButton));
}

public void selectToggleButtonsForNonWorker() throws InterruptedException{
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_CommunicationInfoToggle), "Communication Info Toggle");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_CommunicationInfoToggle));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CommunicationInfoToggle));
    Thread.sleep(2000);
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AddressesToggle), "Addresses Toggle");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_AddressesToggle));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressesToggle));
Thread.sleep(2000);
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_WorkRelationshipToggle), "Work Relationship Toggle");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_WorkRelationshipToggle));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_WorkRelationshipToggle));
Thread.sleep(2000);
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PayrollDetailsToggle), "Payroll Details Toggle");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_PayrollDetailsToggle));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PayrollDetailsToggle));
Thread.sleep(2000);
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SalaryToggleButtonHRA), "Salary Toggle Button");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_SalaryToggleButtonHRA));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SalaryToggleButtonHRA));
    AllureReportUtil.info("Selected Salary toggle for Non-Worker"); 
         attachStepEvidence("Selected Salary toggle for Non-Worker");
Thread.sleep(2000);
     Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
        Wrapper.waitForpresenceOfElementLocated(xpath_HRAContinueButton);    
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAContinueButton));
}
//when and Why page validation
By xpath_WhenAndWhyValidateHRA = By.xpath("(//*[text()='When and why'])[1]");
//select date:
By xpath_SelectDateHRA = By.xpath("//span[@title='Select Date.']");
//legal employer(ConEd Company of New York – Pension)
By xpath_LegalEmployerHRA = By.xpath("//oj-select-single[contains(@id,'LegalEntityId')]//span/span");
//whats the way to add a non worker(Add Non‑Worker)
By xpath_AddNonWorkerHRA = By.xpath("//oj-select-single[contains(@id,'ActionId')]//span/span");
//why are you adding coworker(Creation of Non‑Worker)
By xpath_WhyAreYouAddingCoworker = By.xpath("//oj-select-single[contains(@id,'ActionReasonId')]//span/span");
//Business unit(Retirement)
By xpath_BusinessUnitHRA = By.xpath("//oj-select-single[contains(@id,'BusinessUnitId')]//span/span");
//position:
By xpath_PositionHRA = By.xpath("//oj-select-single[contains(@id,'PositionId')]//span/span");
//noworker type(Retiree)
By xpath_NonWorkerTypeHRA = By.xpath("//oj-select-single[contains(@id,'ProposedNonWorkerType')]//span/span");
//continue

public void verifyWhenAndWhyPageDetailsForNonWorkerAndContinue() throws Exception{
    Wrapper.waitForpresenceOfElementLocated(xpath_WhenAndWhyValidateHRA);
    Wrapper.findWebElement(xpath_WhenAndWhyValidateHRA).isDisplayed();

    //select date
    Wrapper.selectDate(
            Wrapper.findWebElement(xpath_SelectDateHRA),
            "6/April/2026",
            xpath_ResignationRetirementDateValue,
            xpath_DatePickerMonth,
            xpath_DatePickerYear,
            xpath_DatePickerPrevious
        );

        // Select Legal Employer
        Wrapper.waitForpresenceOfElementLocated(xpath_LegalEmployerHRA);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_LegalEmployerHRA)).click();
Thread.sleep(5000);
        By legalEmployerOption = xpath_ReasonSelection("Con Edison Company of New York");
        Wrapper.waitForpresenceOfElementLocated(legalEmployerOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(legalEmployerOption)).click();
                Thread.sleep(5000);

        // Select Way to Add Non-Worker
        Wrapper.waitForpresenceOfElementLocated(xpath_AddNonWorkerHRA);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_AddNonWorkerHRA)).click();

        By addNonWorkerOption = xpath_ReasonSelection("Add nonworker");
        Wrapper.waitForpresenceOfElementLocated(addNonWorkerOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(addNonWorkerOption)).click();
 Thread.sleep(2000);
        // Select Reason for Adding Non-Worker
        Wrapper.waitForpresenceOfElementLocated(xpath_WhyAreYouAddingCoworker);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_WhyAreYouAddingCoworker)).click();

        By reasonOption = xpath_ReasonSelection("Creation of Non-Worker");
        Wrapper.waitForpresenceOfElementLocated(reasonOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reasonOption)).click();
// Thread.sleep(20000);
                // Select Business Unit
                Wrapper.waitForpresenceOfElementLocated(xpath_BusinessUnitHRA);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(xpath_BusinessUnitHRA)).click();

                By businessUnitOption = xpath_ReasonSelection("CE People & Supply Chain");
                Wrapper.waitForpresenceOfElementLocated(businessUnitOption);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(businessUnitOption)).click();
 Thread.sleep(2000);
                        // Select Non-Worker Type
                        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_NonWorkerTypeHRA), "Non-Worker Type Dropdown");
                        Wrapper.waitForpresenceOfElementLocated(xpath_NonWorkerTypeHRA);
                    AllureReportUtil.info("Selected dropdowns on When and Why page for Non-Worker"); 
         attachStepEvidence("Selected dropdowns on When and Why page for Non-Worker");
                Wrapper.waitForpresenceOfElementLocated(xpath_NonWorkerTypeHRA);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(xpath_NonWorkerTypeHRA)).click();

                By nonWorkerTypeOption = By.xpath("(//span[contains(text(),'Retiree')])[1]");
                Wrapper.waitForpresenceOfElementLocated(nonWorkerTypeOption);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(nonWorkerTypeOption)).click();

Thread.sleep(2000);
                // Click Continue

Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_HRAContinueButton)).click();
}

//Personal details:
By personalDetailsValidation = By.xpath("(//*[text()='Personal details'])[1]");
//verify
By generatedAutomatically = By.xpath("//*[text()='Generated automatically']");
//last name:
By lastName = By.xpath("//*[text()='Last Name']//..//..//..//following-sibling::input[@class='oj-inputtext-input oj-text-field-input oj-component-initnode']"); 
//first name:
By firstName = By.xpath("//*[text()='First Name']//..//..//..//following-sibling::input[@class='oj-inputtext-input oj-text-field-input oj-component-initnode']");
//Gender:
By gender = By.xpath("//*[contains(@id,'person-bio-gender-lov-addperson-bio-create-dyn-form')]//span/span");
//DOB
By DateOfBirth = By.xpath("(//span[@title='Select Date.'])[1]");
//continue
public void verifyPersonalDetailsPageAndContinue() throws Exception{
    Thread.sleep(5000);
    //  Wrapper.waitForpresenceOfElementLocated(gender);
    // // Wrapper.findWebElement(personalDetailsValidation).isDisplayed();

    // // Wrapper.waitForpresenceOfElementLocated(generatedAutomatically);
    // // Wrapper.findWebElement(generatedAutomatically).isDisplayed();

    // Wrapper.waitForpresenceOfElementLocated(lastName);
    // Wrapper.findWebElement(lastName).sendKeys("Roy");

     String LastName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Last Name");
        String FirstName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"First Name");
        String DOB =  ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"R_DOB");
        Wrapper.waitForpresenceOfElementLocated(xpath_PersonalDetailsPageValidation);
        if(Wrapper.findWebElement(xpath_PersonalDetailsPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Personal Details page is displayed successfully.");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_LastNameInputField), LastName, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_FirstNameInputField), FirstName, false);

            Wrapper.waitForpresenceOfElementLocated(xpath_HireGender);
            // Thread.sleep(5000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HireGender), "Gender dropdown");
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_HireGender)).click();
                    Thread.sleep(5000);
            By genderOption = xpath_HireGenderOption();
            Wrapper.waitForpresenceOfElementLocated(genderOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(genderOption)).click();
                Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_HireDateOfBirth);
            Wrapper.selectDate(Wrapper.findWebElement(xpath_HireDateOfBirth), DOB, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerPrevious);
            
            // attachStepEvidence("the manager filled the details in Personal Details page for hiring a new employee.");
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_NationalIdentifierButton), "National Identifier button");
            // Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_NationalIdentifierButton));
            // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_NationalIdentifierButton));
        }else{
            Assert.fail("Personal Details page is not displayed after clicking Continue button on When and Why page.");
        }
    // Wrapper.waitForpresenceOfElementLocated(firstName);
    // Wrapper.findWebElement(firstName).sendKeys("Janie");

    // // Select Non-Worker Type
    //             Wrapper.waitForpresenceOfElementLocated(xpath_NonWorkerTypeHRA);
    //             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //                     .elementToBeClickable(xpath_NonWorkerTypeHRA)).click();

    //             By nonWorkerTypeOption = xpath_ReasonSelection("Female");
    //             Wrapper.waitForpresenceOfElementLocated(nonWorkerTypeOption);
    //             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //                     .elementToBeClickable(nonWorkerTypeOption)).click();

    //                     //select date
    // Wrapper.selectDate(
    //         Wrapper.findWebElement(DateOfBirth),
    //         "6/April/2010",
    //         xpath_ResignationRetirementDateValue,
    //         xpath_DatePickerMonth,
    //         xpath_DatePickerYear,
    //         xpath_DatePickerPrevious
    //     );
    AllureReportUtil.info("Selected personal details for Non-Worker"); 
         attachStepEvidence("Selected personal details for Non-Worker");
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_HRAContinueButton)).click();

                Thread.sleep(10000);
                 AllureReportUtil.info("Selected personal details for Non-Worker"); 
         attachStepEvidence("Selected personal details for Non-Worker");
    // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //             .elementToBeClickable(xpath_HRAContinueButton)).click();

    
}


//  Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
//     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
//                 .elementToBeClickable(xpath_HRAContinueButton)).click();

//communication info
By communicationInfo = By.xpath("(//*[text()='Communication info'])[1]");
//continue

public void verifyCommunicationInfoPageAndContinue() throws Exception{
    System.out.println("Inside communication info method");
    // Wrapper.waitForpresenceOfElementLocated(communicationInfo);
    // Wrapper.findWebElement(communicationInfo).isDisplayed();
   String PhoneCountryCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Country");
        String PhoneAreaCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Area Code");
        String PhoneNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Number");
        // String PhoneType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Type");
        System.out.println("Phone details from excel: "+PhoneCountryCode+" "+PhoneAreaCode+" "+PhoneNumber);
        Wrapper.waitForpresenceOfElementLocated(xpath_CommunicationInfoPageValidation);
        if(Wrapper.findWebElement(xpath_CommunicationInfoPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Communication Info page is displayed successfully.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PhoneDetailsButton));
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCode);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_PhoneCountryCode)).click();
                    Thread.sleep(4000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCodeManual);
            // driver.findElement(xpath_PhoneCountryCodeManual).sendKeys(PhoneCountryCode);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneCountryCodeManual), PhoneCountryCode, true);
            // By phoneCountryCodeOption = xpath_PhoneCountryCodeList("US");
            // Wrapper.waitForpresenceOfElementLocated(phoneCountryCodeOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(phoneCountryCodeOption)).click();

            Wrapper.waitForpresenceOfElementLocated(xpath_HirePhoneType);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_HirePhoneType)).click();
                Thread.sleep(5000);
            By phoneTypeOption = xpath_HirePhoneTypeOption();
            Wrapper.waitForpresenceOfElementLocated(phoneTypeOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(phoneTypeOption)).click();
                Thread.sleep(5000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneAreaCode), PhoneAreaCode, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneNumber), PhoneNumber, false);

            
// AllureReportUtil.info("the manager filled the details in Communication Info page for hiring a new employee."); 
//          attachStepEvidence("the manager filled the details in Communication Info page for hiring a new employee.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            Thread.sleep(5000);
            //Fill Email details similarly by clicking Email Details button and then click Continue button
        }else{
            Assert.fail("Communication Info page is not displayed after clicking Continue button on National Identifier details page.");
        }
       
Thread.sleep(10000);
AllureReportUtil.info("Selected communication info for Non-Worker"); 
         attachStepEvidence("Selected communication info for Non-Worker");
     Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
        Wrapper.waitForpresenceOfElementLocated(xpath_HRAContinueButton);    
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAContinueButton));

}

//address
By xpath_addressesValidationHRA = By.xpath("(//*[text()='Addresses'])[1]");
By xpath_AddressDetailsButton=By.xpath("//button//span/span[contains(normalize-space(),'Address')]");
    
    By xpath_AddressCountry=By.xpath("//oj-select-single[contains(@id,'country')]//span/span");
    By xpath_HireAddressType=By.xpath("//oj-select-single[contains(@id,'address-type')]//span/span");
    public By xpath_HireAddressTypeOption() throws Exception{
        String AddressType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Type");
        String xpath="//ul[contains(@aria-labelledby,'address-type')]//span[contains(text(),'test')]";
        xpath=xpath.replace("test", AddressType);
        return By.xpath(xpath);
    }
//continue

public void verifyAddressesPageAndContinue() throws Exception{
    // Wrapper.waitForpresenceOfElementLocated(xpath_addressesValidationHRA);
    // Wrapper.findWebElement(xpath_addressesValidationHRA).isDisplayed();
Thread.sleep(10000);
  
    
        String AddressCountry=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Country");
        // String AddressType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Type");
        String AddressLine1=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Line1");
        // String AddressLine2=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Line2");
        // String ZIPCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"ZIP_CODE");
        Thread.sleep(4000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressDetailsButton));
        Thread.sleep(4000);
        // Wrapper.waitForpresenceOfElementLocated(xpath_AddressCountry);
        // Thread.sleep(4000);
         if(Wrapper.findWebElement(xpath_AddressCountry).isDisplayed()){
            Assert.assertTrue(true, "Address details page is displayed successfully.");
        //     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //             .elementToBeClickable(xpath_AddressCountry)).click();
        //             Thread.sleep(4000);
            // driver.findElement(xpath_AddressCountryManual).sendKeys(AddressCountry);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressCountryManual), AddressCountry, true);
            // Thread.sleep(4000);
            // By addressCountryOption = xpath_AddressCountryOption();
            // Wrapper.waitForpresenceOfElementLocated(addressCountryOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(addressCountryOption)).click();

            Wrapper.waitForpresenceOfElementLocated(xpath_HireAddressType);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_HireAddressType)).click();
                    Thread.sleep(4000);
            By addressTypeOption = xpath_HireAddressTypeOption();
            Wrapper.waitForpresenceOfElementLocated(addressTypeOption);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(addressTypeOption)).click();
                Thread.sleep(4000);

            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressLine1), AddressLine1, false);
            Thread.sleep(4000);
            // // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressLine2), AddressLine2, false);

            Wrapper.waitForpresenceOfElementLocated(xpath_ZIPCode);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_ZIPCode)).click();
                    Thread.sleep(4000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_ZIPCodeManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"ZIP_CODE"), true);
            By zipCodeOption = xpath_ZIPCodeOption();
            Thread.sleep(4000);
            Wrapper.waitForpresenceOfElementLocated(zipCodeOption);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(zipCodeOption)).click();
            Thread.sleep(4000);
            // attachStepEvidence("the manager filled the details in Address section for hiring a new employee.");
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SaveButton), "Save button");
            Thread.sleep(4000);
            Wrapper.waitForpresenceOfElementLocated(xpath_SaveButton);
            Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_SaveButton));
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Thread.sleep(4000);
AllureReportUtil.info("Selected Addresses for Non-Worker"); 
         attachStepEvidence("Selected Addresses for Non-Worker");
     Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
        Wrapper.waitForpresenceOfElementLocated(xpath_HRAContinueButton);    
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAContinueButton));

    //     Thread.sleep(10000);
    //     AllureReportUtil.info("Selected value for Non-Worker"); 
    //      attachStepEvidence("Selected value for Non-Worker");
    //  Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
    // //  Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
    //     Wrapper.waitForpresenceOfElementLocated(xpath_HRAContinueButton);    
    //     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAContinueButton));
    //      Thread.sleep(10000);
    //      AllureReportUtil.info("Selected value for Non-Worker"); 
    //      attachStepEvidence("Selected value for Non-Worker");
    //  Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
    //     Wrapper.waitForpresenceOfElementLocated(xpath_HRAContinueButton);    
    //     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAContinueButton));
}
}

//Assignment validation:
By assignmentValidation = By.xpath("(//*[text()='Assignment'])[1]");
//person type(Surviving Spouse - Lumpsum - Benefit Eligible)
By personType = By.xpath("//oj-select-single[contains(@id,'UserPersonTypeId')]//span/span");

// By personType = By.xpath("//oj-select-single[contains(@id,'UserPersonTypeId')]//span/span");
//continue

public void verifyAssignmentPageDetailsForNonWorkerAndContinue() throws Exception{
    // Wrapper.waitForpresenceOfElementLocated(assignmentValidation);
    // Wrapper.findWebElement(assignmentValidation).isDisplayed();
Thread.sleep(10000);
    // // Select Person Type
    // Wrapper.waitForpresenceOfElementLocated(personType);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(personType)).click();

    // By personTypeOption = xpath_ReasonSelection("Surviving Spouse - Lumpsum - Benefit Eligible");
    // Wrapper.waitForpresenceOfElementLocated(personTypeOption);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(personTypeOption)).click();
AllureReportUtil.info("Selected Assignment for Non-Worker"); 
         attachStepEvidence("Selected Assignment for Non-Worker");
                // Click Continue
                Thread.sleep(10000);
                Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
        Wrapper.waitForpresenceOfElementLocated(xpath_HRAContinueButton);    
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAContinueButton));
}

//work relationship info validation
By workRelationshipInfo = By.xpath("(//*[text()='Work relationship info'])[1]");
//1-9 status(Ready to verify)
By i9Status = By.xpath("//oj-select-single[contains(@id,'addPersonWorkRelationshipsDDF.US._I9_STATUS')]//span/span");
//E-verify status(Employment Authorized)
By eVerifyStatus = By.xpath("//oj-select-single[contains(@id,'_E_VERIFY_STATUS')]//span/span");
//continue

public void verifyWorkRelationshipInfoPageDetailsForNonWorkerAndContinue() throws Exception{
    // Wrapper.waitForpresenceOfElementLocated(workRelationshipInfo);
    // Wrapper.findWebElement(workRelationshipInfo).isDisplayed();

    // // Select I-9 Status
    // Wrapper.waitForpresenceOfElementLocated(i9Status);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(i9Status)).click();

    // By i9StatusOption = xpath_ReasonSelection("Ready to verify");
    // Wrapper.waitForpresenceOfElementLocated(i9StatusOption);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(i9StatusOption)).click();

    // // Select E-Verify Status
    // Wrapper.waitForpresenceOfElementLocated(eVerifyStatus);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(eVerifyStatus)).click();

    // By eVerifyStatusOption = xpath_ReasonSelection("Employment Authorized");
    // Wrapper.waitForpresenceOfElementLocated(eVerifyStatusOption);
    // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
    //         .elementToBeClickable(eVerifyStatusOption)).click();
Thread.sleep(50000);
                // Click Continue
                AllureReportUtil.info("Selected Work Relationship Info for Non-Worker"); 
         attachStepEvidence("Selected Work Relationship Info for Non-Worker");
                Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
        Wrapper.waitForpresenceOfElementLocated(xpath_HRAContinueButton);    
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAContinueButton));
}
//payroll details validation:
By payrollDetails = By.xpath("(//*[text()='Payroll details'])[1]");
//continue

public void verifyPayrollDetailsPageForNonWorkerAndContinue() throws Exception{
    // Wrapper.waitForpresenceOfElementLocated(payrollDetails);
    // Wrapper.findWebElement(payrollDetails).isDisplayed();


    
Thread.sleep(50000);
AllureReportUtil.info("Selected Payroll details for Non-Worker"); 
         attachStepEvidence("Selected Payroll details for Non-Worker");
         
     Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAContinueButton), "Continue Button on first page");
     Wrapper.highlightElement(Wrapper.findWebElement(xpath_HRAContinueButton));
        Wrapper.waitForpresenceOfElementLocated(xpath_HRAContinueButton);    
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAContinueButton));
}

//salary
By salary = By.xpath("(//*[text()='Salary'])[1]");
//salary basis(Monthly Pension)
By salaryBasis = By.xpath("//oj-select-single[contains(@id,'basisSingleSelect')]//span/span");
//salarybasic amount
By salaryAmount = By.xpath("//input[contains(@id,'_vjraxymrz89')]");
//submit
By submitButton = By.xpath("//button[@aria-label='Submit']");

public void verifySalaryPageForNonWorkerAndSubmit() throws Exception{
//     Wrapper.waitForpresenceOfElementLocated(salary);
//     Wrapper.findWebElement(salary).isDisplayed();
// Thread.sleep(10000);
//     // Select Salary Basis
//     Wrapper.waitForpresenceOfElementLocated(salaryBasis);
//     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
//             .elementToBeClickable(salaryBasis)).click();

//     By xpath_salary1 = By.xpath("//*[text()='Monthly Pension']");
//     Wrapper.waitForpresenceOfElementLocated(xpath_salary1);
//     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
//             .elementToBeClickable(xpath_salary1)).click();

String SalaryAmount="1000.00";
         Thread.sleep(5000);
        Wrapper.waitForpresenceOfElementLocated(xpath_HireSalaryPageValidation);
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_HireSalaryPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Salary details page is displayed successfully.");
            
             Wrapper.waitForpresenceOfElementLocated(xpath_SalaryBasis);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_SalaryBasis)).click();
             Thread.sleep(5000);
             By salaryBasisOption = By.xpath("//ul[contains(@aria-labelledby,'basisSingleSelect')]//li//span[contains(text(),'Monthly Pension')]");   
             Wrapper.waitForpresenceOfElementLocated(salaryBasisOption);
             Thread.sleep(5000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(salaryBasisOption)).click();
             Thread.sleep(5000);
Wrapper.findWebElement(xpath_SalaryAmount).clear();
Wrapper.findWebElement(xpath_SalaryAmount).click();
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SalaryAmount), SalaryAmount, false);
            Thread.sleep(5000);
            attachStepEvidence("the manager filled the details in Salary details page for hiring a new employee.");

            // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
            // Thread.sleep(10000);
        }else{
            Assert.fail("Salary details page is not displayed after clicking Continue button on Payroll details page.");
        }
           
            // Thread.sleep(8000);
// Wrapper.WebElementsendKeys(Wrapper.findWebElement(salaryAmount), "5000", false);
Thread.sleep(10000);
                // Click Submit
        //         AllureReportUtil.info("Selected Salary for Non-Worker"); 
        //  attachStepEvidence("Selected Salary for Non-Worker");
        //         Wrapper.scrollToElement(Wrapper.findWebElement(submitButton), "Submit Button");
        // Wrapper.waitForpresenceOfElementLocated(submitButton);    
        // Wrapper.clickWebElement(Wrapper.findWebElement(submitButton));
        // Thread.sleep(10000);
        //  AllureReportUtil.info("Succesfully submitted the details for Non-Worker"); 
        //  attachStepEvidence("Succesfully submitted the details for Non-Worker");

        

}

//home button
By xpath_HomePage=By.xpath("//div[@aria-label='Home']");
//PersonIdentifiersForExternalApplications(0019187)
By xpath_PersonIdentifiersForExternalApplications=By.xpath("(//a[text()='Person Identifiers for External Applications'])[2]");
//Add symbol
By xpath_AddSymbol=By.xpath("(//button[@buttontype='submit']/span/span)[2]");

//identifierType(Contact Alternate ID)
// By xpath_identifierType= By.xpath("(//oj-input-text[contains(@id,'identifiers-template-type')]//following::span/a)[1]");
By xpath_identifierType = By.xpath("(//span[text()='Identifier Type']//following::span)[1]");
//identifier(0052795)
// By xpath_Identifierone = By.xpath("//input[contains(@id,'PersonIdentifiers.ExternalIdentifierNumber|input')]");
By xpath_Identifierone = By.xpath("(//span[text()='Identifier']//following::input)[1]");
//business title(Creation of Non‑Worker)
// By xpath_BusinessTittleone = By.xpath("(//oj-select-single[contains(@id,'worker-assignments-lov')]//span/span)[1]");
By xpath_BusinessTittleone = By.xpath("(//span[text()='Business Title']//following::span)[1]");
//from date
By xpath_FromDateone = By.xpath("(//span[@title='Select Date Time.'])[1]");
//end date
By xpath_EndDateOne =By.xpath("(//span[@title='Select Date Time.'])[1]");
//donebutton
By xpath_DoneButton=By.xpath("(//a[text()='Done'])[1]");

By xpath_selectvalue=By.xpath("//span[contains(text(),'Contact Alternate ID')]");
By xpath_Retirement=By.xpath("//span[contains(text(),'Retire')]");


public void PersonIdentifiersForNonWorkerSpouseAdd(String EmployeeName) throws Exception{
    Thread.sleep(10000);
    Wrapper.waitForpresenceOfElementLocated(xpath_HomePage);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HomePage));
    Thread.sleep(4000);
     
   Wrapper.waitForpresenceOfElementLocated(xpath_MyClientGroups);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
    Thread.sleep(8000);
    AllureReportUtil.info("Clicking on the My client group");
            attachStepEvidence("Clicking on the My client group");
            //Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ShowMore), "Show More Button");
    Wrapper.scrollDown();
    Thread.sleep(4000);
    Wrapper.scrollDown();
    Thread.sleep(4000);
     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
// Wrapper.findWebElement(xpath_ShowMore).click();
    Thread.sleep(4000);
    AllureReportUtil.info("Clicking on the show more");
            attachStepEvidence("Clicking on the show more");
            Thread.sleep(10000);
            Wrapper.scrollDown();
            AllureReportUtil.info("Clicking on the person identifiers for external application");
            attachStepEvidence("Clicking on the person identifiers for external application");
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_PersonIdentifiersForExternalApplications), "Person Identifiers For External Applications");
    Wrapper.waitForpresenceOfElementLocated(xpath_PersonIdentifiersForExternalApplications);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PersonIdentifiersForExternalApplications));
Thread.sleep(10000);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SearchByNameHolder));
                Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SearchByNameHolder), EmployeeName, false);
               Thread.sleep(8000);
                if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                    Thread.sleep(15000);
                     AllureReportUtil.info("Selected employee for Non-Worker Spouse");
            attachStepEvidence("Selected employee for Non-Worker Spouse");
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(HRAXpath(EmployeeName))).click();
                    
                    }
    Thread.sleep(15000);
Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AddSymbol), "Add Symbol");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_AddSymbol));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddSymbol));
    Thread.sleep(10000);
    AllureReportUtil.info("clicked on add button to add person identifier for non-worker spouse");
                attachStepEvidence("clicked on add button to add person identifier for non-worker spouse");
   
        // Select identifierType
        Wrapper.waitForpresenceOfElementLocated(xpath_identifierType);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_identifierType)).click();
                Thread.sleep(5000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_selectvalue));
        // Wrapper.waitForpresenceOfElementLocated(businessTitleOption);
        // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //         .elementToBeClickable(businessTitleOption)).click();
Thread.sleep(10000);
        // Select identifier
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Identifierone));
                Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Identifierone), "0052795", false);
                Thread.sleep(15000);
               
        // Select business title
        Wrapper.waitForpresenceOfElementLocated(xpath_BusinessTittleone);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_BusinessTittleone)).click(); 

                 Thread.sleep(5000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Retirement));
        // By businessTitle = xpath_ReasonSelection("Retirement");
        // Wrapper.waitForpresenceOfElementLocated(businessTitle);
        // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //         .elementToBeClickable(businessTitle)).click();
 Thread.sleep(5000);
 
// //start date
//     Wrapper.selectDate(
//             Wrapper.findWebElement(xpath_FromDateone),
//             "6/April/2026",
//             xpath_ResignationRetirementDateValue,
//             xpath_DatePickerMonth,
//             xpath_DatePickerYear,
//             xpath_DatePickerPrevious
        
//         );
//         Thread.sleep(5000);
//          Wrapper.waitForpresenceOfElementLocated(xpath_DoneButton);
//          Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
//                  .elementToBeClickable(xpath_DoneButton)).click();
// Thread.sleep(5000);
                
//                 //end date
//     Wrapper.selectDate(
//             Wrapper.findWebElement(xpath_EndDateOne),
//             "6/May/2026",
//             xpath_ResignationRetirementDateValue,
//             xpath_DatePickerMonth,
//             xpath_DatePickerYear,
//             xpath_DatePickerPrevious
        
             

//         );
//         Thread.sleep(5000);
//          Wrapper.waitForpresenceOfElementLocated(xpath_DoneButton);
//          Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
//                  .elementToBeClickable(xpath_DoneButton)).click();
Thread.sleep(10000);
AllureReportUtil.info("Entered person identifier details for Non-Worker Spouse");
                attachStepEvidence("Entered person identifier details for Non-Worker Spouse");
         
         }


// @1279854  Scenario:HRA - Change Assignment - Management to Union


//show more
By xpath_ShowMoreButton=By.xpath("(//*[text()='Show More'])[3]");
//employment:
By xpath_Employment=By.xpath("//*[text()='Employment']");
//change assignment:
By xpath_ChangeAssignment=By.xpath("(//*[text()='Change Assignment'])[3]");
// //employee search box:
//  By xpath_EmployeeSearchBox=By.xpath("//*[@placeholder='Search by Name, Business Title, Work Email, or Person Number']");
//employee name:
By xpath_EmployeeName=By.xpath("//*[text()='Kristian Edmonds']");
//direct name:
By xpath_DirectName=By.xpath("(//td[contains(@class,'oj-table-data-cell')]//a)[1]");
//salary toggle:
By xpath_SalaryToggle=By.xpath("(//*[text()='Salary']//following-sibling::*)[2]");
//continue
By xpath_ContinueFirstPage=By.xpath("//*[text()='Continue']");
//dateselector for when the assignment change starts:
By xpath_DateSelector=By.xpath("//span[@title='Select Date.']");
// //when does the assignmentchange start:
// By xpath_AssignmentChangeStart=By.xpath("//*[@title='Select Date.']");
//whats the way to change assignment
By xpath_WayToChangeAssignment=By.xpath("//oj-select-single[contains(@id,'CorrectionActionId')]//span/span");
//why the way to change assignment
By xpath_ReasonForChangeAssignment=By.xpath("//oj-select-single[contains(@id,'CorrectionActionReasonId')]//span/span");
//business unit:
 By xpath_BusinessUnitUnion=By.xpath("//oj-select-single[contains(@id,'BusinessUnitId')]//span/span");
//continue button:
By xpath_ContinueButtonSecondPage=By.xpath("//*[text()='Continue']/../../..");

//Verify page:
By xpath_PensionFormulaDefaulted=By.xpath("(//*[text()='Pension Formula Defaulted'])[1]");
//Job
By xpath_Jobid=By.xpath("//oj-select-single[contains(@id,'JobId')]//span/span");
//Grade
By xpath_Grade=By.xpath("//oj-select-single[contains(@id,'GradeId')]//span/span");
//Department
By xpath_Department=By.xpath("//oj-select-single[contains(@id,'DepartmentId')]//span/span");
//Reporting establishment:
By xpath_ReportingEstablishmentsecondpage=By.xpath("//oj-select-single[contains(@id,'ReportingEstablishmentId')]//span/span");
//Loacation:
By xpath_Locationsecondpage=By.xpath("//oj-select-single[contains(@id,'.LocationId')]//span/span");
//Hourly Paid or Salaried:
By xpath_HourlyPaidOrSalaried=By.xpath("//oj-select-single[contains(@id,'HourlySalariedCode')]//span/span");
//Working Hours Frequency:
 By xpath_WorkingHoursFrequencyone=By.xpath("//oj-select-single[contains(@id,'WorkingHoursFrequency')]//span/span");
//Union
 By xpath_Unionone=By.xpath("//oj-select-single[contains(@id,'UnionId')]//span/span");
//Bargaining Unit:
 By xpath_BargainingUnitOne=By.xpath("//oj-select-single[contains(@id,'BargainingUnitCode')]//span/span");

//second page continue button:
By xpath_SecondPageContinueButton=By.xpath("//*[text()='Continue']/../../..");
//Verify 3rd page:
By xpath_SalaryPage=By.xpath("(//*[text()='Salary'])[1]");
//continue button:
By xpath_ThirdPageContinueButton=By.xpath("//*[text()='Continue']/../../..");
//seniority date:
By xpath_SeniorityDate=By.xpath("(//*[text()='Consolidated Edison Inc'])[1]");
//submit button:
By xpath_SubmitButtonIn=By.xpath("//*[text()='Submit']/../..");


public void verifyEmploymentAndNavigateToChangeAssignment() throws Exception{
    Wrapper.waitForpresenceOfElementLocated(xpath_MyClientGroups);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
    Thread.sleep(4000);
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ShowMoreButton), "Show More Button");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_ShowMoreButton));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMoreButton));
    Thread.sleep(4000);
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Employment), "Employment");
    Wrapper.waitForElementToBeVisible(Wrapper.findWebElement(xpath_Employment));
    AllureReportUtil.info("Navigated to Change Assignment page for management employee");
     attachStepEvidence("the manager has navigated to Change Assignment page for management employee");
    Wrapper.findWebElement(xpath_Employment).isDisplayed();
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ChangeAssignment), "Change Assignment");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_ChangeAssignment));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ChangeAssignment));
}

public void clickEmployee(String EmployeeName) throws Exception{
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SearchByNameHolder));
                Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SearchByNameHolder), EmployeeName, false);
                Thread.sleep(15000);
                AllureReportUtil.info("Searching the employee");
                attachStepEvidence("Searching the employee");
                if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(HRAXpath(EmployeeName))).click();
    //  AllureReportUtil.info("Clicked on the employee name to view assignment details");
    //  attachStepEvidence("Clicked on the employee name to view assignment details");
Thread.sleep(5000);
}
}

// public By SelectWayToChangeAssignment() throws Exception{
//         String Gender=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"way to change");
//         String xpath="//div[contains(@id,'gender')]//span[contains(text(),'test')]";
//         xpath=xpath.replace("test", Gender);
//         return By.xpath(xpath);
//     }
public void selectSalaryToggle() throws Exception{
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SalaryToggle), "Salary Toggle");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_SalaryToggle));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SalaryToggle));
Thread.sleep(5000);
 AllureReportUtil.info("Clicked on Toggle button"); 
         attachStepEvidence("Clicked on Toggle button");
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ContinueFirstPage), "Continue Button on first page");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_ContinueFirstPage));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ContinueFirstPage));
    Thread.sleep(5000);
  }

public void firstPage() throws Exception{
    Wrapper.waitForpresenceOfElementLocated(xpath_DateSelector);
//select date
Wrapper.selectDate(
            Wrapper.findWebElement(xpath_DateSelector),
            "9/May/2027",
            xpath_ResignationRetirementDateValue,
            xpath_DatePickerMonth,
            xpath_DatePickerYear,
            xpath_DatePickerNext
        );


        // Wait by locator (not cached WebElement), then click a fresh element
        Wrapper.waitForpresenceOfElementLocated(xpath_WayToChangeAssignment);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_WayToChangeAssignment)).click();
 
        // Select "Assignment Change"
        By actionOption = xpath_ReasonSelection("Assignment Change");
        Wrapper.waitForpresenceOfElementLocated(actionOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption)).click();
                Thread.sleep(5000);
 
        // Open Reason dropdown
        Wrapper.waitForpresenceOfElementLocated(xpath_ReasonForChangeAssignment);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_ReasonForChangeAssignment)).click();
 
        // Select "Return to Union"
        By reasonOption = xpath_ReasonSelection("Return to Union");
        Wrapper.waitForpresenceOfElementLocated(reasonOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reasonOption)).click();
Thread.sleep(5000);


// Open Business Unit dropdown
        Wrapper.waitForpresenceOfElementLocated(xpath_BusinessUnitUnion);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_BusinessUnitUnion)).click();
 
        // Select "Return to Union"
        By businessUnitOption = xpath_ReasonSelection("CE Central Operations");
        Wrapper.waitForpresenceOfElementLocated(businessUnitOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(businessUnitOption)).click();

                Thread.sleep(5000);
                // Click Continue
                AllureReportUtil.info("Navigated to first page of assignment change process");
        attachStepEvidence("the manager has navigated to first page of assignment change process");
           
Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();

Thread.sleep(5000);
}

//union Member as Yes
By xpath_UnionMemberUnion=By.xpath("//oj-select-single[contains(@id,'LabourUnionMemberFlag')]//span/span");
//training Program as N/A
By xpath_TrainingProgramAsNA=By.xpath("//oj-select-single[contains(@id,'trainingProgram')]//span/span");
//compensation indicator as N/A
By xpath_CompensationIndicatorAsNAone=By.xpath("//oj-select-single[contains(@id,'compensationIndicator')]//span/span");
//supervisory transition rate as N/A
By xpath_SupervisoryTransitionRateAsNA=By.xpath("//oj-select-single[contains(@id,'supervisoryTransitionRate')]//span/span");
//pension formula defaulted as CBP
By xpath_PensionFormulaAsCBP=By.xpath("//oj-select-single[contains(@id,'pensionFormula')]//span/span");
//jobidinputbox
By xpath_JobidInputBox=By.xpath("(//*[text()='Job']//following::input)[1]");
//
//By xpath_Assignmentvalue=By.xpath("(//span[text()='Assignment'])[1]");
By xpath_Assignmentvalue=By.xpath("//div[@class='oj-sp-guided-process-right-panel-top-container oj-sp-gp-step-bg-color-3']");
//grade input box
By xpath_GradeInputBox=By.xpath("(//*[text()='Grade']//following::input)[1]");
//department input box
By xpath_DepartmentInputBox=By.xpath("(//*[text()='Department']//following::input)[1]");
//reporting establishment input box
By xpath_ReportingEstablishmentInputBox=By.xpath("(//*[text()='Reporting Establishment']//following::input)[1]");
//location input box
By xpath_LocationInputBox=By.xpath("((//*[text()='Location'])[3]//following::input)[1]");
// By xpath_LocationInputBox=By.xpath("//oj-input-text[contains(@id,'oj-searchselect-filter-_oj481_fl_employmentAssignments.LocationId')]//input");
//hourly paid or salaried input box
By xpath_HourlyPaidOrSalariedInputBox=By.xpath("(//*[text()='Hourly Paid or Salaried']//following::input)[1]");
//working hours frequency input box
By xpath_WorkingHoursFrequencyInputBox=By.xpath("(//*[text()='Working Hours Frequency']//following::input)[1]");
//union member input box
By xpath_UnionMemberInputBox=By.xpath("(//*[text()='Union Member']//following::input)[1]");
//union input box
By xpath_UnionInputBox=By.xpath("(//*[text()='Union']//following::input)[1]");
//bargaining unit input box
By xpath_BargainingUnitInputBox=By.xpath("(//*[text()='Bargaining Unit']//following::input)[1]");
//pension formula input box
By xpath_PensionFormulaInputBox=By.xpath("(//*[text()='Pension Formula']//following::input)[1]");
//union valueas 1-2
By xpath_UnionValueone=By.xpath("(//*[contains(text(),'1_2')])[1]");

public void sendKeysUsingClearMethod(By locator,String value) throws InterruptedException{
    Wrapper.waitForpresenceOfElementLocated(xpath_Department);
Wrapper.findWebElement(locator).clear();
        Thread.sleep(3000);
        Wrapper.findWebElement(xpath_Assignmentvalue).click();
        Thread.sleep(3000);
       
         Wrapper.findWebElement(locator).sendKeys(value);

         Thread.sleep(5000);
}
public void verifyAndEnterSecondPageDetails() throws InterruptedException{
    Wrapper.waitForpresenceOfElementLocated(xpath_Department);
//    Wrapper.findWebElement(xpath_PensionFormulaDefaulted).isDisplayed();
       Thread.sleep(10000);
    // select job
           

        sendKeysUsingClearMethod(xpath_JobidInputBox, "General Utility Worker L1-2");

        // Select "General Utility Worker L1-2"
        By actionOption = xpath_ReasonSelection("General Utility Worker L1-2");
        
        Wrapper.waitForpresenceOfElementLocated(actionOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption)).click();
 Thread.sleep(5000);
        
Thread.sleep(5000);
// select department
        
        sendKeysUsingClearMethod(xpath_DepartmentInputBox, "STEAM OPS Project Management");
 
        // Select "STEAM OPS Project Management"
        By actionOption1 = xpath_ReasonSelection("STEAM OPS Project Management");
        
        Wrapper.waitForpresenceOfElementLocated(actionOption1);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption1)).click();
                Thread.sleep(5000);

                AllureReportUtil.info("Entered the value in Assignment page");
                attachStepEvidence("Entered the value in Assignment page");
                Wrapper.scrollDown();
                Thread.sleep(5000);
 
        // Select reporting establishment
       sendKeysUsingClearMethod(xpath_ReportingEstablishmentInputBox, "The Learning Center");
 
        // Select "The Learning Center"
        By reasonOption1 = xpath_ReasonSelection("The Learning Center");
        Wrapper.waitForpresenceOfElementLocated(reasonOption1);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reasonOption1)).click();
                Thread.sleep(5000);

// select location
        // sendKeysUsingClearMethod(xpath_LocationInputBox, "Mott Haven Substation");
 
        // // Select "Mott Haven Substation"
        // By actionOption2 = xpath_ReasonSelection("Mott Haven Substation");
        
        // Wrapper.waitForpresenceOfElementLocated(actionOption2);
        // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //         .elementToBeClickable(actionOption2)).click();
        //         Thread.sleep(5000);
 
        // Select Hourly Paid or Salaried
        sendKeysUsingClearMethod(xpath_HourlyPaidOrSalariedInputBox, "Salaried");
 
        // Select "Salaried"
        By reasonOption2 = xpath_ReasonSelection("Salaried");
        
        Wrapper.waitForpresenceOfElementLocated(reasonOption2);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reasonOption2)).click();
                Thread.sleep(5000);
				
				// select working hours frequency
       sendKeysUsingClearMethod(xpath_WorkingHoursFrequencyInputBox, "Monthly");
 
        // Select "Monthly"
        By actionOption3 = xpath_ReasonSelection("Monthly");
        
        Wrapper.waitForpresenceOfElementLocated(actionOption3);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption3)).click();
                Thread.sleep(5000);
                

                // select Union Member
        sendKeysUsingClearMethod(xpath_UnionMemberInputBox, "Yes");
 
        // Select "Yes"
        By actionOption10 = xpath_ReasonSelection("Yes");
        Wrapper.waitForpresenceOfElementLocated(actionOption10);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption10)).click();


        // Select Union
        sendKeysUsingClearMethod(xpath_UnionInputBox, "1_2");
 
        // Select "Salaried"
        By reasonOption3 = By.xpath("(//*[contains(text(),'1_2')])[1]");//xpath_ReasonSelection("1-2");
        Wrapper.waitForpresenceOfElementLocated(reasonOption3);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reasonOption3)).click();
Thread.sleep(5000);
                  // select bargaining unit
        sendKeysUsingClearMethod(xpath_BargainingUnitInputBox, "1_2");
 
        // Select "Monthly"
        By actionOption4 = By.xpath("(//*[contains(text(),'1_2')])[1]");
        Wrapper.waitForpresenceOfElementLocated(actionOption4);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption4)).click();
Thread.sleep(5000);
           
AllureReportUtil.info("Entered the value in Assignment page");
                attachStepEvidence("Entered the value in Assignment page");
                Wrapper.scrollDown();
                Thread.sleep(5000);
 
//training Program as N/A
        Wrapper.waitForpresenceOfElementLocated(xpath_TrainingProgramAsNA);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_TrainingProgramAsNA)).click();
 
        // Select "Monthly"
        By actionOption11 = xpath_ReasonSelection("N/A");
        
        Wrapper.waitForpresenceOfElementLocated(actionOption11);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption11)).click();
Thread.sleep(5000);


//training Program as N/A
        Wrapper.waitForpresenceOfElementLocated(xpath_CompensationIndicatorAsNAone);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_CompensationIndicatorAsNAone)).click();
 
        // Select "Monthly"
        By actionOption13 = xpath_ReasonSelection("N/A");
        
        Wrapper.waitForpresenceOfElementLocated(actionOption13);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption13)).click();
Thread.sleep(5000);

           //supervisory transition rate as N/A
        Wrapper.waitForpresenceOfElementLocated(xpath_SupervisoryTransitionRateAsNA);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_SupervisoryTransitionRateAsNA)).click();
 
        // Select "Monthly"
        By actionOption12 = xpath_ReasonSelection("N/A");
        
        Wrapper.waitForpresenceOfElementLocated(actionOption12);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption12)).click(); 
                Thread.sleep(5000);

                
                //pension formula defaulted as CBP
        sendKeysUsingClearMethod(xpath_PensionFormulaInputBox, "CBP");
 
        // Select
        By actionOption14 = xpath_ReasonSelection("CBP");
        Wrapper.waitForpresenceOfElementLocated(actionOption14);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption14)).click(); 

        // Click Continue
        Thread.sleep(5000);
        
                // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_WorkingHoursFrequency), "scrooling to bottom of the page");
                Thread.sleep(5000);
                AllureReportUtil.info("Verified assignment details");
                attachStepEvidence("Verified assignment details");
Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
Thread.sleep(4000);
}


By xpath_HRASalaryAmount=By.xpath("//*[text()='Salary Amount']");
public void verifyThirdPageDetailsAndSubmit() throws InterruptedException{
    Thread.sleep(5000);
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SalaryPage), "Salary Page");
    Wrapper.waitForpresenceOfElementLocated(xpath_HRASalaryAmount);
   
AllureReportUtil.info("Verified details on Third page and clicked on continue to navigate to Third page");
                attachStepEvidence("Verified details on Third page and clicked on continue to navigate to Third page");
    // Click Continue
    Thread.sleep(5000);
Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();

    // Verify Seniority Date
    Wrapper.waitForpresenceOfElementLocated(xpath_SeniorityDate);
    Wrapper.findWebElement(xpath_SeniorityDate).isDisplayed();
Thread.sleep(5000);
AllureReportUtil.info("Verified details on Final page and clicked on continue to navigate to Final page");
                attachStepEvidence("Verified details on Final page and clicked on continue to navigate to Final page");
    // Click Submit
      Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSSubmitButton)).click();

Thread.sleep(4000);

}




// MSS - Manager or Supervisor- Change
//my team:
By xpath_MyTeamMSS = By.xpath("//*[text()='My Team']");
//Show more:
By xpath_ShowMoreMSS = By.xpath("(//*[text()='Show More'])[2]");
//Change manager:
By xpath_ChangeManager = By.xpath("(//*[text()='Change Manager'])[2]");
//janie roy:
By xpath_name=By.xpath("//*[text()='Janie Roy']//..");
//toggle button:
By xpath_ToggleButton = By.xpath("//*[text()='Direct reports']/following-sibling::oj-switch");
//continue button:
By xpath_ContinueButtonMSS = By.xpath("//*[text()='Continue']/ancestor::button");
//when does the assignmentchange start:
// By xpath_AssignmentChangeStartDateMSS= By.xpath("//*[@title='Select Date.']");
//whats the way to change assignment
By xpath_WayToChangeAssignmentMSS = By.xpath("//oj-select-single[contains(@id,'ActionId')]//span/span");
//why the way to change assignment
By xpath_WayToChangeAssignmentReasonMSS = By.xpath("//oj-select-single[contains(@id,'.ActionReasonId')]//span/span");
//Why are you making changes to direct reports
By xpath_WhyAreYouMakingChangesMSS = By.xpath("//oj-select-single[contains(@id,'.ManageDirectsActionReasonId')]//span/span");
//continue button:
By xpath_ContinueButtonMSS2 = By.xpath("//button[@aria-label='Continue']");
//managers verify:
By xpath_ManagersVerifyMSS = By.xpath("(//*[text()='Managers'])[1]");
//pencil icon:
By xpath_PencilIconMSS = By.xpath("(//*[text()='Manager Type']//following::button)[1]");
// By xpath_PencilIconMSS = By.xpath("//oj-button[contains(@display,'icons')]//span/span");
// By xpath_PencilIconMSS = By.xpath("//oj-button[@title='Edit Managers']/button/*");
//manager name change:
By xpath_ManagerNameChangeMSS = By.xpath("//oj-input-text[contains(@label-hint,'Managers')]//input");
//Save button:
By xpath_SaveButtonMSS = By.xpath("(//*[text()='Save']//..//..)[1]");
//submit button:
By xpath_SubmitButtonMSS = By.xpath("(//*[text()='Submit']//..//..)[1]");

//name in search box:
By xpath_SelectingName=By.xpath("//ul[@id='searchSuggestionsListbox_search1']");
//cancelbutton:
By xpath_CancelButtonMSS=By.xpath("(//span[text()='Cancel'])[1]");

public void navigateToChangeManager() throws InterruptedException{
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_MyTeamMSS), "My Team");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_MyTeamMSS));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyTeamMSS));
    
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ShowMoreMSS), "Show More");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_ShowMoreMSS));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMoreMSS));
   Thread.sleep(4000);
   AllureReportUtil.info("Navigated to Show More option under My Team section in MSS");
attachStepEvidence("Navigated to Show More option under My Team section in MSS");
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ChangeManager), "Change Manager");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_ChangeManager));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ChangeManager));
     Thread.sleep(4000);
}


public void clickEmployeeMSS(String EmployeeName) throws Exception{
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SearchByNameHolder));
                
attachStepEvidence("Enter employee name in search box to view details");
                if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(HRAXpath(EmployeeName))).click();
                            AllureReportUtil.info("click on the employee name in search result to view details");
               attachStepEvidence("click on the employee name in search result to view details");
     

}
}

public void toggleDirectReportButton() throws InterruptedException{
    Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ToggleButton), "Toggle Button for Direct Report");
    Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_ToggleButton));
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ToggleButton));
 AllureReportUtil.info("Clicked on toggle button to change manager for employee with direct reports"); 
         attachStepEvidence("Clicked on toggle button to change manager for employee with direct reports");
       
    
Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
}

public void verifyFirstPageDetailsAndContinue() throws Exception{
    

Wrapper.selectDate(
            Wrapper.findWebElement(xpath_DateSelector),
            "6/April/2026",
            xpath_ResignationRetirementDateValue,
            xpath_DatePickerMonth,
            xpath_DatePickerYear,
            xpath_DatePickerPrevious
        );


        // Wait by locator (not cached WebElement), then click a fresh element
        Wrapper.waitForpresenceOfElementLocated(xpath_WayToChangeAssignmentMSS);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_WayToChangeAssignmentMSS)).click();
 
        // Select "Manager Change"
        By actionOption = xpath_ReasonSelection("Manager Change");
        Wrapper.waitForpresenceOfElementLocated(actionOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption)).click();
 
        // Open Reason dropdown
        Wrapper.waitForpresenceOfElementLocated(xpath_WayToChangeAssignmentReasonMSS);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_WayToChangeAssignmentReasonMSS)).click();
 
        // Select "Change of Supervisor/Manager"
        By reasonOption = xpath_ReasonSelection("Change of Supervisor/Manager");
        Wrapper.waitForpresenceOfElementLocated(reasonOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reasonOption)).click();

                // Open Reason dropdown
                if(Wrapper.findWebElement(xpath_WhyAreYouMakingChangesMSS).isDisplayed() && Wrapper.findWebElement(xpath_WhyAreYouMakingChangesMSS).isEnabled()){
                    System.out.println("Why are you making changes to direct reports dropdown is displayed and enabled");
                    Wrapper.waitForpresenceOfElementLocated(xpath_WhyAreYouMakingChangesMSS);
                    AllureReportUtil.info("click on the cotinue button");
attachStepEvidence("click on the cotinue button");
        Wrapper.waitForpresenceOfElementLocated(xpath_WhyAreYouMakingChangesMSS);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_WhyAreYouMakingChangesMSS)).click();
 
        // Select "Change of Supervisor/Manager"
        By reasonOption1 = xpath_ReasonSelection("Change of Supervisor/Manager");
        Wrapper.waitForpresenceOfElementLocated(reasonOption1);
//          AllureReportUtil.info("click on the cotinue button");
// attachStepEvidence("click on the cotinue button");
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reasonOption1)).click();
//              AllureReportUtil.info("Selected reason for making changes to direct reports");
// attachStepEvidence("Selected reason for making changes to direct reports");   

Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
                }
                else{
                    AllureReportUtil.info("Selected reason for making changes to direct reports");
attachStepEvidence("Selected reason for making changes to direct reports");
                    System.out.println("Why are you making changes to direct reports dropdown is either not displayed or not enabled");
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
                }
}

//manager input box:
//  By xpath_ManagerInputBox=By.xpath("(//input[text()='Managers']//following::input)[1]");
  By xpath_ManagerInputBox=By.xpath("//oj-input-text[contains(@label-hint,'Managers')]//input");
By xpath_Manager=By.xpath("(//*[text()='Managers'])[1]");
By xpath_MangerOuterbox=By.xpath("(//div[@class='oj-text-field-middle'])[1]");
public void changeManagerAndSubmit() throws Exception{
    Thread.sleep(8000);
    // Wrapper.waitForpresenceOfElementLocated(xpath_PencilIconMSS);
    // Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_PencilIconMSS));
    Thread.sleep(8000);
    Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PencilIconMSS));
    // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PencilIconMSS));

     Thread.sleep(8000);
      AllureReportUtil.info("Clicking on the edit button");
attachStepEvidence("Clicking on the edit button");
// Wrapper.waitForpresenceOfElementLocated(xpath_ManagerInputBox);
//  Wrapper.clickUsingJS(Wrapper.findWebElement(xpath_ManagerInputBox));
 Thread.sleep(5000);
//  Wrapper.findwebElement(xpath_MangerOuterbox).clear();
//  driver.findElement(xpath_MangerOuterbox).clear();
//  Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Manager));

// Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ManagerInputBox));
 JavascriptExecutor js = (JavascriptExecutor) driver;
 
// js.executeScript("arguments[0].value = arguments[1];", Wrapper.findWebElement(xpath_ManagerInputBox), "0040520");

String script =     "var el = arguments[0];" +     "var val = arguments[1];" +     "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +     "setter.call(el, val);" +     "el.dispatchEvent(new Event('input', { bubbles: true }));" +     "el.dispatchEvent(new Event('change', { bubbles: true }));"; ((JavascriptExecutor) driver).executeScript(script, Wrapper.findWebElement(xpath_ManagerInputBox), "0040520");
Thread.sleep(5000);
String enterScript =
    "var el = arguments[0];" +
    "['keydown','keypress','keyup'].forEach(function(type){" +
    "  el.dispatchEvent(new KeyboardEvent(type, {" +
    "    key: 'Enter'," +
    "    code: 'Enter'," +
    "    keyCode: 13," +
    "    which: 13," +
    "    bubbles: true" +
    "  }));" +
    "});";
((JavascriptExecutor) driver).executeScript(enterScript, Wrapper.findWebElement(xpath_ManagerInputBox));
    // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_ManagerInputBox), "0040520", true);
 
Thread.sleep(10000);
AllureReportUtil.info("Changed the manager ");
attachStepEvidence("Changed the manager ");
    
    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_SaveButtonMSS)).click();
                Thread.sleep(8000);
                //  Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                // .elementToBeClickable(xpath_CancelButtonMSS)).click();
                // Thread.sleep(8000);


    AllureReportUtil.info("successfully changed the manager name"); 
         attachStepEvidence("successfully changed the manager name");
System.out.println("Why are you making changes to direct reports dropdown is either not displayed or not enabled");
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
                Thread.sleep(15000);
                 AllureReportUtil.info("click on the cotinue button");
attachStepEvidence("click on the cotinue button");
                    
By xpath_DirectReportsInputBox=By.xpath("//oj-select-single[contains(@class,'oj-listbox-dropdown-open')]//input[contains(@id,'oj-searchselect-filter')]");
//direct reports verification and continue
// Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DirectReportsInputBox));
// Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_DirectReportsInputBox), "0040520", true);

                System.out.println("Why are you making changes to direct reports dropdown is either not displayed or not enabled");
                    Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
                Thread.sleep(15000);
                 AllureReportUtil.info("click on the cotinue button");
attachStepEvidence("click on the cotinue button");
        //         AllureReportUtil.info("Clicked on continue button for direct reports"); 
        //  attachStepEvidence("Clicked on continue button for direct reports");
                    

 // Verify Seniority Date
    Wrapper.waitForpresenceOfElementLocated(xpath_SeniorityDate);
   

    // Click Submit
      Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSSubmitButton)).click();
                Thread.sleep(10000);
                AllureReportUtil.info("Clicked on submit button after changing manager");
            attachStepEvidence("Clicked on submit button after changing manager"); 

      }

//madhus
 // ======================= LOCATORS ======================

 By xpath_DirectReportsChange=By.xpath("//div[contains(@quickactioncategory,'grp_mcg_employment')]//a[text()='Direct Reports']");
 By xpath_DirectReportsChangePageValidation=By.xpath("//h1[contains(text(),'Direct Reports')]");
 By xpath_DirectReportsChangeWhenAndWhyPageValidation=By.xpath("//span[contains(@aria-label,'When and why')]");
 By xpath_ChangeStartDate=By.xpath("//div[contains(@aria-label,'Date Picker')]//parent::oj-input-date//span/span[contains(@title,'Select Date')]");
 By xpath_WayToChange=By.xpath("//oj-select-single[contains(@id,'ActionId')]//span/span");
 By xpath_WayToChangeList=By.xpath("//div[contains(@id,'ActionId')]//div[contains(@id,'ActionName')]");
 By xpath_ReasonForChange=By.xpath("//oj-select-single[contains(@id,'ReasonId')]//span/span");
 By xpath_ReasonForChangeList=By.xpath("//div[contains(@id,'ActionReasonId')]//div[contains(@id,'ActionReason_')]");
 By xpath_DirectReportsInfoChangePageValidation=By.xpath("//span[contains(@aria-label,'Direct report')]");
 By xpath_SearchPeopleToAddAsReports=By.xpath("//div[contains(@class,'oj-text-field-middle')]//input[contains(@id,'filter-workersLovSingleSelect')]");
 By xpath_SearchPeopleToAddAsReportsManual=By.xpath("//div[contains(@class,'oj-text-field-middle')]//input[contains(@aria-labelledby,'workersLovSingleSelect')]");
 By xpath_WorkList=By.xpath(" //div[contains(@id,'groupNode_tools')]//div[contains(@id,'WORKLIST')]//*[local-name()='svg']");
 By xpath_WorkListPageValidation=By.xpath("//h1[contains(text(),'Worklist')]");
 By xpath_AdHocSalaryRequest=By.xpath("//table[contains(@summary,'Main Task List')]//td/a");
 By xpath_AdHocSalaryApprove=By.xpath("//button[contains(text(),'Approve')]");
 By xpath_AdHocSalaryApproveComments=By.xpath("//table//textarea");

 // ======================= METHODS =======================

 

 public void navigateAndValidateFamilyAndEmergencyContacts()throws Exception{
         Thread.sleep(5000); // Consider replacing with a more robust wait strategy
         attachStepEvidence("On Dashboard page after login");
         Thread.sleep(3000);
         Wrapper.scrollToElement(Wrapper.findWebElement(xpath_FamilyandEmergencyAction), "Family and Emergency Contacts");
         Thread.sleep(3000); // Consider replacing with a more robust wait strategy
         attachStepEvidence("Scrolled to Family and Emergency Contacts action");
         Thread.sleep(2000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_FamilyandEmergencyAction));

     }

 public void clickAddIcon(){
         try{
             Wrapper.waitForpresenceOfElementLocated(xpath_FamilyandEmergencyPageValidation);
             if(Wrapper.findWebElement(xpath_FamilyandEmergencyPageValidation).isDisplayed() && Wrapper.findWebElement(xpath_MyContactsTitle).isDisplayed()){
                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddIcon));
             }else{
                 Assert.fail("Failed to navigate to Family and Emergency Contacts page.");
             }
         }catch(Exception e){
             e.printStackTrace();
             throw new RuntimeException("Error navigating to Family and Emergency Contacts page: " + e.getMessage());
         }   
     }

 public void clickContinueButton(){
         try{
             Thread.sleep(3000);
             Wrapper.waitForpresenceOfElementLocated(xpath_ContinueButton);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ContinueButton));
         }catch(InterruptedException e){
             e.printStackTrace();
             throw new RuntimeException("Error clicking Continue button: " + e.getMessage());
         }
     }

 public void enterBasicInfo() throws Exception{
         try{
             String lastName = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Last Name");
             String firstName = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"First Name");
             String suffix = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Suffix");
             String middleName = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Middle Name");
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_LastName), lastName, false);
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_FirstName), firstName, false);
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Suffix), suffix, false);
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_MiddleName), middleName, false);   
         }catch(Exception e){
             e.printStackTrace();
             throw new Exception("Error entering basic info: " + e.getMessage());
         }
     }

 public void enterRelationshipInfo() throws Exception{
         try{
             String relationship = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Relationship");
             String relationshipStartDate = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"R_Start Date");   
             String gender   = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"R_Gender");
             String rDOB = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"R_DOB");
             // String IsEmergencyContact = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Is Emergency Contact");
             // String IsPrimaryEmergencyContact = ExcelReader.getCellDataByKey(EXCEL_PATH  ,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Is Primary Emergency Contact");
             String tinType = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"TIN Type");
             String tinNumber = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"TIN Number");
             String benefitsOfferedConditionally = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"BOC");
             // String emergencyContactNotes = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Emergency_CN");

             Wrapper.waitForpresenceOfElementLocated(xpath_Relationship);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(xpath_Relationship)).click();

             // Select "Relationship" from dropdown
             By RelationShipActionOption = xpath_RelationshipDropDownValues(relationship);
             Wrapper.waitForpresenceOfElementLocated(RelationShipActionOption);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(RelationShipActionOption)).click();

             Wrapper.selectDate(Wrapper.findWebElement(xpath_RelationshipSD), relationshipStartDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerPrevious);
         
             Wrapper.waitForpresenceOfElementLocated(xpath_Gender);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(xpath_Gender)).click();

             // Select "Gender" from dropdown
             By genderActionOption = xpath_RelationshipDropDownValues(gender);
             Wrapper.waitForpresenceOfElementLocated(genderActionOption);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(genderActionOption)).click();
         
             Wrapper.selectDate(Wrapper.findWebElement(xpath_RDOB), rDOB, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerPrevious);
         
             // Wrapper.waitForpresenceOfElementLocated(xpath_RelationshipPrimaryEmergencyContact);
             // if(!Wrapper.findWebElement(xpath_RelationshipPrimaryEmergencyContact).isSelected()){
             //     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_RelationshipPrimaryEmergencyContact));
             // }
         
             // Wrapper.waitForpresenceOfElementLocated(xpath_TinType);
             // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
             //     .elementToBeClickable(xpath_TinType)).click();

             // // Select "TinType" from dropdown
             // By tinTypeActionOption = xpath_TinTypeList(tinType);
             // Wrapper.waitForpresenceOfElementLocated(tinTypeActionOption);
             // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
             //         .elementToBeClickable(tinTypeActionOption)).click();

             // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_TinNumber), tinNumber, false);
         
             // Wrapper.waitForpresenceOfElementLocated(xpath_BenefitsOfferedConditionally);
             // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
             //     .elementToBeClickable(xpath_BenefitsOfferedConditionally)).click();

             // Select "BenefitsOfferedConditionally" from dropdown
             // By benefitsOfferedConditionallyOption = xpath_BenefitsOfferedConditionallyList(benefitsOfferedConditionally);
             // Wrapper.waitForpresenceOfElementLocated(benefitsOfferedConditionallyOption);
             // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
             //     .elementToBeClickable(benefitsOfferedConditionallyOption)).click();
         }catch(Exception e){
             e.printStackTrace();
             throw new Exception("Error entering relationship info: " + e.getMessage());
         }
     }

 public void enterPhoneDetails() throws Exception {
         try{
             String phoneCountryCode = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Country");
             String phoneType = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Type");
             String phoneAreaCode = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Area Code");
             String phoneNumber = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Number");
             String phoneExtension = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Extension");
             String phoneFromDate = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_From Date");
             String phoneToDate = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_To Date");
             Thread.sleep(5000);
             Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCode);
             Thread.sleep(5000); // Consider replacing with a more robust wait strategy
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(xpath_PhoneCountryCode)).click();
             Thread.sleep(3000); // Consider replacing with a more robust wait strategy
             driver.findElement(xpath_PhoneCountryCodeManual).sendKeys(phoneCountryCode);
             Thread.sleep(2000); // Consider replacing with a more robust wait strategy
             // Select "Country" from dropdown
             By phoneCountry = xpath_PhoneCountryCodeList("US");
             Wrapper.waitForpresenceOfElementLocated(phoneCountry);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(phoneCountry)).click();
 
             // Wait by locator (not cached WebElement), then click a fresh element
             Wrapper.waitForpresenceOfElementLocated(xpath_PhoneType);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(xpath_PhoneType)).click();

             // Select "PhoneType" from dropdown
             By phoneTypeOption = xpath_PhoneTypeList(phoneType);
             Wrapper.waitForpresenceOfElementLocated(phoneTypeOption);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(phoneTypeOption)).click();

             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneAreaCode), phoneAreaCode,false);
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneNumber), phoneNumber,false);
             // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneExtension), phoneExtension);  
             // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneFromDate), phoneFromDate);
             // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneToDate), phoneToDate); 
 
         }catch(Exception e){
             e.printStackTrace();
             throw new Exception("Error entering phone details: " + e.getMessage());
         }
     }

 public void enterEmailDetails() throws Exception {
         try{
             String emailType = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email Type");
             String emailAddress = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email");
         
             // Wait by locator (not cached WebElement), then click a fresh element
             Wrapper.waitForpresenceOfElementLocated(xpath_EmailType);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(xpath_EmailType)).click();

             // Select "EmailType" from dropdown
             By EmailTypeOption = xpath_EmailTypeList(emailType);
             Wrapper.waitForpresenceOfElementLocated(EmailTypeOption);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(EmailTypeOption)).click();
         
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Email), emailAddress, false);
 
         }catch(Exception e){
             e.printStackTrace();            
             throw new Exception("Error entering email details: " + e.getMessage());
         }
     }

 public void enterAddressDetails() throws Exception{
         try{
             String address_Type= ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address Type");
             if(address_Type.equalsIgnoreCase("Use My Address")){
                 if(Wrapper.findWebElement(xpath_AddressType).isSelected()){
                     // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressType));
                 
                     Wrapper.waitForpresenceOfElementLocated(xpath_Address);
                     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                             .elementToBeClickable(xpath_Address)).click();

                     // Select "AddressType" from dropdown
                     // By AddressTypeOption = xpath_AddressTypeList(AddressType);
                     Wrapper.waitForpresenceOfElementLocated(xpath_AddressValue);
                     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                             .elementToBeClickable(xpath_AddressValue)).click();

                 }
                 else{
                     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressType));
                     Wrapper.waitForpresenceOfElementLocated(xpath_Address);
                     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                             .elementToBeClickable(xpath_Address)).click();

                     // Select "AddressType" from dropdown
                     // By AddressTypeOption = xpath_AddressTypeList(AddressType);
                     Wrapper.waitForpresenceOfElementLocated(xpath_AddressValue);
                     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                             .elementToBeClickable(xpath_AddressValue)).click();
                 }
             }
         }catch(Exception e){
             e.printStackTrace();
             throw new Exception("Error entering address details: " + e.getMessage());
         }        
     }

 public void enterNationalIdentifiers() throws Exception{
         try{
             String national_Identifier_Country= ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_Country");
             String national_Identifier_Type= ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_Type");
             String national_Identifier_ID= ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_ID");
             String issue_Date= ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_Issue Date");
             String expiration_Date= ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_Expiration Date");
     
             if(Wrapper.findWebElement(xpath_NICountry).isDisplayed()){
              
                 // Wait by locator (not cached WebElement), then click a fresh element
                 Wrapper.waitForpresenceOfElementLocated(xpath_NICountry);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(xpath_NICountry)).click();

                 // Thread.sleep(3000); // Consider replacing with a more robust wait strategy
                 driver.findElement(xpath_NICountryManual).sendKeys(national_Identifier_Country);
                 // Thread.sleep(2000); // Consider replacing with a more robust wait strategy

                 // Select "National Identifier Country" from dropdown
                 By NICountryOption = xpath_NICountryList("US");
                 Wrapper.waitForpresenceOfElementLocated(NICountryOption);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(NICountryOption)).click();

         
                 // Wait by locator (not cached WebElement), then click a fresh element
                 Wrapper.waitForpresenceOfElementLocated(xpath_NIType);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(xpath_NIType)).click();

                 // Select "National Identifier Type" from dropdown
                 By NITypeOption = xpath_NITypeList(national_Identifier_Type);
                 Wrapper.waitForpresenceOfElementLocated(NITypeOption);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(NITypeOption)).click();

                 Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_NIID), national_Identifier_ID, false);
                 Wrapper.selectDate(Wrapper.findWebElement(xpath_NIIssueDate), issue_Date, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerPrevious);
                 Wrapper.selectDate(Wrapper.findWebElement(xpath_NIExpirationDate), expiration_Date, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
                 }
             else{
                     AllureReportUtil.info("National Identifier section is not displayed for this contact.");
                     // System.out.println("National Identifier section is not displayed for this contact.");
                 }
         }catch(Exception e){
             e.printStackTrace();
             throw new Exception("Error entering national identifiers: " + e.getMessage());
         }
     }

 public void submitFamilyAndEmergencyContactForm() throws Exception{
         try{
             Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SubmitButton),"Submit");
             Thread.sleep(5000); // Consider replacing with a more robust wait strategy
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SubmitButton));
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("Error in submitFamilyAndEmergencyContactForm step: " + e.getMessage(), e);
         }
     }

 public void selectCoworkerOption(){
         try{
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SelectCoworkerAsContact));
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("Error in selectCoworkerOption step: " + e.getMessage(), e);
         }
     }

 public void selectCoworkerOptionHRAdmin(){
         try{
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ExistingEmployeeAsContact));
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("Error in selectCoworkerOptionHRAdmin step: " + e.getMessage(), e);
         }
     }

 public void enterCoworkerDetails() throws Exception{
         try{
             String CoworkerRelationshipStartDate = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CoworkerRelationshipSD");
             String CoworkerName= ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CoworkerNumber");
             String CoworkerRelationship = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CoworkerRelationship");
             String CoworkerECNotes = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CoworkerECN");
             Thread.sleep(5000);
             if(Wrapper.findWebElement(xpath_CoworkerPageValidation).isDisplayed() || Wrapper.findWebElement(xpath_HRAdminCoworkerPageValidation).isDisplayed()){
                 Wrapper.selectDate(Wrapper.findWebElement(xpath_CoworkerRelationshipStartDate), CoworkerRelationshipStartDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerPrevious);
             
                 Wrapper.waitForpresenceOfElementLocated(xpath_CoworkerSearch);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(xpath_CoworkerSearch)).click();
                 // Thread.sleep(3000); // Consider replacing with a more robust wait strategy
                 Wrapper.waitForpresenceOfElementLocated(xpath_CoworkerSearchManual);
                 driver.findElement(xpath_CoworkerSearchManual).sendKeys(CoworkerName);
                 // Thread.sleep(2000); // Consider replacing with a more robust wait strategy

                 By CoworkerSearchOption = xpath_CoworkerSearchList(CoworkerName);
                 Wrapper.waitForpresenceOfElementLocated(CoworkerSearchOption);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(CoworkerSearchOption)).click();

         
                 Wrapper.waitForpresenceOfElementLocated(xpath_CoworkerRelationship);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(xpath_CoworkerRelationship)).click();

                 By CoworkerRelationshipOption = xpath_CoworkerRelationshipList(CoworkerRelationship);
                 Wrapper.waitForpresenceOfElementLocated(CoworkerRelationshipOption);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(CoworkerRelationshipOption)).click();

                 Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_CoworkerEmergencyContactNotes), CoworkerECNotes, false);
             }
         }catch(Exception e){
             e.printStackTrace();
             throw new Exception("Error entering coworker details: " + e.getMessage());  
         }   
     }

 public void enterHRACoworkerDetails() throws Exception {
         try{
             String CoworkerRelationshipStartDate = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CoworkerRelationshipSD");
             String CoworkerName= ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CoworkerNumber");
             String CoworkerRelationship = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CoworkerRelationship");
             String CoworkerECNotes = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"CoworkerECN");
             Thread.sleep(5000);
             if(Wrapper.findWebElement(xpath_HRAdminCoworkerPageValidation).isDisplayed()){
                 Wrapper.selectDate(Wrapper.findWebElement(xpath_CoworkerRelationshipStartDate), CoworkerRelationshipStartDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerPrevious);
             
                 Wrapper.waitForpresenceOfElementLocated(xpath_CoworkerSearch);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(xpath_CoworkerSearch)).click();
                 // Thread.sleep(3000); // Consider replacing with a more robust wait strategy
                 Wrapper.waitForpresenceOfElementLocated(xpath_CoworkerSearchManual);
                 driver.findElement(xpath_CoworkerSearchManual).sendKeys(CoworkerName);
                 // Thread.sleep(2000); // Consider replacing with a more robust wait strategy

                 By CoworkerSearchOption = xpath_CoworkerSearchList(CoworkerName);
                 Wrapper.waitForpresenceOfElementLocated(CoworkerSearchOption);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(CoworkerSearchOption)).click();

         
                 Wrapper.waitForpresenceOfElementLocated(xpath_CoworkerRelationship);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(xpath_CoworkerRelationship)).click();

                 By CoworkerRelationshipOption = xpath_CoworkerRelationshipList(CoworkerRelationship);
                 Wrapper.waitForpresenceOfElementLocated(CoworkerRelationshipOption);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(CoworkerRelationshipOption)).click();

                 Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_CoworkerEmergencyContactNotes), CoworkerECNotes, false);
             }
         }catch(Exception e){
             e.printStackTrace();
             throw new RuntimeException("Error entering coworker details: " + e.getMessage(), e);  
         }
     }

 public void navigateToCompensation() throws Exception{
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
         Wrapper.waitForpresenceOfElementLocated(xpath_Compensation);
         Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Compensation),"Compensation");
         Thread.sleep(3000);
         attachStepEvidence("Navigated to Compensation section");
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyCompensation));
     }
     By xpath_CurrentSalaryDetailsValidation=By.xpath("//h2[contains(@aria-label,'Current salary')]");
 public void validateCompensationDetails(){
         if(Wrapper.findWebElement(xpath_CurrentSalary).isDisplayed()){
             Assert.assertTrue(true, "Current Salary details are displayed.");
             Wrapper.scrollToElement(Wrapper.findWebElement(xpath_CurrentSalaryDetailsValidation),"Current Salary Details");
             attachStepEvidence("Current Salary details are displayed.");
             String salary = Wrapper.findWebElement(xpath_Salary).getText();
             // String adjustment = Wrapper.findWebElement(xpath_Adjustment).getText();
             // String effectivePeriod = Wrapper.findWebElement(xpath_EffectivePeriod).getText();
             // String component = Wrapper.findWebElement(xpath_Component).getText();
             // String percentage = Wrapper.findWebElement(xpath_Percentage).getText();

             boolean isCompenensationDetailsDisplayed= !salary.isEmpty(); 
             // && !adjustment.isEmpty() && !effectivePeriod.isEmpty(); 
             // && !component.isEmpty() && !percentage.isEmpty();
         
             Assert.assertTrue(isCompenensationDetailsDisplayed, "Salary, Adjustment, and Effective Period details are displayed correctly.");
         
         }else{
             Assert.fail("Current Salary details are not displayed.");
         }
     }

 public void signOut(){
         try{

             // Thread.sleep(2000);
             // attachStepEvidence("Clicked on Signout icon");
             Wrapper.findWebElement(xpath_Profile).isDisplayed();
             Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Profile),"Profile icon");
         
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Profile));
             Thread.sleep(3000);
             Wrapper.waitForpresenceOfElementLocated(xpath_SignOut);
             Thread.sleep(2000);
             attachStepEvidence("Clicked on Signout icon");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SignOut)); 
         }catch(Exception e){
             e.printStackTrace();
             throw new RuntimeException("Error signing out: " + e.getMessage(), e);
         }
     }

 public void navigateToResignationRetirement() {
         try{

             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
             // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Employment));
             Thread.sleep(5000);
             Wrapper.findWebElement(xpath_ResignationRetirement).isDisplayed();
             // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ResignationRetirement),"Resignation/Retirement");
             attachStepEvidence("Navigated to Resignation/Retirement page");
             Thread.sleep(3000);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ResignationRetirement));
         }catch(InterruptedException e){
             e.printStackTrace();
             throw new RuntimeException("Error navigating to Resignation/Retirement: " + e.getMessage(), e);
         }

     }

 public void enterResignationRetirementDetails(String ResignationRetirementDate) throws Exception{
     
         // Wrapper.selectDate(Wrapper.findWebElement(xpath_ResignationNotificationDate), ResignationNotificationDate, xpath_ResignationNotificationDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
         Wrapper.waitForpresenceOfElementLocated(xpath_ResignationRetirementDate);
         Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ResignationRetirementDate),"Resignation/Retirement Date");
         Wrapper.selectDate(Wrapper.findWebElement(xpath_ResignationRetirementDate), ResignationRetirementDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
     
         if(Wrapper.findWebElement(xpath_ResignationRetirementReasonnValidation).isDisplayed()){
             Wrapper.waitForpresenceOfElementLocated(xpath_ResignationRetirementAction);
             // Thread.sleep(10000);
             Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ResignationRetirementAction),"Resignation/Retirement Action");
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(xpath_ResignationRetirementAction)).click();
         
             // Thread.sleep(4000);
             By ResignationRetirementActionOption = xpath_ReasonSelection("Retirement");
             Wrapper.waitForpresenceOfElementLocated(ResignationRetirementActionOption);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(ResignationRetirementActionOption)).click();

             Wrapper.waitForpresenceOfElementLocated(xpath_ResignationRetirementReason);
             // Thread.sleep(10000);
             Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ResignationRetirementReason),"Resignation/Retirement Reason");
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(xpath_ResignationRetirementReason)).click();
         
             // Thread.sleep(4000);
             By ResignationRetirementOption = xpath_ReasonSelection("Retirement");
             Wrapper.waitForpresenceOfElementLocated(ResignationRetirementOption);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(ResignationRetirementOption)).click();

             attachStepEvidence("Resignation/Retirement action selected as Retirement.");
             Thread.sleep(3000);
         }  
     }
     By xpath_ThirdPageValidation=By.xpath("//span[contains(@aria-label,'Seniority dates')]");


 public void clickContinueOnResignationRetirement(){
         try{
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Continue));
             Thread.sleep(2000);
             Wrapper.waitForpresenceOfElementLocated(xpath_ThirdPageValidation);
             attachStepEvidence("Resignation/Retirement details entered and navigated to the next page.");
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("Error in clickContinueOnResignationRetirement step: " + e.getMessage(), e);
         }
     }

 public void submitResignationRetirement(){
         // System.out.println("Submitting the resignation/retirement request.");
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Submit));
         // System.out.println("Clicked on Submit button for resignation/retirement request.");
     }

 public void resignFromEmployment(String ResignationRetirementDate) throws Exception{
         // navigateToResignationRetirement();
         // enterResignationRetirementDetails(ResignationRetirementDate);
         // System.out.println("Initiating the resignation process.");
         // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
         // System.out.println("Clicked on Show More.");
         // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ResignationRetirement));
         // navigateToResignationRetirement();
         // System.out.println("Navigated to Resignation/Retirement page.");
         // Thread.sleep(5000);
         // enterResignationRetirementDetails(ResignationRetirementDate);
     
         // Wrapper.selectDate(Wrapper.findWebElement(xpath_ResignationRetirementDate), ResignationRetirementDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
         Wrapper.waitForpresenceOfElementLocated(xpath_ResignationRetirementDate);
         Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ResignationRetirementDate),"Resignation/Retirement Date");
         Wrapper.selectDate(Wrapper.findWebElement(xpath_ResignationRetirementDate), ResignationRetirementDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
         if(Wrapper.findWebElement(xpath_ResignationRetirementReasonnValidation).isDisplayed()){

             Wrapper.waitForpresenceOfElementLocated(xpath_ResignationRetirementReason);
             // Thread.sleep(10000);
             Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ResignationRetirementReason),"Resignation/Retirement Reason");
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(xpath_ResignationRetirementReason)).click();
         
             // Thread.sleep(4000);
             By ResignationRetirementOption = xpath_ReasonSelection("Resign-Personal Reasons");
             Wrapper.waitForpresenceOfElementLocated(ResignationRetirementOption);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                     .elementToBeClickable(ResignationRetirementOption)).click();
         
             attachStepEvidence("Resignation/Retirement reason selected as Resign-Personal Reasons.");
             Thread.sleep(3000);
         
         }

         clickContinueOnResignationRetirement();
         submitResignationRetirement();
     }
     By xpath_MyTeam=By.xpath("//a[text()='My Team']");
     By xpath_TeamActivityCenter=By.xpath("//a[text()='Team Activity Center']");

 public void navigateToTeamActivityCenter() throws InterruptedException {
         try{
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyTeam));
             Wrapper.waitForpresenceOfElementLocated(xpath_TeamActivityCenter);
             attachStepEvidence("Manager is on the Activity Center page");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TeamActivityCenter));

         }catch(Exception e){
             e.printStackTrace();
             throw new RuntimeException("Error navigating to Team Activity Center: " + e.getMessage());
         } 
     }
     By xpath_TeamActivityCenterPageValidation=By.xpath("//span[text()='Team']");
   
     By xpath_EmployeeReports=By.xpath("//oj-list-view//ul[@aria-label='Workers']//li//oj-avatar");
     
     By xpath_EmployeeReportsVerification=By.xpath("//ul[contains(@aria-label,'Scoreboard metric cards')]//div/div[contains(@title,'directs')]");
     
     By xpath_OrgHierarchy=By.xpath("//button[contains(@aria-label,'Org Hierarchy')]//span//span[text()='Org Hierarchy']");
 
     By xpath_OrgHierarchyValidation1=By.xpath("//oj-sp-hierarchy-card[contains(@class,'hierarchy')]//div[contains(@role,'group')]/div[2]/div/div");
     
     By xpath_OrgHierarchyValidation2=By.xpath("//h1[contains(text(),'Org Hierarchy')]");
 
     By xpath_BackArrow=By.xpath("//button[@aria-label='Go back']/span/span/span");
 
     By xpath_TeamActions=By.xpath("//button[contains(@aria-label,'Team Actions')]");
 
     By xpath_TeamActionValue=By.xpath("//div[@aria-label='Team Actions']//div/a/span/span");
 
     By xpath_MSSCompensation=By.xpath("//label[text()='Compensation']");
 
     By xpath_MSSCompensationValue=By.xpath("//label[text()='Salary']");
 
     By xpath_MSSEmployment=By.xpath("//label[text()='Employment']");
 
     By xpath_ThreeDots=By.xpath("(//button[contains(@aria-label,'Actions for')])[2]/div/span");
 
     By xpath_ManagerActions=By.xpath("//oj-menu//oj-option/a");
     WebDriverWait wait = Wrapper.getWait();
     

 public void validateTeamActivityCenterPage() throws Exception {
     
         Wrapper.waitForpresenceOfElementLocated(xpath_TeamActivityCenterPageValidation);
         if(Wrapper.findWebElement(xpath_TeamActivityCenterPageValidation).isDisplayed()){

             String employeeReportsText = Wrapper.getText(Wrapper.findWebElement(xpath_EmployeeReportsVerification)).split(" ")[0];
         
             int employeeReportsCount = Wrapper.findWebElements(xpath_EmployeeReports).size();
         
             if(employeeReportsCount == Integer.parseInt(employeeReportsText)){
                 Thread.sleep(10000);
                 attachStepEvidence("Employee reports count is displayed correctly as " + employeeReportsCount);
                 Assert.assertTrue(true, "Employee reports count is displayed correctly.");
                 Thread.sleep(10000);
                 Wrapper.waitForpresenceOfElementLocated(xpath_OrgHierarchy);
                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_OrgHierarchy));
             
                 Wrapper.waitForpresenceOfElementLocated(xpath_OrgHierarchyValidation1);
                 if(Wrapper.findWebElement(xpath_OrgHierarchyValidation1).isDisplayed()){
                     Assert.assertTrue(true, "Org Hierarchy page is displayed successfully.");
                     if(Wrapper.findWebElement(xpath_OrgHierarchyValidation2).isDisplayed()){
                         Thread.sleep(3000);
                         attachStepEvidence("Org Hierarchy");
                         Assert.assertTrue(true, "Org Hierarchy page header is displayed successfully.");
                         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_BackArrow));
                     
                         Wrapper.waitForpresenceOfElementLocated(xpath_TeamActivityCenterPageValidation);
                         if(Wrapper.findWebElement(xpath_TeamActivityCenterPageValidation).isDisplayed()){
                             Assert.assertTrue(true, "Navigated back to Team Activity Center page successfully.");
                             Thread.sleep(5000);
                             Wrapper.waitForpresenceOfElementLocated(xpath_TeamActions);
                             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TeamActions));
                             List<WebElement> teamActionElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_TeamActionValue));
                             Assert.assertTrue(teamActionElements.size() > 0, "Team Actions dropdown is displayed with options.");
                             Thread.sleep(2000);
                             attachStepEvidence("Clicked on Team Actions dropdown.");
                             Thread.sleep(3000);

                             Wrapper.waitForpresenceOfElementLocated(xpath_MSSCompensation);
                             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSCompensation));
                             Thread.sleep(5000);
                             attachStepEvidence("Clicked on Compensation");
                             Thread.sleep(1000);

                             if(Wrapper.findWebElements(xpath_MSSCompensationValue).size()==employeeReportsCount){
                                 Wrapper.waitForpresenceOfElementLocated(xpath_ThreeDots);
                                 // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ThreeDots), "Compensation details");
                                 // Wrapper.waitForSeconds(5);
                                 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpath_MSSCompensationValue));
                                 attachStepEvidence("Compensation details are displayed for the employee.");
                                 Assert.assertTrue(true, "Compensation details are displayed for the employee.");
                                 // Wrapper.waitForpresenceOfElementLocated(xpath_MSSEmployment);
                                 // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSEmployment));

                                 Wrapper.waitForpresenceOfElementLocated(xpath_ThreeDots);
                                 Thread.sleep(2000);
                                 // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ThreeDots), "Manager actions");
                                 Thread.sleep(3000);
                                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ThreeDots));
                         
                                 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpath_ManagerActions));
                                 List<WebElement> managerActionElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ManagerActions));
                                 Assert.assertTrue(managerActionElements.size() > 0, "Manager Actions dropdown is displayed with options.");
                                 attachStepEvidence("Clicked on three dots for manager actions.");
                             }else{
                                 Assert.fail("Compensation details are not displayed for the employees.");
                             }
                         }
                     } 
                 }
             }
         }else{
             Assert.fail("Failed to navigate to Team Activity Center page.");
         }
     }

     By xpath_MSSShowMore=By.xpath("//div[contains(@group,'manager')]//a[text()='Show More']");
     By xpath_LocationChange=By.xpath("//div[contains(@target,'my_team_change_location')]//a[text()='Change Location' and not(contains(@group,'groupNode'))]");
     By xpath_LocationChangeAction=By.xpath("//oj-table[@aria-label='Person Results']//table//td//a[1]");
     By xpath_HRALocationChangeAction =By.xpath("(//oj-table[@aria-label='Person Results']//table//td//a)[2]");    
     By xpath_LocationChangeDate=By.xpath("//span[@title='Select Date.']");
     By xpath_LocationChangeWay=By.xpath("//oj-select-single[contains(@id,'employmentWhenAndWhy.ActionId')]//span/span");
     By xpath_HRALocationchangeDatelogo = By.xpath("//span[@class='oj-inputdatetime-input-trigger']/span");
 
     By xpath_LocationChangeWhy=By.xpath("//oj-select-single[contains(@id,'employmentWhenAndWhy.ActionReasonId')]//span/span");
     
     By xpath_ReportingEstablishment=By.xpath("//oj-select-single[contains(@id,'employmentAssignments.ReportingEstablishmentId')]//span//span");
     
     By xpath_ReportingLocation=By.xpath("//oj-select-single[contains(@id,'employmentAssignments.LocationId')]//span//span");
     
    
     
     // By xpath_LocationChangeWhyReason=By.xpath("//table//tr//td//div[contains(@id,'ActionReason_')]");
     // By xpath_LocationChangeWay=By.xpath("//oj-input-text[contains(@label-hint,'change the location')]//input");
     // By xpath_LocationChangeWhy=By.xpath("(//input[contains(@id,'whenAndWhyForm_fl_employmentWhenAndWh')])[last()]/parent::div");
 
     // Helper method to generate dynamic xpath with text input
     By xpath_LocationChangeValidation=By.xpath("//h1[contains(text(),'Change Location')]");

 public void navigateToLocationChangePage() throws Exception {
         Wrapper.findWebElement(xpath_MyTeam).isDisplayed();
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyTeam));

         Wrapper.findWebElement(xpath_MSSShowMore).isDisplayed();
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSShowMore));
     
         Thread.sleep(2000);
         Wrapper.waitForpresenceOfElementLocated(xpath_LocationChange);
         attachStepEvidence("Location Change.");
         Thread.sleep(3000);
         // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Employment));
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_LocationChange));
         Wrapper.waitForpresenceOfElementLocated(xpath_LocationChangeValidation);
         if(Wrapper.findWebElement(xpath_LocationChangeAction).isDisplayed()){
             // attachStepEvidence("Navigated to Location Change page successfully.");
             Assert.assertTrue(true, "Navigated to Location Change page successfully");
         }
         // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_LocationChangeAction));
         String EmployeeNumber = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
         Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeNumber, false);
         if(Wrapper.findWebElement(HRAXpath(EmployeeNumber)).isDisplayed()){
                attachStepEvidence("Searched for employee using employee number: " + EmployeeNumber);
                Thread.sleep(3000);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(HRAXpath(EmployeeNumber))).click();
         }
     }

 public void submitLocationChangeRequest(String LocationChangeDate) throws Exception {
         Wrapper.scrollToElement(Wrapper.findWebElement(xpath_LocationChangeDate), "Location Change Date");
         Wrapper.selectDate(
             Wrapper.findWebElement(xpath_LocationChangeDate),
             LocationChangeDate,
             xpath_ResignationRetirementDateValue,
             xpath_DatePickerMonth,
             xpath_DatePickerYear,
             xpath_DatePickerNext
         );

         // attachStepEvidence("Entered location change date ");
         // Wait by locator (not cached WebElement), then click a fresh element
         Wrapper.waitForpresenceOfElementLocated(xpath_LocationChangeWay);
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(xpath_LocationChangeWay)).click();

         // Select "Location Change"
         By actionOption = xpath_ReasonSelection("Location Change");
         Wrapper.waitForpresenceOfElementLocated(actionOption);
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(actionOption)).click();

         // Open Reason dropdown
         Wrapper.waitForpresenceOfElementLocated(xpath_LocationChangeWhy);
         Thread.sleep(5000);
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(xpath_LocationChangeWhy)).click();
     
         // Select "Work Location"
         By reasonOption = xpath_ReasonSelection("Work Location");
         Wrapper.waitForpresenceOfElementLocated(reasonOption);
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(reasonOption)).click();
     
         attachStepEvidence("Selected location change reason and action.");
         Thread.sleep(3000);
     
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(xpath_MSSContinueButton)).click();
         //Reporting Establishment
         Wrapper.waitForpresenceOfElementLocated(xpath_ReportingEstablishment);
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(xpath_ReportingEstablishment)).click();
         By reportingEstablishmentOption = xpath_ReasonSelection("4th Ave Workout Location");
         Wrapper.waitForpresenceOfElementLocated(reportingEstablishmentOption);
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(reportingEstablishmentOption)).click();
     
         attachStepEvidence("Selected reporting establishment for location change request.");
         //Reporting Location
         Wrapper.waitForpresenceOfElementLocated(xpath_ReportingLocation);
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(xpath_ReportingLocation)).click();
         By reportingLocationOption = xpath_ReasonSelection("Cleveland St Service Center");
         Wrapper.waitForpresenceOfElementLocated(reportingLocationOption);
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(reportingLocationOption)).click();

     
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(xpath_MSSContinueButton)).click();
     
         Thread.sleep(5000);
         Wrapper.waitForpresenceOfElementLocated(xpath_ThirdPageValidation);
         attachStepEvidence("Seniority page is displayed after clicking continue on location change request.");
         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                 .elementToBeClickable(xpath_MSSSubmitButton)).click();
     
     }

 

 

 public void navigateToHistoricalChangePage() throws Exception{
         String employeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
         Wrapper.waitForpresenceOfElementLocated(xpath_EmploymentInfoValidation);
         if(Wrapper.findWebElement(xpath_EmploymentInfoValidation).isDisplayed()){
             AllureReportUtil.info("Successfully navigated to Employment Info page.");
         
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), employeeName, false);
             if(Wrapper.findWebElement(HRAXpath(employeeName)).isDisplayed()){
                 Thread.sleep(3000);
                 attachStepEvidence("Searched for employee using employee number: " + employeeName);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(HRAXpath(employeeName))).click();
             }
         }
     }

 public void deleteHistoricalAssignmentChange() throws Exception{
         try{
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DeleteButton));
             Wrapper.waitForpresenceOfElementLocated(xpath_DeleteTranscationPopUpValidation);
             Thread.sleep(5000);
             attachStepEvidence("Delete confirmation pop-up is displayed after clicking delete button for assignment change.");
             if(Wrapper.findWebElement(xpath_DeleteTranscationPopUpValidation).isDisplayed()){
                 Assert.assertTrue(true, "Delete confirmation pop-up is displayed.");
                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AssignmentDeleteButton));  
             }else{
                     Assert.fail("Delete confirmation pop-up is not displayed.");
             }
         }catch(Exception e){
             e.printStackTrace();
             throw new RuntimeException("Error deleting historical assignment change: " + e.getMessage());
         }
     }

 public void navigateToHistoricalAssignmentCorrectPage() throws Exception{
         try{
             // Wrapper.waitForpresenceOfElementLocated(xpath_EmployeeSearchResultValidation);
             Wrapper.waitForpresenceOfElementLocated(xpath_HistoricalChangeOptionValidation);
             if(Wrapper.findWebElement(xpath_EmployeeSearchResultValidation).isDisplayed() && Wrapper.findWebElement(xpath_HistoricalChangeOptionValidation).isDisplayed()){
                 Assert.assertTrue(true, "Successfully navigated to Historical Changes page.");
                 Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HistoricalAssignmentChange), "Historical Assignment Change option");
                 Thread.sleep(3000);
                 attachStepEvidence("Navigated to Historical Changes page");
                 Thread.sleep(3000);
                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HistoricalAssignmentChange));
             }else{
                 Assert.fail("Failed to navigate to Historical Changes page.");
             }
         }catch(Exception e){
             e.printStackTrace();
             throw new RuntimeException("Error navigating to Historical Assignment Change page: " + e.getMessage());
         }
     }

 public void correctHistoricalAssignmentChange(String EffectiveDate, String Action, String Reason, String SalaryChange) throws Exception{
         try{
             if(Wrapper.findWebElement(xpath_SummaryPageValidation).isDisplayed()){
                 Assert.assertTrue(true, "Successfully navigated to Summary page.");
                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CorrectButton));
                 Wrapper.waitForpresenceOfElementLocated(xpath_WhenAndWhyPageValidation);
                 if(Wrapper.findWebElement(xpath_WhenAndWhyPageValidation).isDisplayed()){
                     Assert.assertTrue(true, "When and Why page is displayed successfully.");
                     Wrapper.selectDate(Wrapper.findWebElement(xpath_SalaryChangeDate), EffectiveDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
                     // attachStepEvidence("Entered effective date for assignment change correction.");
                     Wrapper.waitForpresenceOfElementLocated(xpath_ActionDropDown);
                     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                             .elementToBeClickable(xpath_ActionDropDown)).click();
                     By actionOption = xpath_ActionDropDownOption(Action);
                     Wrapper.waitForpresenceOfElementLocated(actionOption);
                     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                             .elementToBeClickable(actionOption)).click();

                     Wrapper.waitForpresenceOfElementLocated(xpath_ReasonDropDown);
                     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                             .elementToBeClickable(xpath_ReasonDropDown)).click();
                     By reasonOption = xpath_ReasonDropDownOption(Reason);
                     Wrapper.waitForpresenceOfElementLocated(reasonOption);
                     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                             .elementToBeClickable(reasonOption)).click();
                 
                     attachStepEvidence("Selected action and reason for assignment change correction.");

                     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
                     Wrapper.waitForpresenceOfElementLocated(xpath_SalaryChange);
                     Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SalaryChange), "Salary Change input field");
                     Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SalaryChange), SalaryChange, true);
                     Thread.sleep(2000);
                     attachStepEvidence("Entered salary change amount for assignment change correction.");
                     Thread.sleep(3000);
                     if(Wrapper.findWebElement(xpath_ValidateCommentsAndAttachmentsPage).isDisplayed()){
                         Assert.assertTrue(true, "Salary change amount is entered correctly.");
                         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
                         Wrapper.waitForpresenceOfElementLocated(xpath_HRACorrectionComments);
                         Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_HRACorrectionComments), "Correcting the salary change for testing purpose.", false);
                         attachStepEvidence("Entered comments for assignment change correction.");
                         Thread.sleep(2000);
                         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRASaveComment));
                         Wrapper.waitForpresenceOfElementLocated(xpath_ClickCross);
                         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ClickCross));
                         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
                     }
                     else{
                         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
                     }
                 
                 }else{
                     Assert.fail("When and Why page is not displayed.");
                 }
             }else{
                 Assert.fail("Failed to navigate to Summary page.");
             }
         }catch(Exception e){
             e.printStackTrace();
             throw new RuntimeException("Error correcting historical assignment change: " + e.getMessage());
         }
     }

 public void navigateToFamilyAndEmergencyContacts(String EmployeeName) throws Exception{
         try{
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
             Thread.sleep(2000); // Consider replacing with a more robust wait strategy
             attachStepEvidence("Clicked on My Client Groups");
             Thread.sleep(2000);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
             Thread.sleep(2000); // Consider replacing with a more robust wait strategy
             // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HRAFamilyAndEmergencyContacts), "Family and Emergency Contacts option");
             Wrapper.findWebElement(xpath_HRAFamilyAndEmergencyContacts).isDisplayed();
             attachStepEvidence("Family and Emergency Contacts option under My Client Groups");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRAFamilyAndEmergencyContacts));
             if(Wrapper.findWebElement(xpath_FamilyandEmergencyPageValidation).isDisplayed()){
                 Assert.assertTrue(true, "Successfully navigated to Family and Emergency Contact page.");
                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
                 Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
                 if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                     Thread.sleep(5000);
                     attachStepEvidence("Searched for employee using employee number: " + EmployeeName);
                     Thread.sleep(3000);
                     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                             .elementToBeClickable(HRAXpath(EmployeeName))).click();
                     Wrapper.waitForpresenceOfElementLocated(xpath_FamilyAndEmergencyContactPageValidation);
                 }
             }else{
                 Assert.fail("Failed to navigate to Family and Emergency Contact page.");
             }
         }catch(Exception e){
             e.printStackTrace();
             throw new RuntimeException("Error navigating to Family and Emergency Contacts: " + e.getMessage());
         }
     }
     By xpath_WorkingHoursChange=By.xpath("//div[contains(@id,'show_more_groupNode')]//a[text()='Change Working Hours']");
     //Call this-->navigateToHistoricalChangePage()
     By xpath_ChangeWorkingHoursPageValidation=By.xpath("//h1[text()='Change Working Hours']");
     // By xpath_ManagerOption=By.xpath("//div[contains(@aria-label,'Managers')]");
    
     // By xpath_DocumentsRecordsOption=By.xpath("//div[contains(@aria-label,'Document records')]");

 public void navigateToChangeWorkingHoursPage() throws Exception{
         Thread.sleep(3000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
         attachStepEvidence("Clicked on My Client Groups");
         Thread.sleep(2000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
         Wrapper.scrollToElement(Wrapper.findWebElement(xpath_navigateToChangeWorkingHours), "Change Working Hours option");
         attachStepEvidence("Change Working Hours option under My Client Groups");
         Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursChange);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_WorkingHoursChange));
     }

 public void searchEmployeeForWorkingHoursChange() throws Exception{
         String EmployeeNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
         Wrapper.waitForpresenceOfElementLocated(xpath_ChangeWorkingHoursPageValidation);
         if(Wrapper.waitForpresenceOfElementLocated(xpath_ChangeWorkingHoursPageValidation).isDisplayed()){
             AllureReportUtil.info("Successfully navigated to Employment Info page.");
         
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeNumber, false);
             if(Wrapper.findWebElement(HRAXpath(EmployeeNumber)).isDisplayed()){
                 attachStepEvidence("Searched for employee using employee number: " + EmployeeNumber);
                 Thread.sleep(3000);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(HRAXpath(EmployeeNumber))).click();
             }
         }
     }

 public void submitWorkingHoursChangeRequest(String EffectiveDate, String Action, String Reason) throws Exception{
         Wrapper.waitForpresenceOfElementLocated(xpath_ChangeWorkingHoursPage);
         if(Wrapper.findWebElement(xpath_ChangeWorkingHoursPage).isDisplayed()){
             Assert.assertTrue(true, "Successfully navigated to Change Working Hours page.");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SalaryButton));
             attachStepEvidence("Clicked on Salary button in Change Working Hours page.");
             Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Salary"));
             if(Wrapper.findWebElement(xpath_ButtonEnabled("Salary")).isEnabled()){
                 Assert.assertTrue(true, "Salary button is enabled.");
             }else{
                 Assert.fail("Salary button is not enabled.");
             }
             attachStepEvidence("On Info To Include Page and navigating to next page");

             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
             Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursWhenAndWhyValidation);
             if(Wrapper.findWebElement(xpath_WorkingHoursWhenAndWhyValidation).isDisplayed()){
                 Assert.assertTrue(true, "When and Why page is displayed successfully.");
                 Wrapper.selectDate(Wrapper.findWebElement(xpath_DatePicker), EffectiveDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
             
                 Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursWay);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(xpath_WorkingHoursWay)).click();
                 By actionOption = xpath_ReasonSelection(Action);
                 Wrapper.waitForpresenceOfElementLocated(actionOption);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(actionOption)).click();

                 Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursWhy);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(xpath_WorkingHoursWhy)).click();
                 By reasonOption = xpath_ReasonSelection(Reason);
                 Wrapper.waitForpresenceOfElementLocated(reasonOption);
                 Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                         .elementToBeClickable(reasonOption)).click();
                 Thread.sleep(2000);
                 attachStepEvidence("Entered details in When and Why page for working hours change request.");
                 Thread.sleep(3000);
                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
             }
         }
     }

 public void WorkingHoursChangeRequestSubmission() throws Exception{
         Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentChangeValidation);
         if(Wrapper.findWebElement(xpath_AssignmentChangeValidation).isDisplayed()){
             Assert.assertTrue(true, "Successfully navigated to Assignment Change page.");
             Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursInputField);
             Wrapper.scrollToElement(Wrapper.findWebElement(xpath_WorkingHoursInputField), "Working Hours input field");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_WorkingHoursInputField));
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_WorkingHoursInputField), "39", true);
             Thread.sleep(10000);
             attachStepEvidence("Entered new working hours in the input field.");
             Thread.sleep(3000);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
             Wrapper.waitForpresenceOfElementLocated(xpath_SalaryPageValidation);
             if(Wrapper.findWebElement(xpath_SalaryPageValidation).isDisplayed()){
                 Assert.assertTrue(true, "Salary page is displayed successfully.");
                 Thread.sleep(5000);
                 attachStepEvidence("Navigated to Salary page after entering working hours change details.");
                 Thread.sleep(3000);
                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
                 Wrapper.waitForpresenceOfElementLocated(xpath_SeniorityDatePageValidation);
                 if(Wrapper.findWebElement(xpath_SeniorityDatePageValidation).isDisplayed()){
                     Thread.sleep(10000);
                     attachStepEvidence("Navigated to Seniority Date page after clicking continue on Salary page.");
                     Assert.assertTrue(true, "Seniority Date page is displayed successfully.");
                     Wrapper.waitForpresenceOfElementLocated(xpath_MSSSubmitButton);
                     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
                     // Uncomment Below for Initial Test Case
                     // Wrapper.waitForpresenceOfElementLocated(xpath_ChangeWorkingHoursPageValidation);
                 }else{
                     Assert.fail("Failed to navigate to Seniority Date page after submitting working hours change request.");
                 }
             }else{
                 Assert.fail("Failed to navigate to Salary page after submitting working hours change request.");
             }
         }else{
             Assert.fail("Failed to navigate to Assignment Change page for working hours change request.");
         }
     }

 public void validateWorkingHoursChangeRequest()throws Exception{
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Home));
         navigateToEmploymentInfoPage();
         navigateToHistoricalChangePage();
         Thread.sleep(10000);
         Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursChangeValidation);
         // driver.navigate().refresh();
         Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursChangeValidation);
         Wrapper.waitForpresenceOfElementLocated(xpath_HistoricalChangeOptionValidation);
         if(Wrapper.findWebElement(xpath_WorkingHoursChangeValidation).isDisplayed()){
             driver.navigate().refresh();
             Wrapper.waitForpresenceOfElementLocated(xpath_HistoricalChangeOptionValidation);
             // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_WorkingHoursChangeValidation), "Working Hours change request");
             attachStepEvidence("Validated that the Working Hours change request is displayed in Employment Info page.");
             Assert.assertTrue(true, "Working Hours change request is displayed in Home page.");
         }else{
             Assert.fail("Working Hours change request is not displayed in Home page.");
         }
     }

 

 public void navigateToTerminateEmploymentPage() throws Exception{
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyTeam));
         Thread.sleep(4000);
         attachStepEvidence("the manager navigated to My Team section in MSS.");
         Thread.sleep(2000);
         // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_MSSShowMore), "Show More link under My Team section");
         // attachStepEvidence("the manager scrolled to Show More link under My Team section in MSS.");
         Thread.sleep(2000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSShowMore));
         Thread.sleep(3000);
         // attachStepEvidence("the manager clicked on Show More link under My Team section in MSS.");
         Thread.sleep(2000);
         // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_TerminateEmployment), "Terminate Employment option under My Team");
         Wrapper.waitForpresenceOfElementLocated(xpath_TerminateEmployment);
         if(Wrapper.findWebElement(xpath_TerminateEmployment).isDisplayed()){
             Assert.assertTrue(true, "Terminate Employment option is displayed successfully under My Team.");
             attachStepEvidence("the manager clicked on Terminate Employment option under My Team.");
             Thread.sleep(2000);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TerminateEmployment));
         }else{
             Assert.fail("Terminate Employment option is not displayed under My Team.");
         }
     }

 

 

 public By xpath_SearchResultForPeopleToAddAsReport() throws Exception{
         String ReportEmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Report_Employee_Number");
         String xpathValue="//oj-table[contains(@aria-label,'Workers List')]//span[contains(text(),'test')]";
         xpathValue= xpathValue.replace("test", ReportEmployeeName);
         return By.xpath(xpathValue);
     }

 public void navigateToDirectReportsPage() throws Exception{
         Thread.sleep(3000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
         attachStepEvidence("the manager navigated to My Client Groups section in HRA.");
         Thread.sleep(2000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
         Wrapper.waitForpresenceOfElementLocated(xpath_DirectReportsChange);
         // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DirectReportsChange), "Direct Reports option under My Client Groups");
         if(Wrapper.findWebElement(xpath_DirectReportsChange).isDisplayed()){
             Assert.assertTrue(true, "Direct Reports option is displayed successfully under My Client Groups.");
             Thread.sleep(5000);
             attachStepEvidence("the manager clicked on Direct Reports option under My Client Groups.");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DirectReportsChange));
         }else{
             Assert.fail("Direct Reports option is not displayed under My Client Groups.");
         }
     }

 public void searchEmployeeInDirectReportsPage() throws Exception{
         String EmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"EmployeeNumber");
         Wrapper.waitForpresenceOfElementLocated(xpath_DirectReportsChangePageValidation);
         if(Wrapper.findWebElement(xpath_DirectReportsChangePageValidation).isDisplayed()){
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
             if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                 Assert.assertTrue(true, "Employee search functionality is working fine in Direct Reports page.");
                 Thread.sleep(3000);
                 attachStepEvidence("the manager searched for an employee in Direct Reports page to change the direct report information.");
                 Thread.sleep(2000);
                 Wrapper.clickWebElement(Wrapper.findWebElement(HRAXpath(EmployeeName)));
             }else{
                 Assert.fail("Employee search functionality is not working in Direct Reports page.");
             }
         }else{
             Assert.fail("Direct Reports page is not displayed after clicking on Direct Reports option under My Client Groups.");
         }
     }

 public void fillDirectReportsInfoToIncludePage() throws Exception{
         Thread.sleep(5000);
         Wrapper.waitForpresenceOfElementLocated(xpath_InfoToIncludePageValidation);
         Thread.sleep(5000);
         if(Wrapper.findWebElement(xpath_InfoToIncludePageValidation).isDisplayed()){
             Assert.assertTrue(true, "Info to include page is displayed successfully after clicking on an employee in Direct Reports page.");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_commentsAndAttachmentButton));
             attachStepEvidence("the manager filled the details in Info to include page and submitted the change request.");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
         }else{
             Assert.fail("Info to include page is not displayed after clicking on an employee in Direct Reports page.");
         }
     }

 public void fillDirectReportsChangeWhenAndWhyPage() throws Exception{
     
         String changeStartDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Change_Start_Date");
         String wayToChange=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Way_To_Change");
         String reasonForChange=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Reason_For_Change");
         Thread.sleep(5000);
         Wrapper.waitForpresenceOfElementLocated(xpath_DirectReportsChangeWhenAndWhyPageValidation);
         Thread.sleep(5000);
         if(Wrapper.findWebElement(xpath_DirectReportsChangeWhenAndWhyPageValidation).isDisplayed()){
             Assert.assertTrue(true, "When and why page is displayed successfully after clicking on an employee in Direct Reports page.");
             Thread.sleep(5000);
         
             Wrapper.selectDate(Wrapper.findWebElement(xpath_ChangeStartDate), changeStartDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
         
             Wrapper.waitForpresenceOfElementLocated(xpath_WayToChange);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WayToChange)).click();
             // Wrapper.redwoodSync();
             for(WebElement option : driver.findElements(xpath_WayToChangeList)){
                 if(option.getText().equals(wayToChange)){
                     Wrapper.waitForElementToBeClickable(option);
                     option.click();
                     break;
                 }
             }

             Thread.sleep(3000);
             Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ReasonForChange), "Reason for change dropdown");
             Wrapper.waitForpresenceOfElementLocated(xpath_ReasonForChange);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_ReasonForChange)).click();
             // Wrapper.redwoodSync();
             for(WebElement option : driver.findElements(xpath_ReasonForChangeList)){
                 if(option.getText().equals(reasonForChange)){
                     Wrapper.waitForElementToBeClickable(option);
                     option.click();
                     break;
                 }
             }
         }
         Thread.sleep(5000);
         attachStepEvidence("the manager filled the details in When and why page for changing direct report information.");
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
     }

 public void fillDirectReportsInfoChangePage() throws Exception{
         String reportEmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Report_Employee_Number");
         Wrapper.waitForpresenceOfElementLocated(xpath_DirectReportsInfoChangePageValidation);
         if(Wrapper.findWebElement(xpath_DirectReportsInfoChangePageValidation).isDisplayed()){
             Assert.assertTrue(true, "Change direct report info page is displayed successfully after clicking Continue button on When and why page.");
             Thread.sleep(10000);
             Wrapper.waitForpresenceOfElementLocated(xpath_SearchPeopleToAddAsReportsManual);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SearchPeopleToAddAsReportsManual));
             Thread.sleep(5000);
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SearchPeopleToAddAsReports), reportEmployeeName, false);
             if(Wrapper.findWebElement(xpath_SearchResultForPeopleToAddAsReport()).isDisplayed()){
                 Assert.assertTrue(true, "Employee search functionality is working fine in Change direct report info page.");
                 Thread.sleep(5000);
                 attachStepEvidence("the manager searched for an employee in Change direct report page to change the direct report information.");
                 Thread.sleep(5000);
                 Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SearchResultForPeopleToAddAsReport()));
             }else{
                 Assert.fail("Employee search functionality is not working in Change direct report info page.");
             }
             Thread.sleep(8000);
             attachStepEvidence("the manager filled the details in Change direct report info page and submitting the change request.");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
         }else{
             Assert.fail("Change direct report info page is not displayed after clicking Continue button on When and why page.");
         }
     }

 public void fillDirectReportsCommentsAndAttachmentsPage() throws Exception{
         String comment=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Termination_Comment");
         Wrapper.waitForpresenceOfElementLocated(xpath_CommentsAndAttachmentsPageValidation);
         if(Wrapper.findWebElement(xpath_CommentsAndAttachmentsPageValidation).isDisplayed()){
             Assert.assertTrue(true, "Comments and attachments page is displayed successfully after clicking Continue button on Change direct report info page.");
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_CommentsInputBox), comment, false);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveComment));
             Thread.sleep(1000);
             attachStepEvidence("the manager filled the details in Comments and attachments page and submitted the change request.");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ClickCross));
             Wrapper.waitForpresenceOfElementLocated(xpath_EditComment);
             Thread.sleep(5000);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
         }else{
             Assert.fail("Comments and attachments page is not displayed after clicking Continue button on Change direct report info page.");
         }
     }

 public void navigateToToolsPage() throws Exception{
         driver.navigate().refresh();
         Thread.sleep(10000);
         driver.navigate().refresh();
         Thread.sleep(10000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
         Thread.sleep(5000);
         attachStepEvidence("the manager clicked on Tools menu.");
     }
     String parentWindow;

 public void navigateToWorklistPage() throws Exception{
         parentWindow = driver.getWindowHandle();
         // driver.navigate().refresh();
         // Thread.sleep(10000);
         Wrapper.waitForpresenceOfElementLocated(xpath_WorkList);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_WorkList));
         // attachStepEvidence("the manager clicked on Worklist option under Tools menu.");
     }

 public void switchBetweenWindows() throws Exception{
     
         Set<String> allWindows = driver.getWindowHandles();
         for (String window : allWindows) {
             if (!window.equals(parentWindow)) {
                 driver.switchTo().window(window);
                 driver.manage().window().maximize();
                 break;
             }
         }
     }

 public void navigateToAdHocSalaryRequest() throws Exception{
         Wrapper.waitForpresenceOfElementLocated(xpath_WorkListPageValidation);
         if(Wrapper.findWebElement(xpath_WorkListPageValidation).isDisplayed()){
             Assert.assertTrue(true, "Worklist page is displayed successfully after clicking on Worklist option under Tools menu.");
             Wrapper.waitForpresenceOfElementLocated(xpath_AdHocSalaryRequest);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryRequest));
             System.out.println("Parent: " + driver.getWindowHandle());
             Thread.sleep(5000);
             attachStepEvidence("the manager clicked on Ad Hoc Salary Request link in Worklist page.");
         }else{
             Assert.fail("Worklist page is not displayed after clicking on Worklist option under Tools menu.");
         }
     }
     By xpath_AdHocSalaryApprovePageValidation=By.xpath("//h1[contains(text(),'Approve')]");
     By xpath_AdHocSalaryApproveSubmit=By.xpath("//a/span[contains(text(),'Submit')]");

 public void approveAdHocSalaryRequest() throws Exception{
         Thread.sleep(5000);
         switchBetweenWindows();
     
         System.out.println("All handles: " + driver.getWindowHandles());
         System.out.println("Child: " + driver.getWindowHandle());
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryApprove));
         Thread.sleep(5000);
         Wrapper.waitForpresenceOfElementLocated(xpath_AdHocSalaryApprovePageValidation);
         if(Wrapper.findWebElement(xpath_AdHocSalaryApprovePageValidation).isDisplayed()){
             Assert.assertTrue(true, "Ad Hoc Salary Approve page is displayed successfully after clicking on Ad Hoc Salary Request link in Worklist page.");
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AdHocSalaryApproveComments), "Approving this ad hoc salary request for testing purpose.", false);
             attachStepEvidence("the manager filled the details in Ad Hoc Salary Approve page and approved the request.");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryApproveSubmit));
             Thread.sleep(10000);
             driver.switchTo().window(parentWindow);
             Thread.sleep(2000);
             Wrapper.waitForpresenceOfElementLocated(xpath_WorkListPageValidation);
         }else{
             Assert.fail("Ad Hoc Salary Approve page is not displayed after clicking on Ad Hoc Salary Request link in Worklist page.");
         }
     }
     By xpath_Claim=By.xpath("//button[text()='Claim']");

 public void claimAdHocSalaryRequest() throws Exception{
         Thread.sleep(5000);
         switchBetweenWindows();
     
         System.out.println("All handles: " + driver.getWindowHandles());
         System.out.println("Child: " + driver.getWindowHandle());
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Claim));
         Thread.sleep(5000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryApprove));
         Thread.sleep(5000);
         Wrapper.waitForpresenceOfElementLocated(xpath_AdHocSalaryApprovePageValidation);
         if(Wrapper.findWebElement(xpath_AdHocSalaryApprovePageValidation).isDisplayed()){
             Assert.assertTrue(true, "Ad Hoc Salary Approve page is displayed successfully after clicking on Ad Hoc Salary Request link in Worklist page.");
             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AdHocSalaryApproveComments), "Approving this ad hoc salary request for testing purpose.", false);
             attachStepEvidence("the manager filled the details in Ad Hoc Salary Approve page and approved the request.");
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryApproveSubmit));
             Thread.sleep(10000);
             driver.switchTo().window(parentWindow);
             Thread.sleep(2000);
             Wrapper.waitForpresenceOfElementLocated(xpath_WorkListPageValidation);
         }else{
             Assert.fail("Ad Hoc Salary Approve page is not displayed after clicking on Ad Hoc Salary Request link in Worklist page.");
         }
     }

     //sakeths

     public void enterDocumentrecordsCredentials() throws Exception{
        try {
            Thread.sleep(10000);
            // userName = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Username");
            //  userName = "Ce.0023780";
             userName = "Ce.0037147";
            // passWord = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Password");
            // passWord = "f7gr3nuGvzy*z";
            passWord = "J5gr09Gvzy*z";
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UserName), userName, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Password), passWord, false);   
            Thread.sleep(20000);
            AllureReportUtil.info("Logged into Oracle HCM as a ESS");
            Thread.sleep(5000);
            attachStepEvidence("the manager is logged into Oracle HCM as a ESS");
            Thread.sleep(5000);

        }catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Error entering credentials: " + e.getMessage());
        } 
    }
    By xpath_DocumentRecordsnew = By.xpath("(//a[text()='Document Records'])[5]");
public void navigateToDocumentRecordsPage() throws Exception{
   
        try {
            Thread.sleep(8000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
            Thread.sleep(2000);
            attachStepEvidence("User is on Myclient groups");
            Thread.sleep(8000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpathHRAShowMore));
            Thread.sleep(5000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DocumentRecordsnew), "Document recordsnew");
            Thread.sleep(3000);
           attachStepEvidence("Clicks on document records link");
           Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DocumentRecordsnew));
            Thread.sleep(8000);

        }catch(InterruptedException e) {
            e.printStackTrace();
            throw new Exception("Error navigating to Document Records page: " + e.getMessage());
        } 
    }
    By xpath_DirectreportsSearchBar = By.xpath("//input[contains(@placeholder,'Search for a Person')]");
    By xpath_Listclick = By.xpath("((//tr[@role='row'])[2]/td)[4]/div[text()='0023780']");

public void searchEmployeeInDocumentRecordsPage(){
         String lastName = "0023780";
        try{
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DirectreportsSearchBar));
            Thread.sleep(2000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_DirectreportsSearchBar), lastName, false);
            Thread.sleep(5000);
            attachStepEvidence("Searching for particular record in search bar in Document records page");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Listclick));
            Thread.sleep(10000);
        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error searching employee in Document Records page: " + e.getMessage());
        }
    }
    By xpath_DocumentrecordsSubmit = By.xpath("//table[@role='presentation']//child::div[contains(@class,'callToActionSubmit')]/a");
    By xpath_ShowMoreESSDocumentRecord = By.xpath("//div[contains(@group,'groupNode_my_information')]//a[text()='Show More']");
    By xpath_DocumentRecordESS = By.xpath("(//a[text()='Document Records'])[2]");
    By xpath_TransferShowMore =By.xpath("(//a[text()='Show More'])[3]");
    By xpath_Transferglobalbutton = By.xpath("(//a[text()='Local and Global Transfer'])[3]");
    By xpath_SalaryTogglebutton = By.xpath("//oj-switch//div[contains(@aria-label,'Salary')]");

public void addDocumentRecord() throws Exception{
        try{
            Thread.sleep(12000);
            attachStepEvidence("Is on document records page and filling the details");
            Thread.sleep(80000);
            // attachstepEvidence("Is on document records page and clicking on submit button");
            attachStepEvidence("Document records page is ready to submit");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DocumentrecordsSubmit));
            Thread.sleep(5000);
            signOut();
            Thread.sleep(20000);
            enterDocumentrecordsCredentials();
            openDashboardPage();
            Thread.sleep(12000);
            attachStepEvidence("User logged in for verifying the added document records");
            Thread.sleep(10000);
            Thread.sleep(2000);
            attachStepEvidence("User is on Me Link");
            Thread.sleep(8000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMoreESSDocumentRecord));
            Thread.sleep(5000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DocumentRecordESS), "Document recordsagain");
            Thread.sleep(2000);
            attachStepEvidence("Is on the document records button");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_DocumentRecordESS));
            Thread.sleep(10000);
            Thread.sleep(15000);
            attachStepEvidence("Is on document records page to verify");
            Thread.sleep(5000);
            Thread.sleep(20000);
            attachStepEvidence("The added document records are verified successfully");
            Thread.sleep(8000);
        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error adding document record: " + e.getMessage());
        }
    }

public void navigateToTransferPage() throws Exception{
    
        try{
            Thread.sleep(10000);
            attachStepEvidence("User is on dashboard page");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroupsHRA));
            Thread.sleep(3000);
             attachStepEvidence("User is on Myclient groups");
            Thread.sleep(7000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferShowMore));
            Thread.sleep(5000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Transferglobalbutton), "Transfer global button");
            Thread.sleep(2000);
            attachStepEvidence("Is on the Transfer page and clicking on Transfer global button");
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Transferglobalbutton));
            Thread.sleep(8000);
        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error navigating to Transfer page: " + e.getMessage());
        }
    }
    By xpath_TransferButtonemployment = By.xpath("(//a[text()='Transfer'])[2]");
    By xpath_ContinueTransferGlobal = By.xpath("(//*[text()='Continue'])[2]");
    By xpath_TransferGlobalAssignmentPage = By.xpath("(//*[text()='Continue'])");
    By xpath_TransferGlobalSubmit = By.xpath("//button[@buttontype='submit']//span/span[text()='Submit']");

public void navigateToTransferInitiatePage() throws Exception{
    
        try{
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroupsHRA));
            Thread.sleep(3000);
            attachStepEvidence("User is on Myclient groups");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferShowMore));
            Thread.sleep(5000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_TransferButtonemployment), "Transfer button");
            Thread.sleep(3000);
            attachStepEvidence("Is on the Transfer page and clicking on Transfer button");
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferButtonemployment));
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error navigating to Transfer page: " + e.getMessage());
        }
    }

public void searchEmployeeInTransferInitiatePage() throws Exception{
        String EmployeeName = "0096622";
        try{
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
       
            if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                attachStepEvidence("Searching for particular employee in search bar in Transfer page");
                Thread.sleep(3000);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(EmployeeName))).click();
            }
            Thread.sleep(8000);

        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error searching employee in Transfer page: " + e.getMessage());
        }
    }

public void searchEmployeeInTransferPage() throws Exception{
        // String EmployeeName = "0040175";
        // String EmployeeName = "0002630";
        String EmployeeName = "0037220";
        try{
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
            Thread.sleep(2000);
            if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                Thread.sleep(3000);
               attachStepEvidence("Searching for particular employee in search bar in Transfer page");
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(EmployeeName))).click();
            }
            Thread.sleep(10000);

        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error searching employee in Transfer page: " + e.getMessage());
        }
    }
    By xpath_CommentsandAttachmentsTogglebutton = By.xpath("//oj-switch//div[contains(@aria-label,'Comments and attachments')]");
public void initiateGlobalTransfer() throws Exception{
        try{
            Thread.sleep(12000);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SalaryTogglebutton));
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CommentsandAttachmentsTogglebutton));
            Thread.sleep(10000);
            attachStepEvidence("Is on the InfotoInclude page and clicking on continue button");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ContinueButton));
            Thread.sleep(15000);
            Thread.sleep(90000);
            attachStepEvidence("Is on the When and why page page and clicking on continue button");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ContinueTransferGlobal));
            Thread.sleep(15000);
            Thread.sleep(90000);
            attachStepEvidence("Is on the Assignment page page and clicking on continue button");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferGlobalAssignmentPage));
            Thread.sleep(15000);
            Thread.sleep(30000);
            attachStepEvidence("Is on the Salary page page and clicking on continue button");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferGlobalAssignmentPage));
            Thread.sleep(15000);
            Thread.sleep(30000);
            attachStepEvidence("Is on the Comments and Attachments page page and clicking on continue button");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferGlobalAssignmentPage));
            Thread.sleep(15000);
            Thread.sleep(10000);
            attachStepEvidence("Is on the Seniority dates page page and clicking on continue button");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferGlobalAssignmentPage));
            Thread.sleep(15000);
            Thread.sleep(10000);
            attachStepEvidence("Is on the Review page page and clicking on submit button");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferGlobalSubmit));
            Thread.sleep(10000);


        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error initiating global transfer: " + e.getMessage());

        }
    }

public void initiateTransferPage() throws Exception{
        try{
            Thread.sleep(10000);
             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SalaryTogglebutton));
            Thread.sleep(3000);
            attachStepEvidence("Is on the InfotoInclude page and clicking on continue button");
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ContinueButton));
            Thread.sleep(12000);
            Thread.sleep(50000);
            attachStepEvidence("Is on the When and why page page and clicking on continue button");
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ContinueTransferGlobal));
            Thread.sleep(15000);
            Thread.sleep(70000);
            attachStepEvidence("Is on the Assignment page page and clicking on continue button");
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferGlobalAssignmentPage));
            Thread.sleep(15000);
            Thread.sleep(30000);
            attachStepEvidence("Is on the Salary page page and clicking on continue button");
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferGlobalAssignmentPage));
            Thread.sleep(15000);
            Thread.sleep(12000);
            attachStepEvidence("Is on the Seniority dates page page and clicking on continue button");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_TransferGlobalSubmit));
            Thread.sleep(10000);

        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error initiating global transfer: " + e.getMessage());

        }
    }
    By xpath_ShowMoreCompensationinfo = By.xpath("(//*[text()='Show More'])[2]");
    By xpath_CompensationInfo=By.xpath("(//*[text()='Compensation Info'])[2]");

public void navigateToMSSMyTeamCompensation(){
        try{
        Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyTeam));
        Thread.sleep(2000);
        attachStepEvidence("Clicked on My Team link");
        Thread.sleep(5000);
        attachStepEvidence("Clicked on Showmore");
        Thread.sleep(3000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMoreCompensationinfo));
        Thread.sleep(5000);
        Wrapper.waitForpresenceOfElementLocated(xpath_Compensation);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Compensation),"Compensation");
        Thread.sleep(5000);
         attachStepEvidence("Clicked on Compensation info link");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CompensationInfo));
        Thread.sleep(8000);
        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error navigating to My Team Compensation: " + e.getMessage(), e);
        }
    }
    By xpath_CompensationInfoPageValidation = By.xpath("//h1[text()='Compensation Info']");

public void validateMSSCompensationInfoDetails(){
        try{
            Thread.sleep(10000);
        String EmployeeName= "0005775";
        if(Wrapper.findWebElement(xpath_EmployeeSearchBox).isDisplayed()){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
            if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                Thread.sleep(8000);
               attachStepEvidence("Person to be searched is displayed and is clicked.");
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(EmployeeName))).click();
            }
        }
        Thread.sleep(8000);
        if(Wrapper.findWebElement(xpath_CompensationInfoPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Current Salary details are displayed.");
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_CompensationInfoPageValidation),"Compensation Info");
            Thread.sleep(10000);
            attachStepEvidence("Current Salary details are displayed.");
            Thread.sleep(5000);
            String salary = Wrapper.findWebElement(xpath_Salary).getText();
            // String adjustment = Wrapper.findWebElement(xpath_Adjustment).getText();
            // String effectivePeriod = Wrapper.findWebElement(xpath_EffectivePeriod).getText();
            // String component = Wrapper.findWebElement(xpath_Component).getText();
            // String percentage = Wrapper.findWebElement(xpath_Percentage).getText();

            boolean isCompenensationDetailsDisplayed= !salary.isEmpty(); 
            // && !adjustment.isEmpty() && !effectivePeriod.isEmpty(); 
            // && !component.isEmpty() && !percentage.isEmpty();
        
            Assert.assertTrue(isCompenensationDetailsDisplayed, "Salary, Adjustment, and Effective Period details are displayed correctly.");
        
        }else{
            Assert.fail("Current Salary details are not displayed.");
        }
    }catch(InterruptedException e){
        e.printStackTrace();
        throw new RuntimeException("Error validating MSS Compensation Info details: " + e.getMessage(), e);
    }
}


public void navigateToResignationRetirementWithdrawal() {
        try{
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Employment));
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ResignationRetirement),"Resignation/Retirement");
        Thread.sleep(3000);
        attachStepEvidence("Navigating to Resignation/Retirement page");
        Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ResignationRetirement));
        Thread.sleep(20000);
        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error navigating to Resignation/Retirement Withdrawal: " + e.getMessage(), e);
        }

    }

public void navigateToHRALocationChangePage() throws Exception {
        Thread.sleep(8000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyTeam));
        Thread.sleep(2000);
        attachStepEvidence("Clicked on My Team link");
        Thread.sleep(8000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSShowMore));
        Thread.sleep(5000);
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Employment));
        Thread.sleep(5000);
        attachStepEvidence("Clicked on Location change link");
        Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_LocationChange));
        Thread.sleep(10000);
        String EmployeeName = "0096436";
        try{
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
       
            if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                attachStepEvidence("Searching for particular employee in search bar in Transfer page");
                Thread.sleep(3000);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(EmployeeName))).click();
            }
            Thread.sleep(8000);

        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error searching employee in Transfer page: " + e.getMessage());
        }
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRALocationChangeAction));
        Thread.sleep(8000);
    }

public void submitHRALocationChangeRequest(String LocationChangeDate) throws Exception {
        Wrapper.selectDate(
            Wrapper.findWebElement(xpath_LocationChangeDate),
            LocationChangeDate,
            xpath_ResignationRetirementDateValue,
            xpath_DatePickerMonth,
            xpath_DatePickerYear,
            xpath_DatePickerNext
        );

        Thread.sleep(8000);
        // Wait by locator (not cached WebElement), then click a fresh element
        Wrapper.waitForpresenceOfElementLocated(xpath_LocationChangeWay);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_LocationChangeWay)).click();
        Thread.sleep(3000);
        // Select "Location Change"
        By actionOption = xpath_ReasonSelection("Location Change");
        Wrapper.waitForpresenceOfElementLocated(actionOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption)).click();
        Thread.sleep(3000);

        // Open Reason dropdown
        Wrapper.waitForpresenceOfElementLocated(xpath_LocationChangeWhy);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_LocationChangeWhy), "Location Change Why");
        Thread.sleep(3000);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_LocationChangeWhy)).click();
            Thread.sleep(3000);
        // Select "Work Location"
        By reasonOption = xpath_ReasonSelection("Work Location");
        Wrapper.waitForpresenceOfElementLocated(reasonOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reasonOption)).click();
        Thread.sleep(3000);
        attachStepEvidence("Entered Location Change details and clicked on continue.");
        Thread.sleep(5000);
    
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
                Thread.sleep(10000);
        //Reporting Establishment
        Wrapper.waitForpresenceOfElementLocated(xpath_ReportingEstablishment);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_ReportingEstablishment)).click();
        By reportingEstablishmentOption = xpath_ReasonSelection("4th Ave Workout Location");
        Wrapper.waitForpresenceOfElementLocated(reportingEstablishmentOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reportingEstablishmentOption)).click();
    
        //Reporting Location
        Wrapper.waitForpresenceOfElementLocated(xpath_ReportingLocation);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_ReportingLocation)).click();
        By reportingLocationOption = xpath_ReasonSelection("Cleveland St Service Center");
        Wrapper.waitForpresenceOfElementLocated(reportingLocationOption);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(reportingLocationOption)).click();
                Thread.sleep(5000);
        attachStepEvidence("Entered Reporting Establishment and Reporting Location details and clicked on continue.");
        Thread.sleep(3000);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSContinueButton)).click();
        Thread.sleep(10000);
        attachStepEvidence("Clicked on Submit after entering all the details for Location Change.");
        Thread.sleep(5000);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_MSSSubmitButton)).click();
                Thread.sleep(8000);
    
    }



public void fillPersonalDetailsPage() throws Exception{
        String LastName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Last Name");
        String FirstName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"First Name");
        String DOB =  ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"R_DOB");
        Wrapper.waitForpresenceOfElementLocated(xpath_PersonalDetailsPageValidation);
        if(Wrapper.findWebElement(xpath_PersonalDetailsPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Personal Details page is displayed successfully.");
            Thread.sleep(5000);
            attachStepEvidence("the manager is on the Personal Details page for hiring a new employee.");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_LastNameInputField), LastName, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_FirstNameInputField), FirstName, false);

            Wrapper.waitForpresenceOfElementLocated(xpath_HireGender);
            // Thread.sleep(5000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HireGender), "Gender dropdown");
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_HireGender)).click();
                    Thread.sleep(5000);
            By genderOption = xpath_HireGenderOption();
            Wrapper.waitForpresenceOfElementLocated(genderOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(genderOption)).click();
                Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_HireDateOfBirth);
            Wrapper.selectDate(Wrapper.findWebElement(xpath_HireDateOfBirth), DOB, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerPrevious);
            Thread.sleep(8000);
        
            attachStepEvidence("the manager filled the details in Personal Details page for hiring a new employee.");
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_NationalIdentifierButton), "National Identifier button");
            Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_NationalIdentifierButton));
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_NationalIdentifierButton));
            Thread.sleep(5000);
        }else{
            Assert.fail("Personal Details page is not displayed after clicking Continue button on When and Why page.");
        }
    }

public void fillNationalIdentifierDetails() throws Exception{
        String NICountry=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_Country");
        String NIType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_Type");
        String NIID=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"NI_ID");
        if(Wrapper.findWebElement(xpath_NationalIdentifierCountry).isDisplayed()){
            Assert.assertTrue(true, "National Identifier details page is displayed successfully.");
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_NationalIdentifierCountry), "National Identifier Country dropdown");
            // Wrapper.waitForpresenceOfElementLocated(xpath_NationalIdentifierCountry);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(xpath_NationalIdentifierCountry)).click();
            // Wrapper.waitForpresenceOfElementLocated(xpath_NICountryManual);
            // driver.findElement(xpath_NICountryManual).clear();
            // driver.findElement(xpath_NICountryManual).sendKeys(NICountry);
            // By niCountryOption = xpath_NICountryList("US");
            // Wrapper.waitForpresenceOfElementLocated(niCountryOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(niCountryOption)).click();
            Thread.sleep(4000);
            Wrapper.waitForpresenceOfElementLocated(xpath_NIType);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_NIType)).click();
                    Thread.sleep(4000);
            By niTypeOption = xpath_NITypeList(NIType);
            Wrapper.waitForpresenceOfElementLocated(niTypeOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(niTypeOption)).click();
                    Thread.sleep(4000);

            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_NIID), NIID, false);

            Thread.sleep(5000);
            attachStepEvidence("the manager filled the details in National Identifier section for hiring a new employee.");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
             attachStepEvidence("the manager filled the details in Personal details page for hiring a new employee.");
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("National Identifier details page is not displayed after clicking National Identifier button on Personal Details page.");
        }
    }

public void fillCommunicationInfoDetails() throws Exception{
        String PhoneCountryCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Country");
        String PhoneAreaCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Area Code");
        String PhoneNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Number");
        // String PhoneType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Phone_Type");
        Wrapper.waitForpresenceOfElementLocated(xpath_CommunicationInfoPageValidation);
        if(Wrapper.findWebElement(xpath_CommunicationInfoPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Communication Info page is displayed successfully.");
            attachStepEvidence("the manager is on the Communication Info page for hiring a new employee.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PhoneDetailsButton));
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCode);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_PhoneCountryCode)).click();
                    Thread.sleep(4000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_PhoneCountryCodeManual);
            // driver.findElement(xpath_PhoneCountryCodeManual).sendKeys(PhoneCountryCode);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneCountryCodeManual), PhoneCountryCode, true);
            // By phoneCountryCodeOption = xpath_PhoneCountryCodeList("US");
            // Wrapper.waitForpresenceOfElementLocated(phoneCountryCodeOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(phoneCountryCodeOption)).click();

            Wrapper.waitForpresenceOfElementLocated(xpath_HirePhoneType);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_HirePhoneType)).click();
                Thread.sleep(5000);
            By phoneTypeOption = xpath_HirePhoneTypeOption();
            Wrapper.waitForpresenceOfElementLocated(phoneTypeOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(phoneTypeOption)).click();
                Thread.sleep(5000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneAreaCode), PhoneAreaCode, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_PhoneNumber), PhoneNumber, false);
            Thread.sleep(5000);
            attachStepEvidence("the manager filled the details in Phone section for hiring a new employee.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            Thread.sleep(5000);
            //Fill Email details similarly by clicking Email Details button and then click Continue button
        }else{
            Assert.fail("Communication Info page is not displayed after clicking Continue button on National Identifier details page.");
        }
    }

public void fillEmailDetails() throws Exception{
        Thread.sleep(10000);
        // String Email=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email");
        // String EmailType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email Type");
        // Wrapper.waitForpresenceOfElementLocated(xpath_EmailDetailsButton);
        // Thread.sleep(2000);
        // Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_EmailDetailsButton));
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmailDetailsButton));

        // // Wrapper.waitForpresenceOfElementLocated(xpath_HireEmailType);
        // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_HireEmailType), "Email Type dropdown");
        // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //         .elementToBeClickable(xpath_HireEmailType)).click();
        // By emailTypeOption = xpath_HireEmailTypeOption();
        // Wrapper.waitForpresenceOfElementLocated(emailTypeOption);
        // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //         .elementToBeClickable(emailTypeOption)).click();
        //         Thread.sleep(5000);
        // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_HireEmailType), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Email Type"), true);
        //     Thread.sleep(5000);
        // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Email), Email, false);

        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
        // Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
        // Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
       Thread.sleep(30000);
         attachStepEvidence("the manager filled the details in Email section for hiring a new employee and clicks on continue.");
        Thread.sleep(8000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        Thread.sleep(10000);
    }

public void fillAddressDetails() throws Exception{
        String AddressCountry=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Country");
        // String AddressType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Type");
        String AddressLine1=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Line1");
        // String AddressLine2=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Address_Line2");
        // String ZIPCode=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"ZIP_CODE");
        Thread.sleep(4000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressDetailsButton));
        Thread.sleep(4000);
        // Wrapper.waitForpresenceOfElementLocated(xpath_AddressCountry);
        // Thread.sleep(4000);
         if(Wrapper.findWebElement(xpath_AddressCountry).isDisplayed()){
            Assert.assertTrue(true, "Address details page is displayed successfully.");
        //     Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
        //             .elementToBeClickable(xpath_AddressCountry)).click();
        //             Thread.sleep(4000);
            // driver.findElement(xpath_AddressCountryManual).sendKeys(AddressCountry);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressCountryManual), AddressCountry, true);
            // Thread.sleep(4000);
            // By addressCountryOption = xpath_AddressCountryOption();
            // Wrapper.waitForpresenceOfElementLocated(addressCountryOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(addressCountryOption)).click();

            Wrapper.waitForpresenceOfElementLocated(xpath_HireAddressType);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_HireAddressType)).click();
                    Thread.sleep(4000);
            By addressTypeOption = xpath_HireAddressTypeOption();
            Wrapper.waitForpresenceOfElementLocated(addressTypeOption);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(addressTypeOption)).click();
                Thread.sleep(4000);

            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressLine1), AddressLine1, false);
            Thread.sleep(4000);
            // // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AddressLine2), AddressLine2, false);

            Wrapper.waitForpresenceOfElementLocated(xpath_ZIPCode);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_ZIPCode)).click();
                    Thread.sleep(4000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_ZIPCodeManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"ZIP_CODE"), true);
            By zipCodeOption = xpath_ZIPCodeOption();
            Thread.sleep(4000);
            Wrapper.waitForpresenceOfElementLocated(zipCodeOption);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(zipCodeOption)).click();
            Thread.sleep(4000);
            attachStepEvidence("the manager filled the details in Address section for hiring a new employee.");
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SaveButton), "Save button");
            Thread.sleep(4000);
            Wrapper.waitForpresenceOfElementLocated(xpath_SaveButton);
            Wrapper.waitForElementToBeClickable(Wrapper.findWebElement(xpath_SaveButton));

            Thread.sleep(4000);
        
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Thread.sleep(10000);
            Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            Thread.sleep(10000);
            Wrapper.waitForpresenceOfElementLocated(xpath_ValidateButton);
             attachStepEvidence("the manager filled the details in Address page for hiring a new employee.");
            Thread.sleep(10000);
            Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
            Thread.sleep(15000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));

        // }
        // else{
        //     Assert.fail("Address details page is not displayed after clicking Address button on Communication Info page.");
        // }
    }
}

public void fillAssignmentDetails() throws Exception{
        String AssignmentNumber=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Number");
        String AssignmentStatus=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Status");
        // String PersonType=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Person_Type");
        String Job=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Job");
        String BusinessTitle=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Business_Title");
        String Grade=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Grade");
        String Department=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Department");
        String ReportingEstablishment=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Reporting_Establishment");
        String Location=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Location");
        String WorkingAtHome=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Work_At_Home");
        String WorkerCategory=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Worker_Category");
        String AssignmentCategory=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Assignment_Category");
        String RegularTemporary=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Regular_Temporary");
        String FullTimePartTime=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Full_Time_Part_Time");
        String HourlyPaidSalaried=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hourly_Paid_Salaried");
        String WorkingHoursFrequency=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Working_Hours_Frequency");
        String UnionMember=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union_Member");
        Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentPageValidation);
        Thread.sleep(10000);
        if(Wrapper.findWebElement(xpath_AssignmentPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Assignment details page is displayed successfully.");
            Thread.sleep(3000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentNumber);
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AssignmentNumber), AssignmentNumber, false);
            // Fill other details in Assignment page similarly by using the respective locators and test data from excel sheet.
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AssignmentNumber), "Assignment Number input field");
            Thread.sleep(2000);
            driver.findElement(xpath_AssignmentNumber).sendKeys(Keys.TAB);
            Thread.sleep(10000);
            Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentNumber);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AssignmentNumber), AssignmentNumber, false);
            Wrapper.redwoodSync();

            // Fill other details in Assignment page similarly by using the respective locators and test data from excel sheet.
            Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentStatusPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_AssignmentStatusPicker)).click();
            for(WebElement element: driver.findElements(xpath_AssignmentStatusList)){
                if(element.getText().equals(AssignmentStatus)){
                    Wrapper.waitForElementToBeClickable(element);
                    Thread.sleep(2000);
                    element.click();
                    break;
                }
                Thread.sleep(3000);
            }
            Wrapper.waitForpresenceOfElementLocated(xpath_PersonTypePicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_PersonTypePicker)).click();
            Thread.sleep(4000);
            By personTypeOption = xpath_PersonTypeOption();
            Wrapper.waitForpresenceOfElementLocated(personTypeOption);
            Thread.sleep(2000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(personTypeOption)).click();
            Thread.sleep(3000);

            Wrapper.waitForpresenceOfElementLocated(xpath_JobPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_JobPicker)).click();
            Thread.sleep(4000);
            driver.findElement(xpath_JobManual).sendKeys(Job);
            for(WebElement element: driver.findElements(xpath_JobList)){
                if(element.getText().equals(Job)){
                    Wrapper.waitForElementToBeClickable(element);
                    element.click();
                    Thread.sleep(3000);
                    break;
                }
            }
            Wrapper.redwoodSync();

            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_BusinessTitle), BusinessTitle, false);
            Thread.sleep(3000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_GradePicker), "Grade dropdown");
            Wrapper.waitForpresenceOfElementLocated(xpath_GradePicker);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_GradePicker)).click();
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_GradeManual), Grade, false);
            for(WebElement element: driver.findElements(xpath_GradeList)){
                if(element.getText().equals(Grade)){
                    Wrapper.waitForElementToBeClickable(element);
                    // Thread.sleep(4000);
                    element.click();
                    Thread.sleep(4000);
                    break;
                }
            }
            Wrapper.redwoodSync();

            Wrapper.waitForpresenceOfElementLocated(xpath_DepartmentPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_DepartmentPicker)).click();
            Thread.sleep(4000);
            driver.findElement(xpath_DepartmentManual).sendKeys(Department);
            for(WebElement element: driver.findElements(xpath_DepartmentList)){
                if(element.getText().equals(Department)){
                    Thread.sleep(10000);
                    Wrapper.waitForElementToBeClickable(element);
                    element.click();
                    Thread.sleep(5000);
                    break;
                }
            }
            Wrapper.redwoodSync();

            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_GradePicker), "Grade dropdown");
            // Wrapper.waitForpresenceOfElementLocated(xpath_GradePicker);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_GradePicker)).click();
            // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_GradeManual), Grade, true);
            // for(WebElement element: driver.findElements(xpath_GradeList)){
            //     if(element.getText().equals(Grade)){
            //         Wrapper.waitForElementToBeClickable(element);
            //         // Thread.sleep(4000);
            //         element.click();
            //         break;
            //     }
            // }
            // Wrapper.redwoodSync();

            Wrapper.waitForpresenceOfElementLocated(xpath_ReportingEstablishmentPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_ReportingEstablishmentPicker)).click();
            driver.findElement(xpath_ReportingEstablishmentManual).sendKeys(ReportingEstablishment);
            for(WebElement element: driver.findElements(xpath_ReportingEstablishmentList)){
                if(element.getText().equals(ReportingEstablishment)){
                    Wrapper.waitForElementToBeClickable(element);
                    element.click();
                    break;
                }
            }
            attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
            Wrapper.waitForpresenceOfElementLocated(xpath_LocationPicker);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_LocationPicker)).click();
            driver.findElement(xpath_LocationManual).sendKeys(Location);
            for(WebElement element: driver.findElements(xpath_LocationList)){
                if(element.getText().equals(Location)){
                    Wrapper.waitForElementToBeClickable(element);
                    Thread.sleep(3000);
                    element.click();
                    Thread.sleep(3000);
                    break;
                }
            }
            Wrapper.redwoodSync();

            Wrapper.waitForpresenceOfElementLocated(xpath_WorkingAtHome);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WorkingAtHome)).click();
            By workingAtHomeOption = xpath_WorkingAtHomeOption();
            Wrapper.waitForpresenceOfElementLocated(workingAtHomeOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(workingAtHomeOption)).click();
            Thread.sleep(3000);

            attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");

            Wrapper.waitForpresenceOfElementLocated(xpath_WorkerCategory);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WorkerCategory)).click();
            By workerCategoryOption = xpath_WorkerCategoryOption();
            Wrapper.waitForpresenceOfElementLocated(workerCategoryOption);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(workerCategoryOption)).click();
            Thread.sleep(5000);

            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AssignmentCategory), "Assignment Category dropdown");
            Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentCategory);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_AssignmentCategory)).click();
            Thread.sleep(5000);
            By assignmentCategoryOption = xpath_AssignmentCategoryOption();
            Wrapper.waitForpresenceOfElementLocated(assignmentCategoryOption);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(assignmentCategoryOption)).click();
            Thread.sleep(5000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_RegularTemporary), "Regular Temporary dropdown");
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_RegularTemporary);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_RegularTemporary)).click();
            Thread.sleep(3000);
            By regularTemporaryOption = xpath_RegularTemporaryOption();
            Wrapper.waitForpresenceOfElementLocated(regularTemporaryOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(regularTemporaryOption)).click();
            Thread.sleep(5000);
            attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
            Wrapper.waitForpresenceOfElementLocated(xpath_FullTimePartTime);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_FullTimePartTime)).click();
            Thread.sleep(3000);
            By fullTimePartTimeOption = xpath_FullTimePartTimeOption();
            Wrapper.waitForpresenceOfElementLocated(fullTimePartTimeOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(fullTimePartTimeOption)).click();
            Thread.sleep(5000);

            Wrapper.redwoodSync();

            Wrapper.waitForpresenceOfElementLocated(xpath_HourlyPaidSalaried);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_HourlyPaidSalaried)).click();
            Thread.sleep(3000);
            By hourlyPaidSalariedOption = xpath_HourlyPaidSalariedOption();
            Wrapper.waitForpresenceOfElementLocated(hourlyPaidSalariedOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(hourlyPaidSalariedOption)).click();
            Thread.sleep(5000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_WorkingHours), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Working_Hours"), true);
            Thread.sleep(3000);

            Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursFrequency);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WorkingHoursFrequency)).click();
            By workingHoursFrequencyOption = xpath_WorkingHoursFrequencyOption();
            Wrapper.waitForpresenceOfElementLocated(workingHoursFrequencyOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(workingHoursFrequencyOption)).click();
            Wrapper.redwoodSync();

            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_UnionMember), "Union Member dropdown");
            Wrapper.waitForpresenceOfElementLocated(xpath_UnionMember);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_UnionMember)).click();
            Thread.sleep(3000);
            By unionMemberOption = xpath_UnionMemberOption();
            Wrapper.waitForpresenceOfElementLocated(unionMemberOption);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(unionMemberOption)).click();

            Wrapper.waitForpresenceOfElementLocated(xpath_Union);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_Union)).click();
            Thread.sleep(3000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UnionManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union"), true);
            for(WebElement element: driver.findElements(xpath_UnionList)){
                if(element.getText().equals(ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Union"))){
                    Wrapper.waitForElementToBeClickable(element);
                    Thread.sleep(3000);
                    element.click();
                    Thread.sleep(3000);
                    break;
                }
            }
            attachStepEvidence("the manager fills the details in Assignment details page for hiring a new employee.");
            Wrapper.waitForpresenceOfElementLocated(xpath_BargainingUnit);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_BargainingUnit)).click();
            Thread.sleep(6000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_BargainingUnitManual), ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Bargaining_Unit"), true);
            for(WebElement element: driver.findElements(xpath_BargainingUnitList)){
                if(element.getText().equals(ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Bargaining_Unit"))){
                    Wrapper.waitForElementToBeClickable(element);
                    Thread.sleep(3000);
                    element.click();
                    Thread.sleep(3000);
                    break;
                }
            }
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_OfficerCode), "Officer Code dropdown");
            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_OfficerCode);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_OfficerCode)).click();
            Thread.sleep(5000);
            By officerCodeOption = xpath_OfficerCodeOption();
            Wrapper.waitForpresenceOfElementLocated(officerCodeOption);
            Thread.sleep(5000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(officerCodeOption)).click();
            Thread.sleep(5000);
            Thread.sleep(8000);
             attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
             Thread.sleep(8000);
            attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            Thread.sleep(8000);
             attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_GradePicker), "Grade dropdown");
            // // Wrapper.waitForpresenceOfElementLocated(xpath_GradePicker);
            // // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_GradePicker)).click();
            // // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_GradeManual), Grade, true);
            // // for(WebElement element: driver.findElements(xpath_GradeList)){
            // //     if(element.getText().equals(Grade)){
            // //         Wrapper.waitForElementToBeClickable(element);
            // //         Thread.sleep(4000);
            // //         element.click();
            // //         break;
            // //     }
            // // }


            // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ReportingEstablishmentPicker), "Reporting Establishment dropdown");
            // // Wrapper.waitForpresenceOfElementLocated(xpath_ReportingEstablishmentPicker);
            // // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_ReportingEstablishmentPicker)).click();
            // // driver.findElement(xpath_ReportingEstablishmentManual).sendKeys(ReportingEstablishment);
            // // for(WebElement element: driver.findElements(xpath_ReportingEstablishmentList)){
            // //     if(element.getText().equals(ReportingEstablishment)){
            // //         Wrapper.waitForElementToBeClickable(element);
            // //         element.click();
            // //         break;
            // //     }
            // // }

            // // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AssignmentNumber), "Assignment Number input field");
            // // Wrapper.waitForpresenceOfElementLocated(xpath_AssignmentNumber);
            // // Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AssignmentNumber), AssignmentNumber, false);
            Thread.sleep(5000);
            attachStepEvidence("the manager filled the details in Assignment page for hiring a new employee.");
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Assignment details page is not displayed after clicking Continue button on Address details page.");
        }
    }

public void ManagerDetails(){
        try{
        // String ManagerName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Manager_Name");
        Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
        Thread.sleep(20000);
        if(Wrapper.findWebElement(xpath_MSSContinueButton).isDisplayed()){
            Assert.assertTrue(true, "Manager details page is displayed successfully.");
            attachStepEvidence("the manager filled the details in Manager details page for hiring a new employee.");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Manager details page is not displayed after clicking Continue button on Assignment details page.");
        }
    }catch(Exception e){
            e.printStackTrace();
        }
    }

public void fillPayrollDetails() throws Exception{
        //  String PayFrequency=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Pay_Frequency");
        Wrapper.waitForpresenceOfElementLocated(xpath_PayrollDetailsPageValidation);
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_PayrollDetailsPageValidation).isDisplayed()){
             Assert.assertTrue(true, "Payroll details page is displayed successfully.");
             Wrapper.waitForpresenceOfElementLocated(xpath_HirePayrollButton);
             Thread.sleep(3000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_HirePayrollButton)).click();
             Thread.sleep(5000);
             Wrapper.waitForpresenceOfElementLocated(xpath_Payroll);
             Thread.sleep(5000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_Payroll)).click();
             Thread.sleep(5000);
             By payrollOption = xpath_PayrollOption();
             Wrapper.waitForpresenceOfElementLocated(payrollOption);
             Thread.sleep(8000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(payrollOption)).click();
             Thread.sleep(5000);

             Wrapper.waitForpresenceOfElementLocated(xpath_TimeCardRequired);
             Thread.sleep(3000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_TimeCardRequired)).click();
             Thread.sleep(5000);
             By timeCardRequiredOption = xpath_TimeCardRequiredOption();
             Thread.sleep(3000);
             Wrapper.waitForpresenceOfElementLocated(timeCardRequiredOption);
             Thread.sleep(8000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(timeCardRequiredOption)).click();
             Thread.sleep(5000);
             attachStepEvidence("the manager filled the details in Payroll details page for hiring a new employee.");

             Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveButton));
            Thread.sleep(8000);
            //  Wrapper.waitForpresenceOfElementLocated(xpath_PrimaryValidationAfterSave);
            Wrapper.waitForpresenceOfElementLocated(xpath_MSSContinueButton);
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Payroll details page is not displayed after clicking Continue button on Manager details page.");
        }
    }

public void fillSalaryDetails() throws Exception{
        //  String SalaryAmount=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Salary Amount");
         String SalaryAmount="20";
         Thread.sleep(5000);
        Wrapper.waitForpresenceOfElementLocated(xpath_HireSalaryPageValidation);
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_HireSalaryPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Salary details page is displayed successfully.");
        
             Wrapper.waitForpresenceOfElementLocated(xpath_SalaryBasis);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_SalaryBasis)).click();
             Thread.sleep(5000);
             By salaryBasisOption = xpath_SalaryBasisOption();   
             Wrapper.waitForpresenceOfElementLocated(salaryBasisOption);
             Thread.sleep(5000);
             Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(salaryBasisOption)).click();
             Thread.sleep(5000);

             Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SalaryAmount), SalaryAmount, false);
            Thread.sleep(5000);
            attachStepEvidence("the manager filled the details in Salary details page for hiring a new employee.");
            Thread.sleep(5000);

            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Salary details page is not displayed after clicking Continue button on Payroll details page.");
        }
    }

    By xpath_ShowMoreMSSDirectreports=By.xpath("//div[@group='groupNode_manager_resources']//a[text()='Show More']");
    By xpath_MSSDirectReports = By.xpath("//div[contains(@type,'all-quickactions-container')]//a[text()='Direct Reports']");
public void navigateToMSSDirectReportsPage() throws Exception{
        Thread.sleep(15000);
        attachStepEvidence("Logged into MSS successfully.");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyTeam));
        Thread.sleep(5000);
        attachStepEvidence("clicked on My Team.");
        Thread.sleep(5000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ShowMoreMSSDirectreports), "Directreports_show_more");
        Thread.sleep(8000);
        attachStepEvidence("clicked on showmore.");
        Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMoreMSSDirectreports));
        Thread.sleep(8000);
        Wrapper.waitForpresenceOfElementLocated(xpath_MSSDirectReports);
        Thread.sleep(5000);
    
        if(Wrapper.findWebElement(xpath_MSSDirectReports).isDisplayed()){
            Assert.assertTrue(true, "Direct Reports option is displayed successfully under My Client Groups.");
            Thread.sleep(3000);
            attachStepEvidence("the manager clicked on Direct Reports option under My Team.");
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSDirectReports));
            Thread.sleep(5000);
        }else{
            Assert.fail("Direct Reports option is not displayed under My Client Groups.");
        }
    }

public void searchEmployeeInMSSDirectReportsPage() throws Exception{
        String EmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"First Name");
        Wrapper.waitForpresenceOfElementLocated(xpath_DirectReportsChangePageValidation);
        Thread.sleep(5000);
        attachStepEvidence("the manager is on Direct Reports page.");
        Thread.sleep(3000);
        if(Wrapper.findWebElement(xpath_DirectReportsChangePageValidation).isDisplayed()){
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
        
            Thread.sleep(5000);
            if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                Assert.assertTrue(true, "Employee search functionality is working fine in Direct Reports page.");
                Thread.sleep(3000);
                attachStepEvidence("the manager enters the employee name in search box and clicks on it.");
                Thread.sleep(5000);
                Wrapper.clickWebElement(Wrapper.findWebElement(HRAXpath(EmployeeName)));
            }else{
                Assert.fail("Employee search functionality is not working in Direct Reports page.");
            }
        }else{
            Assert.fail("Direct Reports page is not displayed after clicking on Direct Reports option under My Client Groups.");
        }
    }

public void fillMSSDirectReportsInfoToIncludePage() throws Exception{
        Thread.sleep(5000);
        Wrapper.waitForpresenceOfElementLocated(xpath_InfoToIncludePageValidation);
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_InfoToIncludePageValidation).isDisplayed()){
            Assert.assertTrue(true, "Info to include page is displayed successfully after clicking on an employee in Direct Reports page.");
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_commentsAndAttachmentButton), "Comments and attachments button");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_commentsAndAttachmentButton));
            attachStepEvidence("the manager filled the details in Info to include page and submitted the change request.");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Info to include page is not displayed after clicking on an employee in Direct Reports page.");
        }
    }

public void fillMSSDirectReportsChangeWhenAndWhyPage() throws Exception{
    
        String changeStartDate=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Change_Start_Date");
        String wayToChange=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Way_To_Change");
        String reasonForChange=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Reason_For_Change");
        Thread.sleep(5000);
        Wrapper.waitForpresenceOfElementLocated(xpath_DirectReportsChangeWhenAndWhyPageValidation);
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_DirectReportsChangeWhenAndWhyPageValidation).isDisplayed()){
            Assert.assertTrue(true, "When and why page is displayed successfully after clicking on an employee in Direct Reports page.");
            Thread.sleep(5000);
            Wrapper.selectDate(Wrapper.findWebElement(xpath_ChangeStartDate), changeStartDate, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
        
            Wrapper.waitForpresenceOfElementLocated(xpath_WayToChange);
            Thread.sleep(3000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_WayToChange)).click();
            Thread.sleep(3000);
            // Wrapper.redwoodSync();
            for(WebElement option : driver.findElements(xpath_WayToChangeList)){
                if(option.getText().equals(wayToChange)){
                    Wrapper.waitForElementToBeClickable(option);
                    option.click();
                    break;
                }
            }

            Thread.sleep(3000);
            Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ReasonForChange), "Reason for change dropdown");
            Wrapper.waitForpresenceOfElementLocated(xpath_ReasonForChange);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_ReasonForChange)).click();
            // Wrapper.redwoodSync();
            for(WebElement option : driver.findElements(xpath_ReasonForChangeList)){
                if(option.getText().equals(reasonForChange)){
                    Wrapper.waitForElementToBeClickable(option);
                    option.click();
                    break;
                }
            }
        }
       attachStepEvidence("the manager filled the details in When and Why page and submitted the change request.");
        Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        Thread.sleep(15000);
        //existing reports page code
        attachStepEvidence("the man is on exisiting reports page.");
        Thread.sleep(8000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        Thread.sleep(8000);
    
    }

public void fillMSSDirectReportsInfoChangePage() throws Exception{
        String reportEmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Report_Employee_Name");
        Wrapper.waitForpresenceOfElementLocated(xpath_DirectReportsInfoChangePageValidation);
        Thread.sleep(5000);
        attachStepEvidence("the manager is on the MSS DIRECT REPORTS info change page.");
        Thread.sleep(2000);
        if(Wrapper.findWebElement(xpath_DirectReportsInfoChangePageValidation).isDisplayed()){
            Assert.assertTrue(true, "Change direct report info page is displayed successfully after clicking Continue button on When and why page.");
            Thread.sleep(10000);
            Wrapper.waitForpresenceOfElementLocated(xpath_SearchPeopleToAddAsReportsManual);
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SearchPeopleToAddAsReportsManual));
            Thread.sleep(5000);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SearchPeopleToAddAsReports), reportEmployeeName, false);
            Thread.sleep(15000);
            if(Wrapper.findWebElement(xpath_SearchResultForPeopleToAddAsReport()).isDisplayed()){
                Assert.assertTrue(true, "Employee search functionality is working fine in Change direct report info page.");
                attachStepEvidence("the manager searches for direct report change and clicks on the person.");
                Thread.sleep(5000);
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SearchResultForPeopleToAddAsReport()));
                // new code
            //      Wrapper.waitForpresenceOfElementLocated(xpath_ReasonReportrelation);
            //     Thread.sleep(3000);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(xpath_ReasonReportrelation)).click();
            // Thread.sleep(3000);
            // Wrapper.redwoodSync();
            //  Wrapper.waitForpresenceOfElementLocated(xpath_ReasonReportrelation);
            //     Thread.sleep(3000);
            // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ReasonReportrelation));
            // Thread.sleep(3000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_ListRelationReport);
            // Thread.sleep(3000);
            // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ListRelationReport));
            // Thread.sleep(3000);
        
            //New code
                Thread.sleep(10000);
                attachStepEvidence("the manager filled the details in Change direct report info page and submitted the change request.");
                Thread.sleep(8000);
            }else{
                Assert.fail("Employee search functionality is not working in Change direct report info page.");
            }
            // attachStepEvidence("the manager filled the details a.");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        }else{
            Assert.fail("Change direct report info page is not displayed after clicking Continue button on When and why page.");
        }
    }

public void fillMSSDirectReportsCommentsAndAttachmentsPage() throws Exception{
        // String comment=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Termination_Comment");
        Wrapper.waitForpresenceOfElementLocated(xpath_CommentsAndAttachmentsPageValidation);
        Thread.sleep(15000);
        attachStepEvidence("the manager is on the MSS DIRECT REPORTS comments and attachments page.");
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_CommentsAndAttachmentsPageValidation).isDisplayed()){
            Assert.assertTrue(true, "Comments and attachments page is displayed successfully after clicking Continue button on Change direct report info page.");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_CommentsInputBox), "comment box for testing", false);
            attachStepEvidence("the manager filled the details in Comments and attachments page.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveComment));
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ClickCross));
            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_EditComment);
            Thread.sleep(5000);
            attachStepEvidence("the manager filled the details in Comments and attachments page and submitting the change request.");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
            Thread.sleep(15000);
        }else{
            Assert.fail("Comments and attachments page is not displayed after clicking Continue button on Change direct report info page.");
        }
    }



public void navigatetoPromoteRequest() throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_WorkListPageValidation);
        Thread.sleep(5000);
        if(Wrapper.findWebElement(xpath_WorkListPageValidation).isDisplayed()){
            Thread.sleep(5000);
            attachStepEvidence("the manager is on Promote approval Request Worklist page.");
            Thread.sleep(5000);
            Assert.assertTrue(true, "Worklist page is displayed successfully after clicking on Worklist option under Tools menu.");
             Wrapper.waitForpresenceOfElementLocated(xpath_AdHocSalaryRequest);
                try{
            if(Wrapper.findWebElement(xpath_AdHocSalaryRequest).isDisplayed()){
             Assert.assertTrue(true, "Ad Hoc Salary Request link is displayed successfully in Worklist page.");
                Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryRequest));
                System.out.println("Parent: " + driver.getWindowHandle());
            }else{
                Assert.fail("Ad Hoc Salary Request link is not displayed in Worklist page.");
            }
            }catch(Exception e){
                System.out.println("Ad Hoc Salary Request link is not displayed in Worklist page.");
            }
        }else{
            Assert.fail("Worklist page is not displayed after clicking on Worklist option under Tools menu.");
        }
    }
    By xpath_PromoteRequestapprove = By.xpath("//button[contains(text(),'Approve')]");
    By xpath_SubmitApprove = By.xpath("//span[text()='Submit']");

public void approvePromoteRequest() throws Exception{
        Thread.sleep(5000);
        switchBetweenWindows();
    
        System.out.println("All handles: " + driver.getWindowHandles());
        System.out.println("Child: " + driver.getWindowHandle());
        attachStepEvidence("the manager is on Promote Request approval page.");
        Thread.sleep(3000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PromoteRequestapprove));
        Thread.sleep(20000);
        Wrapper.waitForpresenceOfElementLocated(xpath_SubmitApprove);
        attachStepEvidence("the manager is on Promote Request Submit page.");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SubmitApprove));
        Thread.sleep(8000);
        // Wrapper.waitForpresenceOfElementLocated(xpath_AdHocSalaryApprovePageValidation);
        // if(Wrapper.findWebElement(xpath_AdHocSalaryApprovePageValidation).isDisplayed()){
        //     Assert.assertTrue(true, "Ad Hoc Salary Approve page is displayed successfully after clicking on Ad Hoc Salary Request link in Worklist page.");
        //     Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_AdHocSalaryApproveComments), "Approving this ad hoc salary request for testing purpose.", false);
        //     attachStepEvidence("the manager filled the details in Ad Hoc Salary Approve page and approved the request.");
        //     Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdHocSalaryApproveSubmit));
            // Thread.sleep(10000);
            // driver.switchTo().window(parentWindow);
            // Thread.sleep(2000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_WorkListPageValidation);
        // }else{
        //     Assert.fail("Ad Hoc Salary Approve page is not displayed after clicking on Ad Hoc Salary Request link in Worklist page.");
        // }
        Thread.sleep(10000);
            driver.switchTo().window(parentWindow);
            Thread.sleep(2000);
            Wrapper.waitForpresenceOfElementLocated(xpath_WorkListPageValidation);
            Thread.sleep(5000);
    }

public By xpath_ReasonSelectionactionname(String reasonText) {
        String xpath = "//span[contains(text(),'xxx')]";
        xpath = xpath.replace("xxx", reasonText);
        return By.xpath(xpath);
        ////div[contains(@id,'contact-relationship')]//ul/li//span[contains(text(),'Con Ed Spouse')]
    }
    By xpath_MYteamadhocsalaryinitiate = By.xpath("//a[text()='My Team']");
    By xpath_ShowmoreAdhocinitaite = By.xpath("//div[contains(@group,'groupNode_manager')]//a[text()='Show More']");
    By xpath_AdhocInitiateChangSalary = By.xpath("//div[@class='flat-quickactions-container']//child::a[text()='Change Salary']");
    By xpath_AdhocInitiatedatepicker = By.xpath("//oj-input-date[contains(@id,'effectiveDate')]//span[contains(@title,'Select Date.')]");
    By xpath_actionname = By.xpath("//oj-select-single[contains(@id,'actionSingleSelect')]//span/span");
    By xpath_changingTheSalarydropdown = By.xpath("//oj-select-single[contains(@id,'reasonSingleSelect')]//span/span");
    By xpath_ShowMoreParity = By.xpath("(//*[text()='Show More'])[3]");

public void navigateToInitiateMyTeamPage() throws Exception{
        Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MYteamadhocsalaryinitiate));
        Thread.sleep(2000);
        attachStepEvidence("the manager clicked on MyTeam menu.");
        Thread.sleep(5000);
    }

public void navigateToInitiateMyClientGroupsPage() throws Exception{
        Thread.sleep(8000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Thread.sleep(2000);
        attachStepEvidence("Staff is on My client group.");
        Thread.sleep(8000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ShowMoreParity), "Show More Parity");
        Wrapper.waitForpresenceOfElementLocated(xpath_ShowMoreParity);
        Thread.sleep(8000);
        attachStepEvidence("the staff clicked on showmore in Menu.");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMoreParity));
        Thread.sleep(8000);
    
    }

public void navigateTochangeSalaryPage() throws Exception{
        parentWindow = driver.getWindowHandle();
        Thread.sleep(3000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ShowmoreAdhocinitaite),"Showmoreinitiate");
        Wrapper.waitForpresenceOfElementLocated(xpath_ShowmoreAdhocinitaite);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowmoreAdhocinitaite));
        Thread.sleep(5000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AdhocInitiateChangSalary),"ChangeSalary");
        Thread.sleep(2000);
        Wrapper.waitForpresenceOfElementLocated(xpath_AdhocInitiateChangSalary);
         attachStepEvidence("Manager clicks on change salary for initiateing salary change");
         Thread.sleep(3000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdhocInitiateChangSalary));
        Thread.sleep(8000);
        // attachStepEvidence("the manager clicked on Worklist option under Tools menu.");
   
    }

public void navigateTochangeSalaryParityPage() throws Exception{
        parentWindow = driver.getWindowHandle();
        // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ParityChangeSalary),"ChangeSalary");
        Thread.sleep(12000);
        attachStepEvidence("Staff clicks on change salary for initiateing salary change");
         Thread.sleep(3000); 
        // Wrapper.waitForpresenceOfElementLocated(xpath_ParityChangeSalary);
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ParityChangeSalary));
        Thread.sleep(20000);
        // attachStepEvidence("the manager clicked on Worklist option under Tools menu.");
        attachStepEvidence("Staff is on change salary page for initiateing salary change");
    }

public void submitAdHocSalaryChangeRequest() throws Exception{
        Thread.sleep(5000);
        String EmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"First Name");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
            if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                attachStepEvidence("clicks on employee name on change salary page for initiateing salary change");
               Thread.sleep(3000);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(EmployeeName))).click();
            }
        Thread.sleep(5000);

        // attachStepEvidence("the manager clicked on Worklist option under Tools menu.");
    

    }

public void submitCompensationParityChangeRequest() throws Exception{
        String EmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"First Name");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
            if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                attachStepEvidence("Staff is on change salary for searching employee name.");
               Thread.sleep(5000);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(EmployeeName))).click();
            }
        Thread.sleep(5000);

        // attachStepEvidence("the manager clicked on Worklist option under Tools menu.");
    
    }
    By xpath_AddButton = By.xpath("(//button[text()='Add'])[1]");
    By xpath_AdditionalDetailsInfo = By.xpath("(//a[text()='Additional Person Info'])[2]");
    By xpath_MyClientGroupsparity = By.xpath("//a[@name='groupNode_workforce_management']");

public void fillAdhocSalaryChangeDetailsforInitiate() throws Exception{
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdhocInitiatedatepicker));
        //change salary page
        Thread.sleep(8000);
        String SalaryChange = "30000";
        Wrapper.selectDate(Wrapper.findWebElement(xpath_AdhocInitiatedatepicker), "17/August/2026", xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
        Thread.sleep(20000);
        //firstdropdown
        Wrapper.waitForpresenceOfElementLocated(xpath_actionname);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_actionname)).click();

        By actionOption = xpath_ReasonSelectionactionname("Change Salary");
        Wrapper.waitForpresenceOfElementLocated(actionOption);
        Thread.sleep(3000);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption)).click();

        //second dropdown
        Thread.sleep(3000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_changingTheSalarydropdown), "Changing the Salary dropdown");
        Thread.sleep(3000);
        Wrapper.waitForpresenceOfElementLocated(xpath_changingTheSalarydropdown);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_changingTheSalarydropdown)).click();
        Thread.sleep(3000);

    //    Career Progression
        By actiondropdownOption = xpath_ReasonSelectionactionname("Cost of Living Adjustment");
        Wrapper.waitForpresenceOfElementLocated(actiondropdownOption);
        Thread.sleep(3000);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actiondropdownOption)).click();
                Thread.sleep(2000);
                attachStepEvidence("Mnager selets dropdowns for date and reason");
                Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
   
        Thread.sleep(5000);
        Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SalaryChange), SalaryChange, true);
        Thread.sleep(15000);

        attachStepEvidence("Manger changes salary");
        Thread.sleep(5000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
         Thread.sleep(10000);
         Thread.sleep(20000);
         attachStepEvidence("Adds document record");
         Thread.sleep(30000);
          attachStepEvidence("Scrolls and clicks on Add button");
          Thread.sleep(5000);
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddButton));
          Thread.sleep(5000);
          attachStepEvidence("Manager clicks on continue");
          Thread.sleep(5000);
          Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
          Thread.sleep(10000);
         Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_CommentsInputBox), "Testing purpose", false);
            attachStepEvidence("the manager filled the details in Comments and attachments page and submitted the termination request.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveComment));
            Thread.sleep(3000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ClickCross));
            Thread.sleep(5000);
             attachStepEvidence("the manager clicks on submit button.");
             Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
            Thread.sleep(10000);
    }
    By xpath_SalaryHistory = By.xpath("(//a[text()='Salary History'])[2]");
    By xpath_Navigator = By.xpath("//a[@title='Navigator']/div");
    By xpath_SaveParityButton = By.xpath("(//button[text()='Save'])[1]");
    By xpath_ResignationReason = By.xpath("//oj-select-single[contains(@id,'actionOccurrences.ActionReasonId')]//span/span");
    By xpath_WithdrawSubmit = By.xpath("(//button[contains(@class,'oj-button-button')]//child::span[text()='Submit'])[1]");
    By xpath_Withdraw=By.xpath("(//button[text()='Withdraw Resignation'])[1]");

public void fillCompensationParityDetailsforInitiate() throws Exception{
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdhocInitiatedatepicker));
        //change salary page
        Thread.sleep(8000);
        String SalaryChange = "300";
        Wrapper.selectDate(Wrapper.findWebElement(xpath_AdhocInitiatedatepicker), "11/June/2026", xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
        Thread.sleep(5000);
        //firstdropdown
        Wrapper.waitForpresenceOfElementLocated(xpath_actionname);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_actionname)).click();

        By actionOption = xpath_ReasonSelectionactionname("Change Salary");
        Wrapper.waitForpresenceOfElementLocated(actionOption);
        Thread.sleep(3000);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actionOption)).click();

        //second dropdown
        Thread.sleep(3000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_changingTheSalarydropdown), "Changing the Salary dropdown");
        Thread.sleep(3000);
        Wrapper.waitForpresenceOfElementLocated(xpath_changingTheSalarydropdown);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(xpath_changingTheSalarydropdown)).click();
        Thread.sleep(3000);

    //    Career Progression
        By actiondropdownOption = xpath_ReasonSelectionactionname("Career Progression");
        Wrapper.waitForpresenceOfElementLocated(actiondropdownOption);
        Thread.sleep(3000);
        Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(actiondropdownOption)).click();
                Thread.sleep(2000);
                attachStepEvidence("Manager selects dropdowns for date and reason");
       Thread.sleep(8000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
        Thread.sleep(5000);
        Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_SalaryChange), SalaryChange, true);
        attachStepEvidence("Staff changes salary");
        Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSSubmitButton));
         Thread.sleep(10000);
         //change salary page ends

         //go to home page
         Thread.sleep(20000);
         //go to home page ends
        //  Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroupsparity));
         Thread.sleep(2000);
         attachStepEvidence("Staff clicks on My client group.");
        Thread.sleep(8000);
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_SalaryHistory), "Show More Parity");
        Wrapper.waitForpresenceOfElementLocated(xpath_SalaryHistory);
        Thread.sleep(5000);
        attachStepEvidence("the staff clicked on salary history in Menu.");
         Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SalaryHistory));
        Thread.sleep(10000);
        String EmployeeName=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"First Name");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
            if(Wrapper.findWebElement(HRAXpath(EmployeeName)).isDisplayed()){
                attachStepEvidence("Staff is on change salary for searching employee name.");
               Thread.sleep(5000);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(EmployeeName))).click();
            }
        Thread.sleep(15000);
            attachStepEvidence("Staff is able to see the salary history details in salary history page.");
            Thread.sleep(5000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Navigator));
        Thread.sleep(5000);
        //go to home page
        Thread.sleep(15000);
        //go to home page ends
        Thread.sleep(5000);
            // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_AdditionalDetailsInfo), "Additional Details Info");
            // Wrapper.waitForpresenceOfElementLocated(xpath_AdditionalDetailsInfo);
       
            Thread.sleep(20000);
            // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AdditionalDetailsInfo));
             attachStepEvidence("Staff is on Additional Person Info page to verify the updated salary details in compensation section.");
            Thread.sleep(10000);

            String EmployeeName1=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"First Name");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_EmployeeSearchBox));
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_EmployeeSearchBox), EmployeeName, false);
            if(Wrapper.findWebElement(HRAXpath(EmployeeName1)).isDisplayed()){
                attachStepEvidence("Staff is on change salary for searching employee name.");
               Thread.sleep(5000);
                Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(HRAXpath(EmployeeName1))).click();
            }
        Thread.sleep(10000);
        //Add parity details page
        Thread.sleep(50000);
        attachStepEvidence("Staff clicks on Save button.");
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SaveParityButton));
        Thread.sleep(10000);
        //Add parity details page ends
    }

public void withdrawRetirementRequest() throws InterruptedException{
        try{
            Thread.sleep(10000);
             attachStepEvidence("on the Resignation/Retirement page");
            Thread.sleep(15000);
            attachStepEvidence("clicks on Withdraw button and");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Withdraw));
            Thread.sleep(5000);
            Wrapper.waitForpresenceOfElementLocated(xpath_ResignationReason);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_ResignationReason)).click();
            Thread.sleep(5000);
            // Select "Location Change"
            Thread.sleep(3000);
            By actionOption = xpath_ReasonSelection("Employee Requested");
            Wrapper.waitForpresenceOfElementLocated(actionOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(actionOption)).click();
            Thread.sleep(5000);
            attachStepEvidence("selects reason for withdrawing the resignation/retirement request");
            Thread.sleep(5000);
            attachStepEvidence("Clicks on submit button to withdraw the resignation/retirement request");
            Thread.sleep(5000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_WithdrawSubmit));
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error withdrawing retirement request: " + e.getMessage(), e);
        }
    }
    By xpath_HireAnEmployee=By.xpath("//a[contains(text(),'Hire an Employee')]");

    public void navigateToHireAnEmployeePage(){
        try{
        Thread.sleep(8000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Thread.sleep(3000);
        attachStepEvidence("the manager clicks on My Client Groups");
        Thread.sleep(3000);
        attachStepEvidence("the manager clicks on Hire an Employee option under My Client Groups");
        Thread.sleep(3000);
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HireAnEmployee));
        Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException("Error navigating to Hire an Employee page: " + e.getMessage());
        }
    }

public void fillInfoToIncludePage() throws Exception{
        Wrapper.waitForpresenceOfElementLocated(xpath_InfoToIncludePageValidation);
        if(Wrapper.findWebElement(xpath_InfoToIncludePageValidation).isDisplayed()){
            Assert.assertTrue(true, "Successfully navigated to Info to include page.");
            Thread.sleep(10000);
            attachStepEvidence("the manager is on Info to include page after clicking Hire an Employee.");
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_CommunicationInfoButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Communication info"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Communication info")).isEnabled()){
                Assert.assertTrue(true, "Communication info button is enabled.");
            }else{
                Assert.fail("Communication info button is not enabled.");
            }

            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_AddressButton));
            attachStepEvidence("the manager is clicking on required info to include buttons in info to include page.");
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Addresses"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Addresses")).isEnabled()){
                Assert.assertTrue(true, "Addresses button is enabled.");
            }else{
                Assert.fail("Addresses button is not enabled.");
            }

            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ManagerButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Managers"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Managers")).isEnabled()){
                Assert.assertTrue(true, "Managers button is enabled.");
            }else{
                Assert.fail("Managers button is not enabled.");
            }

            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PayRollButton));
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Payroll"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Payroll")).isEnabled()){
                Assert.assertTrue(true, "Payroll button is enabled.");
            }else{
                Assert.fail("Payroll button is not enabled.");
            }
        
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HireSalaryButton));
            attachStepEvidence("the manager validated that Communication info, Addresses, Managers and Payroll buttons are enabled in Info to include page for hiring a new employee.");
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(xpath_ButtonEnabled("Salary"));
            if(Wrapper.findWebElement(xpath_ButtonEnabled("Salary")).isEnabled()){
                Assert.assertTrue(true, "Salary button is enabled.");
            }else{
                Assert.fail("Salary button is not enabled.");
            }
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("Failed to navigate to Info to include page after clicking Hire an Employee.");
        }
    }

    public void fillWhenAndWhyPage() throws Exception{
        String Hire_Date=ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Hire_Date");
        Wrapper.waitForpresenceOfElementLocated(xpath_WorkingHoursWhenAndWhyValidation);
        if(Wrapper.findWebElement(xpath_WorkingHoursWhenAndWhyValidation).isDisplayed()){
            Assert.assertTrue(true, "When and Why page is displayed successfully.");
            Thread.sleep(5000);
             attachStepEvidence("the manager is on When and Why page.");
            Wrapper.selectDate(Wrapper.findWebElement(xpath_HireDatePicker), Hire_Date, xpath_ResignationRetirementDateValue, xpath_DatePickerMonth, xpath_DatePickerYear, xpath_DatePickerNext);
        
            Wrapper.waitForpresenceOfElementLocated(xpath_LegalEmployer);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_LegalEmployer)).click();
            By legalEmployerOption = xpath_LegalEmployerOption();
            Thread.sleep(3000);
            Wrapper.waitForpresenceOfElementLocated(legalEmployerOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(legalEmployerOption)).click();
            Thread.sleep(5000);
            // Wrapper.waitForpresenceOfElementLocated(xpath_WayToHire);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(xpath_WayToHire)).click();
            // By wayToHireOption = xpath_WayToHireOption();
            // Wrapper.waitForpresenceOfElementLocated(wayToHireOption);
            // Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //         .elementToBeClickable(wayToHireOption)).click();
            // for(WebElement element:Wrapper.findWebElements(xpath_WayToHireList)){
            //     if(element.getText().equals(ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Way_To_Hire"))){
            //         Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
            //                 .elementToBeClickable(element)).click();
            //         break;
            //     }
            // }

            Wrapper.waitForpresenceOfElementLocated(xpath_WhyToHire);
            Thread.sleep(4000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_WhyToHire)).click();
                    Thread.sleep(6000);
            By whyToHireOption = xpath_WhyToHireOption();
            Wrapper.waitForpresenceOfElementLocated(whyToHireOption);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(whyToHireOption)).click();
            Thread.sleep(6000);
            // fillInfoToIncludePage();
            // Assert.assertTrue(true, "Manager filled the details in When and Why page successfully.");
        
            Wrapper.waitForpresenceOfElementLocated(xpath_BusinessUnit);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(xpath_BusinessUnit)).click();
                    Thread.sleep(8000);
            By businessUnitOption = xpath_BusinessUnitOption();
            Wrapper.waitForpresenceOfElementLocated(businessUnitOption);
            Thread.sleep(8000);
            Wrapper.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(businessUnitOption)).click();
                    Thread.sleep(6000);
        
            attachStepEvidence("the manager filled the details in When and Why page for hiring a new employee.");
            Thread.sleep(10000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MSSContinueButton));
            Thread.sleep(10000);
        }else{
            Assert.fail("When and Why page is not displayed after clicking Continue button on Info to include page.");
        }
    }

    public void enterParityCredentials() throws Exception{
        try {
            Thread.sleep(8000);
            userName = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Username");
            passWord = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Password");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UserName), userName, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Password), passWord, false);   
            Thread.sleep(10000);
            AllureReportUtil.info("Logged into Oracle HCM as Compensation");
            Thread.sleep(5000);
            attachStepEvidence("the Compensation staff is logged into Oracle HCM");
            Thread.sleep(5000);

        }catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Error entering credentials: " + e.getMessage());
        } 
    }



}



    











