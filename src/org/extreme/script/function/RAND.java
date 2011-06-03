/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;

/**
 * Function.
 */
public class RAND extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        return new Double(Math.random());
    }	public String getCN(){
		return "RAND(): 返回均匀分布的随机数。每计算一次工作表，函数都会返回一个新的随机数值。\n"
		+"备注:\n"
		+"    要生成一个位于a和b之间的随机数，可以使用以下的公式: C=RAND()*(b-a)+a。\n"
		+"    如果要使一个随机产生的数值不随单元格的重计算而改变，可以在编辑框中输入=RAND()并保持编辑状态，然后按F9，将公式永久性地改为随机数。\n"
		+"示例:\n"
		+"假如需要生成一个大于等于0，小于60的随机数，使用公式: =RAND()*60。\n"
		+"假如需要生成一个大于等于0，小于19的随机数，使用公式: =RAND()*19。\n"
		+"假如需要生成一个大于等于0，小于50的随机数，使用公式: =RAND()*50。";
	}
	public Type getType() {
		return MATH;
	}
	public String getEN(){
		return "RAND(): Returns an evenly distributed random number greater than or equal to 0 and less than 1. A new random number is returned every time the worksheet is calculated.\n"
		+"\n"
		+"Re:\n"
		+"1. To generate a random real number between a and b, use: RAND()*(b-a)+a\n"
		+"2. If you want to use RAND to generate a random number but don\'t want the numbers to change every time the cell is calculated, you can enter =RAND() in the formula bar, and then press F9 to change the formula to a random number. \n"
		+"\n"
		+"Example:\n"
		+"   If you want to get a rand number equal or greater than 0, and smaller than 60, use: RAND()*60。";
	}
}