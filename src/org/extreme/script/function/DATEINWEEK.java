package org.extreme.script.function;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.extreme.commons.util.DateUtils;


public class DATEINWEEK extends DateFinder {
	
	protected Date findDate(Date today, int num) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(today);
    	int year = calendar.get(Calendar.YEAR);
    	int month = calendar.get(Calendar.MONTH);
    	int day = calendar.get(Calendar.DAY_OF_MONTH);
    	
    	int weekday;
    	// :�й���Ϊ������һ�����ڵ����һ��
    	if(calendar.get(Calendar.DAY_OF_WEEK) == 1 ) {
    		weekday = 7;
    	}
    	else {
    		weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;	
    	}
    	
    	/*
    	 * :������һ������Ϊ-1����ʾ�����������ܵ����һ�졣
    	 */
    	if(num == -1) {
    		return DateUtils.createDate(year , month+1 , day + 7 - weekday);
    		//return new Date(year - 1900, month, day + 6 - weekday);
    	}
    	/*
    	 * :����ڶ�������Ϊ0�򷵻�������
    	 */
    	else if (num == 0) {
    		return DateUtils.createDate(year , month+1 , day);
    	}
    	else {
    		return DateUtils.createDate(year , month+1 , day + num - weekday);
    		//return new Date(year - 1900, month, day + num - weekday);	
    	}
	}
	
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DATEINWEEK(date, number):����������ĳһ�����ڵ��еڼ�������ڡ�\n"
		+"ʾ����\n"
		+"dateInWeek(\"2008-08-28\", 2)����2008-08-26��\n"
		+"dateInWeek(\"2008-08-28\", -1)����2008-08-31��\n"
		+"������һ������Ϊ-1�����ظ������������ڵ����һ��\n";
	}
	public String getEN(){
		return "DATEINWEEK(date, number):return the date of one day in a week.\n"
		+"Example��\n"
		+"dateInWeek(\"2008-08-28\", 2)=2008-08-26��\n"
		+"dateInWeek(\"2008-08-28\", -1)=2008-08-31��";
	}
}