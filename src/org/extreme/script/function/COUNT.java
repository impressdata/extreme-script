/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.FArray;
import org.extreme.script.Primitive;

/**
 * COUNT.
 */
public class COUNT extends SummaryFunction {
	
	protected double init() {
		return 0;
	}
	
	protected double operation(double preview, double nextEl) {
		return preview + nextEl;
	}
    
    /*
     * 把Object转成double类型的值
     */
    protected double parseObject(Object obj) {
    	if(obj instanceof FArray) {
    		int c = 0;
    		for (int i = 0, len = ((FArray)obj).length(); i < len; i++) {
    			c += parseObject(((FArray)obj).elementAt(i));
    		}
    		
    		return c;
    	} else if (obj == null || obj instanceof Primitive) {
    		return 0;
    	} else {
    		return 1;
    	}
    }	public String getCN(){
		return "COUNT(value1,value2,…): 计算数组或数据区域中所含项的个数。\n"
		+"Value1,value2,…:可包含任何类型数据的参数。";
	}
	public String getEN(){
		return "COUNT(value1,value2,…): Counts the number of cells that contain numbers and also numbers within the list of arguments.\n"
		+"Value1, value2, ...  are 1 to 30 arguments that can contain or refer to a variety of different types of data, but only numbers are counted.\n"
		+"\n"
		+"Re:\n"
		+"1. Arguments that are numbers, dates, or text representations of numbers are counted; arguments that are error values or text that cannot be translated into numbers are ignored. \n"
		+"2. If an argument is an array or reference, only numbers in that array or reference are counted. Empty cells, logical values, text, or error values in the array or reference are ignored. If you need to count logical values, text, or error values, use the COUNTA function. ";
	}
}