/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class FLOOR extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return FunctionHelper.asNumber(Math.floor(Utils.objectToNumber(args[0], false).doubleValue()));
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "FLOOR(number): ������number�ؾ���ֵ��С�ķ���ȥβ���롣\n"
		+"Number:���������ֵ��\n"
		+"ʾ��:\n"
		+"FLOOR(-2.5)����-2��\n"
		+"FLOOR(2.5)����2��\n";
	}
	public String getEN(){
		return "FLOOR(number,significance): Rounds number down, toward zero.\n"
		+"Number is the numeric value you want to round.\n"
		+"Re:\n"
		+"   FLOOR(-2.5) = -2\n"
		+"   FLOOR(2.5) = 2\n";
	}
}