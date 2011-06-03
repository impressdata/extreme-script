/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class COS extends AbstractFunction {

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
            return FunctionHelper.asNumber(Math.cos(((Number) value).doubleValue()));
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "COS(number): 返回一个角度的余弦值。\n"
		+"Number:以弧度表示的需要求余弦值的角度。\n"
		+"备注:\n"
		+"    要把一个角度转换成弧度值，将角度乘于PI()/180。\n"
		+"    COS(n*2*PI()+number)=COS(number)（其中n为整数，number从-pi到pi）。\n"
		+"示例:\n"
		+"COS(0.5)等于0.877582562。\n"
		+"COS(30*PI()/180)等于0.866025404。";
	}
	public String getEN(){
		return "COS(number): Returns the cosine of the given angle.\n"
		+"Number is the angle in radians for which you want the cosine.\n"
		+"\n"
		+"Re:\n"
		+"1. If the angle is in degrees, multiply it by PI()/180 or use the COS function to convert it to radians.\n"
		+"2. COS(n*2*PI()+number)=COS(number), n is an integer, number is between -pi and pi.\n"
		+"\n"
		+"Example:\n"
		+"   COS(0.5)=0.877582562\n"
		+"   COS(30*PI()/180)=0.866025404";
	}
}