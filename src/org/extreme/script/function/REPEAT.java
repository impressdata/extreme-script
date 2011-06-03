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
		return "REPEAT(text,number_times): ����ָ���Ĵ����ظ���ʾ�ı���REPEAT������������ʾͬһ�ַ��������Ե�Ԫ�������䡣\n"
		+"Text:��Ҫ�ظ���ʾ���ı�������ı��ĵ�Ԫ�����á�\n"
		+"Number_times:ָ���ı��ظ��Ĵ�������Ϊ���������number_timesΪ0��REPEAT���������ء��������ı��������number_times��������������ȡ����REPEAT���������ս��ͨ��������32767���ַ���\n"
		+"��ע:\n"
		+"    �ú����ɱ������ڹ������д����򵥵�ֱ��ͼ��\n"
		+"ʾ��:\n"
		+"REPEAT(\"$\",4)���ڡ�$$$$����\n"
		+"�����Ԫ��B10������Ϊ����á���REPEAT(B10,3)���ڡ���������á���";
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