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
	 * @param year 年份
	 * @param month 月份
	 * @param weekOfMonth 这个月的第几周
	 * @param dayOfWeek 星期几
	 * @return
	 */
	public static java.util.Date weekdate(int year, int month, int weekOfMonth, int dayOfWeek) {
		/*
		 * 计算出 x年 y月 1号 是星期几
		 * :把时间设置为0:0:0以及毫秒数都要归为0
		 */
		Calendar c = new java.util.GregorianCalendar(year, month-1, 1);

		// 如果i_week_day =1 的话 实际上是周日
		int i_week_day = c.get(Calendar.DAY_OF_WEEK);
		
		// :先把日期折算该月第一周星期一是日期
		if (i_week_day == Calendar.SUNDAY) {
			c.add(java.util.Calendar.DATE, -6);
		} else {
			c.add(java.util.Calendar.DATE, 2 - i_week_day);
		}
		
		/*
		 * :最后一个参数为-1时表示这个周的最后一天 比如weekdate(2009,12,1,-1)表示2009年12月第一个周的最后一天。
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
		return "weekdate(year,month,weekOfMonth,dayOfWeek): 返回指定年月的指定周的周几的具体日期。\n"
		+"示例：\n"
		+"weekdate(2009,10,2,1)\n"
		+"返回的是2009年的10月的第二个周的第一天即星期一的日期，返回的是2009-10-05\n"
		+"最后一个参数dayOfWeek为-1时，表示这个周的最后一天\n"
		+"示例：\n"
		+"weekdate(2009,12,1,-1)\n"
		+"返回的是2009年的12月的第一个周的最后一天即星期天的日期，返回的是2009-12-06\n";
		
	}
	public String getEN(){
		return "";
	}
	
}