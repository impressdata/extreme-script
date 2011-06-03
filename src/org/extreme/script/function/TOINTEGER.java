/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.logging.Level;

import org.extreme.commons.util.LogUtil;
import org.extreme.commons.util.StringUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Convert String to integer.
 */
public class TOINTEGER extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return args.length > 0 ? number2Int(object2Number(args[0])) : new Integer(0);
    }
    
    /*
     * :把object转成Number
     */
    private Number object2Number(Object obj) {
    	if (obj == null) {
    		return new Integer(0);
    	}
    	
    	String strObject = obj.toString();
    	if (StringUtils.isBlank(strObject)) {
    		return new Integer(0);
    	}
    	
    	try {
        	return Double.valueOf(strObject);
        } catch(NumberFormatException nExp) {
            LogUtil.getLogger().log(Level.WARNING,nExp.getMessage(), nExp);
        }
        
        return new Integer(0);
    }
    
    /*
     * :把Number转成Integer
     */
    private Number number2Int(Number num) {
    	if (num == null) {
    		return new Integer(0);
    	}
    	
    	int intValue = num.intValue();
    	long longValue = num.longValue();
    	
    	if (intValue == longValue) {
    		return new Integer(intValue);
    	} else {
    		return new Long(longValue);
    	}
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "TOINTEGER(text): 将文本转换成Integer对象。\n"
		+"Text:需要转换的文本。\n"
		+"示例:\n"
		+"TOINTEGER(\"123\")等于 new Integer(123)。";
	}
	public String getEN(){
		return "TOINTEGER(text): Convert text to Integer object.\n"
		+"\n"
		+"Example:\n"
		+"TODOUBLE(\"123\") = new Integer(123)。";
	}
}