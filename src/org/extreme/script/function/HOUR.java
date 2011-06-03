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
		return "HOUR(serial_number):����ĳһָ��ʱ���Сʱ��������ָ��HOURΪ0��0:00����23��23:00)֮���һ��������\n"
		+"Serial_number:��������Сʱ��ʱ�䡣\n"
		+"ʾ��:\n"
		+"HOUR(\"11:32:40\")����11��\n"
		+"HOUR(\"11:32:40\", \"HH:mm:ss\")����11��";
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