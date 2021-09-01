package io.jalorx.importFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.errors.ErrCode;
import io.micronaut.http.multipart.CompletedFileUpload;


public abstract class BaseImport {


  @SuppressWarnings("resource")
  public Map<String, List<List<String>>> importFile(CompletedFileUpload file)
      throws FileNotFoundException, IOException {
    InputStream inputStream = file.getInputStream();
    Map<String, List<List<String>>> res = new HashMap<String, List<List<String>>>();
    String[] fileNameArr = file.getFilename().split("\\.");
    String fileType = fileNameArr[fileNameArr.length - 1];
    if ("xlsx".equals(fileType)) {
      XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = null;

      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
        // 获取每个sheet
        sheet = workbook.getSheetAt(i);
        String sheetName = sheet.getSheetName();
        List<List<String>> sheetData = new ArrayList<List<String>>();
        for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
          Row row = sheet.getRow(j);
          if (null != row) {
            // getLastCellNum获取最后一列
            List<String> rowData = new ArrayList<String>();
            for (int k = 0; k < row.getLastCellNum(); k++) {
              if (null != row.getCell(k)) {
                Cell cell = row.getCell(k);
                String v = cell.getStringCellValue();
                rowData.add(v);
              }
            }
            sheetData.add(rowData);
          }
        }
        res.put(sheetName, sheetData);
      }
    } else if ("xls".equals(fileType)) {
      HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
      HSSFSheet sheet = null;

      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
        // 获取每个sheet
        sheet = workbook.getSheetAt(i);
        String sheetName = sheet.getSheetName();
        List<List<String>> sheetData = new ArrayList<List<String>>();
        for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
          Row row = sheet.getRow(j);
          if (null != row) {
            // getLastCellNum获取最后一列
            List<String> rowData = new ArrayList<String>();
            for (int k = 0; k < row.getLastCellNum(); k++) {
              if (null != row.getCell(k)) {
                Cell cell = row.getCell(k);
                String v = cell.getStringCellValue();
                rowData.add(v);
              }
            }
            sheetData.add(rowData);
          }
        }
        res.put(sheetName, sheetData);
      }
    } else {
      throw new BusinessAccessException(new ErrCode(ErrCode.A_PARAMETER_FORMAT_MISMATCH.getCode(),
          ErrCode.A_PARAMETER_FORMAT_MISMATCH.getMessage()), "文件格式不匹配");
    }


    return res;
  }

}

