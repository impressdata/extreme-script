/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class SQRT extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object value = args[0];
        if (value instanceof Number) {
            return new Double(Math.sqrt(((Number) value).doubleValue()));
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "SQRT(number): ����һ��������ƽ������\n"
		+"Number:Ҫ����ƽ��������һ������\n"
		+"��ע:\n"
		+"Number������һ�����������������ش�����Ϣ*NUM!��\n"
		+"ʾ��:\n"
		+"SQRT(64)����8��\n"
		+"SQRT(-64)����*NUM!��";
	}
	public String getEN(){
		return "SQRT(number): Returns a positive square root.\n"
		+"Number is the number for which you want the square root.\n"
		+"\n"
		+"Re:\n"
		+"   If number is negative, SQRT returns the #NUM! error value.\n"
		+"\n"
		+"Example:\n"
		+"   SQRT(64) = 8\n"
		+"   SQRT(-64) returns *NUM!";
	}
}