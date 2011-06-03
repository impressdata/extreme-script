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
public class TANH extends AbstractFunction {

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
                    (Math.pow(Math.E, tmp) - Math.pow(Math.E, -tmp)) /
                    (Math.pow(Math.E, -tmp) + Math.pow(Math.E, tmp)));
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "TANH(number):����ĳһ���ֵ�˫������ֵ��\n"
		+"number:Ϊ����ʵ����\n"
		+"˫�����еļ��㹫ʽ���£�\n"
		+"ʾ��:\n"
		+"TANH(-2) ���� -0.96403\n"
		+"TANH(0) ���� 0\n"
		+"TANH(0.5) ���� 0.462117\n"
		+"";
	}
	public String getEN(){
		return "TANH(number): Returns the hyperbolic tangent of a number.\n"
		+"number is any real number\n"
		+"\n"
		+"Example:\n"
		+"   TANH(-2) = -0.96403\n"
		+"   TANH(0) = 0\n"
		+"   TANH(0.5) = 0.462117\n"
		+"";
	}
}