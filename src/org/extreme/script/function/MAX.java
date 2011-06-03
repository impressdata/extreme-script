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
		return "MAX(number1,number2,��): ���ز����б��е����ֵ��\n"
		+"Number1,number2,��:1��30����Ҫ�ҳ����ֵ�Ĳ�����\n"
		+"��ע:\n"
		+"    �������������֡��հ׵�Ԫ���߼�ֵ�����ֵ��ı����ʽ��\n"
		+"    �����������ò����а����ɽ����ı�ֵ���߼�ֵ����ֵ��հ׵�Ԫ����Щֵ����������㣬�����ɽ������ı�ֵ���Բ��ơ�\n"
		+"    ���������û���κ����֣�MAX������0��\n"
		+"ʾ��:\n"
		+"MAX(0.1,0,1.2)����1.2��";
	}
	public String getEN(){
		return "MAX(number1,number2,��): Returns the largest value in a set of values.\n"
		+"Number1,number2,��  are 1 to 30 numbers for which you want to find the maximum value.\n"
		+"Re:\n"
		+"1. You can specify arguments that are numbers, empty cells, logical values, or text representations of numbers.\n"
		+"2. If an argument is an array or reference, only numbers in that array or reference are used. Empty cells, logical values, or text in the array or reference are ignored.\n"
		+"3. If the arguments contain no numbers, MAX returns 0 (zero).\n"
		+"Example:\n"
		+"   MAX(0.1,0,1.2)=1.2";
	}
}