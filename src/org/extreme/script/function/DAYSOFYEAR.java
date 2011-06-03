package org.extreme.script.function;

import java.util.Date;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class DAYSOFYEAR extends AbstractFunction{
	public Object run(Object[] args) {
		if(args.length < 1) {
			return Primitive.ERROR_NAME;
		}
		
		Object param = args[0];
		int year;
		
		if(param instanceof Date) {
			year = ((Date)param).getYear();
		} else if(param instanceof Number) {
			year = ((Number)param).intValue();
		} else {
			return Primitive.ERROR_NAME;
		}
		
		if((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			return new Integer(366);
		} else {
			return new Integer(365);
		}
	}
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DAYSOFYEAR(year):返回某年包含的天数。\n"
		+"示例：\n"
		+"DAYSOFYEAR(2008)等于365，等价于DAYSOFYEAR(\"2008-01-01\")。";
	}
	public String getEN(){
		return "";
	}
}