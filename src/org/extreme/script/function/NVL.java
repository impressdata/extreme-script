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
		return "NVL(value1,value2,value3,...):在所有参数中返回第一个不是null的值"
		+"value1:可以为任意数，也可以为null。\n"
		+"value2:可以为任意数，也可以为null。\n"
		+"示例:\n"
		+"NVL(12,20)等于12。\n"
		+"NVL(null,12)等于12。\n"
		+"NVL(null,null)等于null。\n"
		+"NVL(20,null)等于20。\n"
		+"NVL(null,null,10)等于10。";
	}
	public String getEN(){
		return "";
	}
}