
/*package utils;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelUtils {
   private static Workbook workbook;
   private static Sheet sheet;
   public static void openExcel(String sheetName) throws IOException {
       FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir") + "/testdata/TestCaseForProject.xlsx"));
       workbook = new XSSFWorkbook(fis);
       sheet = workbook.getSheet(sheetName);
   }
   public static void writeResult(int row, String actual, String status) throws IOException {
       Row r = sheet.getRow(row);
       if (r == null) r = sheet.createRow(row);
       Cell actualCell = r.createCell(2);
       Cell statusCell = r.createCell(3);
       actualCell.setCellValue(actual);
       statusCell.setCellValue(status);
       FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir") + "/testdata/TestCaseForProject.xlsx"));
       workbook.write(fos);
       fos.close();
   }
   public static void printResultsInConsole() {
	    for (Row row : sheet) {
	        String test = getCellValue(row.getCell(0));
	        String exp = getCellValue(row.getCell(1));
	        String act = getCellValue(row.getCell(2));
	        String status = getCellValue(row.getCell(3));
	        if (!act.isEmpty()) {
	            System.out.println(test + " | " + exp + " | " + act + " | " + status);
	        }
	    }
	}

   private static String getCellValue(Cell cell) {
       return (cell == null) ? "" : cell.toString().trim();
   }
}*/

package utils;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    private static Workbook workbook;
    private static Sheet sheet;

    public static void openExcel(String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir") + "/testdata/TestCaseForProject.xlsx"));
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
    }

    public static void writeResult(int row, String actual, String status) throws IOException {
        Row r = sheet.getRow(row);
        if (r == null) r = sheet.createRow(row);
        r.createCell(2).setCellValue(actual);
        r.createCell(3).setCellValue(status);

        FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir") + "/testdata/TestCaseForProject.xlsx"));
        workbook.write(fos);
        fos.close();
    }

    public static void printResultsInConsole() {
        for (Row row : sheet) {
            String test = getCellValue(row.getCell(0));
            String exp = getCellValue(row.getCell(1));
            String act = getCellValue(row.getCell(2));
            String status = getCellValue(row.getCell(3));
            if (!act.isEmpty()) {
                System.out.println(test + " | " + exp + " | " + act + " | " + status);
            }
        }
    }

    private static String getCellValue(Cell cell) {
        return (cell == null) ? "" : cell.toString().trim();
    }
}
