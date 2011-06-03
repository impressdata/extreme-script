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
public class TOHEX extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return Integer.toHexString(Utils.objectToNumber(args[0], false).intValue());
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "TOHEX(int): 将一个十进制整型数转换成十六进制表示的字符串。\n"
		+"int:表示需要进行转换的十进制整数。\n"
		+"示例:\n"
		+"TOHEX(15)等于 \"f\"。\n"
		+"TOHEX(20)等于 \"14\"。";
	}
	public String getEN(){
		return "";
	}
}