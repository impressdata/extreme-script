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
public class EXP extends AbstractFunction {
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
            return FunctionHelper.asNumber(
                    Math.exp(((Number) value).doubleValue()));
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "EXP(number): 返回e的n次幂。常数e为自然对数的底数，等于2.71828182845904。\n"
		+"Number:为任意实数，作为常数e的指数。\n"
		+"备注:\n"
		+"    如果要返回其他常数作为底数的幂，可以使用指数运算符（^）。例如: 在4^2中，4是底数，而2是指数。\n"
		+"    EXP函数与LN函数互为反函数。\n"
		+"示例:\n"
		+"EXP(0)等于1。\n"
		+"EXP(3)等于20.08553692。\n"
		+"EXP(LN(2))等于2。";
	}
	public String getEN(){
		return "EXP(number): Returns e raised to the power of number. The constant e equals 2.71828182845904, the base of the natural logarithm.\n"
		+"Number is the exponent applied to the base e.\n"
		+"\n"
		+"Re:\n"
		+"1. To calculate powers of other bases, use the exponentiation operator (^). For example, in formula 4^2, 4 is base, 2 is exponential.\n"
		+"2. EXP is the inverse of LN, the natural logarithm of number. \n"
		+"\n"
		+"Example:\n"
		+"   EXP(0)=1\n"
		+"   EXP(3)=20.08553692\n"
		+"   EXP(LN(2))=2";
	}
}