package io.jalorx.export.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;

public interface WorkCellStyle {
  short ALIGN_GENERAL = 0;
  short ALIGN_LEFT = 1;
  short ALIGN_CENTER = 2;
  short ALIGN_RIGHT = 3;
  short ALIGN_FILL = 4;
  short ALIGN_JUSTIFY = 5;
  short ALIGN_CENTER_SELECTION = 6;
  short VERTICAL_TOP = 0;
  short VERTICAL_CENTER = 1;
  short VERTICAL_BOTTOM = 2;
  short VERTICAL_JUSTIFY = 3;
  short BORDER_NONE = 0;
  short BORDER_THIN = 1;
  short BORDER_MEDIUM = 2;
  short BORDER_DASHED = 3;
  short BORDER_HAIR = 7;
  short BORDER_THICK = 5;
  short BORDER_DOUBLE = 6;
  short BORDER_DOTTED = 4;
  short BORDER_MEDIUM_DASHED = 8;
  short BORDER_DASH_DOT = 9;
  short BORDER_MEDIUM_DASH_DOT = 10;
  short BORDER_DASH_DOT_DOT = 11;
  short BORDER_MEDIUM_DASH_DOT_DOT = 12;
  short BORDER_SLANTED_DASH_DOT = 13;
  short NO_FILL = 0;
  short SOLID_FOREGROUND = 1;
  short FINE_DOTS = 2;
  short ALT_BARS = 3;
  short SPARSE_DOTS = 4;
  short THICK_HORZ_BANDS = 5;
  short THICK_VERT_BANDS = 6;
  short THICK_BACKWARD_DIAG = 7;
  short THICK_FORWARD_DIAG = 8;
  short BIG_SPOTS = 9;
  short BRICKS = 10;
  short THIN_HORZ_BANDS = 11;
  short THIN_VERT_BANDS = 12;
  short THIN_BACKWARD_DIAG = 13;
  short THIN_FORWARD_DIAG = 14;
  short SQUARES = 15;
  short DIAMONDS = 16;
  short LESS_DOTS = 17;
  short LEAST_DOTS = 18;

  short getIndex();

  void setDataFormat(short var1);

  short getDataFormat();

  String getDataFormatString();

  void setFont(Font var1);

  short getFontIndex();

  void setHidden(boolean var1);

  boolean getHidden();

  void setLocked(boolean var1);

  boolean getLocked();

  void setAlignment(short var1);

  short getAlignment();

  void setWrapText(boolean var1);

  boolean getWrapText();

  void setVerticalAlignment(short var1);

  short getVerticalAlignment();

  void setRotation(short var1);

  short getRotation();

  void setIndention(short var1);

  short getIndention();

  void setBorderLeft(short var1);

  short getBorderLeft();

  void setBorderRight(short var1);

  short getBorderRight();

  void setBorderTop(short var1);

  short getBorderTop();

  void setBorderBottom(short var1);

  short getBorderBottom();

  void setLeftBorderColor(short var1);

  short getLeftBorderColor();

  void setRightBorderColor(short var1);

  short getRightBorderColor();

  void setTopBorderColor(short var1);

  short getTopBorderColor();

  void setBottomBorderColor(short var1);

  short getBottomBorderColor();

  void setFillPattern(short var1);

  short getFillPattern();

  void setFillBackgroundColor(short var1);

  short getFillBackgroundColor();

  Color getFillBackgroundColorColor();

  void setFillForegroundColor(short var1);

  short getFillForegroundColor();

  Color getFillForegroundColorColor();

  void cloneStyleFrom(CellStyle var1);

  void setShrinkToFit(boolean var1);

  boolean getShrinkToFit();
}
