package PageObject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import Utilities.AllureReportUtil;
import Utilities.ExcelReader;
import Utilities.ScenarioContext;
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

    String EXCEL_PATH = "src/test/resources/TestData/OracleHCM_NEW.xlsx";
    String SHEET_NAME = "Security";
    String KEY_COLUMN_HEADER = "Test Case"; 

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

    //xpath Locators
    //Login Page
    By xpath_UserName= By.xpath("//input[contains(@id, 'username')]");
    By xpath_Password=By.xpath("//input[contains(@id, 'password')]");
    By xpath_SigninButton=By.xpath("//*[text()='Sign In']");

    /**
	 *Login method used for logging into Oracle HCM Application
      by retrieving credentials from Excel.
     * Scripted By:gaddem[Gadde Madhukar]  
	**/
    public void enterCredentials() throws Exception {
        try {
            userName = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Username");
            passWord = ExcelReader.getCellDataByKey(EXCEL_PATH,SHEET_NAME,KEY_COLUMN_HEADER,currentScenarioTag,"Password");
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_UserName), userName, false);
            Wrapper.WebElementsendKeys(Wrapper.findWebElement(xpath_Password), passWord, false);   
        }catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Error entering credentials: " + e.getMessage());
        } 
    }

    /**
     * Open the dashboard page after successful login.
     * Scripted By:gaddem[Gadde Madhukar]
     */
    public void openDashboardPage(){
        try {
            Thread.sleep(2000);
            Wrapper.clickWebElement(Wrapper.findWebElement(xpath_SigninButton));
        }catch(InterruptedException e) {            
            e.printStackTrace();
            throw new RuntimeException("Error clicking Sign In button: " + e.getMessage());
        }  
    }

    /**
     * Capture the dashboard items for the current user.
     * Scripted By:gaddem[Gadde Madhukar]
     */
    List<String> dashboardItems;
    List<String> capturedDashboardItems;

     /**
     * Utility method to get the active WebDriver instance.
     * This method retrieves the WebDriver from the ScenarioContext, which is set in the Hooks @Before method.
     * Scripted By: gaddem [Gadde Madhukar]
     * Note: This method should be used in step definitions to ensure that the correct WebDriver instance is used for interactions.
     */
    public void captureDashboardItems(By dashboardItemsLocator,int count){ 
        try {
            
            dashboardItems = new ArrayList<>();
            capturedDashboardItems = new ArrayList<>();
            if(Wrapper.findWebElements(dashboardItemsLocator).size() == count) {
                
                List<WebElement> items = Wrapper.findWebElements(dashboardItemsLocator);
                for (WebElement item : items) {
                    String itemName = item.getText().trim();
                    if (!itemName.isEmpty()) {
                        dashboardItems.add(itemName);
                        capturedDashboardItems.add(itemName);
                    }
                }
                AllureReportUtil.info("Captured " + dashboardItems.size() + " dashboard items are: " + dashboardItems.toString());
                dashboardItems.clear();
            }
            else {
                AllureReportUtil.info("No dashboard items found using locator: " + dashboardItemsLocator.toString());
                Assert.fail("Expected " + count + " dashboard items, but found " + Wrapper.findWebElements(dashboardItemsLocator).size() + " using locator: " + dashboardItemsLocator.toString());
                AllureReportUtil.info("Expected " + count + " dashboard items, but found " + Wrapper.findWebElements(dashboardItemsLocator).size() + " using locator: " + dashboardItemsLocator.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error capturing dashboard items: " + e.getMessage());
        }
    }

    //CE Time Entry Clerk
    // xpath locator

    By xpath_MyClientGroups=By.xpath("//a[contains(text(),'My Client Groups')]");
    By xpath_MyClientGroupsTilesItems=By.xpath("//div[contains(@id,'yourapps_groupNode_workforce')]//a[contains(@class,'app')]");
    By xpath_MyClientGroupsQuickActionItems=By.xpath("//div[contains(@class,'quickactions')]//a[contains(@target,'my_org_')]");
    
    By xpath_HRConnect=By.xpath("//a[contains(text(),'HR Connect')]");
    By xpath_HRConnectTilesItems=By.xpath("//div[contains(@id,'yourapps_groupNode_HR')]//a[contains(@class,'app')]");
    By xpath_HRConnectQuickActionItems=By.xpath("//div[contains(@class,'quickactions')]//a[contains(@target,'itemNode')]");
    
    By xpath_Tools=By.xpath("//a[contains(text(),'Tools')]");
    By xpath_ToolsTilesItems=By.xpath("//div[contains(@id,'yourapps_groupNode_tools')]//a[contains(@class,'app')]");
    By xpath_ToolsQuickActionItems=By.xpath("//div[contains(@class,'quickactions')]//a[contains(@target,'atk_')]");
    
    public void captureMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 1);
        captureDashboardItems(xpath_MyClientGroupsQuickActionItems, 2);
    }
    public void captureHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 2);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 1);
    }
    public void captureToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 2);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }

    public void validateDashboardItems(){
        if(capturedDashboardItems == null || capturedDashboardItems.isEmpty()) {
            Assert.fail("No dashboard items captured to validate.");
            throw new RuntimeException("No dashboard items captured to validate.");
        }
        else {
            Assert.assertTrue(!capturedDashboardItems.isEmpty(), "Validated captured dashboard items");
        }
    }


    //CE Compensation Labor Relations Staff Exclude Retirees

    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->5
    By xpath_ShowMore=By.xpath("//div[contains(@group,'workforce_management')]/a[contains(text(),'Show More')]");
    // By xpath_ShowMoreQuickActionItems=By.xpath("//a[contains(@type,'quickaction') and contains(@target,'my_org') and not(contains(@group,'groupNode'))]");
    By xpath_ShowMoreQuickActionItems=By.xpath("//a[contains(@type,'quickaction') and not(contains(@group,'groupNode'))]");
    
    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->6
    //xpath_HRConnectQuickActionItems-->2

    //xpath_Tools
    //xpath_ToolsTilesItems-->3
    //xpath_ToolsQuickActionItems-->1

    public void captureCompensationLaborRelationsStaffExcludeRetireesMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Clicked Show More to capture additional My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 5);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 10);
    }

    public void captureCompensationLaborRelationsStaffExcludeRetireesHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 6);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 2);
    }

    public void captureCompensationLaborRelationsStaffExcludeRetireesToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 3);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }

    // CE Payroll Staff Full Population
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->6
    //xpath_ShowMore
    //xpath_ShowMoreQuickActionItems-->42

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->5
    //xpath_HRConnectQuickActionItems-->2

    //xpath_Tools
    //xpath_ToolsTilesItems-->3
    //xpath_ToolsQuickActionItems-->1
    public void capturePayrollStaffFullPopulationMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 6);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 42);
    }

    public void capturePayrollStaffFullPopulationHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 5);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 2);
    }

    public void capturePayrollStaffFullPopulationToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 3);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }

    //CE Tech Support View Only Data
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->2
    //xpath_ShowMore
    //xpath_ShowMoreQuickActionItems-->10

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->2
    //xpath_HRConnectQuickActionItems-->1

    //xpath_Tools
    //xpath_ToolsTilesItems-->3
    //xpath_ToolsQuickActionItems-->1

    public void captureTechSupportViewOnlyDataMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 2);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 10);
    }

    public void captureTechSupportViewOnlyDataHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 2);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 1);
    }

    public void captureTechSupportViewOnlyDataToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 3);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }


    //CE Treasury Full Population
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->3
    //xpath_ShowMore
    //xpath_ShowMoreQuickActionItems-->9

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->6
    //xpath_HRConnectQuickActionItems-->2

    //xpath_Tools
    //xpath_ToolsTilesItems-->3
    //xpath_ToolsQuickActionItems-->1

    public void captureTreasuryFullPopulationMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 3);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 9);
    }

    public void captureTreasuryFullPopulationHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 6);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 2);
    }

    public void captureTreasuryFullPopulationToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 3);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }

    //CE Compensation Staff 
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->4
    //xpath_ShowMore
    //xpath_ShowMoreQuickActionItems-->15

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->6
    //xpath_HRConnectQuickActionItems-->2

    //xpath_Tools
    //xpath_ToolsTilesItems-->3
    //xpath_ToolsQuickActionItems-->1

    public void captureCompensationStaffMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 4);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 15);
    }

    public void captureCompensationStaffHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 6);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 2);
    }

    public void captureCompensationStaffToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 3);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }


    //CE HR Employment Data View Only Excl Exec Retiree LEB
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->3
    //xpath_ShowMore
    // By xpath_ScrollToCapture=By.xpath("//div[contains(@id,'all_quickactions_groupNode_workforce_management')]//a[contains(text(),'Employment Info')]");
    //xpath_ShowMoreQuickActionItems-->4

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->6
    //xpath_HRConnectQuickActionItems-->2

    //xpath_Tools
    //xpath_ToolsTilesItems-->3 
    //xpath_ToolsQuickActionItems-->1

    public void captureHRMEmploymentDataViewOnlyExclExecRetireeLEBMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 3);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        // Wrapper.scrollToElement(Wrapper.findWebElement(xpath_ScrollToCapture),"scrolling to Employment Info");
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 4);
    }

    public void captureHRMEmploymentDataViewOnlyExclExecRetireeLEBHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 6);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 2);
    }

    public void captureHRMEmploymentDataViewOnlyExclExecRetireeLEBToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 3);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }

    //CE HR Employment Data View Only Full Population
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->2
    //xpath_ShowMore
    //xpath_ShowMoreQuickActionItems-->4

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->4
    //xpath_HRConnectQuickActionItems-->1

    //xpath_Tools
    //xpath_ToolsTilesItems-->2
    //xpath_ToolsQuickActionItems-->1

    public void captureHRMEmploymentDataViewOnlyFullPopulationMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 2);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 4);
    }

    public void captureHRMEmploymentDataViewOnlyFullPopulationHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 4);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 1);
    }

    public void captureHRMEmploymentDataViewOnlyFullPopulationToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 2);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }

    //CE HR Director Full Population
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->4
    //xpath_ShowMore
    //xpath_ShowMoreQuickActionItems-->11

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->6
    //xpath_HRConnectQuickActionItems-->3
    By xpath_HRDirectorConnectQuickActionItems=By.xpath("//div[contains(@class,'quickactions')]//div[contains(@quickactioncategory,'groupNode')]//a[contains(@group,'HR')]");
    //xpath_Tools
    //xpath_ToolsTilesItems-->3
    //xpath_ToolsQuickActionItems-->1

    public void captureHRDirectorFullPopulationMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 4);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 11);
    }

    public void captureHRDirectorFullPopulationHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 6);
        captureDashboardItems(xpath_HRDirectorConnectQuickActionItems, 3);
    }

    public void captureHRDirectorFullPopulationToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 3);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }


    //CE HR Data View Only LI Data Analytics Full Population
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->5
    //xpath_ShowMore
    //xpath_ShowMoreQuickActionItems-->7

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->5
    //xpath_HRConnectQuickActionItems-->2

    //xpath_Tools
    //xpath_ToolsTilesItems-->3
    //xpath_ToolsQuickActionItems-->1

    public void captureHRDataViewOnlyLIDataAnalyticsFullPopulationMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 5);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 7);
    }

    public void captureHRDataViewOnlyLIDataAnalyticsFullPopulationHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 5);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 2);
    }

    public void captureHRDataViewOnlyLIDataAnalyticsFullPopulationToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 3);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }

    //CE HR QA Compliance Full Population
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->3
    // xpath_ShowMore
    //xpath_MyClientGroupsQuickActionItems-->5

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->5
    //xpath_HRConnectQuickActionItems-->2

    //xpath_Tools
    //xpath_ToolsTilesItems-->3
    //xpath_ToolsQuickActionItems-->1

    public void captureHRQAComplianceFullPopulationMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 3);

        // Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        // Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_MyClientGroupsQuickActionItems, 5);
    }

    public void captureHRQAComplianceFullPopulationHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 5);
        captureDashboardItems(xpath_HRConnectQuickActionItems, 2);
    }

    public void captureHRQAComplianceFullPopulationToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 3);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }

    //HR Production Support
    //MyClientGroups
    //xpath_MyClientGroupsTilesItems-->20
    //xpath_ShowMore
    By xpath_TransactionConfigurationAndAudit=By.xpath("//h4[contains(text(),'Transaction Configuration and Audit')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_NewPerson=By.xpath("//h4[contains(text(),'New Person')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_Employment=By.xpath("//h4[contains(text(),'Employment')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_Compensation=By.xpath("//h4[contains(text(),'Compensation')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_JourneysSetup=By.xpath("//h4[contains(text(),'Journeys Setup')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_Time=By.xpath("//h4[contains(text(),'Time')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_WorkforceStructures=By.xpath("//h4[contains(text(),'Workforce Structures')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_DocumentTypes=By.xpath("//h4[contains(text(),'Document Types')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_Talent=By.xpath("//h4[contains(text(),'Talent')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_Absences=By.xpath("//h4[contains(text(),'Absences')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_DataExchange=By.xpath("//h4[contains(text(),'Data Exchange')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_Payroll=By.xpath("//h4[contains(text(),'Payroll')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_MassUpdates=By.xpath("//h4[contains(text(),'Mass Updates')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    By xpath_WorkforceScheduling=By.xpath("//h4[contains(text(),'Workforce Scheduling')]");//scroll to this element to capture all quick action items under My Client Groups for HR Production Support role
    //xpath_ShowMoreQuickActionItems-->200

    By xpath_BenefitsAdministration=By.xpath("//a[contains(text(),'Benefits Administration')]");
    By xpath_BenefitsAdministrationTilesItems=By.xpath("//div[contains(@id,'yourapps_groupNode_benefits')]//a[contains(@class,'app')]");//3

    By xpath_PartnerManagement=By.xpath("//a[contains(text(),'Partner Management')]");
    By xpath_PartnerManagementTilesItems=By.xpath("//div[contains(@group,'groupNode_partner')]//a[contains(@class,'app')]");//1

    By xpath_ClusterNextNavigation=By.xpath("//div[contains(@id,'clusters-right-nav')]/*[local-name()='svg']");

    By xpath_Knowledge=By.xpath("//a[contains(@name,'groupNode_knowledge')]");
    By xpath_KnowledgeTilesItems=By.xpath("//div[contains(@id,'yourapps_groupNode_knowledge')]//a[contains(@class,'app')]");//3
    By xpath_KnowledgeQuickActionItems=By.xpath("//div[contains(@group,'groupNode_knowledge')]//a[contains(@target,'itemNode')]");//5

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->5
    By xpath_HRProductionSupportConnectQuickActionItems=By.xpath("//div[contains(@id,'cluster_groupNode_HR_HelpDesk')]//a[contains(@group,'groupNode_HR') and not(contains(@id,'showmore'))]");//3

    By xpath_MyEnterprise=By.xpath("//a[contains(text(),'My Enterprise')]");
    By xpath_MyEnterpriseTilesItems=By.xpath("//div[contains(@id,'itemNode_MyEnterprise')]//a[contains(@class,'app')]");//4
    By xpath_MyEnterpriseQuickActionItems=By.xpath("//div[contains(@group,'groupNode_MyEnterprise')]//a[contains(@group,'groupNode_MyEnterprise') and not(contains(@id,'showmore'))]");//1

    //xpath_Tools
    //xpath_ToolsTilesItems-->12
    //xpath_ToolsQuickActionItems-->1

    //xpath_ClusterNextNavigation

    By xpath_Configuration=By.xpath("//a[contains(text(),'Configuration')]");
    By xpath_ConfigurationTilesItems=By.xpath("//div[contains(@id,'yourapps_groupNode_configuration')]//a[contains(@class,'app')]");//2
    
    public void captureHRProductionSupportMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 20);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));

        // Scroll to each section to ensure all quick action items are loaded for capture
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_TransactionConfigurationAndAudit), "scrolling to Transaction Configuration and Audit");
        attachStepEvidence("Scrolled to Transaction Configuration and Audit section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_NewPerson), "scrolling to New Person");
        attachStepEvidence("Scrolled to New Person section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Employment), "scrolling to Employment");
        attachStepEvidence("Scrolled to Employment section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Compensation), "scrolling to Compensation");
        attachStepEvidence("Scrolled to Compensation section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_JourneysSetup), "scrolling to Journeys Setup");
        attachStepEvidence("Scrolled to Journeys Setup section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Time), "scrolling to Time");
        attachStepEvidence("Scrolled to Time section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_WorkforceStructures), "scrolling to Workforce Structures");
        attachStepEvidence("Scrolled to Workforce Structures section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DocumentTypes), "scrolling to Document Types");
        attachStepEvidence("Scrolled to Document Types section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Talent), "scrolling to Talent");
        attachStepEvidence("Scrolled to Talent section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Absences), "scrolling to Absences");
        attachStepEvidence("Scrolled to Absences section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_DataExchange), "scrolling to Data Exchange");
        attachStepEvidence("Scrolled to Data Exchange section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_Payroll), "scrolling to Payroll");
        attachStepEvidence("Scrolled to Payroll section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_MassUpdates), "scrolling to Mass Updates");
        attachStepEvidence("Scrolled to Mass Updates section to load all quick action items under My Client Groups for HR Production Support role");
        Wrapper.scrollToElement(Wrapper.findWebElement(xpath_WorkforceScheduling), "scrolling to Workforce Scheduling");
        attachStepEvidence("Scrolled to Workforce Scheduling section to load all quick action items under My Client Groups for HR Production Support role");

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More and scrolling through all sections");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 200);
    }

    public void captureHRProductionSupportBenefitsAdministrationDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_BenefitsAdministration));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_BenefitsAdministrationTilesItems));
        attachStepEvidence("Captured Benefits Administration dashboard items");
        captureDashboardItems(xpath_BenefitsAdministrationTilesItems, 3);
    }

    public void captureHRProductionSupportPartnerManagementDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_PartnerManagement));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_PartnerManagementTilesItems));
        attachStepEvidence("Captured Partner Management dashboard items");
        captureDashboardItems(xpath_PartnerManagementTilesItems, 1);
    }

    public void clusterNextNavigation(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ClusterNextNavigation));
        // attachStepEvidence("Clicked on Cluster Next Navigation to capture dashboard items under Cluster Next Navigation for HR Production Support role");
    }

    public void captureHRProductionSupportKnowledgeDashboardItems() throws Exception{
        clusterNextNavigation();
        Thread.sleep(2000);// Adding a short sleep to allow the dashboard to load after clicking Cluster Next Navigation
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Knowledge));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_KnowledgeTilesItems));
        attachStepEvidence("Captured Knowledge dashboard items");
        captureDashboardItems(xpath_KnowledgeTilesItems, 3);
        captureDashboardItems(xpath_KnowledgeQuickActionItems, 5);
    }

    public void captureHRProductionSupportHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 5);
        captureDashboardItems(xpath_HRProductionSupportConnectQuickActionItems, 3);
    }

    public void captureHRProductionSupportMyEnterpriseDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyEnterprise));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyEnterpriseTilesItems));
        attachStepEvidence("Captured My Enterprise dashboard items");
        captureDashboardItems(xpath_MyEnterpriseTilesItems, 4);
        captureDashboardItems(xpath_MyEnterpriseQuickActionItems, 1);
    }

    public void captureHRProductionSupportToolsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Tools));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ToolsTilesItems));
        attachStepEvidence("Captured Tools dashboard items");
        captureDashboardItems(xpath_ToolsTilesItems, 12);
        captureDashboardItems(xpath_ToolsQuickActionItems, 1);
    }

    public void captureHRProductionSupportConfigurationDashboardItems() throws Exception{
        // Thread.sleep(2000);
        // clusterNextNavigation();
        Thread.sleep(2000);// Adding a short sleep to allow the dashboard to load after clicking Cluster Next Navigation
        clusterNextNavigation();
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_Configuration));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ConfigurationTilesItems));
        attachStepEvidence("Captured Configuration dashboard items");
        captureDashboardItems(xpath_ConfigurationTilesItems, 2);
    }





}
