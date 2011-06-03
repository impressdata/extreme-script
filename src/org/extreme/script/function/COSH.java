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
public class COSH extends AbstractFunction {

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
            double tmp = ((Number) value).doubleValue();
            return FunctionHelper.asNumber(
                    (Math.pow(Math.E, -tmp) + Math.pow(Math.E, tmp)) / 2);
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "COSH(number): ����һ����ֵ��˫��������ֵ��\n"
		+"Number:��Ҫ����˫��������ֵ��һ��ʵ����\n"
		+"��ע:\n"
		+"   ˫��������ֵ���㹫ʽΪ: ������e����Ȼ�����ĵף�e=2.71828182845904��\n"
		+"ʾ��:\n"
		+"COSH(3)����10.06766200��\n"
		+"COSH(5)����74.20994852��\n"
		+"COSH(6)����201.7156361��";
	}
	public String getEN(){
		return "COSH(number): Returns the hyperbolic cosine of a number.\n"
		+"Number is any real number for which you want to find the hyperbolic cosine.\n"
		+"\n"
		+"Example:\n"
		+"   COSH(3)=10.06766200\n"
		+"   COSH(5)=74.20994852\n"
		+"   COSH(6)=201.7156361";
	}
}