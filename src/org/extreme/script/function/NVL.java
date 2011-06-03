package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

public class NVL extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        for (int i = 0; i < args.length; i++) {
        	Object v = args[i];
        	if (v == Primitive.NULL || v == null || (v instanceof String && ((String)v).length() == 0)) {
        		continue;
        	}
        	
        	return v;
        }
        
        return Primitive.NULL;
    }
	public Type getType() {
		return OTHER;
	}	public String getCN(){
		return "NVL(value1,value2,value3,...):�����в����з��ص�һ������null��ֵ"
		+"value1:����Ϊ��������Ҳ����Ϊnull��\n"
		+"value2:����Ϊ��������Ҳ����Ϊnull��\n"
		+"ʾ��:\n"
		+"NVL(12,20)����12��\n"
		+"NVL(null,12)����12��\n"
		+"NVL(null,null)����null��\n"
		+"NVL(20,null)����20��\n"
		+"NVL(null,null,10)����10��";
	}
	public String getEN(){
		return "";
	}
}