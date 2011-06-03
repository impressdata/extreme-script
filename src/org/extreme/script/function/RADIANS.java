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
		return "RADIANS(angle): 将角度转换成弧度。\n"
		+"Angle:需要转换为弧度的角度。\n"
		+"示例:\n"
		+"RADIANS(90)等于1.570796327（π/2弧度）。";
	}
	public String getEN(){
		return "RADIANS(angle): Converts degrees to radians.\n"
		+"Angle is an angle in degrees that you want to convert.\n"
		+"\n"
		+"Example:\n"
		+"RADIANS(90) = 1.570796327 (π/2 in radians)";
	}
}