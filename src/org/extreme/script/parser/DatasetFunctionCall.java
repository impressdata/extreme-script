package org.extreme.script.parser;

import org.extreme.script.Calculator;


public class DatasetFunctionCall implements Node {
	private String sourceName;
	private String fnName;
	private Node[] arguments;

	DatasetFunctionCall(String sourceName, String fnName, Node[] arguments) {
		this.sourceName = sourceName;
		this.fnName = fnName;
		this.arguments = arguments;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getFnName() {
		return fnName;
	}

	public Node[] getArguments() {
		return arguments;
	}

	public Object eval(Calculator calculator) throws UtilEvalError {
		return calculator.resolveVariable(this);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(sourceName);
		sb.append('.');
		sb.append(fnName);
		if (arguments != null) {
			sb.append('(');
			for (int i = 0; i < arguments.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(arguments[i]);
			}
			sb.append(')');
		}

		return sb.toString();
	}
}
