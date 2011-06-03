/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;
import org.extreme.script.parser.OperationUtils;


/**
 * Function.
 */
public class MOD extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }

        int p1 = Utils.objectToNumber(args[0], false).intValue(), p2 = Utils.objectToNumber(args[1], false).intValue();
        if (p2 == 0) {
        	return OperationUtils.POSITIVE_INFINITY;
        } else {
        	int result = p1 % p2;
        	if (p2 > 0) {
        		return new Integer(Math.abs(result));
        	} else {
        		return new Integer(-Math.abs(result));
        	}
        }
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "MOD(number,divisor):返回两数相除的余数。结果的正负号与除数相同。\n"
		+"number:为被除数。\n"
		+"divisor:为除数。\n"
		+"示例:\n"
		+"MOD(3, 2) 等于 1\n"
		+"MOD(-3, 2) 等于 1\n"
		+"MOD(3, -2) 等于 -1\n"
		+"MOD(-3, -2) 等于 -1\n"
		+"";
	}
	public String getEN(){
		return "MOD(number,divisor): Returns the remainder after number is divided by divisor. The result has the same sign as divisor.\n"
		+"Number is the number for which you want to find the remainder.\n"
		+"Divisor is the number by which you want to divide number.\n"
		+"\n"
		+"Example:\n"
		+"   MOD(3, 2) = 1\n"
		+"   MOD(-3, 2) = 1\n"
		+"   MOD(3, -2) = -1\n"
		+"   MOD(-3, -2) = -1\n"
		+"";
	}
}