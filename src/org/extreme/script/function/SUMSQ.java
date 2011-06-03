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
public class SUMSQ extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        double result = 0;

        for(int i = 0; i < args.length; i++) {
            //p:需要先判断是否为null.
            if(args[i] == null) {
            	continue;
            }
            
            result += parseSQObject(args[i]);
        }

        return FunctionHelper.asNumber(result);
    }
    
    /*
     * 把Object转成double类型的值
     */
    private double parseSQObject(Object obj) {
    	if (obj instanceof Number) {
        	return ((Number)obj).doubleValue() * ((Number)obj).doubleValue();
        } else if (obj instanceof Boolean) {
            return ((Boolean)obj).booleanValue() ? 1 : 0;
        } else if (obj instanceof FArray) {
        	FArray array = (FArray)obj;
        	double sum = 0;
        	for(int i = 0, len = array.length(); i < len; i++) {
        		sum += parseSQObject(array.elementAt(i));
        	}
        	return sum;
        } else {
        	try {
        		return Double.parseDouble(obj.toString());
        	} catch(NumberFormatException exp) {
        		return 0;
        	}
        }
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "SUMSQ(number1,number2, ...):返回所有参数的平方和。\n"
		+"number1, number2, ...:为 1 到 30 个需要求平方和的参数，也可以使用数组或对数组的引用来代替以逗号分隔的参数。\n"
		+"示例:\n"
		+"SUMSQ(3, 4) 等于 25\n"
		+"";
	}
	public String getEN(){
		return "SUMSQ(number1,number2, ...): Returns the sum of the squares of the arguments.\n"
		+"Number1, number2, ... are 1 to 30 arguments for which you want the sum of the squares. You can also use a single array or a reference to an array instead of arguments separated by commas.\n"
		+"\n"
		+"Example:\n"
		+"   SUMSQ(3, 4) = 25\n"
		+"";
	}
}