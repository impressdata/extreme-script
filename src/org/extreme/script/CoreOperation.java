package org.extreme.script;

import org.extreme.commons.util.Utils;
import org.extreme.script.parser.AddExpression;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.UtilEvalError;


public abstract class CoreOperation extends AddExpression {
	public Object binaryOperation(Object left, Object right, String op, Calculator c) throws UtilEvalError {
		
		if (left instanceof Node) {
			left = c.eval((Node)left);
		}
		
		if (right instanceof Node) {
			right = c.eval((Node)right);
		}
	
		// :如果是两个普通的值运算,先试着转成Number
		Number leftNum = Utils.objectToNumber(left, true);
		Number rightNum = Utils.objectToNumber(right, true);
		
		if (leftNum != null) {
			left = leftNum;
		}
		if (rightNum != null) {
			right = rightNum;
		}
		
		return ccRERE(left, right, op);
	}
}
