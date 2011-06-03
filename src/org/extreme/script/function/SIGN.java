/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class SIGN extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object param = null;
        for(int i = 0; i < args.length; i++) {
            param = args[i];

            if (param instanceof Number) {
                double tmp = ((Number) param).doubleValue();
                if (tmp > 0) {
                    return new Integer(1);
                }
                if (tmp < 0) {
                    return new Integer(-1);
                }
                if (tmp == 0) {
                    return new Integer(0);
                }
            }
        }

        return new Integer(0);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "SIGN(number):返回数字的符号。当数字为正数时返回 1，为零时返回 0，为负数时返回 -1。\n"
		+"Number:为任意实数。\n"
		+"示例:\n"
		+"SIGN(10) 等于 1\n"
		+"SIGN(4-4) 等于 0\n"
		+"SIGN(-0.00001) 等于 -1\n"
		+"";
	}
	public String getEN(){
		return "SIGN(number): Determines the sign of a number. Returns 1 if the number is positive, zero (0) if the number is 0, and -1 if the number is negative.\n"
		+"Number is any real number.\n"
		+"\n"
		+"Example:\n"
		+"   SIGN(10) = 1\n"
		+"   SIGN(4-4) = 0\n"
		+"   SIGN(-0.00001) = -1\n"
		+"";
	}
}