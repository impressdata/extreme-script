/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class COMBIN extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }

        Object value1 = args[0];
        Object value2 = args[1];

        if (value1 instanceof Number && value2 instanceof Number) {
            double tmp11 = ((Number) value1).doubleValue();
            int tmp1 = ((Number) value1).intValue();
            double tmp22 = ((Number) value2).doubleValue();
            int tmp2 = ((Number) value2).intValue();
            if (tmp1 < tmp2 || tmp1 < 0 || tmp2 < 0 || tmp11 - tmp1 != 0 || tmp22 - tmp2 != 0) {
                return Primitive.ERROR_VALUE;
            }
            return new Long(jieCheng(tmp1) / (jieCheng(tmp2) * jieCheng(tmp1 - tmp2)));
        }

        return Primitive.ERROR_NAME;
    }

    private long jieCheng(int n) {
        long tmp = 1;
        for (int i = 1; i <= n; i++) {
            tmp *= i;
        }
        return tmp;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "COMBIN(number,number_chosen): 返回若干个指定对象的组合数。该函数与数学表达式为Cnk功能相同。\n"
		+"Number或数学表达式中的“n”指对象总数。\n"
		+"Number_chosen或数学表达式中的“k”指在对象总数中某一组合的数量。\n"
		+"备注:\n"
		+"    Number必须是正整数，number_chosen必须是非负整数。\n"
		+"    如果number和number_chosen小于0或number小于number_chosen，函数返回错误信息*NUM!。\n"
		+"    对象组合是对象的子集。与排列不同的是，组合不涉及对象内部的先后顺序，而顺序对排列是非常重要的。\n"
		+"    假设number=n，number_chosen=k，则: COMBIN(n,k)=Cnk=n!/(k!(n-k)!)。\n"
		+"示例:\n"
		+"COMBIN(5,6)等于*NUM!。\n"
		+"COMBIN(5,2)等于10。";
	}
	public String getEN(){
		return "COMBIN(number,number_chosen): Returns the number of combinations for a given number of items, functionally equals Cnk.\n"
		+"Number is the number of items.\n"
		+"Number chosen is the number of items in each combination.\n"
		+"\n"
		+"Re:\n"
		+"1. Number is truncated to plus integers, number_chosen is truncated to integers exclude negative.\n"
		+"2. If number < 0, number_chosen < 0, or number < number_chosen, COMBIN returns the #NUM! error value. \n"
		+"3. A combination is any set or subset of items, regardless of their internal order. Combinations are distinct from permutations, for which the internal order is significant. \n"
		+"    Suppose number=n, number_chosen=k, then COMBIN(n,k)=Cnk=n!/(k!(n-k)!).\n"
		+"\n"
		+"Example:\n"
		+"   COMBIN(5,6)=*NUM!\n"
		+"   COMBIN(5,2)=10";
	}
}