/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class PI extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
		int size = args.length;
		if (size == 0) {
			return new Double(Math.PI);
		} else if (size == 1) {
			Object times = args[0];
			if (times instanceof Integer) {
				return new Double(Math.PI * ((Integer) times).intValue());
			} else {
				return Primitive.ERROR_VALUE;
			}
		} else {
			return Primitive.ERROR_VALUE;
		}
	}
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "PI(number): ��һ����ѧ������������numberΪ��ʱ���������ؾ�ȷ��15λ����ֵ3.141592653589793����������Ϊ��ʱ��number��ʾPI�ı�����\n"
		+"ʾ��:\n"
		+"SIN(PI()/2)����1��\n"
		+"����Բ������Ĺ�ʽ: S=PI()*(r^2)������SΪԲ�������RΪԲ�İ뾶��\n"
		+"PI(3)����9.42477796076938��";
	}
	public String getEN(){
		return "PI: Returns the number 3.14159265358979, the mathematical constant pi, accurate to 15 digits.\n"
		+"\n"
		+"Example:\n"
		+"   SIN(PI()/2) = 1��\n"
		+"   Formula for area of a circle: S=PI()*(r^2), S is the area, R is the radius.";
	}
}