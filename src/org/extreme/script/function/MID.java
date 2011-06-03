/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class MID extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 3) {
            return Primitive.ERROR_NAME;
        }

        Object textObj = args[0];

        int startNum = -1;
        Object startNumObj = args[1];
        if (startNumObj instanceof Number) {
            startNum = ((Number) startNumObj).intValue();
        }

        int numChars = -1;
        Object numCharsObj = args[2];
        if (numCharsObj instanceof Number) {
            numChars = ((Number) numCharsObj).intValue();
        }
        if (startNum < 1 || numChars < 0) {
            return Primitive.ERROR_VALUE;
        }

        if (textObj != null) {
            String text = "" + textObj;
            if (startNum > text.length()) {
                return "";
            }
            if(startNum+numChars-1 >text.length()){
            	return text.substring(startNum - 1);
            }

            return text.substring(startNum - 1, startNum + numChars - 1);
        }

        return Primitive.ERROR_VALUE;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "MID(text,start_num,num_chars): �����ı����д�ָ��λ�ÿ�ʼ��һ����Ŀ���ַ�������Ŀ���û�ָ����\n"
		+"Text:����Ҫ��ȡ�ַ����ı�����\n"
		+"Start_num:�ı�����Ҫ��ȡ�ַ�����ʼλ�á��ı��е�һ���ַ���start_numΪ1���������ơ�\n"
		+"Num_chars:�����ַ��ĳ��ȡ�\n"
		+"��ע:\n"
		+"    ���start_num�����ı����ȣ�MID�������ء��������ı�����\n"
		+"    ���start_numС���ı����ȣ�����start_num����num_chars�����ı����ȣ�MID��������start_numָ������ʼ�ַ�ֱ���ı�ĩ�������ַ���\n"
		+"    ���start_numС��1��MID�������ش�����Ϣ*VALUE!��\n"
		+"    ���num_chars�Ǹ�����MID�������ش�����Ϣ*VALUE!��\n"
		+"ʾ��:\n"
		+"MID(\"Finemore software\",10,8)���ء�software����\n"
		+"MID(\"Finemore software\",30,5)���ء��������ı�����\n"
		+"MID(\"Finemore software\",0,8)����*VALUE!��\n"
		+"MID(\"Finemore software\",5,-1)����*VALUE!��";
	}
	public String getEN(){
		return "MID(text,start_num,num_chars): MID returns a specific number of characters from a text string, starting at the position you specify, based on the number of characters you specify.\n"
		+"Text is the text string containing the characters you want to extract.\n"
		+"Start_num is the position of the first character you want to extract in text. The first character in text has start_num 1, and so on.\n"
		+"Num_chars specifies the number of characters you want MID to return from text.\n"
		+"\n"
		+"Re:\n"
		+"1. If start_num is greater than the length of text, MID returns \"\" (empty text).\n"
		+"2. If start_num is less than the length of text, but start_num plus num_chars exceeds the length of text, MID returns the characters up to the end of text. \n"
		+"3. If start_num is less than 1, MID returns the #VALUE! error value. \n"
		+"4. If num_chars is negative, MID returns the #VALUE! error value.\n"
		+"\n"
		+"Example:\n"
		+"   MID(\"Finemore software\",10,8) = \"software\"\n"
		+"   MID(\"Finemore software\",30,5) = \"\" (empty text)\n"
		+"   MID(\"Finemore software\",0,8) = *VALUE!\n"
		+"   MID(\"Finemore software\",5,-1) = *VALUE!";
	}
}