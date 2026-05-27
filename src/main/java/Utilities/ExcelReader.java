package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public static String getCellDataByKey(String filePath,
                                          String sheetName,
                                          String keyColumnHeader,
                                          String keyValue,
                                          String targetColumnHeader) throws IOException {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            DataFormatter formatter = new DataFormatter();

            // Header row (assumed at index 0)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Header row (row 0) is missing in sheet: " + sheetName);
            }

            int keyColIdx = findColumnIndex(headerRow, keyColumnHeader);
            int targetColIdx = findColumnIndex(headerRow, targetColumnHeader);

            if (keyColIdx == -1) {
                throw new IllegalArgumentException("Key column header not found: " + keyColumnHeader);
            }
            if (targetColIdx == -1) {
                throw new IllegalArgumentException("Target column header not found: " + targetColumnHeader);
            }

            // Iterate data rows
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                Cell keyCell = row.getCell(keyColIdx);
                String keyCellValue = formatter.formatCellValue(keyCell).trim();

                if (keyValue.equalsIgnoreCase(keyCellValue)) {
                    Cell targetCell = row.getCell(targetColIdx);
                    return formatter.formatCellValue(targetCell).trim();
                }
            }

            throw new IllegalArgumentException("Row not found where [" + keyColumnHeader + "] = " + keyValue);
        }
    }

    private static int findColumnIndex(Row headerRow, String headerName) {
        for (int c = 0; c < headerRow.getLastCellNum(); c++) {
            Cell cell = headerRow.getCell(c);
            if (cell == null) continue;
            String text = cell.getStringCellValue();
            if (text != null && text.trim().equalsIgnoreCase(headerName)) {
                return c;
            }
        }
        return -1;
    }

    public static Object[][] getExecutionData() throws Exception {

        String path = "src/test/resources/TestData/TestRunnerSheet.xlsx";
        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = wb.getSheet("Sheet1");

            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(0).getLastCellNum();

            Object[][] data = new Object[rows][cols];

            for (int i = 1; i <= rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i - 1][j] = sheet.getRow(i).getCell(j).toString();
                }
            }

            return data;
        }
    }
}
