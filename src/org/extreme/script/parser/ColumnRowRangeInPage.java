package org.extreme.script.parser;

import org.extreme.script.Calculator;

public class ColumnRowRangeInPage extends Tiny{
	private Atom atom;
	
	protected ColumnRowRangeInPage(Atom atom) {
		this.atom = atom;
	}
	
	public Atom getAtom() {
		return this.atom;
	}

	public Object eval(Calculator calculator) throws UtilEvalError {
		return calculator.resolveVariable(this);
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex,
			int colChanged, boolean isParameter) {
		return "{" + this.atom.getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter)+ "}";
	}

	public String[] parserParameter() {
		return this.atom.parserParameter();
	}
	
	public String toString() {
		return "{" + this.atom.toString() + "}";
	}
	
	public boolean delay4PageCal() {
		return true;
	}
}
