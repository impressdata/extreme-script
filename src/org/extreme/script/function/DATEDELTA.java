package org.extreme.script.function;

import java.util.Calendar;
import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class DATEDELTA extends AbstractFunction {
	public Object run(Object[] args) {
		if(args.length < 2) {
			return Primitive.ERROR_NAME;
		}
		
		Object dateObj = args[0];
		Object deltaObj = args[1];
		
		int delta = (Utils.objectToNumber(deltaObj, false)).intValue();
		Date date = (dateObj instanceof Date) ? (Date)dateObj : DateUtils.object2Date(dateObj, false);
		Calendar c = new java.util.GregorianCalendar();
		c.setTime(date);
		c.add(java.util.Calendar.DATE, delta);
		
		return c.getTime();
	}
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DATEDELTA(date, deltadays):返回一个日期??date后deltadays的日期。\n"
		+"deltaDays可以为正值，负值，零。\n"
		+"示例：\n"
		+"DATEDELTA(\"2008-08-08\",  -10)等于2008-07-29。\n"
		+"DATEDELTA(\"2008-08-08\",   10)等于2008-08-18。";
	}
	public String getEN(){
		return "";
	}
}