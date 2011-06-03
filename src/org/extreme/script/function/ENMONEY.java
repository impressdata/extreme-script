package org.extreme.script.function;

import java.math.BigDecimal;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;


public class ENMONEY extends AbstractFunction {

	public Object run(Object[] args) {
		String lsValue = "";
		if (args.length < 1) {
			return Primitive.ERROR_NAME;
		}

		Object param;
		for (int i = 0; i < args.length; i++) {
			param = args[i];

			if (param instanceof BigDecimal) {
				try {
					lsValue = MoneyToEnglish((BigDecimal) param);
				} catch (Exception ex) {
					lsValue = "ERROR!";
				}
			}

			if (param instanceof Number) {
				try {
					BigDecimal ldTemp = new BigDecimal(((Number) param).doubleValue());
					lsValue = MoneyToEnglish(ldTemp);
				} catch (Exception ex) {
					lsValue = "ERROR!";
				}
			}
		}

		return lsValue;
	}

	/**
	 * ��������BigDecimal���͵�����ת����Ӣ�Ľ���ַ���
	 * 
	 * @param ldNumber
	 *            BigDecimal ����ת��������
	 * @throws Exception
	 * @return String ת�����Ӣ���ַ���
	 */
	private String MoneyToEnglish(BigDecimal ldNumber) throws Exception {
		String lsResult = "";

		int liLength, liLeft, liRight, liMid, liNum;
		BigDecimal ldTemp;
		String lsSz;
		boolean lbFlag1, lbFlag2;

		if (ldNumber == null) {
			return null;
		}

		lsSz = ldNumber.toString().trim();

		liLength = lsSz.length();
		liMid = lsSz.indexOf(".");
		if (liMid > 0) {
			liLeft = liLength - liMid - 1;
			liRight = liLength - liLeft - 1;
		} else {
			liLeft = liLength;
			liRight = 0;
		}

		ldTemp = ldNumber;
		liNum = ldTemp.divide(new BigDecimal(100000000), 0, BigDecimal.ROUND_DOWN).intValue();
		if (liNum > 9) {
			// �������
			for (int i = 1; i <= liLeft; i++) {
				lsResult = lsResult + "*";
			}
			lsResult = lsResult + ".";
			for (int i = 1; i <= liRight; i++) {
				lsResult = lsResult + "*";
			}
		} else {
			// ����������� XXX,000,000.00
			if (liNum > 0) {
				lsResult += FunctionHelper.NumberToEnglish(new BigDecimal(liNum)) + " HUNDRED ";
				lbFlag1 = true;
			} else {
				lbFlag1 = false;
			}

			ldTemp = ldTemp.add((new BigDecimal(liNum * 100000000)).negate());
			liNum = ldTemp.divide(new BigDecimal(1000000), 0, BigDecimal.ROUND_DOWN).intValue();
			if (liNum > 0) {
				lsResult += FunctionHelper.NumberToEnglish(new BigDecimal(liNum)) + " ";
				lbFlag2 = true;
			} else {
				lbFlag2 = false;
			}

			if (lbFlag1 || lbFlag2) {
				lsResult += "MILLION ";
			}

			// ����ǧ������ 100,XXX,000.00
			ldTemp = ldTemp.add((new BigDecimal(liNum * 1000000)).negate());
			liNum = ldTemp.divide(new BigDecimal(100000), 0, BigDecimal.ROUND_DOWN).intValue();
			if (liNum > 0) {
				lsResult += FunctionHelper.NumberToEnglish(new BigDecimal(liNum)) + " HUNDRED ";
				lbFlag1 = true;
			} else {
				lbFlag1 = false;
			}

			ldTemp = ldTemp.add((new BigDecimal(liNum * 100000)).negate());
			liNum = ldTemp.divide(new BigDecimal(1000), 0, BigDecimal.ROUND_DOWN).intValue();
			if (liNum > 0) {
				lsResult += FunctionHelper.NumberToEnglish(new BigDecimal(liNum)) + " ";
				lbFlag2 = true;
			} else {
				lbFlag2 = false;
			}

			if (lbFlag1 || lbFlag2) {
				lsResult += "THOUSAND ";
			}

			// �����λ�� 100,000,XXX.00
			ldTemp = ldTemp.add((new BigDecimal(liNum * 1000)).negate());
			liNum = ldTemp.divide(new BigDecimal(100), 0, BigDecimal.ROUND_DOWN).intValue();
			if (liNum > 0) {
				lsResult += FunctionHelper.NumberToEnglish(new BigDecimal(liNum)) + " HUNDRED ";
			}

			ldTemp = ldTemp.add((new BigDecimal(liNum * 100)).negate());
			liNum = ldTemp.divide(new BigDecimal(1), 0, BigDecimal.ROUND_DOWN).intValue();
			if (liNum > 0) {
				lsResult += FunctionHelper.NumberToEnglish(new BigDecimal(liNum));
			}

			// �ж��Ƿ�Ϊ0.XX������
			if (lsResult.trim().length() == 0) {
				lsResult = "ZERO";
			}

			// ����С���������
			ldTemp = ldTemp.add((new BigDecimal(liNum)).negate());
			if (ldTemp.compareTo(new BigDecimal(0)) > 0) {
				lsResult = lsResult + " AND CENTS ";
				ldTemp = ldTemp.multiply(new BigDecimal(100)).divide(new BigDecimal(1), 0, BigDecimal.ROUND_HALF_UP);
				lsResult += FunctionHelper.NumberToEnglish(ldTemp);
			}
		}

		return lsResult;
	}

	public Type getType() {
		return TEXT;
	}
	public String getCN(){
		return "ENMONEY(value):��������BigDemical���͵�����ת����Ӣ�Ľ���ַ�����\n"
		+"ʾ����\n"
		+"ENMONEY(23.49)����TWENTY��";
	}
	public String getEN(){
		return "";
	}
}
