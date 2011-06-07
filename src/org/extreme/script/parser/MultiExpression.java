package org.extreme.script.parser;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.extreme.commons.util.Utils;


public class MultiExpression extends MathExpression {
    public static final String MULTI = "*";
    public static final String DIV = "/";
    public static final String MOD = "%";
    // :Ĭ�ϵ�decimal���ʱ�ľ���
    private static final int DEFAULT_DECIMAL_SCALE = 20;
	
	private static boolean isMULTI(String op) {
		return MULTI.equals(op);
	}
	
	private static boolean isDIV(String op) {
		return DIV.equals(op);
	}
	
	private static boolean isMOD(String op) {
		return MOD.equals(op);
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
				// :����ط��ĳ��õľ����Ǹ���ǰ��B1�ľ�������ģ����Լ�����Ҫ���þ��ȵģ����Ǿ��ȴ������أ���ʱû��á�����20λС��
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
}
