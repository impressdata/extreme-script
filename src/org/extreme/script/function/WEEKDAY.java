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
		return "WEEKDAY(Serial_number):��ȡ���ڲ�����������������ֵΪ����0��6֮���ĳһ�������ֱ���������е�ĳһ�죨�������յ�����������\n"
		+"Serial_number:���������\n"
		+"��ע:\n"
		+"ExtremScript�����ڱ���Ϊϵ������һ��ϵ��������һ����֮ƥ������ڣ��Է����û������ڽ�����ֵʽ���㡣\n"
		+"��1900������ϵͳ�У�ExtremScript���ӱ��1900��1��1�ձ���Ϊϵ����2����1900��1��2�ձ���Ϊϵ����3��\n"
		+"��1900��1��3�ձ���Ϊϵ����4�����������ơ�����1900������ϵͳ��1998��1��1�մ�Ϊϵ����35796��\n"
		+"����:\n"
		+"WEEKDAY(\"2005/9/10\")����6������������\n"
		+"WEEKDAY(\"2005/9/11\")����0�������գ���\n"
		+"WEEKDAY(35796)����4�������ģ���\n"
		+"";
	}
	public String getEN(){
		return "WEEKDAY(Serial_number):Returns the day of the week corresponding to a date. The day is given as an integer, ranging from 0(Sunday) to 6 (Saturday), by default.\n"
		+"Serial_number is a sequential number that represents the date of the day you are trying to find.\n"
		+"\n"
		+"Re:\n"
		+"ExtremScript stores dates as sequential serial numbers so they can be used in calculations.\n"
		+"ExtremScript stores January 1, 1900 as serial number 2��January 2, 1900 as 3��\n"
		+"January 3, 1900 as 4, and so on, so January 1, 1998 as serial number 35796.\n"
		+"\n"
		+"Example:\n"
		+"   WEEKDAY(\"2005/9/10\") = 6 (Saturday)\n"
		+"   WEEKDAY(\"2005/9/11\") = 0 (Sunday)\n"
		+"   WEEKDAY(35796) = 4 (Thursday)\n"
		+"";
	}
}