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
public class LOG10 extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object param = null;

        param = args[0];

        if (param instanceof Number) {
            return FunctionHelper.asNumber(
                    Math.log(((Number) param).doubleValue()) / Math.log(10));
        }


        return new Integer(0);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "LOG10(number):返回以 10 为底的对数。\n"
		+"number: 用于常用对数计算的正实数。\n"
		+"示例:\n"
		+"LOG10(86) 等于 1.934498451\n"
		+"LOG10(10) 等于 1\n"
		+"LOG10(1E5) 等于 5\n"
		+"";
	}
	public String getEN(){
		return "LOG10(number): Returns the base-10 logarithm of a number.\n"
		+"Number is the positive real number for which you want the base-10 logarithm.\n"
		+"\n"
		+"Example:\n"
		+"   LOG10(86) = 1.934498451\n"
		+"   LOG10(10) = 1\n"
		+"   LOG10(1E5) = 5\n"
		+"";
	}
}