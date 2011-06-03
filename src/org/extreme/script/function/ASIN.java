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
		return "ASIN(number): ����ָ����ֵ�ķ�����ֵ��������ֵΪһ���Ƕȣ����ؽǶ��Ի�����ʽ��ʾ��\n"
		+"Number:��Ҫ���ؽǶȵ�����ֵ��\n"
		+"��ע:\n"
		+"    ָ����ֵ������-1��1֮�䣨��1��-1����\n"
		+"    ���ؽǶ���-pi/2��pi/2֮�䣨��-pi/2��pi/2����\n"
		+"    �ýǶ���ʽ������ֵʱ��������ֵ����180/PI()��\n"
		+"ʾ��:\n"
		+"ASIN(0.5)����0.523598776��pi/6���ȣ���\n"
		+"ASIN(1)����1.570796327��pi/2���ȣ���\n"
		+"ASIN(0.5)*180/PI()����30���ȣ���";
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