/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class FACT extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object param = null;
        for(int ai = 0; ai < args.length; ai++) {
            param = args[ai];

            if (param instanceof Number) {
// calculate the result
                double tmp = ((Number) param).doubleValue();
                if (tmp == 0) {
                    return new Integer(1);
                }
                long result = 1;
                for (int i = 1; i <= (int) tmp; i++) {
                    result = result * i;
                }
                return new Long(result);
            }
        }

        return new Integer(0);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "FACT(number):返回数的阶乘，一个数的阶乘等于 1*2*3*...*该数。\n"
		+"number:要计算其阶乘的非负数。如果输入的 number 不是整数，则截尾取整。\n"
		+"示例:\n"
		+"FACT(1) 等于 1\n"
		+"FACT(1.9) 等于 FACT(1) 等于 1\n"
		+"FACT(0) 等于 1\n"
		+"FACT(5) 等于 1*2*3*4*5 等于 120\n"
		+"";
	}
	public String getEN(){
		return "FACT(number): Returns the factorial of a number. The factorial of a number is equal to 1*2*3*...* number.\n"
		+"Number is the nonnegative number you want the factorial of. If number is not an integer, it is truncated.\n"
		+"\n"
		+"Example:\n"
		+"   FACT(1) = 1\n"
		+"   FACT(1.9) = FACT(1) = 1\n"
		+"   FACT(0) = 1\n"
		+"   FACT(5) = 1*2*3*4*5 = 120\n"
		+"";
	}
}