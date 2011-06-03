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
		return "COMBIN(number,number_chosen): �������ɸ�ָ���������������ú�������ѧ���ʽΪCnk������ͬ��\n"
		+"Number����ѧ���ʽ�еġ�n��ָ����������\n"
		+"Number_chosen����ѧ���ʽ�еġ�k��ָ�ڶ���������ĳһ��ϵ�������\n"
		+"��ע:\n"
		+"    Number��������������number_chosen�����ǷǸ�������\n"
		+"    ���number��number_chosenС��0��numberС��number_chosen���������ش�����Ϣ*NUM!��\n"
		+"    ��������Ƕ�����Ӽ��������в�ͬ���ǣ���ϲ��漰�����ڲ����Ⱥ�˳�򣬶�˳��������Ƿǳ���Ҫ�ġ�\n"
		+"    ����number=n��number_chosen=k����: COMBIN(n,k)=Cnk=n!/(k!(n-k)!)��\n"
		+"ʾ��:\n"
		+"COMBIN(5,6)����*NUM!��\n"
		+"COMBIN(5,2)����10��";
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