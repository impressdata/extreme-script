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
public class SECOND extends AbstractFunction {

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
        return new Integer(day.get(Calendar.SECOND));
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "MINUTE(serial_number):����ĳһָ��ʱ�����������ֵ�ǽ���0��59֮���һ��������\n"
		+"Serial_number:��������������ʱ�䡣\n"
		+"ʾ��:\n"
		+"SECOND(\"15:36:25\")����25��\n"
		+"SECOND(\"15:36:25\", \"HH:mm:ss\")����25��";
	}
	public String getEN(){
		return "MINUTE(serial_number): Returns the seconds of a time value. The second is given as an integer in the range 0 (zero) to 59.\n"
		+"Serial_number is the time that contains the seconds you want to find.\n"
		+"Example:\n"
		+"   SECOND(\"15:36:25\") = 25\n"
		+"   SECOND(\"15:36:25\", \"HH:mm:ss\") = 25   ";
	}
}