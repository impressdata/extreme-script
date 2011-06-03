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
public class DAY extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	Date d = TODATE.arguments2Date(args);
        
        GregorianCalendar day = new GregorianCalendar();
        day.setTime(d);
        return new Integer(day.get(Calendar.DATE));
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DAY:(serial_number)返回日期中的日。DAY是介于1和31之间的一个数。\n"
		+"Serial_number:含有所求的年的日期.\n"
		+"备注:\n"
		+"ExtremScript将日期保存为系列数，一个系列数代表一个与之匹配的日期，以方便用户对日期进行数值式计算。\n"
		+"在1900年日期系统中，ExtremScript电子表格将1900年1月1日保存为系列数2，将1900年1月2日保存为系列数3，\n"
		+"将1900年1月3日保存为系列数4……依此类推。如在1900年日期系统，1998年1月1日存为系列数35796。\n"
		+"示例:\n"
		+"DAY(\"2000/1/1\")等于1。\n"
		+"DAY(\"2006/05/05\")等于5。\n"
		+"DAY(\"1997/04/20\")等于20。\n"
		+"DAY(\"2000-1-1\", \"yyyy-MM-dd\")等于1。\n"
		+"DAY(\"2006-05-05\", \"yyyy-MM-dd\")等于5。\n"
		+"DAY(\"1997-04-20\", \"yyyy-MM-dd\")等于20。\n"
		+"DAY(35796)等于1。";
	}
	public String getEN(){
		return "DAY(serial_number): Returns the day of a date, represented by a serial number. The day is given as an integer ranging from 1 to 31.\n"
		+"Serial_number is the date of the day you are trying to find. \n"
		+"\n"
		+"Re:\n"
		+"ExtremScript stores dates as sequential serial numbers so they can be used in calculations.\n"
		+"ExtremScript stores January 1, 1900 as serial number 2，January 2, 1900 as 3，\n"
		+"January 3, 1900 as 4, and so on, so January 1, 1998 as serial number 35796.\n"
		+"\n"
		+"Example:\n"
		+"   DAY(\"2000/1/1\") = 1\n"
		+"   DAY(\"2006/05/05\") = 5\n"
		+"   DAY(\"1997/04/20\") = 20\n"
		+"   DAY(\"2000-1-1\", \"yyyy-MM-dd\") = 1\n"
		+"   DAY(\"2006-05-05\", \"yyyy-MM-dd\") = 5\n"
		+"   DAY(\"1997-04-20\", \"yyyy-MM-dd\") = 20   \n"
		+"   DAY(35796) = 1";
	}
}