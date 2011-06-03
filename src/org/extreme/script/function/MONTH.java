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
public class MONTH extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {        
        Date d = TODATE.arguments2Date(args);
        
        GregorianCalendar month = new GregorianCalendar();
        month.setTime(d);
        return new Integer(month.get(Calendar.MONTH) + 1);
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "MONTH:(serial_number)���������е��¡����ǽ���1��12֮���һ������\n"
		+"Serial_number:����������µ�����.\n"
		+"��ע:\n"
		+"ExtremScript�����ڱ���Ϊϵ������һ��ϵ��������һ����֮ƥ������ڣ��Է����û������ڽ�����ֵʽ���㡣\n"
		+"��1900������ϵͳ�У�ExtremScript���ӱ��1900��1��1�ձ���Ϊϵ����2����1900��1��2�ձ���Ϊϵ����3��\n"
		+"��1900��1��3�ձ���Ϊϵ����4�����������ơ�����1900������ϵͳ��1998��1��1�մ�Ϊϵ����35796��\n"
		+"ʾ��:\n"
		+"MONTH(\"2000/1/1\")����1��\n"
		+"MONTH(\"2006/05/05\")����5��\n"
		+"MONTH(\"1997/04/20\")����4��\n"
		+"MONTH(\"2000-1-1\", \"yyyy-MM-dd\")����1��\n"
		+"MONTH(\"2006-05-05\", \"yyyy-MM-dd\")����5��\n"
		+"MONTH(\"1997-04-20\", \"yyyy-MM-dd\")����4��\n"
		+"MONTH(35796)����5��";
	}
	public String getEN(){
		return "MONTH:(serial_number): Returns the month of a date represented by a serial number. The month is given as an integer, ranging from 1 (January) to 12 (December).\n"
		+"Serial_number is the date of the month you are trying to find.\n"
		+"\n"
		+"Re:\n"
		+"ExtremScript stores dates as sequential serial numbers so they can be used in calculations.\n"
		+"ExtremScript stores January 1, 1900 as serial number 2��January 2, 1900 as 3��\n"
		+"January 3, 1900 as 4, and so on, so January 1, 1998 as serial number 35796.\n"
		+"Example:\n"
		+"   MONTH(\"2000/1/1\") = 1\n"
		+"   MONTH(\"2006/05/05\") = 5\n"
		+"   MONTH(\"1997/04/20\") = 4\n"
		+"   MONTH(\"2000-1-1\", \"yyyy-MM-dd\") = 1\n"
		+"   MONTH(\"2006-05-05\", \"yyyy-MM-dd\") = 5\n"
		+"   MONTH(\"1997-04-20\", \"yyyy-MM-dd\") = 4\n"
		+"   MONTH(35796) = 5";
	}
}