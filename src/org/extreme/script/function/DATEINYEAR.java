package org.extreme.script.function;

import java.util.Date;

public class DATEINYEAR extends DateFinder {
	
	protected Date findDate(Date scaleDate, int delta) {
		int yearBase = scaleDate.getYear();
		
		return delta < 0 ? new Date(yearBase + 1, 0, delta + 1) : new Date(yearBase, 0, delta);
	}
	
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DATEINYEAR(date, number):����������һ�굱�еڼ�������ڡ�\n"
		+"ʾ����\n"
		+"DATEINYEAR(2008,100)����2008-04-09���ȼ���DATEINYEAR(\"2008-08-08\",100)��Ҳ����2008-04-09.\n"
		+"DATEINYEAR(2008,-1)����2008-12-31���ȼ���DATEINYEAR(\"2008-08-08\",-1)��Ҳ����2008-12-31.";
	}
	public String getEN(){
		return "DATEINYEAR(date, number):return the date of one day in a year.\n"
		+"Example��\n"
		+"DATEINYEAR(2008,100)=2008-04-09��equals to DATEINYEAR(\"2008-08-08\",100)=2008-04-09.\n"
		+"DATEINYEAR(2008,-1)=2008-12-31,equals to DATEINYEAR(\"2008-08-08\",-1)=2008-12-31.";
	}
}