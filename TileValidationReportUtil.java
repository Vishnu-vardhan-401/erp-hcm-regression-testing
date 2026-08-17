package Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Accumulates one row per (Test Case, Menu Option) validated during the test
 * run. Used both to build a per-scenario HTML table for Allure
 * (see HCMSecurity.Validate_All_Tiles()) and to write a single consolidated
 * Excel file covering every scenario in the run (see Hooks.tearDown()).
 *
 * Columns: Test Case Name | Menu Option | Expected Tiles/Links | Actual Tiles/Links | Comments
 */
public final class TileValidationReportUtil {

    private TileValidationReportUtil() {
        // Utility class
    }

    public static class ResultRow {
        public final String testCaseName;
        public final String menuOption;
        public final String expectedTiles;
        public final String actualTiles;
        public final String comments;

        public ResultRow(String testCaseName, String menuOption, String expectedTiles, String actualTiles, String comments) {
            this.testCaseName = testCaseName;
            this.menuOption = menuOption;
            this.expectedTiles = expectedTiles;
            this.actualTiles = actualTiles;
            this.comments = comments;
        }
    }

    // Thread-safe since Cucumber may run scenarios in parallel depending on config.
    private static final List<ResultRow> results = new CopyOnWriteArrayList<>();

    public static void addResult(String testCaseName, String menuOption, String expectedTiles, String actualTiles, String comments) {
        results.add(new ResultRow(testCaseName, menuOption, expectedTiles, actualTiles, comments));
    }

    public static List<ResultRow> getResults() {
        return results;
    }

    public static List<ResultRow> getResultsForTestCase(String testCaseName) {
        return results.stream()
                .filter(r -> r.testCaseName != null && r.testCaseName.equalsIgnoreCase(testCaseName))
                .collect(Collectors.toList());
    }

    /**
     * Builds a standalone HTML page containing a table of the given rows,
     * for attaching to Allure via AllureReportUtil.attachHtml(). Rows whose
     * comments don't start with "All tiles present" are highlighted red.
     */
    public static String buildHtmlTable(List<ResultRow> rows) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><style>")
                .append("table { border-collapse: collapse; width: 100%; font-family: Arial, sans-serif; font-size: 13px; }")
                .append("th, td { border: 1px solid #999; padding: 6px 8px; text-align: left; vertical-align: top; }")
                .append("th { background-color: #2c3e50; color: white; }")
                .append("tr.pass { background-color: #eafaf1; }")
                .append("tr.fail { background-color: #fdecea; }")
                .append("</style></head><body>")
                .append("<table>")
                .append("<tr><th>Test Case Name</th><th>Menu Option</th><th>Expected Tiles/Links</th><th>Actual Tiles/Links</th><th>Comments</th></tr>");

        for (ResultRow row : rows) {
            boolean isPass = row.comments != null && row.comments.startsWith("All tiles present");
            html.append("<tr class=\"").append(isPass ? "pass" : "fail").append("\">")
                    .append("<td>").append(escapeHtml(row.testCaseName)).append("</td>")
                    .append("<td>").append(escapeHtml(row.menuOption)).append("</td>")
                    .append("<td>").append(escapeHtml(row.expectedTiles)).append("</td>")
                    .append("<td>").append(escapeHtml(row.actualTiles)).append("</td>")
                    .append("<td>").append(escapeHtml(row.comments)).append("</td>")
                    .append("</tr>");
        }

        html.append("</table></body></html>");
        return html.toString();
    }

    private static String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    public static void clear() {
        results.clear();
    }

    /**
     * Writes every accumulated result (across all scenarios in this run) to a
     * single Excel file with columns:
     * Test Case Name | Menu Option | Expected Tiles/Links | Actual Tiles/Links | Comments
     */
    public static void writeReportToExcel(String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Tile Validation Report");

            String[] headers = {"Test Case Name", "Menu Option", "Expected Tiles/Links", "Actual Tiles/Links", "Comments"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIndex = 1;
            for (ResultRow result : results) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(result.testCaseName);
                row.createCell(1).setCellValue(result.menuOption);
                row.createCell(2).setCellValue(result.expectedTiles);
                row.createCell(3).setCellValue(result.actualTiles);
                row.createCell(4).setCellValue(result.comments);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                workbook.write(fos);
            }
        }
    }
}
