package org.extreme.script.function;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


/*
 *RANK function
 */
public class RANK extends AbstractFunction {
    public Object run(Object[] args) {
        if (args.length < 3) {
            return Primitive.ERROR_NAME;
        }
        double number = 0;//第一个参数，即所求秩的那个数
        List refList = new LinkedList(); //保存第二个参数，这里使用list为了能够舍弃非实数的值
        boolean order = false;//第三个参数，排序，true(非零)为升序，false(零)为降序

        //这里获得三个参数
        Object param;
        for(int i = 0; i < args.length; i++) {
            param = args[i];
            double tmpValue = 0;
            if (param instanceof Number) {
                tmpValue = ((Number) param).doubleValue();
            } else if (param instanceof Boolean) {
                if (((Boolean) param).booleanValue()) {
                    tmpValue = 1;
                }
            } else if (i != 0 && i != args.length - 1 && param instanceof FArray) {
            	
            } else {
                param = null; //遇到不是数字，不是Boolean 的对象 ，返回错误信息
            }

            if (i == 0) {
                //kurt 第一个参数number获得
                if (param == null) {
                    return Primitive.ERROR_NAME;
                }
                number = tmpValue;
            } else if (i == args.length - 1) {
                //kurt 第三个参数order获得
            	if (tmpValue != 0) {
                    order = true;
                }
            } else {
                //kurt 第二个参数ref获得
            	if (param instanceof FArray) {
            		for (int f = 0; f < ((FArray)param).length(); f++) {
            			refList.add(((FArray)param).elementAt(f));
            		}
            	} else if (param != null) {
                    refList.add(new Double(tmpValue));
                }
            }
        }

        //kurt 筛选后的第二个参数，舍弃了非实数的值
        int sizeOfRef = refList.size();
        double[] ref = new double[sizeOfRef];
        int i = 0;
        for (Iterator iterator = refList.iterator(); iterator.hasNext();) {
            ref[i] = ((Number) iterator.next()).doubleValue();
            i++;
        }
        //对ref数组排序，升序
        Arrays.sort(ref);
        //求秩
        if (order) {
            //kurt 升序时
            for (i = 1; i < sizeOfRef + 1; i++) {
                if (number == ref[i - 1]) {
                    break;
                }
            }
            if (i == sizeOfRef + 1) {
                i = 0;       //kurt number不在数组中
            }
        } else {
            //kurt 降序时
            for (i = sizeOfRef; i > 0; i--) {
                if (number == ref[i - 1]) {
                    i = sizeOfRef - i + 1;
                    break;
                }
            }
            //这里不用判断number是不是不在数组中，因为如果不在的话，此时i已经为0了
        }
        
        //此时i就是所求秩
        return new Integer(i);
    }
	public Type getType() {
		return OTHER;
	}
	public String getCN(){
		return "RANK(number,ref,order): 返回一个数在一个数组中的秩。(如果把这个数组排序，该数的秩即为它在数组中的序号。)\n"
		+"Number 所求秩的数。(可以是Boolean型，true=1，false=0)\n"
		+"Ref    可以是数组，引用，或一系列数，非实数的值被忽略处理(接受Boolean型，true=1，false=0)。\n"
		+"Order  指定求秩的参数，非零为升序，零为降序\n"
		+"\n"
		+"备注\n"
		+"1.  RANK对重复的数返回相同的秩，但重复的数影响后面的数的秩，比如，在一组升序排列的整数中，如果5出现了2次，并且秩为3，那么6的秩为5 (没有数的秩是4).\n"
//		+"2.  在前面的例子中，如果想得到5的修正的秩为3.5，可以在返回秩的基础上加上一个修正因子。该修正因子同时适合升序和降序的情况。\n"
//		+"    修正因子为 [COUNT(ref) + 1 ? RANK(number, ref, 0) ? RANK(number, ref, 1)]/2.\n"
//		+"    在下面的例子中，RANK(A2,A1:A5,1) = 3。修正因子为 (5 + 1 ? 2 ? 3)/2 = 0.5 修正后的秩为 3 + 0.5 = 3.5。如果number在ref中只出现一次，修正因子等于0，秩不会变化。\n"
		+"\n"
		+"示例\n"
		+"A1:A5 = 6, 4.5, 4.5, 2, 4\n"
		+"RANK(A1,A1:A5,1) 即 6 的秩为 5.\n"
		+"\n"
		+"RANK(3,1,2,\"go\",3,4,1) = 3, \"go\"被忽略。";
	}
	public String getEN(){
		return "RANK(number,ref,order): Returns the rank of a number in a list of numbers. The rank of a number is its size relative to other values in a list. (If you were to sort the list, the rank of the number would be its position.)\n"
		+"Number    is the number whose rank you want to find. (Boolean is allowed, true will be treated as 1, false will be treated as 0).\n"
		+"Ref    is an array of, or a reference to, a list of numbers. Nonnumeric values in ref are ignored. (Boolean is allowed in ref, true will be treated as 1, false will be treated as 0).\n"
		+"Order    is a number specifying how to rank number.\n"
		+"\n"
		+"Re\n"
		+"    RANK gives duplicate numbers the same rank. However, the presence of duplicate numbers affects the ranks of subsequent numbers. For example, in a list of integers sorted in ascending order, if the number 5 appears twice and has a rank of 3, then 6 would have a rank of 5 (no number would have a rank of 4).\n"
//		+"    For some purposes one might want to use a definition of rank that takes ties into account. In the previous example, one would want a revised rank of 3.5 for the number 5. This can be done by adding the following correction factor to the value returned by RANK. This correction factor is appropriate both for the case where rank is computed in descending order (order = 0 or omitted) or ascending order (order = nonzero value).\n"
//		+"\n"
//		+"    Correction factor for tied ranks=[COUNT(ref) + 1 ? RANK(number, ref, 0) ? RANK(number, ref, 1)]/2.\n"
//		+"\n"
//		+"    In the following example, RANK(A2,A1:A5,1) equals 3. The correction factor is (5 + 1 ? 2 ? 3)/2 = 0.5 and the revised rank that takes ties into account is 3 + 0.5 = 3.5. If number occurs only once in ref, the correction factor will be 0, since RANK would not have to be adjusted for a tie.\n"
		+"\n"
		+"Example\n"
		+"A1:A5 = 6, 4.5, 4.5, 2, 4\n"
		+"RANK(A1,A1:A5,1)  Rank of 6 is 5.\n"
		+"\n"
		+"\n"
		+"RANK(3,1,2,\"go\",3,4,1) = 3, \"go\" is ignored.";
	}
}