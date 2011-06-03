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
public class SIN extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        double result = 0;

//get the parameter from the stack
        Object param = null;
        for(int i = 0; i < args.length; i++) {
            param = args[i];

            if (param instanceof Number) {
// calculate the result
                result = Math.sin(((Number) param).doubleValue());
                break;
            }
        }

        return FunctionHelper.asNumber(result);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "SIN(number): ��������Ƕȵ�����ֵ��\n"
		+"Number:��������ֵ���Ի��ȱ�ʾ�ĽǶȡ�\n"
		+"��ע:\n"
		+"    ��������ĵ�λ�Ƕȣ��������PI()/180����ת���ɻ��ȡ�\n"
		+"ʾ��:\n"
		+"SIN(10)����-0.544021111��\n"
		+"SIN(45*PI()/180)����0.707106781��";
	}
	public String getEN(){
		return "SIN(number): Returns the sine of the given angle.\n"
		+"Number is the angle in radians for which you want the sine.\n"
		+"\n"
		+"Re:\n"
		+"    If your argument is in degrees, multiply it by PI()/180 or use the RADIANS function to convert it to radians.\n"
		+"\n"
		+"Example:\n"
		+"   SIN(10) = -0.544021111\n"
		+"   SIN(45*PI()/180) = 0.707106781";
	}
}