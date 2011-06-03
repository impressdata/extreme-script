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
            //p:��Ҫ���ж��Ƿ�Ϊnull.
            if(args[i] == null) {
            	continue;
            }
            
            result += parseSQObject(args[i]);
        }

        return FunctionHelper.asNumber(result);
    }
    
    /*
     * ��Objectת��double���͵�ֵ
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
		return "SUMSQ(number1,number2, ...):�������в�����ƽ���͡�\n"
		+"number1, number2, ...:Ϊ 1 �� 30 ����Ҫ��ƽ���͵Ĳ�����Ҳ����ʹ������������������������Զ��ŷָ��Ĳ�����\n"
		+"ʾ��:\n"
		+"SUMSQ(3, 4) ���� 25\n"
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