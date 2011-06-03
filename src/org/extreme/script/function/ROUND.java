/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;

/**
 * Function.
 */
public class ROUND extends AbstractFunction {
	/**
	 *������������
	 */
	public Object run(Object[] args) {
		return ROUND5.getRoundValue(args, false);
	}

	public Type getType() {
		return MATH;
	}

	public String getCN() {
		return "ROUND(number,num_digits):����ĳ�����ְ�ָ��λ�����������֡�\n" + "number:��Ҫ������������֡�\n" + "num_digits:ָ����λ��������λ���������롣\n"
				+ "��� num_digits ���� 0�������뵽ָ����С��λ��\n" + "��� num_digits ���� 0�������뵽��ӽ���������\n"
				+ "��� num_digits С�� 0������С�������������롣\n" + "ʾ��:\n" + "ROUND(2.15, 1) ���� 2.2\n"
				+ "ROUND(2.149, 1) ���� 2.1\n" + "ROUND(-1.475, 2) ���� -1.48\n" + "ROUND(21.5, -1) ���� 20";
	}

	public String getEN() {
		return "ROUND(number,num_digits): Rounds a number to a specified number of digits.\n"
				+ "Number is the number you want to round.\n"
				+ "Num_digits specifies the number of digits to which you want to round number.\n"
				+ "\n"
				+ "Re:\n"
				+ "1. If num_digits is greater than 0 (zero), then number is rounded to the specified number of decimal places. \n"
				+ "2. If num_digits is 0, then number is rounded to the nearest integer. \n"
				+ "3. If num_digits is less than 0, then number is rounded to the left of the decimal point. \n" + "\n"
				+ "Example:\n" + "   ROUND(2.15, 1) = 2.2\n" + "   ROUND(2.149, 1) = 2.1\n"
				+ "   ROUND(-1.475, 2) = -1.48\n" + "   ROUND(21.5, -1) = 20";
	}
}