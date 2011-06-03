/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class DAYVALUE extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Date d = DateUtils.object2Date(args[0], false);
        return new Integer((int) (d.getTime() / (1000.0 * 24 * 60 * 60)) + 25570);
	}
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DAYVALUE(date):返回1900年至 date日期所经历的天数。\n"
		+"示例：\n"
		+"DAYVALUE(\"2008/08/08\")等于39668。";
	}
	public String getEN(){
		return "";
	}
}