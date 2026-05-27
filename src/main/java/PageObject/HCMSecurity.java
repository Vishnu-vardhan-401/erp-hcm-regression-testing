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
    //xpath_ShowMoreQuickActionItems-->48

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->6
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
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 48);
    }

    public void capturePayrollStaffFullPopulationHRConnectDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_HRConnect));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_HRConnectTilesItems));
        attachStepEvidence("Captured HR Connect dashboard items");
        captureDashboardItems(xpath_HRConnectTilesItems, 6);
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
    //xpath_MyClientGroupsTilesItems-->1
    //xpath_ShowMore
    //xpath_ShowMoreQuickActionItems-->9

    //xpath_HRConnect
    //xpath_HRConnectTilesItems-->2
    //xpath_HRConnectQuickActionItems-->1

    //xpath_Tools
    //xpath_ToolsTilesItems-->2
    //xpath_ToolsQuickActionItems-->1

    public void captureTechSupportViewOnlyDataMyClientGroupsDashboardItems(){
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_MyClientGroups));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_MyClientGroupsTilesItems));
        attachStepEvidence("Captured My Client Groups dashboard items");
        captureDashboardItems(xpath_MyClientGroupsTilesItems, 1);

        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMore));
        Wrapper.clickWebElement(Wrapper.findWebElement(xpath_ShowMore));
        Wrapper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath_ShowMoreQuickActionItems));
        attachStepEvidence("Captured additional My Client Groups Quick Action items after clicking Show More");
        captureDashboardItems(xpath_ShowMoreQuickActionItems, 9);
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
        captureDashboardItems(xpath_ToolsTilesItems, 2);
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

}

