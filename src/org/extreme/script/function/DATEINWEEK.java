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
    	// :中国认为周日是一个星期的最后一天
    	if(calendar.get(Calendar.DAY_OF_WEEK) == 1 ) {
    		weekday = 7;
    	}
    	else {
    		weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;	
    	}
    	
    	/*
    	 * :如果最后一个参数为-1，表示本日期所在周的最后一天。
    	 */
    	if(num == -1) {
    		return DateUtils.createDate(year , month+1 , day + 7 - weekday);
    		//return new Date(year - 1900, month, day + 6 - weekday);
    	}
    	/*
    	 * :如果第二个参数为0则返回他本身。
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
		return "DATEINWEEK(date, number):函数返回在某一个星期当中第几天的日期。\n"
		+"示例：\n"
		+"dateInWeek(\"2008-08-28\", 2)等于2008-08-26。\n"
		+"dateInWeek(\"2008-08-28\", -1)等于2008-08-31。\n"
		+"如果最后一个参数为-1，返回该日期所在星期的最后一天\n";
	}
	public String getEN(){
		return "DATEINWEEK(date, number):return the date of one day in a week.\n"
		+"Example：\n"
		+"dateInWeek(\"2008-08-28\", 2)=2008-08-26。\n"
		+"dateInWeek(\"2008-08-28\", -1)=2008-08-31。";
	}
}