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
public class LN extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object param = null;
        for(int i = 0; i < args.length; i++) {
            param = args[i];

            if (param instanceof Number) {
                return FunctionHelper.asNumber(
                        Math.log(((Number) param).doubleValue()));
            }
        }

        return new Integer(0);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "LN(number):����һ��������Ȼ��������Ȼ�����Գ����� e��2.71828182845904��Ϊ�ס�\n"
		+"number:�����ڼ�������Ȼ��������ʵ����\n"
		+"ʾ��:\n"
		+"LN(86) ���� 4.45437\n"
		+"LN(2.7182818) ���� 1\n"
		+"LN(EXP(3)) ���� 3\n"
		+"EXP(LN(4)) ���� 4\n"
		+"";
	}
	public String getEN(){
		return "LN(number): Returns the natural logarithm of a number. Natural logarithms are based on the constant e (2.71828182845904).\n"
		+"Number is the positive real number for which you want the natural logarithm.\n"
		+"\n"
		+"Example:\n"
		+"   LN(86) = 4.45437\n"
		+"   LN(2.7182818) = 1\n"
		+"   LN(EXP(3)) = 3\n"
		+"   EXP(LN(4)) = 4\n"
		+"";
	}
}