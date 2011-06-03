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
	 *规则：四舍五入
	 */
	public Object run(Object[] args) {
		return ROUND5.getRoundValue(args, false);
	}

	public Type getType() {
		return MATH;
	}

	public String getCN() {
		return "ROUND(number,num_digits):返回某个数字按指定位数舍入后的数字。\n" + "number:需要进行舍入的数字。\n" + "num_digits:指定的位数，按此位数进行舍入。\n"
				+ "如果 num_digits 大于 0，则舍入到指定的小数位。\n" + "如果 num_digits 等于 0，则舍入到最接近的整数。\n"
				+ "如果 num_digits 小于 0，则在小数点左侧进行舍入。\n" + "示例:\n" + "ROUND(2.15, 1) 等于 2.2\n"
				+ "ROUND(2.149, 1) 等于 2.1\n" + "ROUND(-1.475, 2) 等于 -1.48\n" + "ROUND(21.5, -1) 等于 20";
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