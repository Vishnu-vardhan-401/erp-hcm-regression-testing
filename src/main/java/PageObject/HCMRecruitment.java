package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utilities.AllureReportUtil;
import Utilities.ExcelReader;
import Utilities.ScenarioContext;
import Utilities.Wrapper;

public class HCMRecruitment {
    WebDriver driver;

    public HCMRecruitment(WebDriver driver) {
        
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
    
}
