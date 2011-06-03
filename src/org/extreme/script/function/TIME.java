/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.Calendar;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class TIME extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 3) {
            return Primitive.ERROR_NAME;
        }
        
        Calendar c = Calendar.getInstance();
        // :HOUR_OF_DAY��ʾ��24Сʱ�Ƶ�
        c.set(Calendar.HOUR_OF_DAY, Utils.objectToNumber(args[0], false).intValue());
        c.set(Calendar.MINUTE, Utils.objectToNumber(args[1], false).intValue());
        c.set(Calendar.SECOND, Utils.objectToNumber(args[2], false).intValue());
        
        return c.getTime();
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "TIME(hour,minute,second): ���ش���ָ��ʱ���С��������0:00:00��12:00:00 A.M.����23:59:59��11:59:59 P.M.��֮���ʱ��ɷ���0��0.99999999֮��Ķ�Ӧ��ֵ��\n"
		+"Hour:����0��23֮�������\n"
		+"Minute:����0��59֮�������\n"
		+"Second:����0��59֮�������\n"
		+"ʾ��:\n"
		+"TIME(14,40,0)����2:40 PM��\n"
		+"TIME(19,43,24)����7:43 PM��";
	}
	public String getEN(){
		return "TIME(hour,minute,second): Returns the decimal number for a particular time. The decimal number returned by TIME is a value ranging from 0 (zero) to 0.99999999, representing the times from 0:00:00 (12:00:00 AM) to 23:59:59 (11:59:59 P.M.).\n"
		+"Hour is a number from 0 (zero) to 23 representing the hour.\n"
		+"Minute is a number from 0 to 59 representing the minute.\n"
		+"Second is a number from 0 to 59 representing the second.\n"
		+"Example:\n"
		+"   TIME(14,40,0) = 2:40 PM\n"
		+"   TIME(19,43,24) = 7:43 PM";
	}
}