/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class UPPER extends AbstractFunction {

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
            return ("" + value).toUpperCase();
        }

        return null;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "UPPER(text): ���ı������е��ַ�ת��Ϊ��д��\n"
		+"Text:��Ҫת��Ϊ��д�ַ����ı������ǰ����ı��ĵ�Ԫ�����á�\n"
		+"ʾ��:\n"
		+"UPPER(\"notes\")���ڡ�NOTES����\n"
		+"�����Ԫ��E5��ֵΪ��Examples������UPPER(E5)���ڡ�EXAMPLES����";
	}
	public String getEN(){
		return "UPPER(text): Converts text to uppercase.\n"
		+"Text is the text you want converted to uppercase. Text can be a reference or text string.\n"
		+"\n"
		+"Example:\n"
		+"   UPPER(\"notes\") = \"NOTES\"\n"
		+"   If the value of cell E5 is \"Examples\", then UPPER(E5) = \"EXAMPLES\".";
	}
}