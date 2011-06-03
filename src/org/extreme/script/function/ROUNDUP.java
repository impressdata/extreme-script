/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;

/**
 * Function.
 */
public class ROUNDUP extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	return FunctionHelper.roundUpOrDown(args, true);
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "ROUNDUP(number,num_digits):远离零值，向上（绝对值增大的方向）舍入数字。\n"
		+"number:为需要向上舍入的任意实数。\n"
		+"num_digits:舍入后的数字的位数。\n"
		+"函数 ROUNDUP 和函数 ROUND 功能相似，不同之处在于函数 ROUNDUP 总是向上舍入数字。\n"
		+"示例:\n"
		+"ROUNDUP(3.2,0) 等于 4\n"
		+"ROUNDUP(76.9,0) 等于 77\n"
		+"ROUNDUP(3.14159, 3) 等于 3.142\n"
		+"ROUNDUP(-3.14159, 1) 等于 -3.2\n"
		+"ROUNDUP(31415.92654, -2) 等于 31,500\n"
		+"\n"
		+"";
	}
	public String getEN(){
		return "ROUNDUP(number,num_digits): Rounds a number up, away from 0 (zero).\n"
		+"Number is any real number that you want rounded up.\n"
		+"Num_digits is the number of digits to which you want to round number.\n"
		+"\n"
		+"ROUNDUP behaves like ROUND, except that it always rounds a number up. \n"
		+"\n"
		+"Example:\n"
		+"   ROUNDUP(3.2,0) = 4\n"
		+"   ROUNDUP(76.9,0) = 77\n"
		+"   ROUNDUP(3.14159, 3) = 3.142\n"
		+"   ROUNDUP(-3.14159, 1) = -3.2\n"
		+"   ROUNDUP(31415.92654, -2) = 31,500\n"
		+"\n"
		+"";
	}
}