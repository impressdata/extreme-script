/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.Calculator;
import org.extreme.script.CalculatorEmbeddedFunction;
import org.extreme.script.parser.UtilEvalError;

/**
 * Function.
 */
public class AND extends CalculatorEmbeddedFunction {
	
	public Object evpression(Object[] args)
			throws UtilEvalError {
		boolean result = true;
		
		Calculator c = this.getCalculator();

		for (int i = 0; i < args.length; i++) {
			Object o = c.eval(args[i]);
			if (o instanceof Boolean) {
				result &= ((Boolean)o).booleanValue();
				if(!result) {
					break;
				}
			}
		}

		return Boolean.valueOf(result);
	}

	public Type getType() {
		return LOGIC;
	}	public String getCN(){
		return "AND(logical1,logical2,…): 当所有参数的值为真时，返回TRUE；当任意参数的值为假时，返回FALSE。\n"
		+"Logical1,logical2,…:指1到30个需要检验TRUE或FALSE的条件值。\n"
		+"备注:\n"
		+"    参数必须是逻辑值，或是含有逻辑值的数组或引用。\n"
		+"    如果数组或引用中含有文本或空的单元格，则忽略其值。\n"
		+"    如果在指定的单元格区域中没有逻辑值，AND函数将返回错误信息*NAME?。\n"
		+"示例:\n"
		+"AND(1+7=8,5+7=12)等于TRUE。\n"
		+"AND(1+7=8,5+7=11)等于FALSE。\n"
		+"如果单元格A1到A4的值分别为TRUE、TRUE、FALSE和TRUE，则:\n"
		+"AND(A1:A4)等于FALSE。\n"
		+"如果单元格A5的值在0~50之间，则: AND(0<A5,A5<50)等于TRUE。";
	}
	public String getEN(){
		return "AND(logical1,logical2,…): Returns TRUE if all its arguments are TRUE; returns FALSE if one or more argument is FALSE.\n"
		+"Logical1, logical2, ... are 1 to 30 conditions you want to test that can be either TRUE or FALSE.\n"
		+"\n"
		+"Re:\n"
		+"1. The arguments must evaluate to logical values such as TRUE or FALSE, or the arguments must be arrays or references that contain logical values. \n"
		+"2. If an array or reference argument contains text or empty cells, those values are ignored. \n"
		+"3. If the specified range contains no logical values, AND returns the *NAME? error value. \n"
		+"\n"
		+"Example:\n"
		+"   AND(1+7=8,5+7=12) = TRUE。\n"
		+"   AND(1+7=8,5+7=11) = FALSE。\n"
		+"   If the value of A1:A4 areTRUE, TRUE, FALSE and TRUE, then AND(A1:A4) = FALSE.\n"
		+"   If the value of A5 is between 0 and 50, then AND(0<A5,A5<50) = TRUE.";
	}
}