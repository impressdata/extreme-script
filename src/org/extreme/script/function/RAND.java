/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;

/**
 * Function.
 */
public class RAND extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        return new Double(Math.random());
    }	public String getCN(){
		return "RAND(): ���ؾ��ȷֲ����������ÿ����һ�ι������������᷵��һ���µ������ֵ��\n"
		+"��ע:\n"
		+"    Ҫ����һ��λ��a��b֮��������������ʹ�����µĹ�ʽ: C=RAND()*(b-a)+a��\n"
		+"    ���Ҫʹһ�������������ֵ���浥Ԫ����ؼ�����ı䣬�����ڱ༭��������=RAND()�����ֱ༭״̬��Ȼ��F9������ʽ�����Եظ�Ϊ�������\n"
		+"ʾ��:\n"
		+"������Ҫ����һ�����ڵ���0��С��60���������ʹ�ù�ʽ: =RAND()*60��\n"
		+"������Ҫ����һ�����ڵ���0��С��19���������ʹ�ù�ʽ: =RAND()*19��\n"
		+"������Ҫ����һ�����ڵ���0��С��50���������ʹ�ù�ʽ: =RAND()*50��";
	}
	public Type getType() {
		return MATH;
	}
	public String getEN(){
		return "RAND(): Returns an evenly distributed random number greater than or equal to 0 and less than 1. A new random number is returned every time the worksheet is calculated.\n"
		+"\n"
		+"Re:\n"
		+"1. To generate a random real number between a and b, use: RAND()*(b-a)+a\n"
		+"2. If you want to use RAND to generate a random number but don\'t want the numbers to change every time the cell is calculated, you can enter =RAND() in the formula bar, and then press F9 to change the formula to a random number. \n"
		+"\n"
		+"Example:\n"
		+"   If you want to get a rand number equal or greater than 0, and smaller than 60, use: RAND()*60��";
	}
}