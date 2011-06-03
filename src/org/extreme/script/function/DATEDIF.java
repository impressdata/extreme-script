/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class DATEDIF extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 3) {
            return Primitive.ERROR_NAME;
        }

        if (args[2] instanceof String) {
        	//p:传输进来的可以是日期，也可以是String.
            Date firstDate = null;
            if(args[0] instanceof Date) {
            	firstDate = (Date) args[0];
            } else {
            	firstDate = DateUtils.object2Date(args[0], false);
            }
            Date secondDate = null;
            if(args[1] instanceof Date) {
            	secondDate = (Date) args[1];
            } else {
            	secondDate = DateUtils.object2Date(args[1], false);
            }

            int nDay = 0;
            GregorianCalendar firstArg = new GregorianCalendar();
            firstArg.setTime(firstDate);
            GregorianCalendar secondArg = new GregorianCalendar();
            secondArg.setTime(secondDate);
            if (args[2].equals("D")) {
                nDay = Math.abs((int) ((firstDate.getTime() - secondDate.getTime()) /
                        (24.0 * 60 * 60 * 1000)));
            }
            if (args[2].equals("M")) {
                if ((firstArg.get(Calendar.YEAR) == secondArg.get(Calendar.YEAR)) &&
                        (firstArg.get(Calendar.MONTH) == secondArg.get(Calendar.MONTH))) {
                    nDay = 0;
                } else {
                    nDay = Math.abs(((12 * (firstArg.get(Calendar.YEAR) -
                            secondArg.get(Calendar.YEAR))) + firstArg.get(Calendar.MONTH) -
                            secondArg.get(Calendar.MONTH))) - 1;
                }
            }
            if (args[2].equals("Y")) {
                nDay = Math.abs(firstArg.get(Calendar.YEAR) - secondArg.get(Calendar.YEAR));
            }
            if (args[2].equals("MD")) {
                nDay =  Math.abs(firstArg.get(Calendar.DAY_OF_MONTH) -
                        secondArg.get(Calendar.DAY_OF_MONTH));
            }
            if (args[2].equals("YM")) {
                nDay = Math.abs(firstArg.get(Calendar.MONTH) - secondArg.get(Calendar.MONTH));
            }
            if (args[2].equals("YD")) {
                nDay =  Math.abs(firstArg.get(Calendar.DAY_OF_YEAR) -
                        secondArg.get(Calendar.DAY_OF_YEAR));
            }

            return new Integer(nDay);
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DATEDIF(start_date,end_date,unit):返回两个指定日期间的天数、月数或年数。\n"
		+"Start_date:代表所指定时间段的初始日期。\n"
		+"End_date:代表所指定时间段的终止日期。\n"
		+"Unit:函数返回信息的类型。\n"
		+"若unit=“Y”，则DATEDIF返回指定时间段的年差数。\n"
		+"若unit=“M”，则DATEDIF返回指定时间段的月差数。\n"
		+"若unit=“D”，则DATEDIF返回指定时间段的日差数。\n"
		+"若unit=“MD”，则DATEDIF忽略年和月，返回指定时间段的日差数。\n"
		+"若unit=“YM”，则DATEDIF忽略年和日，返回指定时间段的月差数。\n"
		+"若unit=“YD”，则DATEDIF忽略年，返回指定时间段的日差数。\n"
		+"示例:\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"Y\")等于3，即在2001年2月28日与2004年3月20日之间有3个整年。\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"M\")等于36，即在2001年2月28日与2004年3月20日之间有36个整月。\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"D\")等于1116，即在2001年2月28日与2004年3月20日之间有1116个整天。\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"MD\")等于8，即忽略月和年后，2001年2月28日与2004年3月20日的差为8天。\n"
		+"DATEDIF(\"2001/1/28\",\"2004/3/20\",\"YM\")等于2，即忽略日和年后，2001年1月28日与2004年3月20日的差为2个月。\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"YD\")等于21，即忽略年后，2001年2月28日与2004年3月20日的差为21天。";
	}
	public String getEN(){
		return "DATEDIF(start_date,end_date,unit): Return number of day, or month, or year, between the two specified date.\n"
		+"Start_date is the start date.\n"
		+"End_date is the end date.\n"
		+"Unit specifys the type of the return value.\n"
		+"If unit=\"Y\", then DATEIF returns number of year between the two date.\n"
		+"If unit=\"M\", then DATEIF returns number of month between the two date.\n"
		+"If unit=\"D\", then DATEIF returns number of day between the two date.\n"
		+"If unit=\"MD\", then DATEIF returns number of day between the two date, year and month are ignored.\n"
		+"If unit=\"YM\", then DATEIF returns number of month between the two date, year and day are ignored.\n"
		+"If unit=\"YD\", then DATEIF returns number of day between the two date, year is ignored.\n"
		+"Example:\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"Y\") = 3 (years)\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"M\") = 36 (month)\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"D\") = 1116 (days)\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"MD\") = 8 (days), year and month are ignored.\n"
		+"DATEDIF(\"2001/1/28\",\"2004/3/20\",\"YM\") = 2 (month), year and day are ignored.\n"
		+"DATEDIF(\"2001/2/28\",\"2004/3/20\",\"YD\") = 21 (days), year is ignored.";
	}
}