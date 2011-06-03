package org.extreme.script.function;

import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;


public class DAYSOFQUARTER extends AbstractFunction {
	public Object run(Object[] args) {
		if (args.length == 0 ) {
			return new Integer(0);
		}
		java.util.Date date = args.length == 0 ? new Date() : DateUtils.object2Date(args[0], false);
		int month = date.getMonth();
		
		// ֻ��һ�����б仯�����£��������ǹ̶���
		if ( month == 1 || month == 2 || month ==3) {
			return new Integer((int)DateUtils.subtractDate(
					new Date(date.getYear(), 3, date.getDate()), 
					new Date(date.getYear(), 0, date.getDate()),
					"d"
			));
		} else if ( month == 4 || month == 5 || month == 6) {
			return new Integer((int)91);
		} else if ( month == 7 || month == 8 || month == 9) {
			return new Integer((int)92);
		} else if ( month == 10 || month == 11 || month == 12) {
			return new Integer((int)92);
		} else  return null;
	}
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DAYSOFQUARTER(date): ���ش�1900��1�º�ĳ��ĳ���ȵ�������\n"
		+"ʾ����\n"
		+"DAYSOFQUARTER(\"2009-02-01\")����90��\n"
		+"DAYSOFQUARTER(\"2009/05/05\")����91��";
	}
	public String getEN(){
		return "DAYSOFQUARTER(date):return the days of one day in a quarter.\n"
		+"Example:\n"
		+"DAYSOFQUARTER(\"2009-02-01\") = 90.\n"
		+"DAYSOFQUARTER(\"2009/05/05\") = 91.";
	}
}