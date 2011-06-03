/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class TRUNC extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        if (args.length == 1) {
            Object param = args[0];
            if (!(param instanceof Number)) {
            	return null;
            }
            return new Integer((int) ((Number) param).doubleValue());
        }

        Object param1 = args[0];
        Object param2 = args[1];
        double tmp = 1;
        if ((param1 instanceof Number) && (param2 instanceof Number)) {
            if (((Number) param2).intValue() < 0) {
                for (int i = 1; i <= -1 * ((Number) param2).intValue(); i++) {
                    tmp *= 10;
                }
                if (((Number) param1).doubleValue() >= 0) {
                    return new Double((long) (((Number) param1).doubleValue() / tmp) * tmp);
                } else {
                    return new Double((long) (((Number) param1).doubleValue() / tmp) * tmp);
                }
            }
            if (((Number) param2).intValue() == 0) {
                if (((Number) param1).doubleValue() >= 0) {
                    return new Long((long) (((Number) param1).doubleValue()));
                } else {
                    return new Long((long) (((Number) param1).doubleValue()));
                }
            } else {
                for (int i = 1; i <= ((Number) param2).intValue(); i++) {
                    tmp *= 10;
                }
                if (((Number) param1).doubleValue() >= 0) {
                    return new Double((long) (((Number) param1).doubleValue() * tmp) / tmp);
                } else {
                    return new Double((long) (((Number) param1).doubleValue() * tmp) / tmp);
                }
            }

        }
        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "TRUNC(number,num_digits):将数字的小数部分截去，返回整数。\n"
		+"number:需要截尾取整的数字。\n"
		+"num_digits:用于指定取整精度的数字。\n"
		+"示例:\n"
		+"TRUNC(8.9) 等于 8\n"
		+"TRUNC(-8.9) 等于 -8\n"
		+"TRUNC(PI()) 等于 3\n"
		+"";
	}
	public String getEN(){
		return "TRUNC(number,num_digits): Truncates a number to an integer by removing the fractional part of the number.\n"
		+"Number is the number you want to truncate.\n"
		+"Num_digits is a number specifying the precision of the truncation. The default value for num_digits is 0 (zero).\n"
		+"\n"
		+"Example:\n"
		+"   TRUNC(8.9) = 8\n"
		+"   TRUNC(-8.9) = -8\n"
		+"   TRUNC(PI()) = 3\n"
		+"";
	}
}