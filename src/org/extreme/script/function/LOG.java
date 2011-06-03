/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;


/**
 * LOG Function.
 */
public class LOG extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        double value = Utils.objectToNumber(args[0], false).doubleValue();
        double base = args.length < 2 ? 10 : Utils.objectToNumber(args[1], false).doubleValue();
        
        return FunctionHelper.asNumber(Math.log(value) / Math.log(base));
    }

	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "LOG(number,base): ��ָ�������������������ֵ�Ķ�����\n"
		+"Number:��Ҫ���������ʵ����\n"
		+"Base:�����ĵ��������ʡ�Ե�����Ĭ��ֵΪ10��\n"
		+"ʾ��:\n"
		+"LOG(16,2)����4��\n"
		+"LOG(10)����1��\n"
		+"LOG(24,3)����2.892789261��";
	}
	public String getEN(){
		return "LOG(number,base): Returns the logarithm of a number to the base you specify.\n"
		+"Number is the positive real number for which you want the logarithm.\n"
		+"Base is the base of the logarithm. If base is omitted, it is assumed to be 10.\n"
		+"\n"
		+"Example:\n"
		+"   LOG(16,2) = 4\n"
		+"   LOG(10) = 1\n"
		+"   LOG(24,3) = 2.892789261";
	}
}