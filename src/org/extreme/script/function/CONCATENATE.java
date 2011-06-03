/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;


/**
 * Function.
 */
public class CONCATENATE extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        StringBuffer sb = new StringBuffer();
        
        for(int i = 0; i < args.length; i++) {
        	sb.append(Utils.objectToString(args[i]));
        }

        return sb.toString();
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "CONCATENATE(text1,text2,...): 将数个字符串合并成一个字符串。\n"
		+"Text1,text2,...:需要合并成单个文本的文本项，可以是字符，数字或是单元格引用。\n"
		+"备注:\n"
		+"    也可以用“&”来代替CONCATENATE函数对文本项进行合并。\n"
		+"示例:\n"
		+"CONCATENATE(\"Average \",\"Price\")等于“Average Price”。这相当于“Average”&“ ”&“Price”。\n"
		+"CONCATENATE(\"1\",\"2\")等于12。";
	}
	public String getEN(){
		return "CONCATENATE(text1,text2,...): Joins several text strings into one text string.\n"
		+"Text1, text2, ... are 1 to 30 text items to be joined into a single text item. The text items can be text strings, numbers, or single-cell references.\n"
		+"\n"
		+"Re:\n"
		+"   The \"&\" operator can be used instead of CONCATENATE to join text items.\n"
		+"\n"
		+"Example:\n"
		+"   CONCATENATE(\"Average \",\"Price\") = \"Average Price\", it equals \"Average\"&\" \"&\"Price\".\n"
		+"   CONCATENATE(\"1\",\"2\") = 12";
	}
}