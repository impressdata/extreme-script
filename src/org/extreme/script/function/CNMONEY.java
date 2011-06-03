package org.extreme.script.function;

import java.math.BigDecimal;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Number to chinese money.
 */
public class CNMONEY extends AbstractFunction {
    // 中文金额单位数组
    private String[] straChineseUnit = new String[]{"分", "角", "圆", "拾", "佰",
                                                    "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
    // 中文数字字符数组
    private String[] straChineseNumber = new String[]{"零", "壹", "贰", "叁",
                                                      "肆", "伍", "陆", "柒", "捌", "玖"};

    public Object run(Object[] args) {
    	String lsValue = "";
    	int size = args.length;
    	
    	if(size ==1) {
    		Object param = args[0];
    		
    		if (param instanceof String){
    			try {
    				lsValue = MoneyToChinese(new BigDecimal( (String)param ));
    			} catch (Exception ex) {
    				lsValue = Primitive.ERROR_VALUE.toString();
    			}
    		} else if (param instanceof BigDecimal) {
    			try {
    				lsValue = MoneyToChinese((BigDecimal) param);
    			} catch(Exception ex) {
    				lsValue = Primitive.ERROR_VALUE.toString();
    			}
    		} else if (param instanceof Number) {
    			try {
    				BigDecimal ldTemp = new BigDecimal(((Number) param).doubleValue());
    				lsValue = MoneyToChinese(ldTemp);
    			} catch(Exception ex) {
    				lsValue = Primitive.ERROR_VALUE.toString();
    			}
    		} else {
    			lsValue = Primitive.ERROR_VALUE.toString();
    		}
    	} else if(size == 2) {
    		Object firstParam = args[0];
    		Object unit = args[1];
    		if (firstParam instanceof Number) {
    			BigDecimal ldTemp = new BigDecimal(((Number) firstParam).doubleValue());
    			
    			String unitStr = unit.toString();
    			if(unitStr.equalsIgnoreCase("s")) {
    				ldTemp = ldTemp.multiply(new BigDecimal(10));
    			} else if(unitStr.equalsIgnoreCase("b")) {
    				ldTemp = ldTemp.multiply(new BigDecimal(100));
    			} else if(unitStr.equalsIgnoreCase("q")) {
    				ldTemp = ldTemp.multiply(new BigDecimal(1000));
    			} else if(unitStr.equalsIgnoreCase("w")) {
    				ldTemp = ldTemp.multiply(new BigDecimal(10000));
    			} else if(unitStr.equalsIgnoreCase("sw")) {
    				ldTemp = ldTemp.multiply(new BigDecimal(100000));
    			} else if(unitStr.equalsIgnoreCase("bw")) {
    				ldTemp = ldTemp.multiply(new BigDecimal(1000000));
    			} else if(unitStr.equalsIgnoreCase("qw")) {
    				ldTemp = ldTemp.multiply(new BigDecimal(10000000));
    			} else if(unitStr.equalsIgnoreCase("y")) {
    				ldTemp = ldTemp.multiply(new BigDecimal(100000000));
    			} else if(unitStr.equalsIgnoreCase("sy")) {
    				ldTemp = ldTemp.multiply(new BigDecimal(1000000000));
    			} else if(unitStr.equalsIgnoreCase("by")) {
    				ldTemp = ldTemp.multiply(new BigDecimal("10000000000"));
    			} else if(unitStr.equalsIgnoreCase("qy")) {
    				ldTemp = ldTemp.multiply(new BigDecimal("100000000000"));
    			} else if(unitStr.equalsIgnoreCase("wy")) {
    				ldTemp = ldTemp.multiply(new BigDecimal("1000000000000"));
    			}
    			
    			try {
    				lsValue = MoneyToChinese(ldTemp);
    			} catch(Exception ex) {
    				lsValue = Primitive.ERROR_VALUE.toString();
    			}
    		} else {
    			lsValue = Primitive.ERROR_VALUE.toString();
    		}
    	} else {
    		lsValue = Primitive.ERROR_VALUE.toString();
    	}
 
        return lsValue;
    }

    /**
     * 将给定的BigDecimal类型的数字转换成中文金额字符串
     *
     * @param ldCurrency BigDecimal 所需转换的BigDecimal类型数字
     * @return String 转换后的中文金额字符串 例如： MoneyToChinese("101.89")="壹佰零壹元捌角玖分"
     *         MoneyToChinese("100.89")="壹佰零捌角玖分"
     *         MoneyToChinese("100")="壹佰圆整"
     */
    private String MoneyToChinese(BigDecimal ldCurrency) throws Exception {
        String strChineseCurrency = "";

        // 零数位标记
        boolean bZero = true;
        // 中文金额单位下标
        int ChineseUnitIndex = 0;

        if (ldCurrency == null) {
            return null;
        }

        if (ldCurrency.doubleValue() == 0) {
            return "零圆整";
        }

        // 处理小数部分，四舍五入
        double doubMoneyNumber = Math.round(ldCurrency.doubleValue() * 100);

        // 是否负数
        boolean bNegative = doubMoneyNumber < 0;

        // 取绝对值
        doubMoneyNumber = Math.abs(doubMoneyNumber);

        // 循环处理转换操作
        while (doubMoneyNumber > 0) {
            // 整的处理(无小数位)
            if (ChineseUnitIndex == 2 && strChineseCurrency.length() == 0) {
                strChineseCurrency = strChineseCurrency + "整";
            }

            if (doubMoneyNumber % 10 > 0) {
                // 非零数位的处理
                strChineseCurrency = straChineseNumber[(int) (doubMoneyNumber % 10)]
                        + straChineseUnit[ChineseUnitIndex]
                        + strChineseCurrency;
                bZero = false;
            } else {
                // 零数位的处理
                if (ChineseUnitIndex == 2) {
                    // 元的处理(个位)
                    // 段中有数字
                    if (doubMoneyNumber > 0) {
                        strChineseCurrency = straChineseUnit[ChineseUnitIndex]
                                + strChineseCurrency;
                        bZero = true;
                    }
                } else {
                    // 万、亿数位的处理
                    if (ChineseUnitIndex == 6 || ChineseUnitIndex == 10) {
                        // 段中有数字
                        if (doubMoneyNumber % 10000 > 0) {
                        	// 这位上为0，零不需要写
                            strChineseCurrency = straChineseUnit[ChineseUnitIndex]
                                    + strChineseCurrency;
                            bZero = true;
                        }
                    }
                }

                // 前一数位非零的处理
                if (!bZero) {
                    strChineseCurrency = straChineseNumber[0]
                            + strChineseCurrency;
                }

                bZero = true;
            }

            doubMoneyNumber = Math.floor(doubMoneyNumber / 10);
            ChineseUnitIndex++;
        }

        // 负数的处理
        if (bNegative) {
            strChineseCurrency = "负" + strChineseCurrency;
        }

        return strChineseCurrency;
    }

    public Type getType() {
    	return TEXT;
    }
    	public String getCN(){
		return "CNMONEY(number,unit)返回人民币大写。\n"
		+"number:需要转换的数值型的数。\n"
		+"unit:单位，\"s\",\"b\",\"q\",\"w\",\"sw\",\"bw\",\"qw\",\"y\",\"sy\",\"by\",\"qy\",\"wy\"分别代表“拾”，“佰”，“仟”，“万”，“拾万”，“佰万”，“仟万”，“亿”，“拾亿”，“佰亿”，“仟亿”，“万亿”。\n"
		+"备注:\n"
		+"    单位可以为空，如果为空，则直接将number转换为人民币大写，否则先将number与单位的进制相乘，然后再将相乘的结果转换为人民币大写。\n"
		+"示例:\n"
		+"CNMONEY(1200)等于壹仟贰佰圆整。\n"
		+"CNMONEY(12.5,\"w\")等于壹拾贰万伍仟圆整。\n"
		+"CNMONEY(56.3478,\"bw\")等于伍仟陆佰叁拾肆万柒仟捌佰圆整。\n"
		+"CNMONEY(3.4567,\"y\")等于叁亿肆仟伍佰陆拾柒万圆整。";
	}
	public String getEN(){
		return "";
	}
}