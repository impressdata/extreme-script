package org.extreme.script.parser;

import java.math.BigDecimal;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.script.FArray;


public class RelationExpression extends MathExpression {
    public static final String GT = ">";
    public static final String LT = "<";
    public static final String GE = ">=";
    public static final String LE = "<=";
    public static final String EQ = "=";
    public static final String NE1 = "!=";
    public static final String NE2 = "<>";
	
	private static Boolean onEQ(String op) {
		return Boolean.valueOf(GE.equals(op) || LE.equals(op) || EQ.equals(op));
	}
	
	private static Boolean onLT(String op) {
		return Boolean.valueOf(LT.equals(op) || LE.equals(op) || NE1.equals(op) || NE2.equals(op));
	}
	
	private static Boolean onGT(String op) {
		return Boolean.valueOf(GT.equals(op) || GE.equals(op) || NE1.equals(op) || NE2.equals(op));
	}
	
	protected Object arrayBinaryOperation(FArray array1, FArray array2, String op) {
		boolean equal = true;
		if (array1.length() == array2.length()) {
			for (int i = 0, len = array1.length(); i < len; i++) {
				// :ֻҪ����һ������ȵ�,�Ϳ���ֱ���˳�
				if (!(equal = ComparatorUtils.equals(array1.elementAt(i), array2.elementAt(i)))) {
					break;
				}
			}
		} else {
			equal = false;
		}
		
		// :�������array����ȵ�
		if (equal) {
			return (GE.equals(op) || LE.equals(op) || EQ.equals(op)) ? Boolean.TRUE : Boolean.FALSE;
		}
		
		// :�������array�ǲ���ȵ�,��ô����벻��ȵĲ������Ϳ���ֱ�ӷ��ؽ��
		if (EQ.equals(op)) {
			return Boolean.FALSE;
		} else if (NE1.equals(op) || NE2.equals(op)) {
			return Boolean.TRUE;
		}
		
		// :�ڲ���ȵ������,��ǰ����,һ����Ԫ�رȽ�,ֻҪһ��Ԫ�ز����,�Ϳ��Է���GT,GE,LT,LE��Ӧ�Ľ����
		
		// :���������������С�����ϱȽ�
		int min = Math.min(array1.length(), array2.length());
		int result = 0; // 0��ʾ���, >0��ʾ����, <0��ʾС��
		for (int i = 0; i < min && result == 0; i++) {
			result = ComparatorUtils.compare(array1.elementAt(i), array2.elementAt(i));
		}
		
		// :���min����������Ԫ�ض����,��ô���ȴ���Ǹ��ʹ�
		if (result == 0) {
			result = array1.length() - array2.length();
		}

		if (result > 0) {
			return (GT.equals(op) || GE.equals(op)) ? Boolean.TRUE : Boolean.FALSE;
		} else if (result < 0) {
			return (GT.equals(op) || GE.equals(op)) ? Boolean.FALSE : Boolean.TRUE;
		}
		
		// :�����ϲ����ߵ���һ����
		return Boolean.FALSE;
	}
	
	protected boolean isRelationExpression() {
		return true;
	}
	
	protected Object ccNINI(String op) {
		return onEQ(op);
	}

	protected Object ccNIPI(String op) {
		return onLT(op);
	}

	protected Object ccNIRE(Object right, String op) {
		return onLT(op);
	}

	protected Object ccPINI(String op) {
		return onGT(op);
	}

	protected Object ccPIPI(String op) {
		return onEQ(op);
	}

	protected Object ccPIRE(Object right, String op) {
		return onGT(op);
	}

	protected Object ccRENI(Object left, String op) {
		return onGT(op);
	}

	protected Object ccREPI(Object left, String op) {
		return onLT(op);
	}

	protected Object ccRERE(Object left, Object right, String op) {
		if (left instanceof Number && right instanceof Number) {
			return numberOperation(left, right, op);
		}
		
		if (ComparatorUtils.equals(left, right)) {
			return onEQ(op);
		} else if (ComparatorUtils.compare(left, right) > 0) {
			return onGT(op);
		} else {
			return onLT(op);
		}
	}
	
	protected Object decimalOperation(BigDecimal B1, BigDecimal B2, String op) {
		return compareOperation(ComparatorUtils.compare(B1, B2), op);
	}
	
	protected Object doubleOperation(double D1, double D2, String op) {
		return compareOperation(ComparatorUtils.compare(D1, D2), op);
	}
	
	protected Object intOperation(int I1, int I2, String op) {
		return compareOperation(I1 - I2, op);
	}
	
	private Object compareOperation(double compareResult, String op) {
		if (compareResult == 0) {
			return onEQ(op);
		} else if (compareResult > 0) {
			return onGT(op);
		} else {
			return onLT(op);
		}
	}
}
