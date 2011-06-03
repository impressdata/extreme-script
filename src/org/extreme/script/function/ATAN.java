/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Function;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class ATAN extends AbstractFunction {

	/**
	 * Run the function on the stack. Pops the arguments from the stack, then
	 * return the result.
	 */
	public Object run(Object[] args) {
		if (args.length < 1) {
			return Primitive.ERROR_NAME;
		}

		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof Number) {
				// calculate the result
				return FunctionHelper.asNumber(Math.atan(((Number) args[i]).doubleValue()));
			}
		}

		return Primitive.ERROR_NAME;
	}
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "ATAN(number): ����ָ����ֵ�ķ�����ֵ��ָ����ֵ�Ƿ��ؽǶȵ�����ֵ�����ؽǶ��Ի�����ʽ��ʾ��\n"
		+"Number:���ؽǶȵ����С�\n"
		+"��ע:\n"
		+"    ���ؽǶ���-pi/2��pi/2֮�䡣\n"
		+"    ������ؽǶȵ���-pi/2��pi/2��ATAN�����ش�����Ϣ*NUM!��\n"
		+"    �ýǶ���ʽ������ֵʱ��������ֵ����180/PI()��\n"
		+"ʾ��:\n"
		+"ATAN(-1)����-0.785398163��-pi/4���ȣ���\n"
		+"ATAN(0)����0�����ȣ���\n"
		+"ATAN(2)*180/PI()����63.43494882���ȣ���";
	}
	public String getEN(){
		return "ATAN(number): Returns the arctangent, or inverse tangent, of a number. The arctangent is the angle whose tangent is number, the returned angle is given in radians. \n"
		+"Number is the tangent of the angle you want.\n"
		+"\n"
		+"Re:\n"
		+"    The returned angle is in the range -pi/2 to pi/2.\n"
		+"    If returned angle equals -pi/2 or pi/2, ATAN returns error *NUM!.\n"
		+"    To express the arctangent in degrees, multiply the result by 180/PI( ) or use the DEGREES function.\n"
		+"\n"
		+"Example:\n"
		+"   ATAN(-1)=-0.785398163 (-pi/4 in radians)\n"
		+"   ATAN(0)=0 (in radians)\n"
		+"   ATAN(2)*180/PI()=63.43494882 (in degrees)";
	}
}