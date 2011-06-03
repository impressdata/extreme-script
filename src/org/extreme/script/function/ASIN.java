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
 * ASIN Function.
 */
public class ASIN extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        return FunctionHelper.asNumber(Math.asin(Utils.objectToNumber(args[0], false).doubleValue()));
    }
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "ASIN(number): 返回指定数值的反正弦值。反正弦值为一个角度，返回角度以弧度形式表示。\n"
		+"Number:需要返回角度的正弦值。\n"
		+"备注:\n"
		+"    指定数值必须在-1到1之间（含1与-1）。\n"
		+"    返回角度在-pi/2到pi/2之间（含-pi/2与pi/2）。\n"
		+"    用角度形式返回数值时，返回数值乘以180/PI()。\n"
		+"示例:\n"
		+"ASIN(0.5)等于0.523598776（pi/6弧度）。\n"
		+"ASIN(1)等于1.570796327（pi/2弧度）。\n"
		+"ASIN(0.5)*180/PI()等于30（度）。";
	}
	public String getEN(){
		return "ASIN(number): Returns the arcsine, or inverse sine, of a number. The arcsine is the angle whose sine is number. The returned angle is given in radians.\n"
		+"Number is the sine of the angle.\n"
		+"\n"
		+"Re:\n"
		+"    Number is in the range -1 to 1.\n"
		+"    The returned angle is in the range -pi/2 to pi/2.\n"
		+"    To express the arcsine in degrees, multiply the result by 180/PI( ) or use the DEGREES function.\n"
		+"\n"
		+"Example:\n"
		+"   ASIN(0.5)=0.523598776 (pi/6 in radians)\n"
		+"   ASIN(1)=1.570796327 (pi/2 in radians)\n"
		+"   ASIN(0.5)*180/PI()=30 (in degrees)";
	}
}