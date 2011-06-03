/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.logging.Level;

import org.extreme.commons.util.LogUtil;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class LEFT extends AbstractFunction {

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
            } else {
            	// :BUG0003836����SQL�н�����ʱ��startNumObj�����Ϊ�ַ���
            	try {
            		num = Integer.parseInt(startNumObj.toString());
            	} catch (Exception e) {
            		LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
            	}
            }
        }

        try {
        	String str = Utils.objectToString(textObj);
        	return str.substring(0, Math.max(0, Math.min(num, str.length())));
        } catch (Exception exp) {
        }

        return "";
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "LEFT(text,num_chars): ����ָ�����ַ��������ı����еĵ�һ����ǰ�����ַ���\n"
		+"Text:������Ҫѡȡ�ַ����ı�����Ԫ�����á�\n"
		+"Num_chars:ָ�����ص��ַ������ȡ�\n"
		+"��ע:\n"
		+"    Num_chars��ֵ������ڻ����0��\n"
		+"    ���num_chars���������ı��ĳ��ȣ�LEFT�������������е��ı���\n"
		+"    ���ʡ��num_chars����Ĭ��ֵΪ1��\n"
		+"ʾ��:\n"
		+"LEFT(\"Fine software\",8)���ڡ�Fine sof����\n"
		+"LEFT(\"Fine software\")���ڡ�F����\n"
		+"�����Ԫ��A3�к��С�China������LEFT(A3,2)���ڡ�Ch����";
	}
	public String getEN(){
		return "LEFT(text,num_chars): LEFT returns the first character or characters in a text string, based on the number of characters you specify.\n"
		+"Text is the text string that contains the characters you want to extract.\n"
		+"Num_chars specifies the number of characters you want LEFT to extract.\n"
		+"\n"
		+"Re:\n"
		+"1. Num_chars must be greater than or equal to zero.\n"
		+"2. If num_chars is greater than the length of text, LEFT returns all of text.\n"
		+"3. If num_chars is omitted, it is assumed to be 1.\n"
		+"\n"
		+"Example:\n"
		+"   LEFT(\"Fine software\",8) = \"Fine sof\"\n"
		+"   LEFT(\"Fine software\") = \"F\"\n"
		+"   If value of cell A3 is \"China\", then LEFT(A3,2) = \"Ch\".";
	}
}