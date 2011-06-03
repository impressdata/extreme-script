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
public class WEEKDAY extends AbstractFunction {

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
        return new Integer(day.get(Calendar.DAY_OF_WEEK) - 1);
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "WEEKDAY(Serial_number):获取日期并返回星期数。返回值为介于0到6之间的某一整数，分别代表星期中的某一天（从星期日到星期六）。\n"
		+"Serial_number:输入的日期\n"
		+"备注:\n"
		+"ExtremScript将日期保存为系列数，一个系列数代表一个与之匹配的日期，以方便用户对日期进行数值式计算。\n"
		+"在1900年日期系统中，ExtremScript电子表格将1900年1月1日保存为系列数2，将1900年1月2日保存为系列数3，\n"
		+"将1900年1月3日保存为系列数4……依此类推。如在1900年日期系统，1998年1月1日存为系列数35796。\n"
		+"举例:\n"
		+"WEEKDAY(\"2005/9/10\")等于6（星期六）。\n"
		+"WEEKDAY(\"2005/9/11\")等于0（星期日）。\n"
		+"WEEKDAY(35796)等于4（星期四）。\n"
		+"";
	}
	public String getEN(){
		return "WEEKDAY(Serial_number):Returns the day of the week corresponding to a date. The day is given as an integer, ranging from 0(Sunday) to 6 (Saturday), by default.\n"
		+"Serial_number is a sequential number that represents the date of the day you are trying to find.\n"
		+"\n"
		+"Re:\n"
		+"ExtremScript stores dates as sequential serial numbers so they can be used in calculations.\n"
		+"ExtremScript stores January 1, 1900 as serial number 2，January 2, 1900 as 3，\n"
		+"January 3, 1900 as 4, and so on, so January 1, 1998 as serial number 35796.\n"
		+"\n"
		+"Example:\n"
		+"   WEEKDAY(\"2005/9/10\") = 6 (Saturday)\n"
		+"   WEEKDAY(\"2005/9/11\") = 0 (Sunday)\n"
		+"   WEEKDAY(35796) = 4 (Thursday)\n"
		+"";
	}
}