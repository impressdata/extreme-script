/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Function;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class CEILING extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return FunctionHelper.asNumber(Math.ceil(Utils.objectToNumber(args[0], false).doubleValue()));
    }
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "CEILING(number): 将参数number沿绝对值增大的方向，舍入为最接近的整数\n"
		+"Number:指待舍入的数值。\n"
		+"CEILING(-2.5)等于-3。\n"
		+"CEILING(0.5)等于1。";
	}
	public String getEN(){
		return "CEILING(number): Returns number rounded up, away from zero.\n"
		+"Number is the value you want to round.\n"
		+"Example:\n"
		+"   CEILING(-2.5)=-3\n"
		+"   CEILING(0.5)=1";
	}
}