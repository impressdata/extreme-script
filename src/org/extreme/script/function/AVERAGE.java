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
		return "AVERAGE(number1,number2,��): ����ָ�����ݵ�ƽ��ֵ��\n"
		+"Number1,number2��:���ڼ���ƽ��ֵ�Ĳ�����\n"
		+"��ע:\n"
		+"    �������������֣����Ǻ������ֵ����ƣ���������á�\n"
		+"    �����������ò����к������֣��߼�ֵ����հ׵�Ԫ����Щֵ�������ԣ����ǣ���Ԫ���е���ֵ�������㡣\n"
		+"ʾ��:\n"
		+"���A1:A6������Ϊ��ages�����ֱ����10��23��14��24��33��25����:\n"
		+"AVERAGE(A1:A6)����21.5��\n"
		+"AVERAGE(ages)����21.5��\n"
		+"�������һ������Ϊ18�ģ������������ƽ��ֵΪ: AVERAGE(A1:A6,18)����21��";
	}
	public String getEN(){
		return "AVERAGE(number1,number2,��): Returns the average (arithmetic mean) of the arguments.\n"
		+"Number1, number2, ...  are 1 to 30 numeric arguments for which you want the average.\n"
		+"\n"
		+"Re:\n"
		+"1. The arguments must either be numbers or be names, arrays, or references that contain numbers. \n"
		+"2. If an array or reference argument contains text, logical values, or empty cells, those values are ignored; however, cells with the value zero are included. \n"
		+"\n"
		+"Example:\n"
		+"���A1:A6������Ϊ��ages�����ֱ����10��23��14��24��33��25����:\n"
		+"AVERAGE(A1:A6)����21.5��\n"
		+"AVERAGE(ages)����21.5��\n"
		+"�������һ������Ϊ18�ģ������������ƽ��ֵΪ: AVERAGE(A1:A6,18)����21��";
	}
}