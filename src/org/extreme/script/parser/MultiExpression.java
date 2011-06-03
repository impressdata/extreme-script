package org.extreme.script.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extreme.commons.util.Utils;
import org.extreme.script.Calculator;


public class MultiExpression extends MathExpression {
    private static final String MULTI = "*";
    private static final String DIV = "/";
    private static final String MOD = "%";
    // :默认的decimal相除时的精度
    private static final int DEFAULT_DECIMAL_SCALE = 20;
    
	String[] ops;
	Node[] powers;
	
	
	MultiExpression(Node[] powers, String[] ops) {
		this.powers = powers;
		this.ops = ops;
	}
	
	private static boolean isMULTI(String op) {
		return MULTI.equals(op);
	}
	
	private static boolean isDIV(String op) {
		return DIV.equals(op);
	}
	
	private static boolean isMOD(String op) {
		return MOD.equals(op);
	}
	
	protected int sizeOfNodes() {
		return powers.length;
	}
	
	protected String emptyNodesException() {
		return "powerList should not be empty";
	}
	
	protected Node getNodeByIndex(int idx) {
		return powers[idx];
	}
	
	protected String getOpByIndex(int idx) {
		return ops[idx];
	}
	
	protected boolean shortcutJudge() {
		return false;
	}
	
	protected Object ccNINI(String op) {
		if (isMOD(op)) {
			return OperationUtils.ZERO;
		} else if (isMULTI(op)) {
			return OperationUtils.POSITIVE_INFINITY;
		} else {
			return new Integer(1);
		}
	}

	protected Object ccPIPI(String op) {
		return ccNINI(op);
	}

	protected Object ccNIPI(String op) {
		if (isMOD(op)) {
			return OperationUtils.ZERO;
		} else if (isMULTI(op)) {
			return OperationUtils.NEGATIVE_INFINITY;
		} else {
			return new Integer(-1);
		}
	}

	protected Object ccPINI(String op) {
		return ccNIPI(op);
	}

	protected Object ccNIRE(Object right, String op) {
		if (isMOD(op)) {
			return OperationUtils.ZERO;
		}
		
		Number rightNum = Utils.objectToNumber(right, true);
		if (rightNum == null) {
			return OperationUtils.NEGATIVE_INFINITY;
		}
		
		int rightInt = rightNum.intValue();
		if (isMULTI(op)) {
			if (rightInt > 0) {
				return OperationUtils.NEGATIVE_INFINITY;
			} else if (rightInt == 0) {
				return OperationUtils.ZERO;
			} else {
				return OperationUtils.POSITIVE_INFINITY;
			}
		} else {
			if (rightInt >= 0) {
				return OperationUtils.NEGATIVE_INFINITY;
			} else {
				return OperationUtils.POSITIVE_INFINITY;
			}
		}
	}

	protected Object ccPIRE(Object right, String op) {
		Object reverseResult = ccNIRE(right, op);
		if (reverseResult == OperationUtils.POSITIVE_INFINITY) {
			return OperationUtils.NEGATIVE_INFINITY;
		} else if (reverseResult == OperationUtils.NEGATIVE_INFINITY) {
			return OperationUtils.POSITIVE_INFINITY;
		} else {
			return reverseResult;
		}
	}

	protected Object ccRENI(Object left, String op) {
		if (isMOD(op) || isDIV(op)) {
			return OperationUtils.ZERO;
		}
		
		Number leftNum = Utils.objectToNumber(left, true);
		if (leftNum == null) {
			return OperationUtils.NEGATIVE_INFINITY;
		}
		
		int leftInt = leftNum.intValue();
		if (leftInt > 0) {
			return OperationUtils.NEGATIVE_INFINITY;
		} else if (leftInt == 0) {
			return OperationUtils.ZERO;
		} else {
			return OperationUtils.POSITIVE_INFINITY;
		}
	}

	protected Object ccREPI(Object left, String op) {
		Object reverseResult = ccRENI(left, op);
		if (reverseResult == OperationUtils.POSITIVE_INFINITY) {
			return OperationUtils.NEGATIVE_INFINITY;
		} else if (reverseResult == OperationUtils.NEGATIVE_INFINITY) {
			return OperationUtils.POSITIVE_INFINITY;
		} else {
			return reverseResult;
		}
	}

