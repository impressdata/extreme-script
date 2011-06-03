/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class LOWER extends AbstractFunction {

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
            return ("" + value).toLowerCase();
        }

        return null;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "LOWER(text): �����еĴ�д��ĸת��ΪСд��ĸ��\n"
		+"Text:��Ҫת��ΪСд��ĸ���ı�����LOWER������ת���ı����з���ĸ���ַ���\n"
		+"ʾ��:\n"
		+"LOWER(\"A.M.10:30\")���ڡ�a.m.10:30����\n"
		+"LOWER(\"China\")���ڡ�china����";
	}
	public String getEN(){
		return "LOWER(text): Converts all uppercase letters in a text string to lowercase.\n"
		+"Text is the text you want to convert to lowercase. LOWER does not change characters in text that are not letters.\n"
		+"\n"
		+"Example:\n"
		+"   LOWER(\"A.M.10:30\") = \"a.m.10:30\"\n"
		+"   LOWER(\"China\") = \"china\"";
	}
}