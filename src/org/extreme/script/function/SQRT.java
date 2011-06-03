/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class SQRT extends AbstractFunction {

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
            return new Double(Math.sqrt(((Number) value).doubleValue()));
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "SQRT(number): 返回一个正数的平方根。\n"
		+"Number:要求其平方根的任一正数。\n"
		+"备注:\n"
		+"Number必须是一个正数，否则函数返回错误信息*NUM!。\n"
		+"示例:\n"
		+"SQRT(64)等于8。\n"
		+"SQRT(-64)返回*NUM!。";
	}
	public String getEN(){
		return "SQRT(number): Returns a positive square root.\n"
		+"Number is the number for which you want the square root.\n"
		+"\n"
		+"Re:\n"
		+"   If number is negative, SQRT returns the #NUM! error value.\n"
		+"\n"
		+"Example:\n"
		+"   SQRT(64) = 8\n"
		+"   SQRT(-64) returns *NUM!";
	}
}