	protected Object ccRERE(Object left, Object right, String op) {
		return numberOperation(left, right, op);
	}
	
	protected Object decimalOperation(BigDecimal B1, BigDecimal B2, String op) {
		if (isMULTI(op)) {
			return B1.multiply(B2);
		} else if (isDIV(op)) {
			if (B1.compareTo(OperationUtils.BZERO) == 0) {
				return new Integer(0);
			} else if (B2.compareTo(OperationUtils.BZERO) == 0) {
				return B1.compareTo(OperationUtils.BZERO) > 0 ? OperationUtils.POSITIVE_INFINITY : OperationUtils.NEGATIVE_INFINITY;
			} else {
				// :这个地方的除得的精度是根据前面B1的精度来算的，所以计算是要设置精度的，但是精度从哪来呢，暂时没想好。先来20位小数
				return B1.divide(B2, 
						B1.scale() > DEFAULT_DECIMAL_SCALE ? B1.scale() : DEFAULT_DECIMAL_SCALE, 
						BigDecimal.ROUND_HALF_EVEN		
				);
			}
		} else {
			return B1.toBigInteger().mod(B2.toBigInteger());
		}
	}

	protected Object doubleOperation(double D1, double D2, String op) {
		if (isMULTI(op)) {
			return new Double(D1 * D2);
		} else if (isDIV(op)) {
			if (D1 == 0) {
				return new Integer(0);
			} else if (D2 == 0) {
				return D1 > 0 ? OperationUtils.POSITIVE_INFINITY : OperationUtils.NEGATIVE_INFINITY;
			} else {
				return new Double(D1 / D2);
			}
		} else {
			return new Double(D1 % D2);
		}
	}
	
	protected Object intOperation(int I1, int I2, String op) {
		if (isMULTI(op)) {
			BigInteger bi =  BigInteger.valueOf(I1).multiply(BigInteger.valueOf(I2));
			if (Math.abs(bi.longValue()) > Math.abs(bi.intValue())) {
				return bi;
			} else {
				return new Integer(bi.intValue());
			}
		} else if (isDIV(op)) {
			if (I1 == 0) {
				return new Integer(0);
			} else if (I2 == 0) {
				return I1 > 0 ? OperationUtils.POSITIVE_INFINITY : OperationUtils.NEGATIVE_INFINITY;
			} else {
				return new Double((double)I1 / I2);
			}
		} else {
			return new Integer(I1 % I2);
		}
	}

	public void traversal4Tiny(TinyHunter hunter) {
		for (int i = 0; i < powers.length; i++) {
			powers[i].traversal4Tiny(hunter);
		}
	}
	
	public String exString(Calculator calculator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < powers.length; i++) {
			if (i > 0) {
				sb.append(' ').append(ops[i - 1]).append(' ');
			}
			sb.append(powers[i].exString(calculator));
		}

		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < powers.length; i++) {
			if (i > 0) {
				sb.append(' ').append(ops[i - 1]).append(' ');
			}
			sb.append(powers[i]);
		}

		return sb.toString();
	}
	
	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < powers.length; i++) {
			if (i > 0) {
				sb.append(' ').append(ops[i - 1]).append(' ');
			}
			sb.append(powers[i].getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter));
		}

		return sb.toString();
	}
	
	public String[] parserParameter() {
		List paraList = new ArrayList();
		for (int i = 0, length = powers.length; i < length; i++) {
			paraList.addAll(Arrays.asList(powers[i].parserParameter()));
		}
		return (String[])paraList.toArray(new String[paraList.size()]);
	}
	
	public void trav4HuntSIL(List list) {
		for (int i = 0; i < powers.length; i++) {
			powers[i].trav4HuntSIL(list);
		}
	}
	
	public void trav4HuntBIL(List list) {
		for (int i = 0; i < powers.length; i++) {
			powers[i].trav4HuntBIL(list);
		}
	}
}
