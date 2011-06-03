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
		return "DAYS360(start_date,end_date,method):����һ�� 360 ����㷨��ÿ������ 30 ��ƣ�һ�깲�� 12 ���£���\n"
		+"���������ڼ��������������ڻ�Ƽ����н����õ����������ϵͳ�ǻ���һ�� 12 ���£�ÿ�� 30 �죬\n"
		+"���ô˺�����������֧�����\n"
		+"Start_date �� end_date :�����ڼ����ڼ���������ֹ���ڡ�\n"
		+"Method : ��ָ�����ڼ������ǲ���ŷ�޷�����������������\n"
		+"Method ���� :\n"
		+"FALSE�����    �������� (NASD)�������ʼ������һ���µ� 31 �ţ������ͬ�µ� 30 �š������ֹ������һ���µ�\n"
		+"31�ţ�������ʼ�������� 30 �ţ�����ֹ���ڵ�����һ���µ� 1 �ţ�������ֹ���ڵ��ڱ��µ� 30 �š�\n"
		+"TRUE           ŷ�޷�������������ʼ���ڻ�����ֹ����Ϊһ���µ� 31 �ţ��������ڱ��µ� 30 �š�\n"
		+"��ע:\n"
		+"ExtremScript�����ڱ���Ϊϵ������һ��ϵ��������һ����֮ƥ������ڣ��Է����û������ڽ�����ֵʽ���㡣\n"
		+"��1900������ϵͳ�У�ExtremScript���ӱ��1900��1��1�ձ���Ϊϵ����2����1900��1��2�ձ���Ϊϵ����3��\n"
		+"��1900��1��3�ձ���Ϊϵ����4�����������ơ�����1900������ϵͳ��1998��1��1�մ�Ϊϵ����35796��\n"
		+"ʾ��:\n"
		+"DAYS360(\"1998/1/30\", \"1998/2/1\") ���� 1";
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
		+"   ExtremScript stores dates as sequential serial numbers so they can be used in calculations. ExtremScript stores January 1, 1900 as serial number 2��January 2, 1900 as 3��\n"
		+"January 3, 1900 as 4, and so on, so January 1, 1998 as serial number 35796.\n"
		+"\n"
		+"Example:\n"
		+"   DAYS360(\"1998/1/30\", \"1998/2/1\") = 1";
	}
}