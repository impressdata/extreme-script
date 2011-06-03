package org.extreme.script.function;

import java.util.Date;

public class DATEINQUARTER extends DateFinder {
	
	protected Date findDate(Date scaleDate, int delta) {
		// scaleQuarter��0ʼ
		int scaleQuarter = (int)(scaleDate.getMonth() / 3);
		// scaleMonth��0ʼ
		int scaleMonth = scaleQuarter * 3;
		// �����dateд0�Ļ�,���ǵ��ϸ��µ����һ��,���monthд12�Ļ�,��������ĵ�һ����
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
		return "DATEINQUARTER(date, number): ����������ĳһ�����ȵ��еڼ�������ڡ�\n"
		+"ʾ����\n"
		+"DATEINQUARTER(\"2009-05-05\", 20)���� 2009-04-20��";
	}
	public String getEN(){
		return "DATEINQUARTER(date, number):return the date of one day in a quarter.\n"
		+"Examle:\n"
		+"DATEINQUARTER(\"2009-05-05\", 20) = 2009-04-20.";
	}
}