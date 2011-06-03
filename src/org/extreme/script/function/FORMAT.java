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
    	
    	//��һ��������format�Ķ���
        Object target = args[0];
        //�ڶ���������format��pattern
    	String pattern = args[1].toString().trim();
    	
    	//:���target��Number�ҷ�Date,�����ַ�������ת��Number��Date
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
    		//ֻ���ڴ���������ʱ,���е���������,����
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
		return "FORMAT(object,format) : ����object��format��ʽ��\n"
		+"object ��Ҫ����ʽ�����󣬿�����String�����֣�Object(���õ���Date, Time)��\n"
		+"format ��ʽ������ʽ��\n"
		+"ʾ��\n"
		+"FORMAT(1234.5, \"#,##0.00\") => 1234.50\n"
		+"FORMAT(1234.5, \"#,##0\") => 1234\n"
		+"FORMAT(1234.5, \"��#,##0.00\") => ��1234.50\n"
		+"FORMAT(1.5, \"0%\") => 150%\n"
		+"FORMAT(1.5, \"0.000%\") => 150.000%\n"
		+"FORMAT(6789, \"##0.0E0\") => 6.789E3\n"
		+"FORMAT(6789, \"0.00E00\") => 6.79E03\n"
		+"FORMAT(date(2007,1,1), \"EEEEE, MMMMM dd, yyyy\") => ����һ��һ�� 01��2007\n"
		+"FORMAT(date(2007,1,13), \"MM/dd/yyyy\") => 01/13/2007\n"
		+"FORMAT(date(2007,1,13), \"M-d-yy\") => 1-13-07\n"
		+"FORMAT(time(16,23,56), \"h:mm:ss a\") => 4:23:56 ����";
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