/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;

/**
 * TAN Function.
 */
public class TAN extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        double result = 0;

//get the parameter from the stack
        Object param = null;
        for(int i = 0; i < args.length; i++) {
            param = args[i];

            if (param instanceof Number) {
// calculate the result
                result = Math.tan(((Number) param).doubleValue());
                break;
            }
        }

        return FunctionHelper.asNumber(result);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "TAN(number): 返回指定角度的正切值。\n"
		+"Number:待求正切值的角度，以弧度表示。如果参数是以度为单位的，乘以Pi()/180后转换为弧度。\n"
		+"示例:\n"
		+"TAN(0.8)等于1.029638557。\n"
		+"TAN(45*Pi()/180)等于1。";
	}
	public String getEN(){
		return "TAN(number): Returns the tangent of the given angle.\n"
		+"Number    is the angle in radians for which you want the tangent.\n"
		+"\n"
		+"If your argument is in degrees, multiply it by PI()/180 or use the RADIANS function to convert it to radians.\n"
		+"\n"
		+"Example:\n"
		+"   TAN(0.8) = 1.029638557\n"
		+"   TAN(45*Pi()/180) = 1";
	}
}