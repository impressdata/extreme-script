/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;

/**
 * CHAR.
 */
public class CHAR extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	for(int i = 0; i < args.length; i++) {
    		if (args[i] instanceof Number) {
                return new Character((char) ((Number) args[i]).intValue());
            }
    	}

        return "";
    }
    public Type getType() {
    	return TEXT;
    }	public String getCN(){
		return "CHAR(number): 根据指定数字返回对应的字符。CHAR函数可将计算机其他类型的数字代码转换为字符。\n"
		+"Number:用于指定字符的数字，介于1~65535之间（包括1和65535）。\n"
		+"示例:\n"
		+"CHAR(88)等于“X”。\n"
		+"CHAR(45)等于“-”。";
	}
	public String getEN(){
		return "CHAR(number): Returns the character specified by a number. Use CHAR to translate code page numbers you    might get from files on other types of computers into characters.\n"
		+"   Number:Number is a number between 1 and 65535 specifying which character you want.(Include 1 and 65535)\n"
		+"Example:\n"
		+"   CHAR(88)=\"X\"\n"
		+"   CHAR(45)=\"-\"";
	}
}