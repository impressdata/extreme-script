package org.extreme.script.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extreme.script.Calculator;


public class DatasetFunctionCall extends Atom {
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

	public void traversal4Tiny(TinyHunter hunter) {
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] != null) {
				arguments[i].traversal4Tiny(hunter);
			}
		}
	}

	public String exString(Calculator calculator) {
		StringBuffer sb = new StringBuffer(sourceName);
		sb.append('.');
		sb.append(fnName);
		if (arguments != null) {
			sb.append('(');
			for (int i = 0; i < arguments.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(arguments[i].exString(calculator));
			}
			sb.append(')');
		}

		return sb.toString();
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

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		StringBuffer sb = new StringBuffer(sourceName);
		sb.append('.');
		sb.append(fnName);
		if (arguments != null) {
			sb.append('(');
			for (int i = 0; i < arguments.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(arguments[i].getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter));
			}
			sb.append(')');
		}

		return sb.toString();
	}

	public String[] parserParameter() {
		List paraList = new ArrayList();
		for (int i = 0, length = arguments.length; i < length; i++) {
			paraList.addAll(Arrays.asList(arguments[i].parserParameter()));
		}
		return (String[]) paraList.toArray(new String[paraList.size()]);
	}

	public boolean delay4PageCal() {
		return false;
	}

	public void trav4HuntSIL(List list) {

	}

	public void trav4HuntBIL(List list) {

	}
}
