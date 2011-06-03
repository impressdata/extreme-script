/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Convert String to integer.
 */
public class TOBINARY extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return Integer.toBinaryString(Utils.objectToNumber(args[0], false).intValue());
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "TOBINARY(int): 将一个十进制整型数转换成二进制表示的字符串。\n"
		+"int:表示需要进行转换的十进制整数。\n"
		+"示例:\n"
		+"TOBINARY(10)等于 \"1010\"。\n"
		+"TOBINARY(20)等于 \"10100\"。";
	}
	public String getEN(){
		return "";
	}
}