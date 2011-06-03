/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


/**
 * Function.
 */
public class EVEN extends EvenOdd {
	
	protected boolean findMatch(long l) {
		return l % 2 != 0;
	}
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "EVEN(number):返回沿绝对值增大方向取整后最接近的偶数。使用该函数可以处理那些成对出现的对象。\n"
		+"number:所要取整的数值。\n"
		+"不论正负号如何，数值都朝着远离 0 的方向舍入。如果 number 恰好是偶数，则不须进行任何舍入处理。\n"
		+"示例:\n"
		+"EVEN(1.5) 等于 2\n"
		+"EVEN(3) 等于 4\n"
		+"EVEN(2) 等于 2\n"
		+"EVEN(-1) 等于 -2";
	}
	public String getEN(){
		return "EVEN(number): Returns number rounded up to the nearest even integer. You can use this function for processing items that come in twos.\n"
		+"Number is the value to round.\n"
		+"Regardless of the sign of number, a value is rounded up when adjusted away from zero. If number is an even integer, no rounding occurs. \n"
		+"\n"
		+"Example:\n"
		+"   EVEN(1.5) = 2\n"
		+"   EVEN(3) = 4\n"
		+"   EVEN(2) = 2\n"
		+"   EVEN(-1) = -2";
	}
}