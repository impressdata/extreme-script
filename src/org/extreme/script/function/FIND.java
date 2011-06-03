/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class FIND extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }

        Object findObj = args[0];
        Object withinObj = args[1];
        int startNum = 0;
        if(args.length > 2) {
        	Object startNumObj = args[2];
            if (startNumObj instanceof Number) {
                startNum = ((Number) startNumObj).intValue();
            }
        }

        int index = ("" + withinObj).indexOf("" + findObj, startNum);
        index += 1;

        return new Integer(index);
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "FIND(find_text,within_text,start_num): 在文本串中查找另一文本串，并且从within_text中的第一个字符开始返回到find_text的起始位置编号。也可以使用SEAERCH函数在文本串中查找另一文本串，所不同的是FIND函数能区分大小写，但不允许使用通配符。\n"
		+"Find_text:需要查找的文本或包含文本的单元格引用。\n"
		+"Within_text:包含需要查找文本的文本或单元格引用。\n"
		+"Start_num:指定进行查找字符的起始位置。在within_text中起始位置字符编号为1。如果省略start_num，则假设值为1。\n"
		+"备注:\n"
		+"    如果find_text不在within_text中，FIND函数返回错误信息*VALUE!。\n"
		+"    如果start_num不大于0，FIND函数返回错误信息*VALUE!。\n"
		+"    如果start_num大于within_text的长度，FIND函数返回错误信息*VALUE!。\n"
		+"    如果find_text是空白文本，FIND函数将在搜索串中匹配第一个字符（即编号为start_num或1的字符）。\n"
		+"示例:\n"
		+"FIND(\"I\",\"Information\")等于1。\n"
		+"FIND(\"i\",\"Information\")等于9。\n"
		+"FIND(\"o\",\"Information\",2)等于4。\n"
		+"FIND(\"o\",\"Information\",12)等于*VALUE!。";
	}
	public String getEN(){
		return "FIND(find_text,within_text,start_num): FIND finds one text string (find_text) within another text string (within_text), and returns the number of the starting position of find_text, from the first character of within_text. You can also use SEARCH to find one text string within another, but unlike SEARCH, FIND is case sensitive and doesn\'t allow wildcard characters.\n"
		+"Find_text is the text you want to find.\n"
		+"Within_text is the text containing the text you want to find.\n"
		+"Start_num specifies the character at which to start the search. The first character in within_text is character number 1. If you omit start_num, it is assumed to be 1.\n"
		+"\n"
		+"Re:\n"
		+"1. If start_num is not greater than zero, FIND and FINDB return the #VALUE! error value. \n"
		+"2. If start_num is not greater than zero, FIND and FINDB return the #VALUE! error value. \n"
		+"3. If start_num is greater than the length of within_text, FIND and FINDB return the #VALUE! error value.\n"
		+"4. If find_text is \"\" (empty text), FIND matches the first character in the search string (that is, the character numbered start_num or 1). \n"
		+"\n"
		+"Example:\n"
		+"   FIND(\"I\",\"Information\") = 1\n"
		+"   FIND(\"i\",\"Information\") = 9\n"
		+"   FIND(\"o\",\"Information\",2) = 4\n"
		+"   FIND(\"o\",\"Information\",12) = *VALUE!";
	}
}