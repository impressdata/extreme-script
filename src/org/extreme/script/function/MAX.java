/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


/**
 * Function.
 */
public class MAX extends MinAndMaxAndSUM {
	
	protected double init() {
		return -Double.MAX_VALUE;
	}
	
	protected double operation(double d1, double d2) {
		return Math.max(d1, d2);
	}
		public String getCN(){
		return "MAX(number1,number2,…): 返回参数列表中的最大值。\n"
		+"Number1,number2,…:1到30个需要找出最大值的参数。\n"
		+"备注:\n"
		+"    参数可以是数字、空白单元格、逻辑值或数字的文本表达式。\n"
		+"    如果数组或引用参数中包含可解析文本值，逻辑值，零值或空白单元格，这些值都将参与计算，而不可解析的文本值忽略不计。\n"
		+"    如果参数中没有任何数字，MAX将返回0。\n"
		+"示例:\n"
		+"MAX(0.1,0,1.2)等于1.2。";
	}
	public String getEN(){
		return "MAX(number1,number2,…): Returns the largest value in a set of values.\n"
		+"Number1,number2,…  are 1 to 30 numbers for which you want to find the maximum value.\n"
		+"Re:\n"
		+"1. You can specify arguments that are numbers, empty cells, logical values, or text representations of numbers.\n"
		+"2. If an argument is an array or reference, only numbers in that array or reference are used. Empty cells, logical values, or text in the array or reference are ignored.\n"
		+"3. If the arguments contain no numbers, MAX returns 0 (zero).\n"
		+"Example:\n"
		+"   MAX(0.1,0,1.2)=1.2";
	}
}