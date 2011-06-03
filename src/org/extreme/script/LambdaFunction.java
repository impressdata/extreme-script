package org.extreme.script;

import org.extreme.commons.util.ArrayUtils;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.UtilEvalError;


/**
 * ������ʾ�ڼ����ʱ���ӳ�ִ��
 * ���磺grepArray([1,2,3,4,5], item == 4)��һ��ʵ��������˵ĺ���������itemΪgrepArray()���������ò�������ʾ����ʱ�ĵ�ǰ��Ԫ��
 * ִ��˳��Ϊ����ִ��grepArray()�����������ú����ĵ�һ����������ʾ�����飬
 * �ٰѱ���ʱ��ǰ��Ԫ��item��4�Ƚϲ�����һ������ֵ��
 * �����true������Ԫ�أ�false�򲻱�����Ԫ�ء�
 * �Ӷ�����ʵ������Ĺ���
 * @author richer
 * @since 6.5
 */
public abstract class LambdaFunction extends CalculatorEmbeddedFunction {
	
	public Object evpression(Object[] args) throws UtilEvalError {
		if (args.length != 2) {
			return Primitive.ERROR_VALUE;
		}
		
		if (args[0] == null) return Primitive.NULL;
		Object firstOb = this.getCalculator().eval(args[0]);
		FArray array = new FArray();
		if (!(firstOb instanceof FArray)) {
			array.add(firstOb);
		} else {
			array = (FArray) firstOb;
		}
		
		return run(array, (Node[])ArrayUtils.subarray(args, 1, args.length));
	}
	
	public abstract Object run(FArray array, Node[] arguments);
}
