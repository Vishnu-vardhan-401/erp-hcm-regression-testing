package Base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import Utilities.ConfigReader;

public class BaseClass {

    // 1. Make driver static so it can be shared
    public static WebDriver driver;

    // 2. Modified method to Create AND Store the driver

    public static void initializeDriver() {
        if(driver != null) {
            return; // Driver already initialized, do nothing
        }
        String browserName = ConfigReader.getProperty("browser").toUpperCase();

        switch (browserName) {
            case "Chrome" -> {
                System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\WebDrivers\\chromedriver.exe");
                driver = new ChromeDriver();
            }
            case "Edge" -> {
                System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\WebDrivers\\msedgedriver.exe");
                driver = new EdgeDriver();
            }
            default -> {
                System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\WebDrivers\\chromedriver.exe");
                driver = new ChromeDriver();
            }
        }

        driver.manage().window().maximize();
        int waitTime = Integer.parseInt(ConfigReader.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
    }

    // 3. New Helper method to give the driver to anyone who asks
    public static WebDriver getDriver() {
        return driver;
    }

    // 4. Cleanup method
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
