package org.extreme.script.parser;

import java.math.BigDecimal;

import org.extreme.commons.util.Utils;


public class PowerExpression extends MathExpression {
	public static final String POW = "^";

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
}
