/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


import java.util.Calendar;

import org.extreme.script.AbstractFunction;


/**
 * Function.
 */
public class TODAY extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
//    	TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
//        TimeZone.setDefault(tz);
        Calendar today = Calendar.getInstance();
        return today.getTime();
    }
	public Type getType() {
		return DATETIME;
	}	public String getCN(){
		return "TODAY():��ȡ��ǰ���ڡ�\n"
		+"ʾ����\n"
		+"���ϵͳ������2005��9��10��\n"
		+"��TODAY()����2005/9/10��";
	}
	public String getEN(){
		return "TODAY(): Returns the serial number of the current date. \n"
		+"\n"
		+"Example:\n"
		+"   If the system date is September 10, 2005, then TODAY() = 2005/9/10.";
	}
}