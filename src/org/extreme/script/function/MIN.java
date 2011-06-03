/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


/**
 * Function.
 */
public class MIN extends MinAndMaxAndSUM {
	
	protected double init() {
		return Double.MAX_VALUE;
	}
	
	protected double operation(double d1, double d2) {
		return Math.min(d1, d2);
	}
		public String getCN(){
		return "MIN(number1,number2,…): 返回参数列表中的最小值。\n"
		+"Number1,number2,…:1到30个需要找出最小值的参数。\n"
		+"备注:\n"
		+"    若参数中没有数字，函数MIN将返回0。\n"
		+"    参数应为数字、空白单元格、逻辑值或是表示数值的文本串。如果参数是错误值时，MIN将返回错误信息。\n"
		+"    如果数组或引用参数中包含可解析文本值，逻辑值，零值或空白单元格，这些值都将参与计算，而不可解析的文本值忽略不计。\n"
		+"示例:\n"
		+"如果B1:B4包含3，6，9，12，则:\n"
		+"MIN(B1:B4)等于3。\n"
		+"MIN(B1:B4,0)等于0。";
	}
	public String getEN(){
		return "MIN(number1,number2,…): Returns the smallest number in a set of values.\n"
		+"Number1,number2,… are 1 to 30 numbers for which you want to find the minimum value.\n"
		+"\n"
		+"Re:\n"
		+"1. If the arguments contain no numbers, MIN returns 0. \n"
		+"2. You can specify arguments that are numbers, empty cells, logical values, or text representations of numbers. Arguments that are error values or text that cannot be translated into numbers cause errors. \n"
		+"3. If an argument is an array or reference, only numbers in that array or reference are used. Empty cells, logical values, or text in the array or reference are ignored. \n"
		+"\n"
		+"Example:\n"
		+"   If B1:B4 are 3, 6, 9, 12 then MIN(B1:B4)=3, MIN(B1:B4,0)=0.";
	}
}