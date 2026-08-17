package Base;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.*;

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
            case "chrome2" -> {
                System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\WebDrivers\\chromedriver.exe");
                // driver = new ChromeDriver();
                String downloadPath = System.getProperty("user.dir") + File.separator + "Reports";
                File folder = new File(downloadPath);
                 
                if (folder.exists()) {
                    System.out.println("Downloads folder exists");
                } else {
                    folder.mkdir();   // creates folder
                    System.out.println("Downloads folder created");
                }
                Map<String, Object> prefs = new HashMap<>();
			    prefs.put("download.default_directory",downloadPath);
			    
                ChromeOptions options1 = new ChromeOptions();
                options1.setExperimentalOption("prefs", prefs);
                options1.addArguments("--force-device-scale-factor=1.3");
                driver = new ChromeDriver(options1);
            }

            case "Chrome" -> {
                System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\WebDrivers\\chromedriver.exe");
                // driver = new ChromeDriver();
                
                ChromeOptions options1 = new ChromeOptions();
                options1.addArguments("--force-device-scale-factor=1.3");
                driver = new ChromeDriver(options1);
            }
            case "Edge" -> {
                System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\WebDrivers\\msedgedriver.exe");
                driver = new EdgeDriver();
            }
            default -> {
                System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\WebDrivers\\chromedriver.exe");
                // driver = new ChromeDriver();
                String downloadPath = System.getProperty("user.dir") + File.separator + "Reports Downloaded";
                File folder = new File(downloadPath);
                 
                if (folder.exists()) {
                    System.out.println("Downloads folder exists");
                } else {
                    folder.mkdir();   // creates folder
                    System.out.println("Downloads folder created");
                }
                Map<String, Object> prefs = new HashMap<>();
			    prefs.put("download.default_directory",downloadPath);
			   // prefs.put("download.prompt_for_download", true);
                ChromeOptions options1 = new ChromeOptions();
                options1.setExperimentalOption("prefs", prefs);
                options1.addArguments("--force-device-scale-factor=1.3");
                driver = new ChromeDriver(options1);
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
