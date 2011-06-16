package org.extreme.script;

import org.extreme.commons.util.StringUtils;
import org.extreme.script.parser.UtilEvalError;


/**
 * �����ʾ����������˳��ִ��
 * ���磺SQRT	(ABS(99) + 1)
 * ִ��˳��Ϊ������ABS(99)����99���ټ���99 +������100��������SQRT(100)����10
 */
public abstract class AbstractFunction extends CalculatorEmbeddedFunction {
	public Object evpression(Object[] arguments) throws UtilEvalError {
		Object[] args = new Object[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			args[i] = this.getCalculator().eval(arguments[i]);
		}
		
		return  args == null || args.length < 1 ? Primitive.ERROR_NAME : run(args);
	 }
	
	public abstract Object run(Object[] args);
	
	public String getEN() {
		return StringUtils.EMPTY;
	}
	
	public String getCN() {
		return StringUtils.EMPTY;
	}
}
