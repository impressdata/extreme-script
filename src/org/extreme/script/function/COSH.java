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
		return "COSH(number): 返回一个数值的双曲线余弦值。\n"
		+"Number:需要求其双曲线余弦值的一个实数。\n"
		+"备注:\n"
		+"   双曲线余弦值计算公式为: ，其中e是自然对数的底，e=2.71828182845904。\n"
		+"示例:\n"
		+"COSH(3)等于10.06766200。\n"
		+"COSH(5)等于74.20994852。\n"
		+"COSH(6)等于201.7156361。";
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