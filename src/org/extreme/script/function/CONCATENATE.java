/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;


/**
 * Function.
 */
public class CONCATENATE extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        StringBuffer sb = new StringBuffer();
        
        for(int i = 0; i < args.length; i++) {
        	sb.append(Utils.objectToString(args[i]));
        }

        return sb.toString();
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "CONCATENATE(text1,text2,...): �������ַ����ϲ���һ���ַ�����\n"
		+"Text1,text2,...:��Ҫ�ϲ��ɵ����ı����ı���������ַ������ֻ��ǵ�Ԫ�����á�\n"
		+"��ע:\n"
		+"    Ҳ�����á�&��������CONCATENATE�������ı�����кϲ���\n"
		+"ʾ��:\n"
		+"CONCATENATE(\"Average \",\"Price\")���ڡ�Average Price�������൱�ڡ�Average��&�� ��&��Price����\n"
		+"CONCATENATE(\"1\",\"2\")����12��";
	}
	public String getEN(){
		return "CONCATENATE(text1,text2,...): Joins several text strings into one text string.\n"
		+"Text1, text2, ... are 1 to 30 text items to be joined into a single text item. The text items can be text strings, numbers, or single-cell references.\n"
		+"\n"
		+"Re:\n"
		+"   The \"&\" operator can be used instead of CONCATENATE to join text items.\n"
		+"\n"
		+"Example:\n"
		+"   CONCATENATE(\"Average \",\"Price\") = \"Average Price\", it equals \"Average\"&\" \"&\"Price\".\n"
		+"   CONCATENATE(\"1\",\"2\") = 12";
	}
}