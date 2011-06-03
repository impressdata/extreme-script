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
public class MINUTE extends AbstractFunction {

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
        return new Integer(day.get(Calendar.MINUTE));
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "MINUTE(serial_number):����ĳһָ��ʱ��ķ���������ֵ�ǽ���0��59֮���һ��������\n"
		+"serial_number:���������������ʱ�䡣\n"
		+"ʾ��:\n"
		+"MINUTE(\"15:36:25\")����36��\n"
		+"MINUTE(\"15:36:25\", \"HH:mm:ss\")����36��";
	}
	public String getEN(){
		return "MINUTE(serial_number): Returns the minutes of a time value. The minute is given as an integer, ranging from 0 to 59.\n"
		+"serial_number is the time that contains the minute you want to find. \n"
		+"\n"
		+"Example:\n"
		+"   MINUTE(\"15:36:25\") = 36\n"
		+"   MINUTE(\"15:36:25\", \"HH:mm:ss\") = 36";
	}
}