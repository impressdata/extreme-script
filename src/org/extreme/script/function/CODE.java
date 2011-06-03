/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class CODE extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object value = args[0];
        if (value != null) {
            String valueString = (String) value;
            if (valueString.length() > 0) {
                return new Integer(valueString.charAt(0));
            }
        }

        return Primitive.ERROR_NAME;
    }
    public Type getType() {
    	return TEXT;
    }	public String getCN(){
		return "CODE(text): 计算文本串中第一个字符的数字代码。返回的代码对应于计算机使用的字符集。\n"
		+"Text:需要计算第一个字符代码的文本或单元格引用。\n"
		+"示例:\n"
		+"CODE(\"S\")等于83。\n"
		+"CODE(\"Spreadsheet\")等于83。";
	}
	public String getEN(){
		return "CODE(text): Returns a numeric code for the first character in a text string. The returned code corresponds to the character set used by your computer.\n"
		+"Text is the text for which you want the code of the first character.\n"
		+"\n"
		+"Example:\n"
		+"   CODE(\"S\") = 83\n"
		+"   CODE(\"Spreadsheet\") = 83";
	}
}