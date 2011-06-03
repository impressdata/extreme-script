package org.extreme.script.parser;

import java.util.List;

import org.extreme.script.Calculator;


public class ClosedExpression extends Atom {
	private Node exp;
	
	ClosedExpression(Node exp) {
		this.exp = exp;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
		return calculator.eval(exp);
	}
	
	public void traversal4Tiny(TinyHunter hunter) {
		exp.traversal4Tiny(hunter);
	}
	
	public String exString(Calculator calculator) {
		return "(" + exp.exString(calculator) + ")";
	}
	
	public String toString() {
		return "(" + exp + ")";
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		return "(" + exp.getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter) + ")";
	}

	public String[] parserParameter() {
		return exp.parserParameter();
	}
	
	public void trav4HuntSIL(List list) {
		exp.trav4HuntSIL(list);
	}
	
	public void trav4HuntBIL(List list) {
		exp.trav4HuntBIL(list);
	}
}
