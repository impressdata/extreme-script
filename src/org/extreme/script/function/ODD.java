/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


/**
 * Function.
 */
public class ODD extends EvenOdd {
	
	protected boolean findMatch(long l) {
		return l % 2 != 1;
	}
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "ODD(number):���ض�ָ����ֵ����������������\n"
		+"number:��Ҫ�����������ֵ��\n"
		+"������������Σ���ֵ������Զ�� 0 �ķ������롣��� number ǡ������������������κ����봦��\n"
		+"ʾ��:\n"
		+"ODD(1.5) ���� 3\n"
		+"ODD(3) ���� 3\n"
		+"ODD(2) ���� 3\n"
		+"ODD(-1) ���� -1";
	}
	public String getEN(){
		return "ODD(number): Returns number rounded up to the nearest odd integer.\n"
		+"Number is the value to round.\n"
		+"Regardless of the sign of number, a value is rounded up when adjusted away from zero. If number is an odd integer, no rounding occurs. \n"
		+"\n"
		+"Example:\n"
		+"   ODD(1.5) = 3\n"
		+"   ODD(3) = 3\n"
		+"   ODD(2) = 3\n"
		+"   ODD(-1) = -1";
	}
}