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
public class POWER extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }
        
        return FunctionHelper.asNumber(Math.pow(
        		Utils.objectToNumber(args[0], false).doubleValue(),
        		Utils.objectToNumber(args[1], false).doubleValue()
        ));
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "POWER(number,power): 返回指定数字的乘幂。\n"
		+"Number:底数，可以为任意实数。\n"
		+"Power:指数。参数number按照该指数次幂乘方。\n"
		+"备注:\n"
		+"    可以使用符号“^”代替POWER，如: POWER(5,2)等于5^2。\n"
		+"示例:\n"
		+"POWER(6,2)等于36。\n"
		+"POWER(14,5)等于537824。\n"
		+"POWER(4,2/3)等于2.519842100。\n"
		+"POWER(3,-2.3)等于0.079913677。";
	}
	public String getEN(){
		return "POWER(number,power): Returns the result of a number raised to a power.\n"
		+"Number is the base number. It can be any real number.\n"
		+"Power is the exponent to which the base number is raised.\n"
		+"\n"
		+"Re:\n"
		+"    The \"^\" operator can be used instead of POWER to indicate to what power the base number is to be raised, such as in 5^2 equals POWER(5,2).\n"
		+"\n"
		+"Example:\n"
		+"   POWER(6,2) = 36\n"
		+"   POWER(14,5) = 537824\n"
		+"   POWER(4,2/3) = 2.519842100\n"
		+"   POWER(3,-2.3) = 0.079913677";
	}
}