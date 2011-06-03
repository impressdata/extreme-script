/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


/**
 * Function.
 */
public class ODD extends EvenOdd {
	
	protected boolean findMatch(long l) {
		return l % 2 != 1;
	}
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "ODD(number):返回对指定数值进行舍入后的奇数。\n"
		+"number:是要舍入求奇的数值。\n"
		+"不论正负号如何，数值都朝着远离 0 的方向舍入。如果 number 恰好是奇数，则不须进行任何舍入处理。\n"
		+"示例:\n"
		+"ODD(1.5) 等于 3\n"
		+"ODD(3) 等于 3\n"
		+"ODD(2) 等于 3\n"
		+"ODD(-1) 等于 -1";
	}
	public String getEN(){
		return "ODD(number): Returns number rounded up to the nearest odd integer.\n"
		+"Number is the value to round.\n"
		+"Regardless of the sign of number, a value is rounded up when adjusted away from zero. If number is an odd integer, no rounding occurs. \n"
		+"\n"
		+"Example:\n"
		+"   ODD(1.5) = 3\n"
		+"   ODD(3) = 3\n"
		+"   ODD(2) = 3\n"
		+"   ODD(-1) = -1";
	}
}