package org.extreme.script.function;

import java.util.Calendar;
import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class MONTHDELTA extends AbstractFunction {
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
		c.add(java.util.Calendar.MONTH, delta);
		return c.getTime();
	}
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "MONTHDELTA(date,delta):返回指定日期date后delta个月的日期。\n"
		+"示例：\n"
		+"MONTHDELTA(\"2008-08-08\", 4)等于2008-12-08。";
	}
	public String getEN(){
		return "";
	}
}