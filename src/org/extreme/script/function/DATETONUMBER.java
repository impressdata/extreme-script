package org.extreme.script.function;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Convert date to number. date.getTime();
 */
public class DATETONUMBER extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return new Long(DateUtils.object2Date(args[0], false).getTime());
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DATETONUMBER(date):返回自 1970 年 1 月 1 日 00:00:00 GMT 经过的毫秒数。\n"
		+"示例：\n"
		+"DATETONUMBER(\"2008-08-08\")等于1218124800000。";
	}
	public String getEN(){
		return "";
	}
}