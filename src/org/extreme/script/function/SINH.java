/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class SINH extends AbstractFunction {

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
            double tmp = ((Number) value).doubleValue();
            return new Double((Math.pow(Math.E, tmp) - Math.pow(Math.E, -tmp)) / 2);
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "SINH(number):返回某一数字的双曲正弦值。\n"
		+"number:为任意实数。\n"
		+"示例:\n"
		+"SINH(1) 等于 1.175201194\n"
		+"SINH(-1) 等于 -1.175201194";
	}
	public String getEN(){
		return "SINH(number): Returns the hyperbolic sine of a number.\n"
		+"number is any real number.\n"
		+"\n"
		+"Example:\n"
		+"   SINH(1) = 1.175201194\n"
		+"   SINH(-1) = -1.175201194";
	}
}