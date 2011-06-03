package org.extreme.script.function;

import java.util.Date;

public class DATEINMONTH extends DateFinder {
	
	protected Date findDate(Date scaleDate, int delta) {
		// scaleMonth��0ʼ
		int scaleMonth = (int)(scaleDate.getMonth() );
		
		int month = delta > 0 ? scaleMonth : scaleMonth + 1;
		int date = delta > 0 ? delta : 1 + delta;
		return new Date(scaleDate.getYear(), month, date);
	}
	
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DATEINMONTH(date, number):����������ĳһ���µ��еڼ�������ڡ�\n"
		+"ʾ����\n"
		+"DATEINMONTH(\"2008-08-08\", 20) ����2008-08-20��\n"
		+"DATEINMONTH(\"2008-08-08\", -1) ����2008-08-31��";
	}
	public String getEN(){
		return "DATEINMONTH(date, number):return the date of one day in a month.\n"
		+"Example:\n"
		+"DATEINMONTH(\"2008-08-08\", 20) = 2008-08-20��\n"
		+"DATEINMONTH(\"2008-08-08\", -1) = 2008-08-31��";
	}
}