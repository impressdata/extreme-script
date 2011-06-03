/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Function;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

/**
 * ASIN Function.
 */
public class ASINH extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        double result = 0;
        
        for(int i = 0; i < args.length; i++) {
            if(args[i] instanceof Number) {
            	double tmp = ((Number) args[i]).doubleValue();
                result = Math.log(tmp + Math.sqrt(tmp * tmp + 1));
                break;
            }
        }

        return FunctionHelper.asNumber(result);
    }
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "ASINH(number): 返回指定数值的反双曲正弦值。反双曲正弦值的双曲正弦等于指定数值。即: ASINH(SINH(number))=number。\n"
		+"Number:任意实数。\n"
		+"示例:\n"
		+"ASINH(-5)等于-2.312438341。\n"
		+"ASINH(8)等于2.776472281。\n"
		+"ASINH(16)等于3.466711038。";
	}
	public String getEN(){
		return "ASINH(number): Returns the inverse hyperbolic sine of a number. The inverse hyperbolic sine is the value whose hyperbolic sine is number, so ASINH(SINH(number)) equals number.\n"
		+"Number is any real number.\n"
		+"\n"
		+"Example:\n"
		+"   ASINH(-5)=-2.312438341\n"
		+"   ASINH(8)=2.776472281\n"
		+"   ASINH(16)=3.466711038";
	}
}