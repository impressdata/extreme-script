/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class RIGHT extends AbstractFunction {

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
            Object startNumObj = args[1];
            if (startNumObj instanceof Number) {
                num = ((Number) startNumObj).intValue();
            }
        }
        try {
        	String str = Utils.objectToString(textObj);
        	return str.substring(str.length() - Math.max(0, Math.min(num, str.length())));
        } catch (Exception exp) {
        }

       return "";
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "RIGHT(text,num_chars): ����ָ�����ַ������ҿ�ʼ�����ı����е����һ���򼸸��ַ���\n"
		+"Text:������Ҫ��ȡ�ַ����ı�����Ԫ�����á�\n"
		+"Num_chars:ָ��RIGHT�������ı�������ȡ���ַ�����Num_chars����С��0�����num_chars�����ı������ȣ�RIGHT���������������ı��������ָ��num_chars����Ĭ��ֵΪ1��\n"
		+"ʾ��:\n"
		+"RIGHT(\"It is interesting\",6)���ڡ�esting����\n"
		+"RIGHT(\"Share Holder\")���ڡ�r����\n"
		+"RIGHT(\"Huge sale\",4)���ڡ�sale����";
	}
	public String getEN(){
		return "RIGHT(text,num_chars): RIGHT returns the last character or characters in a text string, based on the number of characters you specify.\n"
		+"Text is the text string containing the characters you want to extract.\n"
		+"Num_chars specifies the number of characters you want RIGHT to extract.\n"
		+"\n"
		+"Re:\n"
		+"1. Num_chars must be greater than or equal to zero. \n"
		+"2. If num_chars is greater than the length of text, RIGHT returns all of text. \n"
		+"3. If num_chars is omitted, it is assumed to be 1. \n"
		+"\n"
		+"Example:\n"
		+"   RIGHT(\"It is interesting\",6) = \"esting\"\n"
		+"   RIGHT(\"Share Holder\") = \"r\"\n"
		+"   RIGHT(\"Huge sale\",4) = \"sale\"";
	}
}