/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Function;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

/**
 * ACOSH Function.
 */
public class ACOSH extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        double result = 0;
        
        for(int i = 0; i < args.length; i++) {
            if(args[i] instanceof Number) {
            	double tmp = ((Number) args[i]).doubleValue();
                result = Math.log(tmp + Math.sqrt(tmp * tmp - 1));
                break;
            }
        }

        return FunctionHelper.asNumber(result);
    }
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "ACOSH(number): 返回给定数值的反双曲余弦。\n"
		+"Number:返回值的双曲余弦。\n"
		+"备注:\n"
		+"    参数number的值必须大于或等于1。\n"
		+"    ACOSH(COSH(number))=number。\n"
		+"示例:\n"
		+"ACOSH(1)等于0。\n"
		+"ACOSH(8)等于2.768659383。\n"
		+"ACOSH(5.5)等于2.389526435。";
	}
	public String getEN(){
		return "ACOSH(number): Returns the inverse hyperbolic cosine of a number.\n"
		+"Number is the hyperbolic cosine of the return value.\n"
		+"Re:\n"
		+"    Number is any real number equal to or greater than 1.\n"
		+"    ACOSH(COSH(number))=number。\n"
		+"Example:\n"
		+"   ACOSH(1)=0\n"
		+"   ACOSH(8)=2.768659383\n"
		+"   ACOSH(5.5)=2.389526435";
	}
}