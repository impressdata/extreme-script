/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Function;
import org.extreme.script.FunctionHelper;


/**
 * AVERAGE Function.
 */
public class AVERAGE extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	double sum = Utils.objectToNumber(new SUM().run(args), false).doubleValue();
    	int c = Utils.objectToNumber(new COUNT().run(args), false).intValue();

        return FunctionHelper.asNumber(sum / c);
    }
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "AVERAGE(number1,number2,…): 返回指定数据的平均值。\n"
		+"Number1,number2…:用于计算平均值的参数。\n"
		+"备注:\n"
		+"    参数必须是数字，或是含有数字的名称，数组或引用。\n"
		+"    如果数组或引用参数中含有文字，逻辑值，或空白单元格，这些值将被忽略；但是，单元格中的零值则参与计算。\n"
		+"示例:\n"
		+"如果A1:A6被命名为“ages”，分别等于10，23，14，24，33及25，则:\n"
		+"AVERAGE(A1:A6)等于21.5。\n"
		+"AVERAGE(ages)等于21.5。\n"
		+"如果还有一个年龄为18的，求所有年龄的平均值为: AVERAGE(A1:A6,18)等于21。";
	}
	public String getEN(){
		return "AVERAGE(number1,number2,…): Returns the average (arithmetic mean) of the arguments.\n"
		+"Number1, number2, ...  are 1 to 30 numeric arguments for which you want the average.\n"
		+"\n"
		+"Re:\n"
		+"1. The arguments must either be numbers or be names, arrays, or references that contain numbers. \n"
		+"2. If an array or reference argument contains text, logical values, or empty cells, those values are ignored; however, cells with the value zero are included. \n"
		+"\n"
		+"Example:\n"
		+"如果A1:A6被命名为“ages”，分别等于10，23，14，24，33及25，则:\n"
		+"AVERAGE(A1:A6)等于21.5。\n"
		+"AVERAGE(ages)等于21.5。\n"
		+"如果还有一个年龄为18的，求所有年龄的平均值为: AVERAGE(A1:A6,18)等于21。";
	}
}