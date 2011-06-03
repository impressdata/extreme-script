/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


/**
 * Function.
 */
public class SUM extends MinAndMaxAndSUM {
	
	protected double init() {
		return 0;
	}
	
	protected double operation(double preview, double nextEl) {
		return preview + nextEl;
	}	public String getCN(){
		return "SUM(number1,number2,…): 求一个指定单元格区域中所有数字之和。\n"
		+"Number1,number2,…:1到30个参数或指定单元格区域中所有数字。\n"
		+"备注:\n"
		+"    函数将直接键入参数中的数值、逻辑值及文本表达式计算在内。\n"
		+"    若参数是数组或引用，则只有数组或单元格引用中的数值进行计算。\n"
		+"示例:\n"
		+"SUM(70,80)等于150。\n"
		+"SUM(\"70\",80,TRUE)等于151，逻辑值“TRUE”作为1来计算；“FALSE”作为0计算；文本“70”作为70来计算。";
	}
	public String getEN(){
		return "SUM(number1,number2,…): Adds all the numbers in a range of cells.\n"
		+"Number1, number2, ...    are 1 to 30 arguments for which you want the total value or sum.\n"
		+"\n"
		+"Re:\n"
		+"1. Numbers, logical values, and text representations of numbers that you type directly into the list of arguments are counted. See the first and second examples following. \n"
		+"2. If an argument is an array or reference, only numbers in that array or reference are counted. Empty cells, logical values, text, or error values in the array or reference are ignored. See the third example following. \n"
		+"\n"
		+"Example:\n"
		+"   SUM(70,80)=150.\n"
		+"   SUM(\"70\",80,TRUE)=151, TRUE is calculated as 1, FALSE for 0, String \"70\" fo 70.";
	}
}