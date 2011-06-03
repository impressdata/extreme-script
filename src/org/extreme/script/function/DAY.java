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
		return "DAY:(serial_number)���������е��ա�DAY�ǽ���1��31֮���һ������\n"
		+"Serial_number:����������������.\n"
		+"��ע:\n"
		+"ExtremScript�����ڱ���Ϊϵ������һ��ϵ��������һ����֮ƥ������ڣ��Է����û������ڽ�����ֵʽ���㡣\n"
		+"��1900������ϵͳ�У�ExtremScript���ӱ��1900��1��1�ձ���Ϊϵ����2����1900��1��2�ձ���Ϊϵ����3��\n"
		+"��1900��1��3�ձ���Ϊϵ����4�����������ơ�����1900������ϵͳ��1998��1��1�մ�Ϊϵ����35796��\n"
		+"ʾ��:\n"
		+"DAY(\"2000/1/1\")����1��\n"
		+"DAY(\"2006/05/05\")����5��\n"
		+"DAY(\"1997/04/20\")����20��\n"
		+"DAY(\"2000-1-1\", \"yyyy-MM-dd\")����1��\n"
		+"DAY(\"2006-05-05\", \"yyyy-MM-dd\")����5��\n"
		+"DAY(\"1997-04-20\", \"yyyy-MM-dd\")����20��\n"
		+"DAY(35796)����1��";
	}
	public String getEN(){
		return "DAY(serial_number): Returns the day of a date, represented by a serial number. The day is given as an integer ranging from 1 to 31.\n"
		+"Serial_number is the date of the day you are trying to find. \n"
		+"\n"
		+"Re:\n"
		+"ExtremScript stores dates as sequential serial numbers so they can be used in calculations.\n"
		+"ExtremScript stores January 1, 1900 as serial number 2��January 2, 1900 as 3��\n"
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