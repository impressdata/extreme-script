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
		return "FIND(find_text,within_text,start_num): ���ı����в�����һ�ı��������Ҵ�within_text�еĵ�һ���ַ���ʼ���ص�find_text����ʼλ�ñ�š�Ҳ����ʹ��SEAERCH�������ı����в�����һ�ı���������ͬ����FIND���������ִ�Сд����������ʹ��ͨ�����\n"
		+"Find_text:��Ҫ���ҵ��ı�������ı��ĵ�Ԫ�����á�\n"
		+"Within_text:������Ҫ�����ı����ı���Ԫ�����á�\n"
		+"Start_num:ָ�����в����ַ�����ʼλ�á���within_text����ʼλ���ַ����Ϊ1�����ʡ��start_num�������ֵΪ1��\n"
		+"��ע:\n"
		+"    ���find_text����within_text�У�FIND�������ش�����Ϣ*VALUE!��\n"
		+"    ���start_num������0��FIND�������ش�����Ϣ*VALUE!��\n"
		+"    ���start_num����within_text�ĳ��ȣ�FIND�������ش�����Ϣ*VALUE!��\n"
		+"    ���find_text�ǿհ��ı���FIND����������������ƥ���һ���ַ��������Ϊstart_num��1���ַ�����\n"
		+"ʾ��:\n"
		+"FIND(\"I\",\"Information\")����1��\n"
		+"FIND(\"i\",\"Information\")����9��\n"
		+"FIND(\"o\",\"Information\",2)����4��\n"
		+"FIND(\"o\",\"Information\",12)����*VALUE!��";
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