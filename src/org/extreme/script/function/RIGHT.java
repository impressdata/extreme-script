/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class RIGHT extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object textObj = args[0];
        int num = 1;
        if (args.length > 1) {
            Object startNumObj = args[1];
            if (startNumObj instanceof Number) {
                num = ((Number) startNumObj).intValue();
            }
        }
        try {
        	String str = Utils.objectToString(textObj);
        	return str.substring(str.length() - Math.max(0, Math.min(num, str.length())));
        } catch (Exception exp) {
        }

       return "";
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "RIGHT(text,num_chars): 根据指定的字符数从右开始返回文本串中的最后一个或几个字符。\n"
		+"Text:包含需要提取字符的文本串或单元格引用。\n"
		+"Num_chars:指定RIGHT函数从文本串中提取的字符数。Num_chars不能小于0。如果num_chars大于文本串长度，RIGHT函数将返回整个文本。如果不指定num_chars，则默认值为1。\n"
		+"示例:\n"
		+"RIGHT(\"It is interesting\",6)等于“esting”。\n"
		+"RIGHT(\"Share Holder\")等于“r”。\n"
		+"RIGHT(\"Huge sale\",4)等于“sale”。";
	}
	public String getEN(){
		return "RIGHT(text,num_chars): RIGHT returns the last character or characters in a text string, based on the number of characters you specify.\n"
		+"Text is the text string containing the characters you want to extract.\n"
		+"Num_chars specifies the number of characters you want RIGHT to extract.\n"
		+"\n"
		+"Re:\n"
		+"1. Num_chars must be greater than or equal to zero. \n"
		+"2. If num_chars is greater than the length of text, RIGHT returns all of text. \n"
		+"3. If num_chars is omitted, it is assumed to be 1. \n"
		+"\n"
		+"Example:\n"
		+"   RIGHT(\"It is interesting\",6) = \"esting\"\n"
		+"   RIGHT(\"Share Holder\") = \"r\"\n"
		+"   RIGHT(\"Huge sale\",4) = \"sale\"";
	}
}