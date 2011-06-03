/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class PROPER extends AbstractFunction {

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
            StringBuffer textBuf = new StringBuffer();

            String text = "" + textObj;

            char lastCh = 0;
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);

                if (lastCh == ' ') {
                    textBuf.append(Character.toUpperCase(ch));
                } else {
                    textBuf.append(Character.toLowerCase(ch));
                }

                lastCh = ch;
            }

            return textBuf.toString();
        }

        return textObj;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "PROPER(text): 将文本中的第一个字母和所有非字母字符后的第一个字母转化成大写，其他字母变为小写。\n"
		+"Text:需要转化为文本的公式、由双引号引用的文本串或是单元格引用。\n"
		+"示例:\n"
		+"PROPER(\"Finemore Integrated Office\")等于“Finemore Integrated Office”。\n"
		+"PROPER(\"100 percent\")等于“100 Percent”。\n"
		+"PROPER(\"SpreaDSheEt\")等于“Spreadsheet”。";
	}
	public String getEN(){
		return "PROPER(text): Capitalizes the first letter in a text string and any other letters in text that follow any character other than a letter. Converts all other letters to lowercase letters.\n"
		+"Text is text enclosed in quotation , a formula that returns text, or a reference to a cell containing the text you want to partially capitalize.\n"
		+"\n"
		+"Example:\n"
		+"   PROPER(\"Finemore Integrated Office\") = \"Finemore Integrated Office\"\n"
		+"   PROPER(\"100 percent\") = \"100 Percent\"\n"
		+"   PROPER(\"SpreaDSheEt\") = \"Spreadsheet\"";
	}
}