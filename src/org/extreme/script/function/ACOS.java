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
		return "ACOS(number): ����ָ����ֵ�ķ�����ֵ��������ֵΪһ���Ƕȣ����ؽǶ��Ի�����ʽ��ʾ��\n"
		+"Number:��Ҫ���ؽǶȵ�����ֵ��\n"
		+"��ע:\n"
		+"    �����Ĳ���������-1��1֮�䣬����-1��1��\n"
		+"    ���صĽǶ�ֵ��0��Pi֮�䡣\n"
		+"    ���Ҫ�ѷ��صĽǶ��ö�������ʾ����180/PI()�˷���ֵ���ɡ�\n"
		+"ʾ��:\n"
		+"ACOS(1)����0�����ȣ���\n"
		+"ACOS(0.5)����1.047197551��Pi/3���ȣ���\n"
		+"ACOS(0.5)*180/PI()����60���ȣ���";
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