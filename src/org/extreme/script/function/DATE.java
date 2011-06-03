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
		return "DATE(year,month,day): ����һ����ʾĳһ�ض����ڵ�ϵ������\n"
		+"Year:�����꣬��Ϊһ����λ����\n"
		+"Month:�����·ݡ�\n"
		+"��1 month 12�������Ѳ���ֵ��Ϊ�¡�\n"
		+"��month>12�����������һ�·ݿ�ʼ�����ۼӡ�����: DATE(2000,25,2)����2002��1��2�յ�ϵ������\n"
		+"Day:�����ա�\n"
		+"������С�ڵ���ĳָ���µ��������������˲���ֵ��Ϊ�ա�\n"
		+"�����ڴ���ĳָ���µ�������������ָ���·ݵĵ�һ�쿪ʼ�����ۼӡ������ڴ������������µ��������������Ѽ�ȥ�����»����µ������ӵ���������ĸ����ϣ��������ơ�����:DATE(2000,3,35)����2000��4��4�յ�ϵ������\n"
		+"��ע:\n"
		+"   ����Ҫ����ʽ�����ڵ�һ���֣�������µȣ�����ô˹�ʽ��\n"
		+"   ���꣬�º����Ǻ��������Ǻ����еĳ�������˹�ʽ�������������á�\n"
		+"ʾ��:\n"
		+"DATE(1978, 9, 19) ����1978��9��19��.\n"
		+"DATE(1211, 12, 1) ����1211��12��1��.   ";
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