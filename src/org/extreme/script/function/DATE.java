/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.DateUtils;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * DATE.
 */
public class DATE extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 3) {
            return Primitive.ERROR_NAME;
        }
        
        return DateUtils.createDate(
        		Utils.objectToNumber(args[0], false).intValue(),
        		Utils.objectToNumber(args[1], false).intValue(),
        		Utils.objectToNumber(args[2], false).intValue()
        );
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "DATE(year,month,day): 返回一个表示某一特定日期的系列数。\n"
		+"Year:代表年，可为一到四位数。\n"
		+"Month:代表月份。\n"
		+"若1 month 12，则函数把参数值作为月。\n"
		+"若month>12，则函数从年的一月份开始往上累加。例如: DATE(2000,25,2)等于2002年1月2日的系列数。\n"
		+"Day:代表日。\n"
		+"若日期小于等于某指定月的天数，则函数将此参数值作为日。\n"
		+"若日期大于某指定月的天数，则函数从指定月份的第一天开始往上累加。若日期大于两个或多个月的总天数，则函数把减去两个月或多个月的余数加到第三或第四个月上，依此类推。例如:DATE(2000,3,35)等于2000年4月4日的系列数。\n"
		+"备注:\n"
		+"   若需要处理公式中日期的一部分，如年或月等，则可用此公式。\n"
		+"   若年，月和日是函数而不是函数中的常量，则此公式最能体现其作用。\n"
		+"示例:\n"
		+"DATE(1978, 9, 19) 等于1978年9月19日.\n"
		+"DATE(1211, 12, 1) 等于1211年12月1日.   ";
	}
	public String getEN(){
		return "DATE(year,month,day): Returns the sequential serial number that represents a particular date. \n"
		+"Year  The year argument can be one to four digits.\n"
		+"Month is a number representing the month of the year. If 1 < Month < 12, it represents the month.\n"
		+"If month > 12, month adds that number of months to the first month in the year specified. For example, DATE(2000,25,2) returns the serial number representing DATE(2002,1,2).\n"
		+"Day: is a number representing the day of the month. If 1 < Day < 12, it represents the day.\n"
		+"If day is greater than the number of days in the month specified, day adds that number of days to the first day in the month. For example, DATE(2000,3,35) returns the serial number representing April 4, 2000.\n"
		+"\n"
		+"Re:\n"
		+"   The DATE function is most useful in formulas where year, month, and day are formulas, not constants.\n"
		+"\n"
		+"Example:\n"
		+"   DATE(1978, 9, 19) = September 19, 1978\n"
		+"   DATE(1211, 12, 1) = December 1, 1211  ";
	}
}