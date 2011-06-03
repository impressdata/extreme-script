package org.extreme.script.function;

import java.util.Calendar;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class WEEKDATE extends AbstractFunction {

	public Object run(Object[] args) {
		if (args.length < 4) {
			return Primitive.ERROR_NAME;
		}
		
		return weekdate(
				Utils.objectToNumber(args[0], false).intValue(),
				Utils.objectToNumber(args[1], false).intValue(),
				Utils.objectToNumber(args[2], false).intValue(),
				Utils.objectToNumber(args[3], false).intValue()
		);
	}

	/**
	 * @param year ���
	 * @param month �·�
	 * @param weekOfMonth ����µĵڼ���
	 * @param dayOfWeek ���ڼ�
	 * @return
	 */
	public static java.util.Date weekdate(int year, int month, int weekOfMonth, int dayOfWeek) {
		/*
		 * ����� x�� y�� 1�� �����ڼ�
		 * :��ʱ������Ϊ0:0:0�Լ���������Ҫ��Ϊ0
		 */
		Calendar c = new java.util.GregorianCalendar(year, month-1, 1);

		// ���i_week_day =1 �Ļ� ʵ����������
		int i_week_day = c.get(Calendar.DAY_OF_WEEK);
		
		// :�Ȱ�����������µ�һ������һ������
		if (i_week_day == Calendar.SUNDAY) {
			c.add(java.util.Calendar.DATE, -6);
		} else {
			c.add(java.util.Calendar.DATE, 2 - i_week_day);
		}
		
		/*
		 * :���һ������Ϊ-1ʱ��ʾ����ܵ����һ�� ����weekdate(2009,12,1,-1)��ʾ2009��12�µ�һ���ܵ����һ�졣
		 */
		if (dayOfWeek == -1) {
			c.add(java.util.Calendar.DATE, (weekOfMonth - 1) * 7 + 6);
		} 
		else {
			c.add(java.util.Calendar.DATE, (weekOfMonth - 1) * 7 + dayOfWeek - 1);		
		}
		
		return c.getTime();
	}
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "weekdate(year,month,weekOfMonth,dayOfWeek): ����ָ�����µ�ָ���ܵ��ܼ��ľ������ڡ�\n"
		+"ʾ����\n"
		+"weekdate(2009,10,2,1)\n"
		+"���ص���2009���10�µĵڶ����ܵĵ�һ�켴����һ�����ڣ����ص���2009-10-05\n"
		+"���һ������dayOfWeekΪ-1ʱ����ʾ����ܵ����һ��\n"
		+"ʾ����\n"
		+"weekdate(2009,12,1,-1)\n"
		+"���ص���2009���12�µĵ�һ���ܵ����һ�켴����������ڣ����ص���2009-12-06\n";
		
	}
	public String getEN(){
		return "";
	}
	
}