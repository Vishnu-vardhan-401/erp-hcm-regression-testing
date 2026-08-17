package Utilities;
 
import java.io.ByteArrayInputStream;
 
import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriver;
 
import io.qameta.allure.Allure;
 
public final class AllureReportUtil {
 
    private AllureReportUtil() {

        // Utility class

    }
 
    public static void info(String message) {

        Allure.step("[INFO] " + message);

    }
 
    public static String attachText(String name, String text) {

        Allure.addAttachment(name, "text/plain", text, ".txt");

        return text;

    }
 
    public static String attachHtml(String name, String html) {

        Allure.addAttachment(name, "text/html", html, ".html");

        return html;

    }
 
    public static String attachCsv(String name, String csv) {

        Allure.addAttachment(name, "text/csv", csv, ".csv");

        return csv;

    }
 
    public static byte[] attachPng(String name, byte[] content) {

        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(content), ".png");

        return content;

    }
 
    public static void attachScreenshot(WebDriver driver, String name) {

        if (driver == null) {

            attachText("Screenshot skipped", "Driver is null. Could not capture screenshot: " + name);

            return;

        }
 
        if (!(driver instanceof TakesScreenshot screenshotCapableDriver)) {

            attachText("Screenshot skipped", "Driver does not support screenshots: " + name);

            return;

        }
 
        byte[] screenshot = screenshotCapableDriver.getScreenshotAs(OutputType.BYTES);

        attachPng(name, screenshot);

    }

}
 