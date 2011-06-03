/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.logging.Level;

import org.extreme.commons.util.LogUtil;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class LEFT extends AbstractFunction {

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
            } else {
            	// :BUG0003836，在SQL中解析的时候startNumObj会解析为字符型
            	try {
            		num = Integer.parseInt(startNumObj.toString());
            	} catch (Exception e) {
            		LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
            	}
            }
        }

        try {
        	String str = Utils.objectToString(textObj);
        	return str.substring(0, Math.max(0, Math.min(num, str.length())));
        } catch (Exception exp) {
        }

        return "";
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "LEFT(text,num_chars): 根据指定的字符数返回文本串中的第一个或前几个字符。\n"
		+"Text:包含需要选取字符的文本串或单元格引用。\n"
		+"Num_chars:指定返回的字符串长度。\n"
		+"备注:\n"
		+"    Num_chars的值必须等于或大于0。\n"
		+"    如果num_chars大于整个文本的长度，LEFT函数将返回所有的文本。\n"
		+"    如果省略num_chars，则默认值为1。\n"
		+"示例:\n"
		+"LEFT(\"Fine software\",8)等于“Fine sof”。\n"
		+"LEFT(\"Fine software\")等于“F”。\n"
		+"如果单元格A3中含有“China”，则LEFT(A3,2)等于“Ch”。";
	}
	public String getEN(){
		return "LEFT(text,num_chars): LEFT returns the first character or characters in a text string, based on the number of characters you specify.\n"
		+"Text is the text string that contains the characters you want to extract.\n"
		+"Num_chars specifies the number of characters you want LEFT to extract.\n"
		+"\n"
		+"Re:\n"
		+"1. Num_chars must be greater than or equal to zero.\n"
		+"2. If num_chars is greater than the length of text, LEFT returns all of text.\n"
		+"3. If num_chars is omitted, it is assumed to be 1.\n"
		+"\n"
		+"Example:\n"
		+"   LEFT(\"Fine software\",8) = \"Fine sof\"\n"
		+"   LEFT(\"Fine software\") = \"F\"\n"
		+"   If value of cell A3 is \"China\", then LEFT(A3,2) = \"Ch\".";
	}
}