/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Convert String to integer.
 */
public class TOHEX extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return Integer.toHexString(Utils.objectToNumber(args[0], false).intValue());
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "TOHEX(int): ��һ��ʮ����������ת����ʮ�����Ʊ�ʾ���ַ�����\n"
		+"int:��ʾ��Ҫ����ת����ʮ����������\n"
		+"ʾ��:\n"
		+"TOHEX(15)���� \"f\"��\n"
		+"TOHEX(20)���� \"14\"��";
	}
	public String getEN(){
		return "";
	}
}