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
public class RADIANS extends AbstractFunction {

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
            return FunctionHelper.asNumber(
                    Math.toRadians(((Number) value).doubleValue()));
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "RADIANS(angle): ���Ƕ�ת���ɻ��ȡ�\n"
		+"Angle:��Ҫת��Ϊ���ȵĽǶȡ�\n"
		+"ʾ��:\n"
		+"RADIANS(90)����1.570796327����/2���ȣ���";
	}
	public String getEN(){
		return "RADIANS(angle): Converts degrees to radians.\n"
		+"Angle is an angle in degrees that you want to convert.\n"
		+"\n"
		+"Example:\n"
		+"RADIANS(90) = 1.570796327 (��/2 in radians)";
	}
}