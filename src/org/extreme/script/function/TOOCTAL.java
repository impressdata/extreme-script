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
public class TOOCTAL extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return Integer.toOctalString(Utils.objectToNumber(args[0], false).intValue());
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "TOOCTAL(int): ��һ��ʮ����������ת���ɰ˽��Ʊ�ʾ���ַ�����\n"
		+"int:��ʾ��Ҫ����ת����ʮ����������\n"
		+"ʾ��:\n"
		+"TOOCTAL(10)���� \"12\"��\n"
		+"TOOCTAL(20)���� \"24\"��";
	}
	public String getEN(){
		return "";
	}
}