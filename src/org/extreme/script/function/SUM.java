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
		return "SUM(number1,number2,��): ��һ��ָ����Ԫ����������������֮�͡�\n"
		+"Number1,number2,��:1��30��������ָ����Ԫ���������������֡�\n"
		+"��ע:\n"
		+"    ������ֱ�Ӽ�������е���ֵ���߼�ֵ���ı����ʽ�������ڡ�\n"
		+"    ����������������ã���ֻ�������Ԫ�������е���ֵ���м��㡣\n"
		+"ʾ��:\n"
		+"SUM(70,80)����150��\n"
		+"SUM(\"70\",80,TRUE)����151���߼�ֵ��TRUE����Ϊ1�����㣻��FALSE����Ϊ0���㣻�ı���70����Ϊ70�����㡣";
	}
	public String getEN(){
		return "SUM(number1,number2,��): Adds all the numbers in a range of cells.\n"
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