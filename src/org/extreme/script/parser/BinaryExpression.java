package org.extreme.script.parser;

import org.extreme.script.Calculator;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;

/*
 * 二元操作
 */
public abstract class BinaryExpression extends AbstractNode {
	
	public Object eval(Calculator calculator) throws UtilEvalError {
		int size = sizeOfNodes();
		if(size == 0) {
			throw new InterpreterError(emptyNodesException());
		}
		
		Object left = null;
		
		for (int i = 0; i < size; i++) {
			Node node = getNodeByIndex(i);
			if (i == 0) {
				left = node.eval(calculator);
			} else {
				Object right = node.eval(calculator);

				left = binaryOperation(left, right, getOpByIndex(i - 1));
			}

			// 短路操作
			if (i < size - 1 && shortcutJudge() && judge(left)) {
				return left; 
			}
		}
		
		return left;
	}
	
	protected abstract int sizeOfNodes();
	
	protected abstract String emptyNodesException();
	
	protected abstract Node  getNodeByIndex(int idx);
	
	protected abstract String getOpByIndex(int idx);
	
	protected boolean shortcutJudge() {
		return false;
	}
	
	protected boolean judge(Object ob) {
		return false;
	}
	
	protected Object binaryOperation(Object left, Object right, String op) throws UtilEvalError {
    	// : avoid NPE
    	if (left == null) left = Primitive.NULL;
    	if (right == null) right = Primitive.NULL;
    	
    	if (left == Primitive.ERROR_NAME || left == Primitive.ERROR_VALUE || right == Primitive.ERROR_NAME || right == Primitive.ERROR_VALUE) {
    		return Primitive.ERROR_NAME;
    	}
    	
    	// NOFILTER的情况特殊处理
    	if (left == Primitive.NOFILTER || right == Primitive.NOFILTER) {
    		if (isRelationExpression()) {
    			return Boolean.TRUE;
    		} else {
    			return Primitive.NOFILTER;
    		}
    	}
    	
    	/*
    	 * :先处理FArray的情况(两个都是FArray;左FArray;右FArray)
    	 */
		if (left instanceof FArray && right instanceof FArray) {
			return arrayBinaryOperation(((FArray)left), ((FArray)right), op);
		} else if (left instanceof FArray) {
			FArray f1 = (FArray)left;
			Object[] array2 = new Object[f1.length()];
			java.util.Arrays.fill(array2, right);
			
			return arrayBinaryOperation(f1, new FArray(array2), op);
		} else if (right instanceof FArray) {
			FArray f2 = (FArray)right;
			Object[] array1 = new Object[f2.length()];
			java.util.Arrays.fill(array1, left);
			
			return arrayBinaryOperation(new FArray(array1), f2, op);
		} 
		// :非数组情况的binaryOperation
		else {
			return objectBinaryOperation(left, right, op);
		}
	}
	
	/*
	 * 判断是不是关系运算,用于NOFILTER的判断
	 */
	protected boolean isRelationExpression() {
		return false;
	}
	
	/*
	 * :默认的arrayBinaryOperation返回FArray
	 */
	protected Object arrayBinaryOperation(FArray array1, FArray array2, String op) throws UtilEvalError {
		int len1 = array1.length(),
		len2 = array2.length(),
		min = Math.min(len1, len2),
		max = Math.max(len1, len2);
		
		Object[] rets = new Object[max];
		for (int i = 0; i < min; i++) {
			rets[i] = binaryOperation(array1.elementAt(i), array2.elementAt(i), op);
		}
		
		FArray copyFrom = len1 == max ? array1 : array2;
		for (int i = min; i < max; i++) {
			rets[i] = copyFrom.elementAt(i);
		}
		
		return new FArray(rets);
	}
	
	/*
	 * 简单元素的二元操作,不考虑数组情况
	 */
	protected abstract Object objectBinaryOperation(Object left, Object right, String op) throws UtilEvalError;
}
