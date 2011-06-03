/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class MID extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 3) {
            return Primitive.ERROR_NAME;
        }

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

        if (textObj != null) {
            String text = "" + textObj;
            if (startNum > text.length()) {
                return "";
            }
            if(startNum+numChars-1 >text.length()){
            	return text.substring(startNum - 1);
            }

            return text.substring(startNum - 1, startNum + numChars - 1);
        }

        return Primitive.ERROR_VALUE;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "MID(text,start_num,num_chars): 返回文本串中从指定位置开始的一定数目的字符，该数目由用户指定。\n"
		+"Text:包含要提取字符的文本串。\n"
		+"Start_num:文本中需要提取字符的起始位置。文本中第一个字符的start_num为1，依此类推。\n"
		+"Num_chars:返回字符的长度。\n"
		+"备注:\n"
		+"    如果start_num大于文本长度，MID函数返回“”（空文本）。\n"
		+"    如果start_num小于文本长度，并且start_num加上num_chars大于文本长度，MID函数将从start_num指定的起始字符直至文本末的所有字符。\n"
		+"    如果start_num小于1，MID函数返回错误信息*VALUE!。\n"
		+"    如果num_chars是负数，MID函数返回错误信息*VALUE!。\n"
		+"示例:\n"
		+"MID(\"Finemore software\",10,8)返回“software”。\n"
		+"MID(\"Finemore software\",30,5)返回“”（空文本）。\n"
		+"MID(\"Finemore software\",0,8)返回*VALUE!。\n"
		+"MID(\"Finemore software\",5,-1)返回*VALUE!。";
	}
	public String getEN(){
		return "MID(text,start_num,num_chars): MID returns a specific number of characters from a text string, starting at the position you specify, based on the number of characters you specify.\n"
		+"Text is the text string containing the characters you want to extract.\n"
		+"Start_num is the position of the first character you want to extract in text. The first character in text has start_num 1, and so on.\n"
		+"Num_chars specifies the number of characters you want MID to return from text.\n"
		+"\n"
		+"Re:\n"
		+"1. If start_num is greater than the length of text, MID returns \"\" (empty text).\n"
		+"2. If start_num is less than the length of text, but start_num plus num_chars exceeds the length of text, MID returns the characters up to the end of text. \n"
		+"3. If start_num is less than 1, MID returns the #VALUE! error value. \n"
		+"4. If num_chars is negative, MID returns the #VALUE! error value.\n"
		+"\n"
		+"Example:\n"
		+"   MID(\"Finemore software\",10,8) = \"software\"\n"
		+"   MID(\"Finemore software\",30,5) = \"\" (empty text)\n"
		+"   MID(\"Finemore software\",0,8) = *VALUE!\n"
		+"   MID(\"Finemore software\",5,-1) = *VALUE!";
	}
}