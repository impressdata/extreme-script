package org.extreme.script.parser;


import java.util.List;

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
	
	public void traversal4Tiny(TinyHunter hunter) {
		conditionpression.traversal4Tiny(hunter);
	}
	
	public String exString(Calculator calculator) {
		return conditionpression.exString(calculator);
	}
	
	public String toString() {
		return conditionpression.toString();
	}
	

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		return conditionpression.getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter);
	}

	public String[] parserParameter() {
		return conditionpression.parserParameter();
	}
	
	public void trav4HuntSIL(List list) {
		conditionpression.trav4HuntSIL(list);
	}
	
	public void trav4HuntBIL(List list) {
		conditionpression.trav4HuntBIL(list);
	}
}
