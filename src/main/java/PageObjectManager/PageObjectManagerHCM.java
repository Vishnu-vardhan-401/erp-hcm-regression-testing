package PageObjectManager;

import org.openqa.selenium.WebDriver;

import PageObject.HCMSecurity;
// import PageObject.HCMRecruitment;
// import PageObject.HCMBenefits;
// import PageObject.HCMTimeAndLabour;

public class PageObjectManagerHCM {

    private WebDriver driver;

    // Page object references (lazy initialized)
    // private HCMCoreHR hdp;
    private HCMSecurity security;
    // private HCMRecruitment recruitment;
    // private HCMBenefits benefits;
    // private HCMTimeAndLabour timeAndLabour;
    
    public PageObjectManagerHCM(WebDriver driver) {
        this.driver = driver;
    }

    // Returns existing instance or creates new one
    // public HCMCoreHR getHCMCoreHR() {
        // if (hdp == null) {
            // hdp = new HCMCoreHR(driver);
        // }
        // return hdp;
    // }

    public HCMSecurity getHCMSecurity() {
        if (security == null) {
            security = new HCMSecurity(driver);
        }
        return security;
    }
    // public HCMRecruitment getHCMRecruitment() {
        // if (recruitment == null) {
    //         recruitment = new HCMRecruitment(driver);
    //     }
    //     return recruitment;
    // }
    // public HCMBenefits getHCMBenefits() {
    //     if (benefits == null) {
    //         benefits = new HCMBenefits(driver);
    //     }
    //     return benefits;
    // }

    // public HCMTimeAndLabour getHCMTimeAndLabour() {
    //     if (timeAndLabour == null) {
    //         timeAndLabour = new HCMTimeAndLabour(driver);
    //     }
    //     return timeAndLabour;
    // }
}