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
public class REPLACE extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 3 || args.length > 4) {
            return Primitive.ERROR_NAME;
        }
        if (args.length == 3) {
        	return Utils.replaceAllString(
        			Utils.objectToString(args[0]),
        			Utils.objectToString(args[1]),
        			Utils.objectToString(args[2])
        	);
        } else if (args.length == 4) {
        	Object textObj = args[0];

            int startNum = -1;
            Object startNumObj = args[1];
            if (startNumObj instanceof Number) {
                startNum = ((Number) startNumObj).intValue();
            }

            int numChars = -1;
            Object numCharsObj = args[2];
            if (numCharsObj instanceof Number) {
                numChars = ((Number) numCharsObj).intValue();
            }
            if (startNum < 1 || numChars < 0) {
                return Primitive.ERROR_VALUE;
            }
            Object newTextObj = args[3];
            if (textObj != null) {
                String text = "" + textObj;
                if (startNum >= text.length()) {
                    return "";
                }
                return text.substring(0, startNum - 1) + newTextObj +
                        text.substring(startNum + numChars - 1);
            }
            return Primitive.ERROR_VALUE;
        }
        return Primitive.ERROR_VALUE;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "REPLACE(text, texttoreplace, replacetext):根据指定字符串，用其他文本来代替原始文本中的内容。\n"
		+"text：需要被替换部分字符的文本或单元格引用。\n"
		+"regex：指定的字符串或正则表达式。\n"
		+"replacetext:需要替换部分旧文本的文本。\n"
		+"示例：\n"
		+"REPLACE(\"abcd\", \"a\", \"re\")等于\"rebcd\"。\n"
		+"REPLACE(\"a**d\", \"**d\", \"rose\")等于\"arose\"。\n"
		+"REPLACE(old_text,start_num,num_chars,new_text): 根据指定的字符数，用其他文本串来替换某个文本串中的部分内容。\n"
		+"Old_text:需要被替换部分字符的文本或单元格引用。\n"
		+"Start_num:需要用new_text来替换old_text中字符的起始位置。\n"
		+"Num_chars:需要用new_text来替换old_text中字符的个数。\n"
		+"New_text:需要替换部分旧文本的文本。\n"
		+"示例:\n"
		+"REPLACE(\"0123456789\",5,4,\"*\")等于“0123*89”。\n"
		+"REPLACE(\"1980\",3,2,\"99\")等于“1999”。";
	}
	public String getEN(){
		return "REPLACE(text, texttoreplace, replacetext): According to the specified string, some characters of the text will be replaced.\n"
		+"text:you want to replace some characters in the text\n"
		+"regex:Specified string or regular expression.\n"
		+"replacetext:the text that will replace some characters in text.\n"
		+"Example：\n"
		+"REPLACE(\"abcd\", \"a\", \"re\")=\"rebcd\"。\n"
		+"REPLACE(\"a**d\", \"**d\", \"rose\")=\"arose\"。\n"
		+"\n"
		+"REPLACE(old_text,start_num,num_chars,new_text): REPLACE replaces part of a text string, based on the number of characters you specify, with a different text string.\n"
		+"Old_text is text in which you want to replace some characters.\n"
		+"Start_num is the position of the character in old_text that you want to replace with new_text.\n"
		+"Num_chars is the number of characters in old_text that you want REPLACE to replace with new_text.\n"
		+"New_text is the text that will replace characters in old_text.\n"
		+"\n"
		+"Example:\n"
		+"   REPLACE(\"0123456789\",5,4,\"*\") = \"0123*89\"\n"
		+"   REPLACE(\"1980\",3,2,\"99\") = \"1999\"";
	}
}