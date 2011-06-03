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
        double number = 0;//��һ���������������ȵ��Ǹ���
        List refList = new LinkedList(); //����ڶ�������������ʹ��listΪ���ܹ�������ʵ����ֵ
        boolean order = false;//����������������true(����)Ϊ����false(��)Ϊ����

        //��������������
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
                param = null; //�����������֣�����Boolean �Ķ��� �����ش�����Ϣ
            }

            if (i == 0) {
                //kurt ��һ������number���
                if (param == null) {
                    return Primitive.ERROR_NAME;
                }
                number = tmpValue;
            } else if (i == args.length - 1) {
                //kurt ����������order���
            	if (tmpValue != 0) {
                    order = true;
                }
            } else {
                //kurt �ڶ�������ref���
            	if (param instanceof FArray) {
            		for (int f = 0; f < ((FArray)param).length(); f++) {
            			refList.add(((FArray)param).elementAt(f));
            		}
            	} else if (param != null) {
                    refList.add(new Double(tmpValue));
                }
            }
        }

        //kurt ɸѡ��ĵڶ��������������˷�ʵ����ֵ
        int sizeOfRef = refList.size();
        double[] ref = new double[sizeOfRef];
        int i = 0;
        for (Iterator iterator = refList.iterator(); iterator.hasNext();) {
            ref[i] = ((Number) iterator.next()).doubleValue();
            i++;
        }
        //��ref������������
        Arrays.sort(ref);
        //����
        if (order) {
            //kurt ����ʱ
            for (i = 1; i < sizeOfRef + 1; i++) {
                if (number == ref[i - 1]) {
                    break;
                }
            }
            if (i == sizeOfRef + 1) {
                i = 0;       //kurt number����������
            }
        } else {
            //kurt ����ʱ
            for (i = sizeOfRef; i > 0; i--) {
                if (number == ref[i - 1]) {
                    i = sizeOfRef - i + 1;
                    break;
                }
            }
            //���ﲻ���ж�number�ǲ��ǲ��������У���Ϊ������ڵĻ�����ʱi�Ѿ�Ϊ0��
        }
        
        //��ʱi����������
        return new Integer(i);
    }
	public Type getType() {
		return OTHER;
	}
	public String getCN(){
		return "RANK(number,ref,order): ����һ������һ�������е��ȡ�(���������������򣬸������ȼ�Ϊ���������е���š�)\n"
		+"Number �����ȵ�����(������Boolean�ͣ�true=1��false=0)\n"
		+"Ref    ���������飬���ã���һϵ��������ʵ����ֵ�����Դ���(����Boolean�ͣ�true=1��false=0)��\n"
		+"Order  ָ�����ȵĲ���������Ϊ������Ϊ����\n"
		+"\n"
		+"��ע\n"
		+"1.  RANK���ظ�����������ͬ���ȣ����ظ�����Ӱ�����������ȣ����磬��һ���������е������У����5������2�Σ�������Ϊ3����ô6����Ϊ5 (û����������4).\n"
//		+"2.  ��ǰ��������У������õ�5����������Ϊ3.5�������ڷ����ȵĻ����ϼ���һ���������ӡ�����������ͬʱ�ʺ�����ͽ���������\n"
//		+"    ��������Ϊ [COUNT(ref) + 1 ? RANK(number, ref, 0) ? RANK(number, ref, 1)]/2.\n"
//		+"    ������������У�RANK(A2,A1:A5,1) = 3����������Ϊ (5 + 1 ? 2 ? 3)/2 = 0.5 ���������Ϊ 3 + 0.5 = 3.5�����number��ref��ֻ����һ�Σ��������ӵ���0���Ȳ���仯��\n"
		+"\n"
		+"ʾ��\n"
		+"A1:A5 = 6, 4.5, 4.5, 2, 4\n"
		+"RANK(A1,A1:A5,1) �� 6 ����Ϊ 5.\n"
		+"\n"
		+"RANK(3,1,2,\"go\",3,4,1) = 3, \"go\"�����ԡ�";
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