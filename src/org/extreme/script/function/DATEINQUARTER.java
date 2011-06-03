package org.extreme.script.function;

import java.util.Date;

public class DATEINQUARTER extends DateFinder {
	
	protected Date findDate(Date scaleDate, int delta) {
		// scaleQuarter以0始
		int scaleQuarter = (int)(scaleDate.getMonth() / 3);
		// scaleMonth以0始
		int scaleMonth = scaleQuarter * 3;
		// 如果是date写0的话,就是到上个月的最后一天,如果month写12的话,就是明年的第一个月
		// new Date(109, 0, 1) -> 2009.1.1
		// new Date(109, 0, 0) -> 2008.12.31
		// new Date(109, 12, 1) -> 2010.1.1
		int month = delta > 0 ? scaleMonth : scaleMonth + 3;
		int date = delta > 0 ? delta : 1 + delta;
		return new Date(scaleDate.getYear(), month, date);
	}
	
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DATEINQUARTER(date, number): 函数返回在某一个季度当中第几天的日期。\n"
		+"示例：\n"
		+"DATEINQUARTER(\"2009-05-05\", 20)等于 2009-04-20。";
	}
	public String getEN(){
		return "DATEINQUARTER(date, number):return the date of one day in a quarter.\n"
		+"Examle:\n"
		+"DATEINQUARTER(\"2009-05-05\", 20) = 2009-04-20.";
	}
}