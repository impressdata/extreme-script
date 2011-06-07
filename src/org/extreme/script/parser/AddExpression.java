package org.extreme.script.parser;

import java.math.BigDecimal;
import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.commons.util.Utils;


public class AddExpression extends MathExpression {
    public static final String PLUS = "+";
    public static final String MINUS = "-";
	
	public AddExpression() {
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
}
