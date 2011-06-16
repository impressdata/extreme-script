package org.extreme.script.parser;

import org.extreme.script.Calculator;


public class ClosedExpression implements Node {
	private Node exp;
	
	ClosedExpression(Node exp) {
		this.exp = exp;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
		return calculator.eval(exp);
	}
	
	public String toString() {
		return "(" + exp + ")";
	}
}
