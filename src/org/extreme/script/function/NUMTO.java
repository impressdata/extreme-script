package org.extreme.script.function;

import org.extreme.commons.util.StringUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * natural number converts to Chinese.
 * @author richer
 *
 */
public class NUMTO extends AbstractFunction{
	// 中文数字字符数组
    final static String[] CHINESE_NUMBER = new String[]{"零", "一", "二", "三", "四",
    													"五", "六", "七", "八", "九"};
    final static String[] CHINESE_NUMBER_LEVEL_STR = new String[]{"", "十", "百", "千", "万", "亿"};
    final static long[] CHINESE_NUMBER_LEVEL = new long[]{1, 10, 100, 1000, 10000, (long)1E8};
    
	public Object run(Object[] args) {
		 if (args.length < 1) {
	            return Primitive.ERROR_NAME;
	     }
		 Object naturalNumber = args[0];
		 Object isNatural;
		 if (args.length > 1) isNatural = args[1];
		 else isNatural = Boolean.FALSE;
		 String isValue = "";
	    	 if (naturalNumber instanceof Number && ((Number)naturalNumber).longValue() >= 0 && isNatural instanceof Boolean) {
	    		 try {
	    				isValue = numberToChinese(((Number)naturalNumber).longValue(), ((Boolean)isNatural).booleanValue());
	    			} catch (Exception ex) {
	    				isValue = Primitive.ERROR_VALUE.toString();
	    			}
	    		 
	    	 } else {
	    		 return Primitive.ERROR_NAME;
	    	 }
	     		 
	  return isValue;
		
	}
		
	private String numberToChinese(long number, boolean isNatural) throws Exception {
		String strChineseCurrency = StringUtils.EMPTY;
		if (isNatural){
			while(number > 0) {
				strChineseCurrency = CHINESE_NUMBER[(int)number % 10] + strChineseCurrency;			                                       
				number = number / 10;
			}
			return strChineseCurrency;
		}else return this.standardNumberToChinese(number, strChineseCurrency);
	}

	 private String standardNumberToChinese(long number, String strChineseCurrency) throws Exception {
		for (int i = 5; i >=4; i--){
			if (number / CHINESE_NUMBER_LEVEL[i] > 0){
				if (i != 5) 
					strChineseCurrency += this.thousandToChinese(number / CHINESE_NUMBER_LEVEL[i], strChineseCurrency) + CHINESE_NUMBER_LEVEL_STR[i];
				else 
					strChineseCurrency = this.standardNumberToChinese(number/ CHINESE_NUMBER_LEVEL[i], strChineseCurrency) + CHINESE_NUMBER_LEVEL_STR[i];
			} else if (strChineseCurrency.length() > 0 && !strChineseCurrency.endsWith(CHINESE_NUMBER[0]) && number % CHINESE_NUMBER_LEVEL[i] > 0)
				strChineseCurrency += CHINESE_NUMBER[0];
			number %= CHINESE_NUMBER_LEVEL[i];
		}
		return strChineseCurrency += this.thousandToChinese(number, strChineseCurrency);		 
	 }	
	 
	 private String thousandToChinese(long number, String strChineseFormer) throws Exception {
		 String strChineseCurrency = StringUtils.EMPTY;
		 if (number == 10 && strChineseFormer.length() == 0) {
			 return CHINESE_NUMBER_LEVEL_STR[1];
		 }else if (number > 10 && number < 20 && strChineseFormer.length() == 0) {
			 return CHINESE_NUMBER_LEVEL_STR[1] + CHINESE_NUMBER[(int)number % 10];
		 }
		 for (int i = 3; i >= 0; i--){
			if (number / CHINESE_NUMBER_LEVEL[i] > 0)
				strChineseCurrency += CHINESE_NUMBER[(int) (number / CHINESE_NUMBER_LEVEL[i])] + CHINESE_NUMBER_LEVEL_STR[i];
			else if (strChineseCurrency.length() > 0 && !strChineseCurrency.endsWith(NUMTO.CHINESE_NUMBER[0]) && number % CHINESE_NUMBER_LEVEL[i] > 0)
				strChineseCurrency += CHINESE_NUMBER[0];
			number %= CHINESE_NUMBER_LEVEL[i];
		 }
		 return strChineseCurrency;
	 }

		public Type getType() {
			return TEXT;
		}
	public String getCN(){
		return "NUMTO(number,bool)或NUMTO(number):返回number的中文表示。其中bool用于选择中文表示的方式，当没有bool时采用默认方式显示。\n"
		+"示例：NUMTO(2345,true)等于二三四五。\n"
		+"示例：NUMTO(2345,false)等于二千三百四十五。\n"
		+"示例：NUMTO(2345)等于二千三百四十五。";
	}
	public String getEN(){
		return "";
	}
}