/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * ABS Function.
 */
public class DATESUBDATE extends AbstractFunction {
	public Object run(Object[] args) {
		if(args.length != 3) {
			return Primitive.ERROR_NAME;
		}
		
		Object para1 = args[0];
		Object para2 = args[1];
		Object para3 = args[2];
		Date date1 = DateUtils.object2Date(para1, false);
		Date date2 = DateUtils.object2Date(para2, false);
		String op = null;
		
		if(para3 instanceof String) {
			op = (String)para3;
		} else {
			return Primitive.ERROR_NAME;
		}
		
		return new Long(DateUtils.subtractDate(date1, date2, op));
	}
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DATESUBDATE(date1, date2, op):返回两个日期之间的时间差。\n"
		+"op表示返回的时间单位：\n"
		+"\"s\"，以秒为单位。\n"
		+"\"m\"，以分钟为单位。\n"
		+"\"h\"，以小时为单位。\n"
		+"\"d\"，以天为单位。\n"
		+"\"w\"，以周为单位。\n"
		+"示例：\n"
		+"DATESUBDATE(\"2008-08-08\", \"2008-06-06\",\"h\")等于1512。";
	}
	public String getEN(){
		return "";
	}
}