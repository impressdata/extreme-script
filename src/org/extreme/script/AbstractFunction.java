package org.extreme.script;

import org.extreme.commons.util.StringUtils;
import org.extreme.script.parser.UtilEvalError;


/**
 * 这个表示函数参数按顺序执行
 * 例如：SQRT	(ABS(99) + 1)
 * 执行顺序为：先算ABS(99)等于99，再计算99 +１等于100，最后计算SQRT(100)等于10
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
