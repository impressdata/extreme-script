/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.Random;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class RANDBETWEEN extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }

        Object value1 = args[0];
        Object value2 = args[1];
        if (value1 instanceof Number && value2 instanceof Number) {
            int maxValue = Math.max(((Number) value2).intValue(), ((Number) value1).intValue());
            int minValue = Math.min(((Number) value2).intValue(), ((Number) value1).intValue());

            return new Integer((new Random()).nextInt(maxValue - minValue) + minValue);
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "RANDBETWEEN(value1,value2):返回value1和value2之间的一个随机整数。\n"
		+"示例：\n"
		+"RANDBETWEEN(12.333, 13.233)只会返回13。\n"
		+"RANDBETWEEN(11.2, 13.3)有可能返回12或者13。";
	}
	public String getEN(){
		return "";
	}
}