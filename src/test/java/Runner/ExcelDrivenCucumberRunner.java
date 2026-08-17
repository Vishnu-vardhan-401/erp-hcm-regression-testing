package Runner;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.SkipException;
import org.testng.annotations.Test;

import io.cucumber.core.cli.Main;

public class ExcelDrivenCucumberRunner {

    @Test
    public void runSelectedFeaturesFromExcel() throws Exception {

        final String excelClasspath = "TestData/OracleHCMRegressionTestData.xlsx";

        URL excelUrl = Thread.currentThread()
                .getContextClassLoader()
                .getResource(excelClasspath);

        if (excelUrl == null) {
            throw new IllegalStateException("Excel file not found on classpath: " + excelClasspath);
        }

        Path excelPath = Path.of(excelUrl.toURI());

        System.out.println("Excel path: " + excelPath);
        System.out.println("Excel size: " + Files.size(excelPath) + " bytes");

        if (Files.size(excelPath) == 0) {
            throw new IllegalStateException("Excel file is empty: " + excelPath);
        }

        // Extra validation: check XLSX ZIP signature
        try (InputStream sigStream = Files.newInputStream(excelPath)) {
            byte[] signature = sigStream.readNBytes(4);
            if (signature.length < 4 ||
                    signature[0] != 'P' ||
                    signature[1] != 'K') {
                throw new IllegalStateException(
                        "File is not a valid XLSX/ZIP file. First bytes are not PK: " + excelPath
                );
            }
        }

        // --- BLOCK 1: Excel parsing only — if this fails, it really IS an Excel problem ---
        List<String> allTags;
        try (InputStream is = Files.newInputStream(excelPath);
            Workbook workbook = WorkbookFactory.create(is)) {

            int numberOfSheets = workbook.getNumberOfSheets();
            System.out.println("Workbook opened successfully. Sheet count = " + numberOfSheets);

            allTags = new java.util.ArrayList<>();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                System.out.println("Processing sheet: " + sheet.getSheetName());
                List<String> tags = ExcelFeaturePicker.pickTags(sheet);
                allTags.addAll(tags);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                "Failed to open or parse Excel file: " + excelPath +
                ". The workbook is likely corrupted or not a real .xlsx file.", e);
        }

// --- BLOCK 2: Cucumber execution — errors here propagate with their real message ---
        if (allTags.isEmpty()) {
            throw new SkipException("No test cases marked YES in any sheet");
        }

    String tagExpression = String.join(" or ", allTags)
        .replace("\uFEFF", "")
        .replace("\u200B", "")
        .replace("\u00A0", " ")
        .trim();

        System.out.println("Final tag expression: [" + tagExpression + "]");

        String[] argv = {
            "--glue", "StepDefinition",
            "--glue", "Hooks",
            "--tags", tagExpression,
            "--plugin", "pretty",
            "--plugin", "html:reports/cucumber-reports.html",
            "--plugin", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
            "classpath:Features"
        };

        byte exitStatus = Main.run(argv, Thread.currentThread().getContextClassLoader());
        if (exitStatus != 0) {
            throw new AssertionError("Cucumber execution failed with exit code: " + exitStatus);
        }

        // try (InputStream is = Files.newInputStream(excelPath);
        //      Workbook workbook = WorkbookFactory.create(is)) {

        //     int numberOfSheets = workbook.getNumberOfSheets();
        //     System.out.println("Workbook opened successfully. Sheet count = " + numberOfSheets);

        //     List<String> allTags = new java.util.ArrayList<>();
        //     for (int i = 0; i < numberOfSheets; i++) {
        //         Sheet sheet = workbook.getSheetAt(i);
        //         System.out.println("Processing sheet: " + sheet.getSheetName());
        //         List<String> tags = ExcelFeaturePicker.pickTags(sheet);
        //         allTags.addAll(tags);
        //     }

            // if (allTags.isEmpty()) {
            //     throw new SkipException("No test cases marked YES in any sheet");
            // }

            // {
                
            //     String tagExpression = String.join(" or ", allTags)
            //                     .replace("\uFEFF", "")
            //                     .replace("\u200B", "")
            //                     .replace("\u00A0", " ")
            //                     .trim();

            //     System.out.println("Final tag expression: [" + tagExpression + "]");

            //     String[] argv = {
            //         "--glue", "StepDefinition",
            //         "--glue", "Hooks",
            //         "--tags", tagExpression,
            //         "--plugin", "pretty",
            //         "--plugin", "html:reports/cucumber-reports.html",
            //         "--plugin", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
            //         "classpath:Features"
            //     };

        //         byte exitStatus = Main.run(argv, Thread.currentThread().getContextClassLoader());
        //         if (exitStatus != 0) {
        //             throw new AssertionError("Cucumber execution failed with exit code: " + exitStatus);
        //         }

        //         }
        //     }
        // catch (Exception e) {
        //     throw new RuntimeException(
        //             "Failed to open or parse Excel file: " + excelPath +
        //                     ". The workbook is likely corrupted or not a real .xlsx file.",e
        //     );
        // }
    }
}
