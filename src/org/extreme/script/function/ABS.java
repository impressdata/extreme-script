/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Function;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

/**
 * ABS Function.
 */
public class ABS extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        for(int i = 0; i < args.length; i++) {
            if(args[i] instanceof Number) {
                return FunctionHelper.asNumber(Math.abs(((Number) args[i]).doubleValue()));
            }
        }

        return new Integer(0);
    }
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "ABS(number): 返回指定数字的绝对值。绝对值是指没有正负符号的数值。\n"
		+"Number:需要求出绝对值的任意实数。\n"
		+"示例:\n"
		+"ABS(-1.5)等于1.5。\n"
		+"ABS(0)等于0。\n"
		+"ABS(2.5)等于2.5。";
	}
	public String getEN(){
		return "ABS(number): Returns the absolute value of a number. The absolute value of a number is the number without its sign.\n"
		+"NumberNumber is the real number of which you want the absolute value.\n"
		+"Example:\n"
		+"   ABS(-1.5)=1.5\n"
		+"   ABS(0)=0\n"
		+"   ABS(2.5)=2.5";
	}
}