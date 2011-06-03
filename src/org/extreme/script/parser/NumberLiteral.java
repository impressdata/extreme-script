package org.extreme.script.parser;

import org.extreme.commons.util.Utils;
import org.extreme.script.Calculator;


public class NumberLiteral extends Tiny {
	private String statement;
	
	public NumberLiteral(String statement) {
		this.statement = statement;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
		return Utils.string2Number(statement);
	}

	public String toString() {
		return statement;
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		return this.toString();
	}
}
