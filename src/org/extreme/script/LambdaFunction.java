package org.extreme.script;

import org.extreme.commons.util.ArrayUtils;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.UtilEvalError;


/**
 * 这个类表示在计算的时候延迟执行
 * 例如：grepArray([1,2,3,4,5], item == 4)是一个实现数组过滤的函数，其中item为grepArray()函数的内置参数，表示遍历时的当前的元素
 * 执行顺序为：先执行grepArray()函数，遍历该函数的第一个参数所表示的数组，
 * 再把遍历时当前的元素item和4比较并返回一个布尔值，
 * 如果是true则保留该元素，false则不保留该元素。
 * 从而可以实现数组的过滤
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
