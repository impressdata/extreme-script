package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;
/*
 * 相当于！
 */
public class REVERSE extends AbstractFunction {

	public Object run(Object[] args) {
        if (args.length != 1) {
            return Primitive.ERROR_VALUE;
        }
        Object result = Primitive.ERROR_VALUE;
        Object param = args[0];
        if (param instanceof Boolean) {
            Boolean bool= (Boolean)param;
            if(bool.equals(Boolean.FALSE)){
            	result = Boolean.TRUE;
            }else{
            	result =Boolean.FALSE;
            }    	
        }
		return result;
	}
	public Type getType() {
		return LOGIC;
	}
	public String getCN(){
		return "REVERSE(value):返回与value相反的逻辑值。\n"
		+"示例：\n"
		+"REVERSE(true)等于false。";
	}
	public String getEN(){
		return "";
	}
}