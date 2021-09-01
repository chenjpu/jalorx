package io.jalorx.export.utils;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class ExcelUtils {
	short ALIGN_GENERAL              = 0;
	short ALIGN_LEFT                 = 1;
	short ALIGN_CENTER               = 2;
	short ALIGN_RIGHT                = 3;
	short ALIGN_FILL                 = 4;
	short ALIGN_JUSTIFY              = 5;
	short ALIGN_CENTER_SELECTION     = 6;
	short VERTICAL_TOP               = 0;
	short VERTICAL_CENTER            = 1;
	short VERTICAL_BOTTOM            = 2;
	short VERTICAL_JUSTIFY           = 3;
	short BORDER_NONE                = 0;
	short BORDER_THIN                = 1;
	short BORDER_MEDIUM              = 2;
	short BORDER_DASHED              = 3;
	short BORDER_HAIR                = 7;
	short BORDER_THICK               = 5;
	short BORDER_DOUBLE              = 6;
	short BORDER_DOTTED              = 4;
	short BORDER_MEDIUM_DASHED       = 8;
	short BORDER_DASH_DOT            = 9;
	short BORDER_MEDIUM_DASH_DOT     = 10;
	short BORDER_DASH_DOT_DOT        = 11;
	short BORDER_MEDIUM_DASH_DOT_DOT = 12;
	short BORDER_SLANTED_DASH_DOT    = 13;
	short NO_FILL                    = 0;
	short SOLID_FOREGROUND           = 1;
	short FINE_DOTS                  = 2;
	short ALT_BARS                   = 3;
	short SPARSE_DOTS                = 4;
	short THICK_HORZ_BANDS           = 5;
	short THICK_VERT_BANDS           = 6;
	short THICK_BACKWARD_DIAG        = 7;
	short THICK_FORWARD_DIAG         = 8;
	short BIG_SPOTS                  = 9;
	short BRICKS                     = 10;
	short THIN_HORZ_BANDS            = 11;
	short THIN_VERT_BANDS            = 12;
	short THIN_BACKWARD_DIAG         = 13;
	short THIN_FORWARD_DIAG          = 14;
	short SQUARES                    = 15;
	short DIAMONDS                   = 16;
	short LESS_DOTS                  = 17;
	short LEAST_DOTS                 = 18;

	public static XSSFCellStyle xSSFAddStyle(XSSFCellStyle style) {
		style.setAlignment(HorizontalAlignment.forInt(WorkCellStyle.ALIGN_CENTER));// 水平居中
		style.setVerticalAlignment(VerticalAlignment.forInt(WorkCellStyle.VERTICAL_CENTER));// c垂直居中
		// 添加边框
		style.setBorderBottom(BorderStyle.valueOf(WorkCellStyle.BORDER_THIN));
		style.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0), new DefaultIndexedColorMap()));
		style.setBorderLeft(BorderStyle.valueOf(WorkCellStyle.BORDER_THIN));
		style.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0), new DefaultIndexedColorMap()));
		style.setBorderRight(BorderStyle.valueOf(WorkCellStyle.BORDER_THIN));
		style.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0), new DefaultIndexedColorMap()));
		style.setBorderTop(BorderStyle.valueOf(WorkCellStyle.BORDER_THIN));
		style.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0), new DefaultIndexedColorMap()));
		return style;
	}

	public static CellStyle _creatCellStyle(Workbook wb) {
		CellStyle cs = wb.createCellStyle();
		cs.setBorderBottom(BorderStyle.valueOf(WorkCellStyle.BORDER_THIN));
		short c = IndexedColors.BLACK.getIndex();
		cs.setBottomBorderColor(c);
		cs.setBorderLeft(BorderStyle.valueOf(WorkCellStyle.BORDER_THIN));
		cs.setLeftBorderColor(c);
		cs.setBorderRight(BorderStyle.valueOf(WorkCellStyle.BORDER_THIN));
		cs.setRightBorderColor(c);
		cs.setBorderTop(BorderStyle.valueOf(WorkCellStyle.BORDER_THIN));
		cs.setTopBorderColor(c);
		cs.setAlignment(HorizontalAlignment.forInt(WorkCellStyle.ALIGN_CENTER));
		return cs;
	}

}
