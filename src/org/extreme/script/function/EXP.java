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
public class EXP extends AbstractFunction {
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
            return FunctionHelper.asNumber(
                    Math.exp(((Number) value).doubleValue()));
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "EXP(number): ����e��n���ݡ�����eΪ��Ȼ�����ĵ���������2.71828182845904��\n"
		+"Number:Ϊ����ʵ������Ϊ����e��ָ����\n"
		+"��ע:\n"
		+"    ���Ҫ��������������Ϊ�������ݣ�����ʹ��ָ���������^��������: ��4^2�У�4�ǵ�������2��ָ����\n"
		+"    EXP������LN������Ϊ��������\n"
		+"ʾ��:\n"
		+"EXP(0)����1��\n"
		+"EXP(3)����20.08553692��\n"
		+"EXP(LN(2))����2��";
	}
	public String getEN(){
		return "EXP(number): Returns e raised to the power of number. The constant e equals 2.71828182845904, the base of the natural logarithm.\n"
		+"Number is the exponent applied to the base e.\n"
		+"\n"
		+"Re:\n"
		+"1. To calculate powers of other bases, use the exponentiation operator (^). For example, in formula 4^2, 4 is base, 2 is exponential.\n"
		+"2. EXP is the inverse of LN, the natural logarithm of number. \n"
		+"\n"
		+"Example:\n"
		+"   EXP(0)=1\n"
		+"   EXP(3)=20.08553692\n"
		+"   EXP(LN(2))=2";
	}
}