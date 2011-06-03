/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;


/**
 * Function.
 */
public class NOW extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	return DateUtils.TIMEFORMAT.format(new java.util.Date());
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "NOW():获取当前时间。\n"
		+"示例：\n"
		+"如果系统时间是15点18分38秒\n"
		+"则NOW()等于15:18:36。";
	}
	public String getEN(){
		return "NOW(): Returns the serial number of the current date and time. \n"
		+"\n"
		+"Example：\n"
		+"   If the system time is 15:18:38, then NOW() = 15:18:36.";
	}
}