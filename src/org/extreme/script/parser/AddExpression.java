package org.extreme.script.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.extreme.commons.util.DateUtils;
import org.extreme.commons.util.Utils;
import org.extreme.script.Calculator;


public class AddExpression extends MathExpression {
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    
	String[] ops;
	Node[] multis;
	
	public AddExpression() {
		
	}
	
	AddExpression(Node[] multis, String[] ops) {
		this.multis = multis;
		this.ops = ops;
	}
	
	protected int sizeOfNodes() {
		return multis.length;
	}
	
	protected String emptyNodesException() {
		return "multiList should not be empty";
	}
	
	protected Node getNodeByIndex(int idx) {
		return multis[idx];
	}
	
	protected String getOpByIndex(int idx) {
		return ops[idx];
	}
	
	protected boolean shortcutJudge() {
		return false;
	}
	
	private static boolean isPLUS(String op) {
		return PLUS.equals(op);
	}
	
	private static boolean isMINUS(String op) {
		return MINUS.equals(op);
	}

	protected Object ccNINI(String op) {
		return isPLUS(op) ? OperationUtils.NEGATIVE_INFINITY : (Object)OperationUtils.ZERO;
	}

	protected Object ccNIPI(String op) {
		return isPLUS(op) ? (Object)OperationUtils.ZERO : OperationUtils.NEGATIVE_INFINITY;
	}

	protected Object ccNIRE(Object right, String op) {
		return OperationUtils.NEGATIVE_INFINITY;
	}

	protected Object ccPINI(String op) {
		return isPLUS(op) ? (Object)OperationUtils.ZERO : OperationUtils.POSITIVE_INFINITY;
	}

	protected Object ccPIPI(String op) {
		return isPLUS(op) ? OperationUtils.POSITIVE_INFINITY : (Object)OperationUtils.ZERO;
	}

	protected Object ccPIRE(Object right, String op) {
		return OperationUtils.POSITIVE_INFINITY;
	}

	protected Object ccRENI(Object left, String op) {
		return isPLUS(op) ? OperationUtils.NEGATIVE_INFINITY : OperationUtils.POSITIVE_INFINITY;
	}

	protected Object ccREPI(Object left, String op) {
		return isPLUS(op) ? OperationUtils.POSITIVE_INFINITY : OperationUtils.NEGATIVE_INFINITY;
	}

	public Object ccRERE(Object left, Object right, String op) throws UtilEvalError {
		// :如果左边是日期型,右边是数字型,做日期型加减一天的操作
		if (left instanceof Date && right instanceof Number) {
			int i = ((Number)right).intValue();
			return DateUtils.datePlusInteger((Date)left, isPLUS(op) ? i : -i);
		}
		// :如果是加法操作,并且左右有一个是字符串型的
		if ((left instanceof String || right instanceof String) && isPLUS(op)) {
			return Utils.objectToString(left) + Utils.objectToString(right);
		}
		// :如果是减法操作,并且左右都是日期型的,执行日期所差天数的计算
		if (left instanceof Date && right instanceof Date && isMINUS(op)) {
			return new Integer((int)DateUtils.subtractDate((Date)left, (Date)right, DateUtils.DAY));
		}
		
		return numberOperation(left, right, op);
	}
	
	protected Object decimalOperation(BigDecimal B1, BigDecimal B2, String op) {
    	if (isPLUS(op)) {
    		return B1.add(B2);
    	} else {
    		return B1.subtract(B2);
    	}
	}
    
	protected Object doubleOperation(double D1, double D2, String op) {
    	if (isPLUS(op)) {
    		return new Double(D1 + D2);
    	} else {
    		return new Double(D1 - D2);
    	}
	}
	
	protected Object intOperation(int I1, int I2, String op) {
    	if (isPLUS(op)) {
    		return new Integer(I1 + I2);
    	} else {
    		return new Integer(I1 - I2);
    	}
	}

	public void traversal4Tiny(TinyHunter hunter) {
		for (int i = 0; i < multis.length; i++) {
			multis[i].traversal4Tiny(hunter);
		}
	}
	
	public String exString(Calculator calculator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < multis.length; i++) {
			if (i > 0) {
				sb.append(' ').append(ops[i - 1]).append(' ');
			}
			sb.append(multis[i].exString(calculator));
		}

		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < multis.length; i++) {
			if (i > 0) {
				sb.append(' ').append(ops[i - 1]).append(' ');
			}
			sb.append(multis[i]);
		}

		return sb.toString();
	}
	
	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < multis.length; i++) {
			if (i > 0) {
				sb.append(' ').append(ops[i - 1]).append(' ');
			}
			sb.append(multis[i].getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter));
		}

		return sb.toString();
	}

	public String[] parserParameter() {
		List paraList = new ArrayList();
		for (int i = 0, length = multis.length; i < length; i++) {
			paraList.addAll(Arrays.asList(multis[i].parserParameter()));
		}
		return (String[])paraList.toArray(new String[paraList.size()]);
	}
	
	public void trav4HuntSIL(List list) {
		for (int i = 0; i < multis.length; i++) {
			multis[i].trav4HuntSIL(list);
		}
	}
	
	public void trav4HuntBIL(List list) {
		for (int i = 0; i < multis.length; i++) {
			multis[i].trav4HuntBIL(list);
		}
	}
}
