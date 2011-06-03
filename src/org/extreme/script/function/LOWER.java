/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class LOWER extends AbstractFunction {

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
            return ("" + value).toLowerCase();
        }

        return null;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "LOWER(text): 将所有的大写字母转化为小写字母。\n"
		+"Text:需要转化为小写字母的文本串。LOWER函数不转化文本串中非字母的字符。\n"
		+"示例:\n"
		+"LOWER(\"A.M.10:30\")等于“a.m.10:30”。\n"
		+"LOWER(\"China\")等于“china”。";
	}
	public String getEN(){
		return "LOWER(text): Converts all uppercase letters in a text string to lowercase.\n"
		+"Text is the text you want to convert to lowercase. LOWER does not change characters in text that are not letters.\n"
		+"\n"
		+"Example:\n"
		+"   LOWER(\"A.M.10:30\") = \"a.m.10:30\"\n"
		+"   LOWER(\"China\") = \"china\"";
	}
}