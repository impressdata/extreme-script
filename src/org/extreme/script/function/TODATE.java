package org.extreme.script.function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

import org.extreme.commons.util.DateUtils;
import org.extreme.commons.util.LogUtil;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;


/**
 * Format
 */
public class TODATE extends AbstractFunction {
	
	public static final Date arguments2Date(Object[] args) {
    	//yuliqian 这个函数主要功能是将各种不同的类型转换成Date类型，首先判断参数是否是日期型的，如果是直接返回
    	//否则用FunctionHelper中默认的日期型格式来解析参数。如果仍然是不行则看公式中是否自带了日期的格式，
    	//如果是的，就用这个格式来解析。
        if(args.length < 1) {
            return new Date();
        } else if(args.length == 1) {
        	Object para = args[0];
        	if(para instanceof Date) {
        		return (Date)para;
        	} else if(para instanceof Calendar) {
        		return ((Calendar)para).getTime();
        	} else if(para instanceof Number) {
        		// :如果是Number类型,当成是以天为单位的
        		long time = ((Number)para).longValue();
        		Date date = new Date();
        		date.setTime(time * 3600000 * 24);
        		
        		return date;
        	} else {
        		return DateUtils.object2Date(para, false);
        	}
        }
        //p:解析.
        else if(args.length == 2) {
        	Object para1 = args[0];
        	Object para2 = args[1];
        	try {
        		return (new SimpleDateFormat(
        					Utils.objectToString(para2).trim())).parse(
        						Utils.objectToString(para1).trim());
        	} catch(ParseException e) {
        		LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
        	}
        }
        // :添加对语言的支持
        else if (args.length == 3) {
        	Object para1 = args[0];
        	Object para2 = args[1];
        	// ：第三个为语言
        	Object para3 = args[2];
        	Locale locale = new Locale(para3.toString());
        	try {
        		return (new SimpleDateFormat(
        					Utils.objectToString(para2).trim(), locale)).parse(
        						Utils.objectToString(para1).trim());
        	} catch(ParseException e) {
        		LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
        	}
        }
        
        return new Date();
    }
	
    public Object run(Object[] args) {
    	return arguments2Date(args);
    }
	public Type getType() {
		return DATETIME;
	}
    	public String getCN(){
		return "TODATE()函数可以将各种日期形式的参数转换为日期类型。\n"
		+"它有三种参数的形式：\n"
		+"1 参数是一个日期型的参数，那么直接将这个参数返回。\n"
		+"示例：\n"
		+"TODATE(DATE(2007,12,12))返回2007年12月12日组成的日期。\n"
		+"2 参数是以从1970年1月1日0时0分0秒开始的毫秒数，返回对应的时间。\n"
		+"示例：\n"
		+"TODATE(1023542354746)返回2002年6月8日。\n"
		+"3 参数是日期格式的文本，那么返回这个文本对应的日期。\n"
		+"示例：\n"
		+"TODATE(\"2007/10/15\")返回2007年10月5日组成的日期。\n"
		+"TODATE(\"2007-6-8\")返回2007年6月8日组成的日期。\n"
		+"4 有两个参数，第一个参数是一个日期格式的文本，第二个参数是用来解析日期的格式。\n"
		+"示例：\n"
		+"TODATE(\"1/15/07\",\"mm/dd/yy\")返回07年1月15日组成的日期。\n"
		+"5 有三个参数，第一个参数是一个日期格式的文本，第二个参数是用来解析日期的格式，第三个参数为解析日期的语言，如：zh（中文），en（英文）。\n"
		+"示例：\n"
		+"TODATE(\"星期三 1/15/07\",\"EEE mm/dd/yy\", \"zh\")返回07年1月15日组成的日期，使用“zh（中文）”才能够正常解析“星期三”这个字符串。";
	}
	public String getEN(){
		return "TODATE() Function returns parameter(s) to a Date type object.\n"
		+"The four parameter signatures are defined below:\n"
		+"1. Parameter is a Date type, then function returns the parameter.\n"
		+"   Example: TODATE(DATE(2007,12,12)) returns Date object of 12/12/2007\n"
		+"2. Parameter is a number of milliseconds since 00:00:00 01/01/1970, then function returns the calculated Date object.\n"
		+"   Example: TODATE(1023542354746) returns 06/08/2002\n"
		+"3. Parameter is a date format string， then function returns the converted Date object.\n"
		+"   Example: TODATE(\"2007/10/15\") returns Date object of 10/15/2007\n"
		+"4. Parameter 1 is a date format string, and parameter 2 is the date format sting, then function uses the format to convert the date string and returns the correct Date object.\n"
		+"   Example: TODATE(\"1/15/07\",\"mm/dd/yy\") returns Date of 01/15/2007\n"
;	}
}