package org.extreme.script.parser;

import org.extreme.commons.util.BaseCoreUtils;
import org.extreme.script.Calculator;


public class StringLiteral extends Tiny {
	private String statement;
	
	StringLiteral(String statement) {
		this.statement = statement;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
		try {
			return BaseCoreUtils.readESC(statement);
		} catch (Exception e) {
			throw new UtilEvalError(e);
		}
	}

	public String toString() {
		return "\"" + statement + "\"";
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		return this.toString();
	}
}
