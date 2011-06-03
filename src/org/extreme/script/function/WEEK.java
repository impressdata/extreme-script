/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.extreme.script.AbstractFunction;


/**
 * Function.
 */
public class WEEK extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	Date d = TODATE.arguments2Date(args);
    	
        GregorianCalendar day = new GregorianCalendar();
        day.setTime(d);
        return new Integer(day.get(Calendar.WEEK_OF_YEAR));
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "WEEK(serial_num):返回一个代表一年中的第几周的数字。\n"
		+"Serial_num:表示输入的日期。\n"
		+"备注:\n"
		+"ExtremScript将日期保存为系列数，一个系列数代表一个与之匹配的日期，以方便用户对日期进行数值式计算。\n"
		+"在1900年日期系统中，ExtremScript电子表格将1900年1月1日保存为系列数2，将1900年1月2日保存为系列数3，\n"
		+"将1900年1月3日保存为系列数4……依此类推。如在1900年日期系统，1998年1月1日存为系列数35796。\n"
		+"示例:\n"
		+"WEEK(\"2005/1/1\")等于1。\n"
		+"WEEK(\"2005/1/6\")等于2。\n"
		+"WEEK(35796)等于1。";
	}
	public String getEN(){
		return "WEEK(serial_num): Returns the number of week, represented by serial_num, in the year. \n"
		+"Serial_num is the sequence number of the week you are trying to find.\n"
		+"\n"
		+"Re:\n"
		+"ExtremScript stores dates as sequential serial numbers so they can be used in calculations.\n"
		+"ExtremScript stores January 1, 1900 as serial number 2，January 2, 1900 as 3，\n"
		+"January 3, 1900 as 4, and so on, so January 1, 1998 as serial number 35796.\n"
		+"\n"
		+"Example:\n"
		+"   WEEK(\"2005/1/1\") = 1\n"
		+"   WEEK(\"2005/1/6\") = 2\n"
		+"   WEEK(35796) = 1";
	}
}