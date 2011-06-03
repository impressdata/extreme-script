/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Function;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

/**
 * ATANH Function.
 */
public class ATANH extends AbstractFunction {

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
            double tmp = ((Number) value).doubleValue();
            return FunctionHelper.asNumber(
                    Math.log((1 + tmp) / (1 - tmp)) / 2);
        }

        return Primitive.ERROR_NAME;
    }
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "ATANH(number): 返回数字的反双曲正切值，该函数的参数值即为反双曲正切值的双曲正切值。\n"
		+"Number:指介于-1~1之间的任意实数。\n"
		+"备注:\n"
		+"    指定的number必须介于-1~1之间（不包括-1，1）。\n"
		+"    ATANH(TANH(number))=number，例如，ATANH(TANH(8))=8。\n"
		+"示例:\n"
		+"ATANH(-0.5)等于-0.549306144。\n"
		+"ATANH(0)等于0。\n"
		+"ATANH(0.7)等于0.867300528。";
	}
	public String getEN(){
		return "ATANH(number): Returns the inverse hyperbolic tangent of a number. \n"
		+"\n"
		+"Re:\n"
		+"    Number must be between -1 and 1 (excluding -1 and 1).\n"
		+"    ATANH(TANH(number))=number, for example ATANH(TANH(8))=8.\n"
		+"\n"
		+"Example:\n"
		+"   ATANH(-0.5) = -0.549306144\n"
		+"   ATANH(0) = 0\n"
		+"   ATANH(0.7) = 0.867300528";
	}
}