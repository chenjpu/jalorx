package io.jalorx.export.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {

  /**
   * @param str
   * @return boolean   true 表示不为空  false 表示为空
   * */
  public static boolean checkStr(String str) {
    if (null != str && str.trim().length() > 0) return true;
    return false;
  }
  
  /**
   * @param str 需要格式化的字符串
   * @param STR_FORMAT 字符串格式如：000  如str=1 则格式化后字符串为001
   * @return 返回格式化后的字符串
   */
  public static String formatString(String str,String STR_FORMAT){  
	    Double intHao = Double.parseDouble(str);  
	    DecimalFormat df = new DecimalFormat(STR_FORMAT);  
	    return df.format(intHao);  
	} 

  /*  
	  * 判断是否为浮点数，包括double和float  
	  * @param str 传入的字符串  
	  * @return 是浮点数返回true,否则返回false  
	*/  
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
     * 生成N位随机数
     */
    
    public static String generateRanomNum(int N){
        String num = "";
        for (int i = 0; i< N; i++) {
            Integer val = Integer.valueOf((int) (Math.random() * 10));
            if (i == 0 && val == 0) {
                i--;
                continue;
            }
            num += val;
        }
        return num;
    }
	
}
