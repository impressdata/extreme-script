package org.extreme.script.parser;


import org.extreme.script.Calculator;
import org.extreme.script.Primitive;


public class Expression implements Node {
	Node conditionpression;

	Expression(Node conditionpression) {
		this.conditionpression = conditionpression;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
		if(conditionpression == null) {
			throw new InterpreterError("conditionpression should not be null");
		}
		
		Object result = calculator.eval(conditionpression);
		
		if(result == null) {
			result = Primitive.NULL;
		}
		
		return result;
	}
	
	public String toString() {
		return conditionpression.toString();
	}
}
