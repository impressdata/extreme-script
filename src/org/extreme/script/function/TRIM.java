/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class TRIM extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object textObj = args[0];
        if (textObj != null) {
            return ("" + textObj).trim();
        }

        return textObj;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "TRIM(text): 清除文本中所有空格，单词间的单个空格除外，也可用于带有不规则空格的文本。\n"
		+"Text:需要清除空格的文本。\n"
		+"示例:\n"
		+"TRIM(\" Monthly Report\")等于Monthly Report。";
	}
	public String getEN(){
		return "TRIM(text): Removes all spaces from text except for single spaces between words. Use TRIM on text that you have received from another application that may have irregular spacing.\n"
		+"Text is the text from which you want spaces removed.\n"
		+"\n"
		+"Example:\n"
		+"   TRIM(\" Monthly Report\") = \"Monthly Report\"";
	}
}