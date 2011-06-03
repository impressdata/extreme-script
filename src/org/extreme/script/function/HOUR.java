/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class HOUR extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        Date d = DateUtils.object2Date(args[0], false);
        GregorianCalendar day = new GregorianCalendar();
        day.setTime(d);
        return new Integer(day.get(Calendar.HOUR_OF_DAY));
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "HOUR(serial_number):返回某一指定时间的小时数。函数指定HOUR为0（0:00）到23（23:00)之间的一个整数。\n"
		+"Serial_number:包含所求小时的时间。\n"
		+"示例:\n"
		+"HOUR(\"11:32:40\")等于11。\n"
		+"HOUR(\"11:32:40\", \"HH:mm:ss\")等于11。";
	}
	public String getEN(){
		return "HOUR(serial_number): Returns the hour of a time value. The hour is given as an integer, ranging from 0 (12:00 A.M.) to 23 (11:00 P.M.).\n"
		+"Serial_number is the time that contains the hour you want to find.\n"
		+"\n"
		+"Example:\n"
		+"   HOUR(\"11:32:40\") = 11\n"
		+"   HOUR(\"11:32:40\", \"HH:mm:ss\") = 11   ";
	}
}