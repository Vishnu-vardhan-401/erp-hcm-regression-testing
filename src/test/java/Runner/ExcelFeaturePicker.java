//package Runner;
//
//
//import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
//public final class ExcelFeaturePicker {
//
//    private ExcelFeaturePicker() { }
//
//    /**
//     * Reads the Excel file and returns absolute paths to .feature files for which
//     * column B ("Execution") is set to "Yes" (case-insensitive).
//     *
//     * Expected columns:
//     * A: Features (e.g., HCMLogin.feature)
//     * B: Execution ("Yes"/"No")
//     */
//
//    public static int iterateWorkbook() throws IOException {
//        String excelPath = "src/test/resources/TestData/TestRunnerSheet.xlsx";
//        FileInputStream fis = new FileInputStream(excelPath);
//        XSSFWorkbook workbook = new XSSFWorkbook(fis);
//
//        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//            Sheet sheet = workbook.getSheetAt(i);
//            // Process each sheet here
//            System.out.println("Processing sheet: " + sheet.getSheetName());
//
//            return i;
//
//        }
//        return -1;
//    }
//
//    public static List<String> pick(String excelPath, int index) throws Exception {
//        List<String> featuresToRun = new ArrayList<>();
//
//        try (FileInputStream fis = new FileInputStream(excelPath);
//             XSSFWorkbook wb = new XSSFWorkbook(fis)) {
//
//            XSSFSheet sheet = wb.getSheetAt(index);
//            if (sheet == null) {
//                throw new IllegalArgumentException("Sheet not found: " + index);
//            }
//
//            DataFormatter df = new DataFormatter();
//
//            // Start at row 1 (skip headers)
//            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
//                Row row = sheet.getRow(r);
//                if (row == null)
//                    continue;
//
//                String featureName = df.formatCellValue(row.getCell(0)).trim(); // Column A
//                String execution   = df.formatCellValue(row.getCell(1)).trim(); // Column B
//
//                if (featureName.isEmpty())
//                    continue;
//
//                if ("yes".equalsIgnoreCase(execution)) {
//                    // Build absolute path: <project>/src/test/resources/features/<featureName>
//                    Path featurePath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "features", featureName);
//                    featuresToRun.add(featurePath.toString());
//                }
//            }
//        }
//
//        return featuresToRun;
//    }
//}


//package Runner;
//
//import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public final class ExcelFeaturePicker {
//
//    private ExcelFeaturePicker() { }
//
//    /**
//     * @param absoluteExcelPath absolute path to TestRunnerSheet.xlsx (provided by runner)
//     * @param index sheet index to process
//     * @return list like ["classpath:Features/HCMLogin.feature", ...]
//     */
//    public static List<String> pick(String absoluteExcelPath, int index) throws Exception {
//        List<String> featuresToRun = new ArrayList<>();
//
//        try (FileInputStream fis = new FileInputStream(absoluteExcelPath);
//             XSSFWorkbook wb = new XSSFWorkbook(fis)) {
//
//            XSSFSheet sheet = wb.getSheetAt(index);
//            if (sheet == null) {
//                throw new IllegalArgumentException("Sheet not found at index: " + index);
//            }
//
//            DataFormatter df = new DataFormatter();
//
//            // Skip header row (start at r = 1)
//            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
//                Row row = sheet.getRow(r);
//                if (row == null) continue;
//
//                String featureName = df.formatCellValue(row.getCell(0)).trim(); // Column A: Features
//                String execution   = df.formatCellValue(row.getCell(1)).trim(); // Column B: Execution
//
//                if (featureName.isEmpty()) continue;
//                if (!"yes".equalsIgnoreCase(execution)) continue;
//
//                // Normalize to ".feature"
//                if (!featureName.endsWith(".feature")) {
//                    featureName = featureName + ".feature";
//                }
//
//                // IMPORTANT: Your tree uses "Features" (capital F), not "features"
//                String classpathRef = "classpath:Features/" + featureName;
//
//                // Optional: existence check (will be a no-op on classpath: but we can sanity check URL)
//                if (Thread.currentThread().getContextClassLoader()
//                        .getResource("Features/" + featureName) == null) {
//                    throw new IOException("Feature not found on classpath: Features/" + featureName);
//                }
//
//                featuresToRun.add(classpathRef);
//            }
//        }
//
//        return featuresToRun;
//    }
//}


package Runner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public final class ExcelFeaturePicker {

    private ExcelFeaturePicker() {
    }

    public static List<String> pickTags(Sheet sheet) {
        Set<String> tagsSet = new LinkedHashSet<>();
        DataFormatter df = new DataFormatter();

        System.out.println("[Excel Debug] Sheet name: " + sheet.getSheetName());
        System.out.println("[Excel Debug] Last row: " + sheet.getLastRowNum());

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;

            String testCaseId = df.formatCellValue(row.getCell(0)).trim();
            String execution = df.formatCellValue(row.getCell(1)).trim();

            // System.out.println("[Excel Debug] Row " + r + ": Col A='" + testCaseId + "', Col B='" + execution + "'");

            if ("yes".equalsIgnoreCase(execution)) {
                String normalized = normalizeTagToken(testCaseId);
                if (!normalized.isEmpty()) {
                    String tag = "@" + normalized;
                    if (tagsSet.add(tag)) {
                        System.out.println("[Excel Debug] Added tag: [" + tag + "]");
                    } else {
                        System.out.println("[Excel Debug] Duplicate tag skipped: [" + tag + "]");
                    }
                }
            }
        }
        
        List<String> tags = new ArrayList<>(tagsSet);
        System.out.println("[Excel Debug] Final tags (deduplicated): " + tags);
        return tags;
    }

    private static String normalizeTagToken(String raw) {
        if (raw == null) return "";
        String s = stripInvisible(raw);
        if (s.startsWith("@")) s = s.substring(1);
            s = s.trim();
        return s;
    }

    private static String stripInvisible(String s) {
        return s
            .replace("\uFEFF", "") // BOM
            .replace("\u200B", "") // zero-width space
            .replace("\u00A0", " ") // non-breaking space -> normal space
            .trim();
    }


    public static List<String> pick(Sheet sheet) throws Exception {
        List<String> featuresToRun = new ArrayList<>();

        if (sheet == null) {
            throw new IllegalArgumentException("Sheet is null");
        }

        DataFormatter df = new DataFormatter();

        // Start from row 1 (skip header)
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            String featureName = df.formatCellValue(row.getCell(0)).trim();
            String execution = df.formatCellValue(row.getCell(1)).trim();

            if (featureName.isEmpty()) {
                continue;
            }

            if ("yes".equalsIgnoreCase(execution)) {

                if (!featureName.endsWith(".feature")) {
                    featureName = featureName + ".feature";
                }

                // IMPORTANT: match exact folder name from your project
                String featureClasspath = "classpath:Features/" + featureName;

                if (Thread.currentThread()
                        .getContextClassLoader()
                        .getResource("Features/" + featureName) == null) {
                    throw new RuntimeException("Feature file not found: Features/" + featureName);
                }

                featuresToRun.add(featureClasspath);
            }
        }

        return featuresToRun;
    }
}

