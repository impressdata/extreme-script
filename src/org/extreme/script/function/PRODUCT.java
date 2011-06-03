/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.FunctionHelper;

/**
 * Function.
 */
public class PRODUCT extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        double result = 1;

        for(int i = 0; i < args.length; i++) {
            //p:需要先判断是否为null.
            if(args[i] == null) {
            	continue;
            }
            
            result *= parseProductObject(args[i]);
        }

        return FunctionHelper.asNumber(result);
    }
    
    /*
     * 把Object转成double类型的值
     */
    private double parseProductObject(Object obj) {
    	if (obj instanceof Number) {
        	return ((Number)obj).doubleValue();
        } else if (obj instanceof Boolean) {
            return ((Boolean)obj).booleanValue() ? 1 : 0;
        } else if (obj instanceof FArray) {
        	FArray array = (FArray)obj;
        	double product = 1;
        	for(int i = 0, len = array.length(); i < len; i++) {
        		product *= parseProductObject(array.elementAt(i));
        	}
        	return product;
        } else {
        	try {
        		return Double.parseDouble(obj.toString());
        	} catch(NumberFormatException exp) {
        		return 1;
        	}
        }
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "PRODUCT(number1,number2, ...):将所有以参数形式给出的数字相乘，并返回乘积值。\n"
		+"number1, number2, ...:为 1 到 30 个需要相乘的数字参数。\n"
		+"示例:\n"
		+"PRODUCT(3,4) 等于 12\n"
		+"PRODUCT(3,4,5) 等于 60\n"
		+"\n"
		+"";
	}
	public String getEN(){
		return "PRODUCT(number1,number2, ...): Multiplies all the numbers given as arguments and returns the product.\n"
		+"Number1, number2, ... are 1 to 30 numbers that you want to multiply.\n"
		+"\n"
		+"Example:\n"
		+"   PRODUCT(3,4) = 12\n"
		+"   PRODUCT(3,4,5) = 60\n"
		+"\n"
		+"";
	}
}