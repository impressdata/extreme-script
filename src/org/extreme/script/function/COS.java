/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class COS extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object value = args[0];
        if (value instanceof Number) {
            return FunctionHelper.asNumber(Math.cos(((Number) value).doubleValue()));
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "COS(number): ����һ���Ƕȵ�����ֵ��\n"
		+"Number:�Ի��ȱ�ʾ����Ҫ������ֵ�ĽǶȡ�\n"
		+"��ע:\n"
		+"    Ҫ��һ���Ƕ�ת���ɻ���ֵ�����Ƕȳ���PI()/180��\n"
		+"    COS(n*2*PI()+number)=COS(number)������nΪ������number��-pi��pi����\n"
		+"ʾ��:\n"
		+"COS(0.5)����0.877582562��\n"
		+"COS(30*PI()/180)����0.866025404��";
	}
	public String getEN(){
		return "COS(number): Returns the cosine of the given angle.\n"
		+"Number is the angle in radians for which you want the cosine.\n"
		+"\n"
		+"Re:\n"
		+"1. If the angle is in degrees, multiply it by PI()/180 or use the COS function to convert it to radians.\n"
		+"2. COS(n*2*PI()+number)=COS(number), n is an integer, number is between -pi and pi.\n"
		+"\n"
		+"Example:\n"
		+"   COS(0.5)=0.877582562\n"
		+"   COS(30*PI()/180)=0.866025404";
	}
}