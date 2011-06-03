/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class INT extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        Object value = args[0];
        if (value != null && !(value instanceof Number)) {
            value = value.toString();
            try {
                int intValue = Integer.parseInt((String) value);
                value = new Integer(intValue);
            } catch (Exception e) {
                try {
                    double doubleValue = Double.parseDouble((String) value);
                    value = new Double(doubleValue);
                } catch (Exception e1) {
                    return Primitive.ERROR_VALUE;
                }
            }
        }

        if (value != null) {
        	double d = ((Number)value).doubleValue();
        	if (d >= 0) {
        		if (d > Integer.MAX_VALUE) {
        			return new Integer(((Number)value).intValue());
        		} else {
        			return new Long(((Number)value).longValue());
        		}
        	} else {
        		if (d < Integer.MIN_VALUE) {
        			return new Integer(((Number)value).intValue() - 1);
        		} else {
        			return new Long(((Number)value).longValue() - 1);
        		}
        	}
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "INT(number): 返回数字下舍入（数值减小的方向）后最接近的整数值。\n"
		+"Number:需要下舍入为整数的实数。\n"
		+"示例:\n"
		+"INT(4.8)等于4。\n"
		+"INT(-4.8)等于-5。\n"
		+"INT(4.3)等于4。\n"
		+"INT(-4.3)等于-5。\n"
		+"公式INT(A1)将返回A1单元格中的一个正实数的整数数部分。";
	}
	public String getEN(){
		return "INT(number): Rounds a number down to the nearest integer.\n"
		+"\n"
		+"Example:\n"
		+"   INT(4.8) = 4。\n"
		+"   INT(-4.8) = -5。\n"
		+"   INT(4.3) = 4。\n"
		+"   INT(-4.3) = -5。\n"
		+"   Formula INT(A1) returns integer of the real number of A1 Cell.";
	}
}