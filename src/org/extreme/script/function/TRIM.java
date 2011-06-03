/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class TRIM extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object textObj = args[0];
        if (textObj != null) {
            return ("" + textObj).trim();
        }

        return textObj;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "TRIM(text): ����ı������пո񣬵��ʼ�ĵ����ո���⣬Ҳ�����ڴ��в�����ո���ı���\n"
		+"Text:��Ҫ����ո���ı���\n"
		+"ʾ��:\n"
		+"TRIM(\" Monthly Report\")����Monthly Report��";
	}
	public String getEN(){
		return "TRIM(text): Removes all spaces from text except for single spaces between words. Use TRIM on text that you have received from another application that may have irregular spacing.\n"
		+"Text is the text from which you want spaces removed.\n"
		+"\n"
		+"Example:\n"
		+"   TRIM(\" Monthly Report\") = \"Monthly Report\"";
	}
}