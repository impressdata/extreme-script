/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class EXACT extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }

        return new Boolean(ComparatorUtils.equals(args[0], args[1]));
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "EXACT(text1,text2): 检测两组文本是否相同。如果完全相同，EXACT函数返回TRUE；否则，返回FALSE。EXACT函数可以区分大小写，但忽略格式的不同。同时也可以利用EXACT函数来检测输入文档的文字。\n"
		+"Text1:需要比较的第一组文本。\n"
		+"Text2:需要比较的第二组文本。\n"
		+"示例:\n"
		+"EXACT(\"Spreadsheet\",\"Spreadsheet\")等于TRUE。\n"
		+"EXACT(\"Spreadsheet\",\"S preadsheet\")等于FALSE。\n"
		+"EXACT(\"Spreadsheet\",\"spreadsheet\")等于FALSE。";
	}
	public String getEN(){
		return "EXACT(text1,text2): Compares two text strings and returns TRUE if they are exactly the same, FALSE otherwise. EXACT is case-sensitive but ignores formatting differences. Use EXACT to test text being entered into a document.\n"
		+"Text1 is the first text string.\n"
		+"Text2 is the second text string.\n"
		+"\n"
		+"Example:\n"
		+"   EXACT(\"Spreadsheet\",\"Spreadsheet\") = TRUE\n"
		+"   EXACT(\"Spreadsheet\",\"S preadsheet\") = FALSE\n"
		+"   EXACT(\"Spreadsheet\",\"spreadsheet\") = FALSE";
	}
}