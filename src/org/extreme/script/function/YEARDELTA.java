package org.extreme.script.function;

import java.util.Calendar;
import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class YEARDELTA extends AbstractFunction {
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
		c.add(java.util.Calendar.YEAR, delta);

		return c.getTime();
	}
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "YEARDELTA(date, delta):返回指定日期后delta年的日期。\n"
		+"示例：\n"
		+"YEARDELTA(\"2008-10-10\",10)等于2018-10-10。";
	}
	public String getEN(){
		return "";
	}
}