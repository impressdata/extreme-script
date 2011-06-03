/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


/**
 * Function.
 */
public class MIN extends MinAndMaxAndSUM {
	
	protected double init() {
		return Double.MAX_VALUE;
	}
	
	protected double operation(double d1, double d2) {
		return Math.min(d1, d2);
	}
		public String getCN(){
		return "MIN(number1,number2,��): ���ز����б��е���Сֵ��\n"
		+"Number1,number2,��:1��30����Ҫ�ҳ���Сֵ�Ĳ�����\n"
		+"��ע:\n"
		+"    ��������û�����֣�����MIN������0��\n"
		+"    ����ӦΪ���֡��հ׵�Ԫ���߼�ֵ���Ǳ�ʾ��ֵ���ı�������������Ǵ���ֵʱ��MIN�����ش�����Ϣ��\n"
		+"    �����������ò����а����ɽ����ı�ֵ���߼�ֵ����ֵ��հ׵�Ԫ����Щֵ����������㣬�����ɽ������ı�ֵ���Բ��ơ�\n"
		+"ʾ��:\n"
		+"���B1:B4����3��6��9��12����:\n"
		+"MIN(B1:B4)����3��\n"
		+"MIN(B1:B4,0)����0��";
	}
	public String getEN(){
		return "MIN(number1,number2,��): Returns the smallest number in a set of values.\n"
		+"Number1,number2,�� are 1 to 30 numbers for which you want to find the minimum value.\n"
		+"\n"
		+"Re:\n"
		+"1. If the arguments contain no numbers, MIN returns 0. \n"
		+"2. You can specify arguments that are numbers, empty cells, logical values, or text representations of numbers. Arguments that are error values or text that cannot be translated into numbers cause errors. \n"
		+"3. If an argument is an array or reference, only numbers in that array or reference are used. Empty cells, logical values, or text in the array or reference are ignored. \n"
		+"\n"
		+"Example:\n"
		+"   If B1:B4 are 3, 6, 9, 12 then MIN(B1:B4)=3, MIN(B1:B4,0)=0.";
	}
}