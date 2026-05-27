package PageObjectManager;


import org.openqa.selenium.WebDriver;

import PageObject.HCMCoreHR;
import PageObject.HCMSecurity;

public class PageObjectManagerHCM {

    private WebDriver driver;

    // Page object references (lazy initialized)
    private HCMCoreHR hdp;
    private HCMSecurity security;
    public PageObjectManagerHCM(WebDriver driver) {
        this.driver = driver;
    }

    // Returns existing instance or creates new one
    public HCMCoreHR getHCMCoreHR() {
        if (hdp == null) {
            hdp = new HCMCoreHR(driver);
        }
        return hdp;
    }

    public HCMSecurity getHCMSecurity() {
        if (security == null) {
            security = new HCMSecurity(driver);
        }
        return security;
    }
}