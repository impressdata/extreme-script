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
    	//yuliqian ���������Ҫ�����ǽ����ֲ�ͬ������ת����Date���ͣ������жϲ����Ƿ��������͵ģ������ֱ�ӷ���
    	//������FunctionHelper��Ĭ�ϵ������͸�ʽ�����������������Ȼ�ǲ����򿴹�ʽ���Ƿ��Դ������ڵĸ�ʽ��
    	//����ǵģ����������ʽ��������
        if(args.length < 1) {
            return new Date();
        } else if(args.length == 1) {
        	Object para = args[0];
        	if(para instanceof Date) {
        		return (Date)para;
        	} else if(para instanceof Calendar) {
        		return ((Calendar)para).getTime();
        	} else if(para instanceof Number) {
        		// :�����Number����,����������Ϊ��λ��
        		long time = ((Number)para).longValue();
        		Date date = new Date();
        		date.setTime(time * 3600000 * 24);
        		
        		return date;
        	} else {
        		return DateUtils.object2Date(para, false);
        	}
        }
        //p:����.
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
        // :��Ӷ����Ե�֧��
        else if (args.length == 3) {
        	Object para1 = args[0];
        	Object para2 = args[1];
        	// ��������Ϊ����
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
		return "TODATE()�������Խ�����������ʽ�Ĳ���ת��Ϊ�������͡�\n"
		+"�������ֲ�������ʽ��\n"
		+"1 ������һ�������͵Ĳ�������ôֱ�ӽ�����������ء�\n"
		+"ʾ����\n"
		+"TODATE(DATE(2007,12,12))����2007��12��12����ɵ����ڡ�\n"
		+"2 �������Դ�1970��1��1��0ʱ0��0�뿪ʼ�ĺ����������ض�Ӧ��ʱ�䡣\n"
		+"ʾ����\n"
		+"TODATE(1023542354746)����2002��6��8�ա�\n"
		+"3 ���������ڸ�ʽ���ı�����ô��������ı���Ӧ�����ڡ�\n"
		+"ʾ����\n"
		+"TODATE(\"2007/10/15\")����2007��10��5����ɵ����ڡ�\n"
		+"TODATE(\"2007-6-8\")����2007��6��8����ɵ����ڡ�\n"
		+"4 ��������������һ��������һ�����ڸ�ʽ���ı����ڶ��������������������ڵĸ�ʽ��\n"
		+"ʾ����\n"
		+"TODATE(\"1/15/07\",\"mm/dd/yy\")����07��1��15����ɵ����ڡ�\n"
		+"5 ��������������һ��������һ�����ڸ�ʽ���ı����ڶ��������������������ڵĸ�ʽ������������Ϊ�������ڵ����ԣ��磺zh�����ģ���en��Ӣ�ģ���\n"
		+"ʾ����\n"
		+"TODATE(\"������ 1/15/07\",\"EEE mm/dd/yy\", \"zh\")����07��1��15����ɵ����ڣ�ʹ�á�zh�����ģ������ܹ�����������������������ַ�����";
	}
	public String getEN(){
		return "TODATE() Function returns parameter(s) to a Date type object.\n"
		+"The four parameter signatures are defined below:\n"
		+"1. Parameter is a Date type, then function returns the parameter.\n"
		+"   Example: TODATE(DATE(2007,12,12)) returns Date object of 12/12/2007\n"
		+"2. Parameter is a number of milliseconds since 00:00:00 01/01/1970, then function returns the calculated Date object.\n"
		+"   Example: TODATE(1023542354746) returns 06/08/2002\n"
		+"3. Parameter is a date format string�� then function returns the converted Date object.\n"
		+"   Example: TODATE(\"2007/10/15\") returns Date object of 10/15/2007\n"
		+"4. Parameter 1 is a date format string, and parameter 2 is the date format sting, then function uses the format to convert the date string and returns the correct Date object.\n"
		+"   Example: TODATE(\"1/15/07\",\"mm/dd/yy\") returns Date of 01/15/2007\n"
;	}
}