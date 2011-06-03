package org.extreme.script;

import org.extreme.commons.util.StringUtils;
import org.extreme.script.parser.UtilEvalError;


/**
 * ���ڲ����ǵ�Ԫ��ĺ���������
 *
 */
public abstract class AbstractCellFunction extends CalculatorEmbeddedFunction {

	public Object evpression(Object[] arguments) throws UtilEvalError {
		Object[] args = new Object[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			args[i] = this.getCalculator().eval(arguments[i]);
		}
		
		return run(args);
	}
	
	public abstract Object run(Object[] args);

	public String getCN() {
		return StringUtils.EMPTY;
	}

	public String getEN() {
		return StringUtils.EMPTY;
	}

}
