package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
  public FileInputStream fis;
  public FileOutputStream fos;

  public XSSFWorkbook workbook;
  public XSSFSheet sheet;
  public XSSFRow row;
  public XSSFCell cell;
  public CellStyle style;

  String path;

  public ExcelUtility(String path) {
    this.path = path;
  }

  public int getRowCount(String sheetName) throws IOException{
    fis = new FileInputStream(path);
    workbook = new XSSFWorkbook(fis);
    sheet = workbook.getSheet(sheetName);

    int rowCount = sheet.getLastRowNum();
    System.out.println(rowCount);

    workbook.close();
    fis.close();

    return rowCount;
  }

  public int getCellCount(String sheetName, int rowNum) throws IOException{
    fis = new FileInputStream(path);
    workbook = new XSSFWorkbook(fis);
    sheet = workbook.getSheet(sheetName);
    row = sheet.getRow(rowNum);

    int cellCount = row.getLastCellNum();
    System.out.println(cellCount);

    workbook.close();
    fis.close();

    return cellCount;
  }

  public String getCellData(String sheetName, int rowNum, int colNum)  throws IOException {
    fis = new FileInputStream(path);
    workbook = new XSSFWorkbook(fis);
    sheet = workbook.getSheet(sheetName);
    row = sheet.getRow(rowNum);
    cell = row.getCell(colNum);

    DataFormatter formatter = new DataFormatter();
    String data;

    try {
      data = formatter.formatCellValue(cell); //returns as String
    } catch (Exception e) {
      data = "";
    }

    workbook.close();
    fis.close();

    return data;
  }

  public void setCellData(String sheetName, int rowNum, int colNum, String data)  throws IOException {
    File xlfile = new File(path);

    // If file not exists then create new file
    if(!xlfile.exists()) {
      workbook = new XSSFWorkbook();
      fos = new FileOutputStream(path);
      workbook.write(fos);
    }
    fis = new FileInputStream(path);
    workbook=new XSSFWorkbook(fis);

    if(workbook.getSheetIndex(sheetName)==-1) // If sheet not exists then create new Sheet
      workbook.createSheet(sheetName);
    sheet = workbook.getSheet(sheetName);

    if (sheet.getRow(rowNum) == null) // If row not exists then create new Row
      sheet.createRow(rowNum);

    row = sheet.getRow(rowNum);
    cell = row.createCell(colNum);
    cell.setCellValue(data);
    fos = new FileOutputStream(path);

    workbook.write(fos);

    workbook.close();
    fis.close();
    fos.close();
  }

  public void fillGreenColor(String sheetName, int rowNum, int colNum)  throws IOException {
    fis = new FileInputStream(path);
    workbook = new XSSFWorkbook(fis);
    sheet = workbook.getSheet(sheetName);
    row = sheet.getRow(rowNum);
    cell = row.getCell(colNum);

    style = workbook.createCellStyle();

    style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    cell.setCellStyle(style);

    workbook.write(fos);

    workbook.close();
    fis.close();
    fos.close();
  }
}