package org.extreme.script.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extreme.script.Calculator;


public class ConditionalAndExpression extends BinaryExpression {
	private static final String AND = "&&";
	
	Node[] rels;

	ConditionalAndExpression(Node[] rels) {
		this.rels = rels;
	}
	
	protected int sizeOfNodes() {
		return rels.length;
	}
	
	protected String emptyNodesException() {
		return "relationList should not be empty";
	}
	
	protected Node getNodeByIndex(int idx) {
		return rels[idx];
	}
	
	protected String getOpByIndex(int idx) {
		return AND;
	}
	
	protected boolean shortcutJudge() {
		return true;
	}
	
	protected boolean judge(Object ob) {
		return OperationUtils.isFalse(ob);
	}
	
	protected Object objectBinaryOperation(Object left, Object right, String op) {
		return OperationUtils.isFalse(left) ? left : right;
	}
	
	public void traversal4Tiny(TinyHunter hunter) {
		for (int i = 0; i < rels.length; i++) {
			rels[i].traversal4Tiny(hunter);
		}
	}
	
	public String exString(Calculator calculator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rels.length; i++) {
			if (i > 0) {
				sb.append(" && ");
			}
			sb.append(rels[i].exString(calculator));
		}

		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rels.length; i++) {
			if (i > 0) {
				sb.append(" && ");
			}
			sb.append(rels[i]);
		}

		return sb.toString();
	}
	
	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rels.length; i++) {
			if (i > 0) {
				sb.append(" && ");
			}
			sb.append(rels[i].getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter));
		}

		return sb.toString();
	}
	
	public String[] parserParameter() {
		List paraList = new ArrayList();
		for (int i = 0, length = rels.length; i < length; i++) {
			paraList.addAll(Arrays.asList(rels[i].parserParameter()));
		}
		return (String[])paraList.toArray(new String[paraList.size()]);
	}

	public void trav4HuntSIL(List list) {
		for (int i = 0; i < rels.length; i++) {
			rels[i].trav4HuntSIL(list);
		}
	}
	
	public void trav4HuntBIL(List list) {
		for (int i = 0; i < rels.length; i++) {
			rels[i].trav4HuntBIL(list);
		}
	}
}
