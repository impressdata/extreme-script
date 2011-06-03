/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class CODE extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object value = args[0];
        if (value != null) {
            String valueString = (String) value;
            if (valueString.length() > 0) {
                return new Integer(valueString.charAt(0));
            }
        }

        return Primitive.ERROR_NAME;
    }
    public Type getType() {
    	return TEXT;
    }	public String getCN(){
		return "CODE(text): �����ı����е�һ���ַ������ִ��롣���صĴ����Ӧ�ڼ����ʹ�õ��ַ�����\n"
		+"Text:��Ҫ�����һ���ַ�������ı���Ԫ�����á�\n"
		+"ʾ��:\n"
		+"CODE(\"S\")����83��\n"
		+"CODE(\"Spreadsheet\")����83��";
	}
	public String getEN(){
		return "CODE(text): Returns a numeric code for the first character in a text string. The returned code corresponds to the character set used by your computer.\n"
		+"Text is the text for which you want the code of the first character.\n"
		+"\n"
		+"Example:\n"
		+"   CODE(\"S\") = 83\n"
		+"   CODE(\"Spreadsheet\") = 83";
	}
}