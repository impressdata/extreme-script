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
		return "DAYSOFMONTH(date):返回从1900年1月后某年某月包含的天数。\n"
		+"示例：\n"
		+"DAYSOFMONTH(\"1900-02-01\")等于28。\n"
		+"DAYSOFMONTH(\"2008/04/04\")等于30。";
	}
	public String getEN(){
		return "";
	}
}