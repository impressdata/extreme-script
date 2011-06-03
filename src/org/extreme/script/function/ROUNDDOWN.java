/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;

/**
 * Function.
 */
public class ROUNDDOWN extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	return FunctionHelper.roundUpOrDown(args, false);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "ROUNDDOWN(number,num_digits):靠近零值，向下（绝对值减小的方向）舍入数字。\n"
		+"number:为需要向下舍入的任意实数。\n"
		+"num_digits:舍入后的数字的位数。\n"
		+"函数 ROUNDDOWN 和函数 ROUND 功能相似，不同之处在于函数 ROUNDDOWN 总是向下舍入数字。\n"
		+"示例:\n"
		+"ROUNDDOWN(3.2, 0) 等于 3\n"
		+"ROUNDDOWN(76.9,0) 等于 76\n"
		+"ROUNDDOWN(3.14159, 3) 等于 3.141\n"
		+"ROUNDDOWN(-3.14159, 1) 等于 -3.1\n"
		+"ROUNDDOWN(31415.92654, -2) 等于 31,400\n"
		+"";
	}
	public String getEN(){
		return "ROUNDDOWN(number,num_digits): Rounds a number down, toward zero.\n"
		+"Number is any real number that you want rounded down.\n"
		+"Num_digits is the number of digits to which you want to round number.\n"
		+"\n"
		+"ROUNDDOWN behaves like ROUND, except that it always rounds a number down. \n"
		+"\n"
		+"Example:\n"
		+"   ROUNDDOWN(3.2, 0) =  3\n"
		+"   ROUNDDOWN(76.9,0) = 76\n"
		+"   ROUNDDOWN(3.14159, 3) = 3.141\n"
		+"   ROUNDDOWN(-3.14159, 1) = -3.1\n"
		+"   ROUNDDOWN(31415.92654, -2) = 31,400\n"
		+"";
	}
}