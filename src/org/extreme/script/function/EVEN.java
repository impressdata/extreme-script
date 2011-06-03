/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


/**
 * Function.
 */
public class EVEN extends EvenOdd {
	
	protected boolean findMatch(long l) {
		return l % 2 != 0;
	}
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "EVEN(number):�����ؾ���ֵ������ȡ������ӽ���ż����ʹ�øú������Դ�����Щ�ɶԳ��ֵĶ���\n"
		+"number:��Ҫȡ������ֵ��\n"
		+"������������Σ���ֵ������Զ�� 0 �ķ������롣��� number ǡ����ż������������κ����봦��\n"
		+"ʾ��:\n"
		+"EVEN(1.5) ���� 2\n"
		+"EVEN(3) ���� 4\n"
		+"EVEN(2) ���� 2\n"
		+"EVEN(-1) ���� -2";
	}
	public String getEN(){
		return "EVEN(number): Returns number rounded up to the nearest even integer. You can use this function for processing items that come in twos.\n"
		+"Number is the value to round.\n"
		+"Regardless of the sign of number, a value is rounded up when adjusted away from zero. If number is an even integer, no rounding occurs. \n"
		+"\n"
		+"Example:\n"
		+"   EVEN(1.5) = 2\n"
		+"   EVEN(3) = 4\n"
		+"   EVEN(2) = 2\n"
		+"   EVEN(-1) = -2";
	}
}