package org.extreme.script.function;

import java.math.BigDecimal;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Number to chinese money.
 */
public class CNMONEY extends AbstractFunction {
    // ���Ľ�λ����
    private String[] straChineseUnit = new String[]{"��", "��", "Բ", "ʰ", "��",
                                                    "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ"};
    // ���������ַ�����
    private String[] straChineseNumber = new String[]{"��", "Ҽ", "��", "��",
                                                      "��", "��", "½", "��", "��", "��"};

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
     * ��������BigDecimal���͵�����ת�������Ľ���ַ���
     *
     * @param ldCurrency BigDecimal ����ת����BigDecimal��������
     * @return String ת��������Ľ���ַ��� ���磺 MoneyToChinese("101.89")="Ҽ����ҼԪ�ƽǾ���"
     *         MoneyToChinese("100.89")="Ҽ����ƽǾ���"
     *         MoneyToChinese("100")="Ҽ��Բ��"
     */
    private String MoneyToChinese(BigDecimal ldCurrency) throws Exception {
        String strChineseCurrency = "";

        // ����λ���
        boolean bZero = true;
        // ���Ľ�λ�±�
        int ChineseUnitIndex = 0;

        if (ldCurrency == null) {
            return null;
        }

        if (ldCurrency.doubleValue() == 0) {
            return "��Բ��";
        }

        // ����С�����֣���������
        double doubMoneyNumber = Math.round(ldCurrency.doubleValue() * 100);

        // �Ƿ���
        boolean bNegative = doubMoneyNumber < 0;

        // ȡ����ֵ
        doubMoneyNumber = Math.abs(doubMoneyNumber);

        // ѭ������ת������
        while (doubMoneyNumber > 0) {
            // ���Ĵ���(��С��λ)
            if (ChineseUnitIndex == 2 && strChineseCurrency.length() == 0) {
                strChineseCurrency = strChineseCurrency + "��";
            }

            if (doubMoneyNumber % 10 > 0) {
                // ������λ�Ĵ���
                strChineseCurrency = straChineseNumber[(int) (doubMoneyNumber % 10)]
                        + straChineseUnit[ChineseUnitIndex]
                        + strChineseCurrency;
                bZero = false;
            } else {
                // ����λ�Ĵ���
                if (ChineseUnitIndex == 2) {
                    // Ԫ�Ĵ���(��λ)
                    // ����������
                    if (doubMoneyNumber > 0) {
                        strChineseCurrency = straChineseUnit[ChineseUnitIndex]
                                + strChineseCurrency;
                        bZero = true;
                    }
                } else {
                    // ������λ�Ĵ���
                    if (ChineseUnitIndex == 6 || ChineseUnitIndex == 10) {
                        // ����������
                        if (doubMoneyNumber % 10000 > 0) {
                        	// ��λ��Ϊ0���㲻��Ҫд
                            strChineseCurrency = straChineseUnit[ChineseUnitIndex]
                                    + strChineseCurrency;
                            bZero = true;
                        }
                    }
                }

                // ǰһ��λ����Ĵ���
                if (!bZero) {
                    strChineseCurrency = straChineseNumber[0]
                            + strChineseCurrency;
                }

                bZero = true;
            }

            doubMoneyNumber = Math.floor(doubMoneyNumber / 10);
            ChineseUnitIndex++;
        }

        // �����Ĵ���
        if (bNegative) {
            strChineseCurrency = "��" + strChineseCurrency;
        }

        return strChineseCurrency;
    }

    public Type getType() {
    	return TEXT;
    }
    	public String getCN(){
		return "CNMONEY(number,unit)��������Ҵ�д��\n"
		+"number:��Ҫת������ֵ�͵�����\n"
		+"unit:��λ��\"s\",\"b\",\"q\",\"w\",\"sw\",\"bw\",\"qw\",\"y\",\"sy\",\"by\",\"qy\",\"wy\"�ֱ����ʰ�������ۡ�����Ǫ�������򡱣���ʰ�򡱣������򡱣���Ǫ�򡱣����ڡ�����ʰ�ڡ��������ڡ�����Ǫ�ڡ��������ڡ���\n"
		+"��ע:\n"
		+"    ��λ����Ϊ�գ����Ϊ�գ���ֱ�ӽ�numberת��Ϊ����Ҵ�д�������Ƚ�number�뵥λ�Ľ�����ˣ�Ȼ���ٽ���˵Ľ��ת��Ϊ����Ҵ�д��\n"
		+"ʾ��:\n"
		+"CNMONEY(1200)����ҼǪ����Բ����\n"
		+"CNMONEY(12.5,\"w\")����Ҽʰ������ǪԲ����\n"
		+"CNMONEY(56.3478,\"bw\")������Ǫ½����ʰ������Ǫ�ư�Բ����\n"
		+"CNMONEY(3.4567,\"y\")����������Ǫ���½ʰ����Բ����";
	}
	public String getEN(){
		return "";
	}
}