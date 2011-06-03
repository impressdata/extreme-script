/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class FLOOR extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return FunctionHelper.asNumber(Math.floor(Utils.objectToNumber(args[0], false).doubleValue()));
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "FLOOR(number): 将参数number沿绝对值减小的方向去尾舍入。\n"
		+"Number:待舍入的数值。\n"
		+"示例:\n"
		+"FLOOR(-2.5)等于-2。\n"
		+"FLOOR(2.5)等于2。\n";
	}
	public String getEN(){
		return "FLOOR(number,significance): Rounds number down, toward zero.\n"
		+"Number is the numeric value you want to round.\n"
		+"Re:\n"
		+"   FLOOR(-2.5) = -2\n"
		+"   FLOOR(2.5) = 2\n";
	}
}