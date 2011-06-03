package org.extreme.script.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extreme.commons.util.Utils;
import org.extreme.script.Calculator;


public class PowerExpression extends MathExpression {
	private static final String POW = "^";

	Node[] unarys;

	PowerExpression(Node[] unarys) {
		this.unarys = unarys;
	}

	protected int sizeOfNodes() {
		return unarys.length;
	}
	
	protected String emptyNodesException() {
		return "unaryList should not be empty";
	}
	
	protected Node getNodeByIndex(int idx) {
		return unarys[idx];
	}
	
	protected String getOpByIndex(int idx) {
		return POW;
	}
	
	protected boolean shortcutJudge() {
		return false;
	}

	protected Object ccNINI(String op) {
		return OperationUtils.ZERO;
	}

	protected Object ccNIPI(String op) {
		return OperationUtils.NEGATIVE_INFINITY;
	}

	protected Object ccNIRE(Object right, String op) {
		Number rightNum = Utils.objectToNumber(right, true);
		if (rightNum == null) {
			return OperationUtils.NEGATIVE_INFINITY;
		} else if (rightNum.intValue() < 0) {
			return OperationUtils.ZERO;
		} else {
			return OperationUtils.NEGATIVE_INFINITY;
		}
	}

	protected Object ccPINI(String op) {
		return OperationUtils.ZERO;
	}

	protected Object ccPIPI(String op) {
		return OperationUtils.POSITIVE_INFINITY;
	}

	protected Object ccPIRE(Object right, String op) {
		Number rightNum = Utils.objectToNumber(right, true);
		if (rightNum == null) {
			return OperationUtils.POSITIVE_INFINITY;
		} else if (rightNum.intValue() < 0) {
			return OperationUtils.ZERO;
		} else {
			return OperationUtils.POSITIVE_INFINITY;
		}
	}

	protected Object ccRENI(Object left, String op) {
		return OperationUtils.ZERO;
	}

	protected Object ccREPI(Object left, String op) {
		Number leftNum = Utils.objectToNumber(left, true);
		if (leftNum == null) {
			return OperationUtils.POSITIVE_INFINITY;
		}

		int leftInt = leftNum.intValue();
		if (leftInt > 0) {
			return OperationUtils.POSITIVE_INFINITY;
		} else if (leftInt == 0) {
			return OperationUtils.ZERO;
		} else {
			return OperationUtils.NEGATIVE_INFINITY;
		}
	}

	protected Object ccRERE(Object left, Object right, String op) {
		return numberOperation(left, right, op);
	}

	protected Object decimalOperation(BigDecimal B1, BigDecimal B2, String op) {
		return doubleOperation(B1.doubleValue(), B2.doubleValue(), op);
	}

	protected Object doubleOperation(double D1, double D2, String op) {
		return new Double(Math.pow(D1, D2));
	}

	protected Object intOperation(int I1, int I2, String op) {
		return new Double(Math.pow(I1, I2));
	}

	public void traversal4Tiny(TinyHunter hunter) {
		for (int i = 0; i < unarys.length; i++) {
			unarys[i].traversal4Tiny(hunter);
		}
	}

	public String exString(Calculator calculator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < unarys.length; i++) {
			if (i > 0) {
				sb.append(" ^ ");
			}
			sb.append(unarys[i].exString(calculator));
		}

		return sb.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < unarys.length; i++) {
			if (i > 0) {
				sb.append(" ^ ");
			}
			sb.append(unarys[i]);
		}

		return sb.toString();
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < unarys.length; i++) {
			if (i > 0) {
				sb.append(" ^ ");
			}
			sb.append(unarys[i].getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter));
		}

		return sb.toString();
	}

	public String[] parserParameter() {
		List paraList = new ArrayList();
		for (int i = 0, length = unarys.length; i < length; i++) {
			if (unarys[i].parserParameter() == null) {
				continue;
			}
			paraList.addAll(Arrays.asList(unarys[i].parserParameter()));
		}
		return (String[]) paraList.toArray(new String[paraList.size()]);
	}
	
	public void trav4HuntSIL(List list) {
		for (int i = 0; i < unarys.length; i++) {
			unarys[i].trav4HuntSIL(list);
		}
	}
	
	public void trav4HuntBIL(List list) {
		for (int i = 0; i < unarys.length; i++) {
			unarys[i].trav4HuntBIL(list);
		}
	}
}
