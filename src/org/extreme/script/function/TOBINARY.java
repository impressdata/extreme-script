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
public class TOBINARY extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return Integer.toBinaryString(Utils.objectToNumber(args[0], false).intValue());
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "TOBINARY(int): ��һ��ʮ����������ת���ɶ����Ʊ�ʾ���ַ�����\n"
		+"int:��ʾ��Ҫ����ת����ʮ����������\n"
		+"ʾ��:\n"
		+"TOBINARY(10)���� \"1010\"��\n"
		+"TOBINARY(20)���� \"10100\"��";
	}
	public String getEN(){
		return "";
	}
}