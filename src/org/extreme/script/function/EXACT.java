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
		return "EXACT(text1,text2): ��������ı��Ƿ���ͬ�������ȫ��ͬ��EXACT��������TRUE�����򣬷���FALSE��EXACT�����������ִ�Сд�������Ը�ʽ�Ĳ�ͬ��ͬʱҲ��������EXACT��������������ĵ������֡�\n"
		+"Text1:��Ҫ�Ƚϵĵ�һ���ı���\n"
		+"Text2:��Ҫ�Ƚϵĵڶ����ı���\n"
		+"ʾ��:\n"
		+"EXACT(\"Spreadsheet\",\"Spreadsheet\")����TRUE��\n"
		+"EXACT(\"Spreadsheet\",\"S preadsheet\")����FALSE��\n"
		+"EXACT(\"Spreadsheet\",\"spreadsheet\")����FALSE��";
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