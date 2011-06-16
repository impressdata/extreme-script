package org.extreme.script.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
}
