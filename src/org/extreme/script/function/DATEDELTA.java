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
		return "DATEDELTA(date, deltadays):����һ������??date��deltadays�����ڡ�\n"
		+"deltaDays����Ϊ��ֵ����ֵ���㡣\n"
		+"ʾ����\n"
		+"DATEDELTA(\"2008-08-08\",  -10)����2008-07-29��\n"
		+"DATEDELTA(\"2008-08-08\",   10)����2008-08-18��";
	}
	public String getEN(){
		return "";
	}
}