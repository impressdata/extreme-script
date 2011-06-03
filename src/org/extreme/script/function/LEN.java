/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class LEN extends AbstractFunction {

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
            return new Integer(("" + value).length());
        } else {
            return new Integer(0);
        }
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "LEN(text): �����ı����е��ַ�����\n"
		+"Text:��Ҫ���䳤�ȵ��ı����ո�Ҳ��Ϊ�ַ���\n"
		+"ʾ��:\n"
		+"LEN(\"Evermore software\")����17��\n"
		+"LEN(\" \")����1��";
	}
	public String getEN(){
		return "LEN(text): LEN returns the number of characters in a text string.\n"
		+"Text is the text whose length you want to find. Spaces count as characters.\n"
		+"\n"
		+"Example:\n"
		+"   LEN(\"Evermore software\") = 17\n"
		+"   LEN(\" \") = 1";
	}
}