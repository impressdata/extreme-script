package org.extreme.script.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extreme.script.Calculator;


public class ConditionalOrExpression extends BinaryExpression {
	private static final String OR = "||";
	
	Node[] ands;

	ConditionalOrExpression(Node[] ands) {
		this.ands = ands;
	}
	
	protected int sizeOfNodes() {
		return ands.length;
	}
	
	protected String emptyNodesException() {
		return "conditionalAndList should not be empty";
	}
	
	protected Node getNodeByIndex(int idx) {
		return ands[idx];
	}
	
	protected String getOpByIndex(int idx) {
		return OR;
	}
	
	protected boolean shortcutJudge() {
		return true;
	}
	
	protected boolean judge(Object ob) {
		return !OperationUtils.isFalse(ob);
	}
	
	protected Object objectBinaryOperation(Object left, Object right, String op) {
		return OperationUtils.isFalse(left) ? right : left;
	}
	
	public void traversal4Tiny(TinyHunter hunter) {
		for (int i = 0; i < ands.length; i++) {
			ands[i].traversal4Tiny(hunter);
		}
	}
	
	public String exString(Calculator calculator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ands.length; i++) {
			if (i > 0) {
				sb.append(" || ");
			}
			sb.append(ands[i].exString(calculator));
		}

		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ands.length; i++) {
			if (i > 0) {
				sb.append(" || ");
			}
			sb.append(ands[i].toString());
		}

		return sb.toString();
	}
	
	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ands.length; i++) {
			if (i > 0) {
				sb.append(" || ");
			}
			sb.append(ands[i].getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter));
		}

		return sb.toString();
	}

	public String[] parserParameter() {
		List paraList = new ArrayList();
		for (int i = 0, length = ands.length; i < length; i++) {
			paraList.addAll(Arrays.asList(ands[i].parserParameter()));
		}
		return (String[])paraList.toArray(new String[paraList.size()]);
	}
	
	public void trav4HuntSIL(List list) {
		for (int i = 0; i < ands.length; i++) {
			ands[i].trav4HuntSIL(list);
		}
	}
	
	public void trav4HuntBIL(List list) {
		for (int i = 0; i < ands.length; i++) {
			ands[i].trav4HuntBIL(list);
		}
	}
}
