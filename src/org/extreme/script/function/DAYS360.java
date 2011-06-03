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
public class DAYS360 extends AbstractFunction {
	
    public Object run(Object[] args) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }
        int result;
        Object[] param = {null, null, null};
        param[0] = args[0];
        param[1] = args[1];
        Date firstDate = new Date();
        Date secondDate = new Date();
        GregorianCalendar firstArg = new GregorianCalendar();
        GregorianCalendar secondArg = new GregorianCalendar();
        if (param[0] instanceof String) {
            firstDate = DateUtils.object2Date(param[0], false);
        }
        if (param[1] instanceof String) {
            secondDate = DateUtils.object2Date(param[1], false);
        }
        if (param[0] instanceof Number) {
            firstDate.setTime((((Number) param[0]).longValue() - 25569) * 1000 * 24 * 60 * 60);
        }
        if (param[1] instanceof Number) {
            secondDate.setTime((((Number) param[1]).longValue() - 25569) * 1000 * 24 * 60 * 60);
        }
        firstArg.setTime(firstDate);
        secondArg.setTime(secondDate);

        if (args.length < 1) {
            if (firstArg.get(Calendar.DAY_OF_MONTH) == 31) {
                firstDate.setTime(firstDate.getTime() - 1000 * 24 * 60 * 60);
                firstArg.setTime(firstDate);
            }
            if ((secondArg.get(Calendar.DAY_OF_MONTH) == 31) &&
                    (firstArg.get(Calendar.DAY_OF_MONTH) < 30)) {
                secondDate.setTime(secondDate.getTime() + 1000 * 24 * 60 * 60);
                secondArg.setTime(secondDate);
            }
            if ((secondArg.get(Calendar.DAY_OF_MONTH) == 31) &&
                    (firstArg.get(Calendar.DAY_OF_MONTH) == 30)) {
                secondDate.setTime(secondDate.getTime() - 1000 * 24 * 60 * 60);
                secondArg.setTime(secondDate);
            }
        } else {
            param[2] = args[2];
            if (param[2] instanceof String) {
                if (param[2].toString().equals("FALSE")) {
                    if (firstArg.get(Calendar.DAY_OF_MONTH) == 31) {
                        firstDate.setTime(firstDate.getTime() - 1000 * 24 * 60 * 60);
                        firstArg.setTime(firstDate);
                    }
                    if ((secondArg.get(Calendar.DAY_OF_MONTH) == 31) &&
                            (firstArg.get(Calendar.DAY_OF_MONTH) < 30)) {
                        secondDate.setTime(secondDate.getTime() + 1000 * 24 * 60 * 60);
                        secondArg.setTime(secondDate);
                    }
                    if ((secondArg.get(Calendar.DAY_OF_MONTH) == 31) &&
                            (firstArg.get(Calendar.DAY_OF_MONTH) == 30)) {
                        secondDate.setTime(secondDate.getTime() - 1000 * 24 * 60 * 60);
                        secondArg.setTime(secondDate);
                    }

                }
            }
            if (param.toString().equals("TRUE")) {
                if (firstArg.get(Calendar.DAY_OF_MONTH) == 31) {
                    firstDate.setTime(firstDate.getTime() - 1000 * 24 * 60 * 60);
                    firstArg.setTime(firstDate);
                }
                if (secondArg.get(Calendar.DAY_OF_MONTH) == 31) {
                    secondDate.setTime(secondDate.getTime() - 1000 * 24 * 60 * 60);
                    secondArg.setTime(secondDate);
                }
            }
        }
        result = 360 * (secondArg.get(Calendar.YEAR) - firstArg.get(Calendar.YEAR)) +
                30 * (secondArg.get(Calendar.MONTH) - firstArg.get(Calendar.MONTH)) +
                secondArg.get(Calendar.DAY_OF_MONTH) - firstArg.get(Calendar.DAY_OF_MONTH);

        //
        return new Integer(result);
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DAYS360(start_date,end_date,method):按照一年 360 天的算法（每个月以 30 天计，一年共计 12 个月），\n"
		+"返回两日期间相差的天数，这在会计计算中将会用到。如果财务系统是基于一年 12 个月，每月 30 天，\n"
		+"可用此函数帮助计算支付款项。\n"
		+"Start_date 和 end_date :是用于计算期间天数的起止日期。\n"
		+"Method : 它指定了在计算中是采用欧洲方法还是美国方法。\n"
		+"Method 定义 :\n"
		+"FALSE或忽略    美国方法 (NASD)。如果起始日期是一个月的 31 号，则等于同月的 30 号。如果终止日期是一个月的\n"
		+"31号，并且起始日期早于 30 号，则终止日期等于下一个月的 1 号，否则，终止日期等于本月的 30 号。\n"
		+"TRUE           欧洲方法。无论是起始日期还是终止日期为一个月的 31 号，都将等于本月的 30 号。\n"
		+"备注:\n"
		+"ExtremScript将日期保存为系列数，一个系列数代表一个与之匹配的日期，以方便用户对日期进行数值式计算。\n"
		+"在1900年日期系统中，ExtremScript电子表格将1900年1月1日保存为系列数2，将1900年1月2日保存为系列数3，\n"
		+"将1900年1月3日保存为系列数4……依此类推。如在1900年日期系统，1998年1月1日存为系列数35796。\n"
		+"示例:\n"
		+"DAYS360(\"1998/1/30\", \"1998/2/1\") 等于 1";
	}
	public String getEN(){
		return "DAYS360(start_date,end_date,method): Returns the number of days between two dates based on a 360-day year (twelve 30-day months), which is used in some accounting calculations. Use this function to help compute payments if your accounting system is based on twelve 30-day months.\n"
		+"Start_date and end_date are the two dates between which you want to know the number of days.\n"
		+"Method is a logical value that specifies whether to use the U.S. or European method in the calculation.\n"
		+"\n"
		+"Method Definition:\n"
		+"FALSE or omitted: U.S. (NASD) method. If the starting date is the 31st of a month, it becomes equal to the 30th of the same month. If the ending date is the 31st of a month and the starting date is earlier than the 30th of a month, the ending date becomes equal to the 1st of the next month; otherwise the ending date becomes equal to the 30th of the same month.\n"
		+"TRUE: European method. Starting dates and ending dates that occur on the 31st of a month become equal to the 30th of the same month.\n"
		+"\n"
		+"Re:\n"
		+"   ExtremScript stores dates as sequential serial numbers so they can be used in calculations. ExtremScript stores January 1, 1900 as serial number 2，January 2, 1900 as 3，\n"
		+"January 3, 1900 as 4, and so on, so January 1, 1998 as serial number 35796.\n"
		+"\n"
		+"Example:\n"
		+"   DAYS360(\"1998/1/30\", \"1998/2/1\") = 1";
	}
}