package org.extreme.script.function;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.extreme.commons.util.DateUtils;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Format
 */
public class FORMAT extends AbstractFunction {
    public Object run(Object[] args) {
    	if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }
    	
    	//第一个参数是format的对象
        Object target = args[0];
        //第二个参数是format的pattern
    	String pattern = args[1].toString().trim();
    	
    	//:如果target非Number且非Date,则作字符串处理转成Number或Date
    	if (!(target instanceof Number) && !(target instanceof Date)) {
			String str = target.toString();
			
			target = Utils.string2Number(str);
			if (target == null) {
				target = DateUtils.string2Date(str, true);
			}
		}
    	
        if (target instanceof Number) {
    		return new DecimalFormat(pattern).format(target);
    	} else if (target instanceof Date) {
    		//只有在处理日期型时,才有第三个参数,地域
    		Locale loc = Locale.getDefault();
    		if(args.length > 2) {
    			Object language = args[2];
    			loc = new Locale(language.toString());
    		}
			return new SimpleDateFormat(pattern, loc).format(target);
    	}

        return target;
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "FORMAT(object,format) : 返回object的format格式。\n"
		+"object 需要被格式化对象，可以是String，数字，Object(常用的有Date, Time)。\n"
		+"format 格式化的样式。\n"
		+"示例\n"
		+"FORMAT(1234.5, \"#,##0.00\") => 1234.50\n"
		+"FORMAT(1234.5, \"#,##0\") => 1234\n"
		+"FORMAT(1234.5, \"￥#,##0.00\") => ￥1234.50\n"
		+"FORMAT(1.5, \"0%\") => 150%\n"
		+"FORMAT(1.5, \"0.000%\") => 150.000%\n"
		+"FORMAT(6789, \"##0.0E0\") => 6.789E3\n"
		+"FORMAT(6789, \"0.00E00\") => 6.79E03\n"
		+"FORMAT(date(2007,1,1), \"EEEEE, MMMMM dd, yyyy\") => 星期一，一月 01，2007\n"
		+"FORMAT(date(2007,1,13), \"MM/dd/yyyy\") => 01/13/2007\n"
		+"FORMAT(date(2007,1,13), \"M-d-yy\") => 1-13-07\n"
		+"FORMAT(time(16,23,56), \"h:mm:ss a\") => 4:23:56 下午";
	}
	public String getEN(){
		return "FORMAT(object,format) : Return a formatted string of \"object\" according to \"format\".\n"
		+"object: The object to format, it could be the type of String, Number or Object.(Date and Time are usually used.)\n"
		+"format: The pattern of the format. \n"
		+"Example\n"
		+"FORMAT(1234.5, \"0.00\") => 1234.50\n"
		+"FORMAT(1234.5, \"#,##0\") => 1234\n"
		+"FORMAT(1.5, \"0%\") => 150%\n"
		+"FORMAT(1.5, \"0.000%\") => 150.000%\n"
		+"FORMAT(6789, \"##0.0E0\") => 6.789E3\n"
		+"FORMAT(6789, \"0.00E00\") => 6.79E03\n"
		+"FORMAT(date(2007,1,13), \"MM/dd/yyyy\") => 01/13/2007\n"
		+"FORMAT(date(2007,1,13), \"M-d-yy\") => 1-13-07\n"
		+"FORMAT(time(1,23,56), \"HH:mm:ss\") => 01:23:56";
	}
}