package org.extreme.script.function;

import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;


public class DAYSOFMONTH extends AbstractFunction {
	public Object run(Object[] args) {
		java.util.Date date = args.length == 0 ? new Date() : DateUtils.object2Date(args[0], false);
		
		return new Integer((int)DateUtils.subtractDate(
				new Date(date.getYear(), date.getMonth() + 1, date.getDate()), 
				new Date(date.getYear(), date.getMonth(), date.getDate()),
				"d"
		));
	}
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DAYSOFMONTH(date):���ش�1900��1�º�ĳ��ĳ�°�����������\n"
		+"ʾ����\n"
		+"DAYSOFMONTH(\"1900-02-01\")����28��\n"
		+"DAYSOFMONTH(\"2008/04/04\")����30��";
	}
	public String getEN(){
		return "";
	}
}