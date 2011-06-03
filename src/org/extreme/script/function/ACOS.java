/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Function;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;


/**
 * Function.
 */
public class ACOS extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        return FunctionHelper.asNumber(Math.acos(Utils.objectToNumber(args[0], false).doubleValue()));
    }
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "ACOS(number): 返回指定数值的反余弦值。反余弦值为一个角度，返回角度以弧度形式表示。\n"
		+"Number:需要返回角度的余弦值。\n"
		+"备注:\n"
		+"    函数的参数必须在-1和1之间，包括-1和1。\n"
		+"    返回的角度值在0和Pi之间。\n"
		+"    如果要把返回的角度用度数来表示，用180/PI()乘返回值即可。\n"
		+"示例:\n"
		+"ACOS(1)等于0（弧度）。\n"
		+"ACOS(0.5)等于1.047197551（Pi/3弧度）。\n"
		+"ACOS(0.5)*180/PI()等于60（度）。";
	}
	public String getEN(){
		return "ACOS(number): Returns the arccosine, or inverse cosine, of a number. The arccosine is the angle whose cosine is number. The returned angle is given in radians.\n"
		+"Number is the cosine of the angle you want.\n"
		+"\n"
		+"Re:\n"
		+"    Number must be from -1 to 1.\n"
		+"    The returned angle is in the range 0 (zero) to pi.\n"
		+"    If you want to convert the result from radians to degrees, multiply it by 180/PI() or use the DEGREES function.\n"
		+"\n"
		+"Example:\n"
		+"   ACOS(1)=0 (in radians)\n"
		+"   ACOS(0.5)=1.047197551 (Pi/3 in radians)\n"
		+"   ACOS(0.5)*180/PI()=60 (in degrees)";
	}
}