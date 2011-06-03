package org.extreme.script.parser;

import org.extreme.script.Calculator;

public class AtomLayerIndex extends Tiny {
	private int index; // ÁÐÐòºÅ

	AtomLayerIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}

	public String toString() {
		return "@" + index;
	}

	public Object eval(Calculator calculator) throws UtilEvalError {		
		return calculator.resolveVariable(this.toString());
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		return this.toString();
	}
}
