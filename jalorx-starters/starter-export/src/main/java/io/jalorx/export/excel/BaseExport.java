package io.jalorx.export.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.Pair;
import io.jalorx.boot.utils.StreamUtils;
import io.jalorx.export.utils.ExcelUtils;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.http.server.types.files.SystemFile;

public class BaseExport<T> {

  public SystemFile export(Definition<T> def) throws BusinessAccessException {

    XSSFWorkbook wb = new XSSFWorkbook();
    Sheet s = wb.createSheet("sheet0");
    for (int i = 0; i < def.getColumnSize(); i++) {
      s.setColumnWidth(i, 6500);
    }
    // 样式
    XSSFCellStyle titleStyle = ExcelUtils.xSSFAddStyle(wb.createCellStyle());
    XSSFFont font = wb.createFont();

    int rowIndex = 0;
    Row row = s.createRow(rowIndex++);

    String tableTitle = def.getTableTitle();

    if (StringUtils.isNotEmpty(tableTitle)) {
      // 合并第一列
      for (int i = 0; i < def.getColumnSize(); i++) {
        Cell cell = row.createCell(i);
        cell.setCellStyle(titleStyle);
      }
      s.addMergedRegion(new CellRangeAddress(0, 0, 0, def.getColumnSize() - 1));
      Cell c0 = row.createCell(0);
      XSSFFont f1 = wb.createFont();
      f1.setFontHeightInPoints((short) 20);// 字体大小
      titleStyle.setFont(f1);
      c0.setCellStyle(titleStyle);
      c0.setCellValue(tableTitle);
    }

    row = s.createRow(rowIndex++);
    font.setFontHeightInPoints((short) 12);// 字体大小
    titleStyle.setFont(font);// 将字体加入到样式中。

    int columnIndex = 0;
    for (Pair cell : def.getColumn()) {
      Cell c = row.createCell(columnIndex++);
      c.setCellValue(cell.getValue());
      c.setCellStyle(titleStyle);
    }


    def.getDataSource().forEachRemaining(data -> {
      Row dataRow = s.createRow(s.getLastRowNum() + 1);
      BeanWrapper<T> wrapper = BeanWrapper.getWrapper(data);
      int dataColumnIndex = 0;
      for (Pair cell : def.getColumn()) {
        Cell c = dataRow.createCell(dataColumnIndex++);
        c.setCellStyle(titleStyle);
        c.setCellValue(wrapper.getRequiredProperty(cell.getKey(), String.class));
      }
    });


    try {
      File tempFile = File.createTempFile(def.getFileName(), ".xlsx");
      tempFile.deleteOnExit();
      OutputStream tempout = new FileOutputStream(tempFile);
      wb.write(tempout);
      return new SystemFile(tempFile).attach(URLEncoder.encode(def.getFileName(), "UTF-8"));
    } catch (Exception ex) {
      // 补充编码
      throw new BusinessAccessException(null, ex);
    } finally {
      // 使用标准的工具类关闭，避免冗余代码
      StreamUtils.safeClose(wb);
    }
  }
}
