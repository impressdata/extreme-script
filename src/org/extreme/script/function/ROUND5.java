/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.math.BigDecimal;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class ROUND5 extends AbstractFunction {
	/**
	 * ��������������������ƣ��͵���5������ˣ�1~4�ᣬ4~6����5��ǰһλ�������ż���Ͳ���λ������������ͽ�λ��
	 */
	public Object run(Object[] args) {
		return getRoundValue(args, true);
	}

	public static Object getRoundValue(Object[] args, boolean isRound5) {

		if (args.length < 2) {
			return Primitive.ERROR_NAME;
		}
		if (!(args[0] instanceof Number) || !(args[1] instanceof Number)) {
			return Primitive.ERROR_NAME;
		}

		boolean isNegative = false;
		double roundNumber = ((Number) args[0]).doubleValue();
		int precision = ((Number) args[1]).intValue();
		if (roundNumber < 0.00D) {
			roundNumber = -roundNumber;
			isNegative = true;
		}
		Object rt;
		if (precision > 0) {
			// �������� С������eg:(5555.555,2) �� 5555.56
			rt = decimalNum(roundNumber, precision, isRound5);
		} else {
			// �������� ��������eg:(5555.555,-2) �� 5600
			rt = integerNum(roundNumber, precision, isRound5);
		}
		if (rt instanceof Double && isNegative) {
			double tmp = ((Double) rt).doubleValue();
			rt = new Double(-tmp);
		}
		return rt;
	}

	public static Object decimalNum(double v, int scale, boolean isRound5) {

		// ԭ���Ƚ�����������������㣬Ȼ���ж�ԭ�������һλ�Ƿ�Ϊ5�������5���жϵ����ڶ�λ����ż
		// eg:0.125 �� 0.13 �� ԭ�������һλ��5 �� �����ڶ�λ��ż�� �� 0.12
		BigDecimal b = new BigDecimal(Utils.doubleToString(v));
		BigDecimal one = new BigDecimal("1");

		String oldNum = Utils.doubleToString(v);
		double returnValue = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		String newNum = Utils.doubleToString(returnValue);
		int lastChar = Integer.parseInt(newNum.substring(newNum.length() - 1));

		// ��Ҫ������������һλ !=5 �� =9 ������ֱ�ӷ��ؽ����eg��0.1295 �� 0.13
		if (!isRound5
				|| (Integer.parseInt(oldNum.substring(oldNum.indexOf('.') + scale + 1, oldNum.indexOf('.') + scale + 2)) != 5)
				|| (Integer.parseInt(oldNum.substring(oldNum.indexOf('.') + scale, oldNum.indexOf('.') + scale + 1)) == 9)) {
			return new Double(returnValue);
		} else {
			if (!(lastChar % 2 == 0)) {
				String getLastChar = String.valueOf(lastChar - 1);
				newNum = newNum.substring(0, newNum.length() - 1) + getLastChar;
				returnValue = Double.parseDouble(newNum);
			}
		}
		return new Double(returnValue);
	}

	public static Object integerNum(double v, int scale, boolean isRound5) {

		// ԭ��������ת����С�����㣬eg��5555 �� 55.55 �� 55.0 �� 5600
		scale = -scale;
		double num = v / (Math.pow(10, scale + 1));
		String halfUp = Utils.objectToString((decimalNum(num, 1, isRound5)));
		double returnValue = Double.parseDouble(halfUp) * (Math.pow(10, scale + 1));

		// ȥ��С���㲿�֣�eg:55525555.55555555�ᱻ�����55520000.00000001
		if (Utils.doubleToString(returnValue).indexOf('.') != -1) {
			int removeDecimal = (int) returnValue;
			String getLastNum = String.valueOf(removeDecimal);

			// eg:55555525.55555555�ᱻ�����55555519.99999999����˸���+1
			if (getLastNum.endsWith("9")) {
				removeDecimal = removeDecimal + 1;
			}
			return new Integer(removeDecimal);
		}

		return new Double(returnValue);
	}

	public Type getType() {
		return MATH;
	}

	public String getCN() {
		return "ROUND5(number,num_digits):������������룬���ż������\n" + "number:��Ҫ������������֡�\n" + "num_digits:ָ����λ��������λ���������롣\n"
				+ "��� num_digits ���� 0�������뵽ָ����С��λ��\n" + "��� num_digits ���� 0�������뵽��ӽ���������\n"
				+ "��� num_digits С�� 0������С�������������롣\n" + "ʾ��:\n" + "ROUND5(2.125, 2) ���� 2.12\n"
				+ "ROUND5(2.135, 2) ���� 2.14\n";
	}

	public String getEN() {
		return "ROUND5(number,num_digits): Rounds a number to a specified number of digits.\n"
				+ "Number is the number you want to round.\n"
				+ "Num_digits specifies the number of digits to which you want to round number.\n"
				+ "\n"
				+ "Re:\n"
				+ "1. If num_digits is greater than 0 (zero), then number is rounded to the specified number of decimal places. \n"
				+ "2. If num_digits is 0, then number is rounded to the nearest integer. \n"
				+ "3. If num_digits is less than 0, then number is rounded to the left of the decimal point. \n" + "\n"
				+ "Example:\n" + "   ROUND5(2.125, 2) = 2.12\n" + "   ROUND5(2.135, 2) = 2.14\n";
	}
}
