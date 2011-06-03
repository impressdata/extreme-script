/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class REPEAT extends AbstractFunction {

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
            Object numObj = args[1];
            if (numObj instanceof Number) {
                num = Math.abs(((Number) numObj).intValue());
            }
        }

        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < num; i++) {
            sf.append(textObj);
        }

        return sf.toString();
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "REPEAT(text,number_times): 根据指定的次数重复显示文本。REPEAT函数可用来显示同一字符串，并对单元格进行填充。\n"
		+"Text:需要重复显示的文本或包含文本的单元格引用。\n"
		+"Number_times:指定文本重复的次数，且为正数。如果number_times为0，REPEAT函数将返回“”（空文本）。如果number_times不是整数，将被取整。REPEAT函数的最终结果通常不大于32767个字符。\n"
		+"备注:\n"
		+"    该函数可被用于在工作表中创建简单的直方图。\n"
		+"示例:\n"
		+"REPEAT(\"$\",4)等于“$$$$”。\n"
		+"如果单元格B10的内容为“你好”，REPEAT(B10,3)等于“你好你好你好”。";
	}
	public String getEN(){
		return "REPEAT(text,number_times): Repeats text a given number of times. Use REPEAT to fill a cell with a number of instances of a text string.\n"
		+"Text is the text you want to repeat.\n"
		+"Number_times is a positive number specifying the number of times to repeat text.\n"
		+"\n"
		+"Re:\n"
		+"1. If number_times is 0 (zero), REPEAT returns \"\" (empty text).\n"
		+"2. The result of the REPEAT function cannot be longer than 32,767 characters, or REPEAT returns #VALUE!.\n"
		+"3. It could be used to produce histogram.\n"
		+"\n"
		+"Example:\n"
		+"   REPEAT(\"$\",4) = \"$$$$\"";
	}
}