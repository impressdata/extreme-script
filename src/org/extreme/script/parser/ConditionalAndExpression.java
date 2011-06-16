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
}
