/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;

/**
 * Function.
 */
public class ROUNDUP extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	return FunctionHelper.roundUpOrDown(args, true);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "ROUNDUP(number,num_digits):Զ����ֵ�����ϣ�����ֵ����ķ����������֡�\n"
		+"number:Ϊ��Ҫ�������������ʵ����\n"
		+"num_digits:���������ֵ�λ����\n"
		+"���� ROUNDUP �ͺ��� ROUND �������ƣ���֮ͬ�����ں��� ROUNDUP ���������������֡�\n"
		+"ʾ��:\n"
		+"ROUNDUP(3.2,0) ���� 4\n"
		+"ROUNDUP(76.9,0) ���� 77\n"
		+"ROUNDUP(3.14159, 3) ���� 3.142\n"
		+"ROUNDUP(-3.14159, 1) ���� -3.2\n"
		+"ROUNDUP(31415.92654, -2) ���� 31,500\n"
		+"\n"
		+"";
	}
	public String getEN(){
		return "ROUNDUP(number,num_digits): Rounds a number up, away from 0 (zero).\n"
		+"Number is any real number that you want rounded up.\n"
		+"Num_digits is the number of digits to which you want to round number.\n"
		+"\n"
		+"ROUNDUP behaves like ROUND, except that it always rounds a number up. \n"
		+"\n"
		+"Example:\n"
		+"   ROUNDUP(3.2,0) = 4\n"
		+"   ROUNDUP(76.9,0) = 77\n"
		+"   ROUNDUP(3.14159, 3) = 3.142\n"
		+"   ROUNDUP(-3.14159, 1) = -3.2\n"
		+"   ROUNDUP(31415.92654, -2) = 31,500\n"
		+"\n"
		+"";
	}
}