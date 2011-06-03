/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;

/**
 * Function.
 */
public class ROUNDDOWN extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	return FunctionHelper.roundUpOrDown(args, false);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "ROUNDDOWN(number,num_digits):������ֵ�����£�����ֵ��С�ķ����������֡�\n"
		+"number:Ϊ��Ҫ�������������ʵ����\n"
		+"num_digits:���������ֵ�λ����\n"
		+"���� ROUNDDOWN �ͺ��� ROUND �������ƣ���֮ͬ�����ں��� ROUNDDOWN ���������������֡�\n"
		+"ʾ��:\n"
		+"ROUNDDOWN(3.2, 0) ���� 3\n"
		+"ROUNDDOWN(76.9,0) ���� 76\n"
		+"ROUNDDOWN(3.14159, 3) ���� 3.141\n"
		+"ROUNDDOWN(-3.14159, 1) ���� -3.1\n"
		+"ROUNDDOWN(31415.92654, -2) ���� 31,400\n"
		+"";
	}
	public String getEN(){
		return "ROUNDDOWN(number,num_digits): Rounds a number down, toward zero.\n"
		+"Number is any real number that you want rounded down.\n"
		+"Num_digits is the number of digits to which you want to round number.\n"
		+"\n"
		+"ROUNDDOWN behaves like ROUND, except that it always rounds a number down. \n"
		+"\n"
		+"Example:\n"
		+"   ROUNDDOWN(3.2, 0) =  3\n"
		+"   ROUNDDOWN(76.9,0) = 76\n"
		+"   ROUNDDOWN(3.14159, 3) = 3.141\n"
		+"   ROUNDDOWN(-3.14159, 1) = -3.1\n"
		+"   ROUNDDOWN(31415.92654, -2) = 31,400\n"
		+"";
	}
}