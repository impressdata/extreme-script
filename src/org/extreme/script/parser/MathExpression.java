package org.extreme.script.parser;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.extreme.commons.util.Utils;



public abstract class MathExpression extends BinaryExpression {
	
	public Object objectBinaryOperation(Object left, Object right, String op) throws UtilEvalError {
		if (left == OperationUtils.POSITIVE_INFINITY) {
			if (right == OperationUtils.POSITIVE_INFINITY) {
				return ccPIPI(op);
			} else if (right == OperationUtils.NEGATIVE_INFINITY) {
				return ccPINI(op);
			} else {
				return ccPIRE(right, op);
			}
		} else if (left == OperationUtils.NEGATIVE_INFINITY) {
			if (right == OperationUtils.POSITIVE_INFINITY) {
				return ccNIPI(op);
			} else if (right == OperationUtils.NEGATIVE_INFINITY) {
				return ccNINI(op);
			} else {
				return ccNIRE(right, op);
			}
		} else {
			if (right == OperationUtils.POSITIVE_INFINITY) {
				return ccREPI(left, op);
			} else if (right == OperationUtils.NEGATIVE_INFINITY) {
				return ccRENI(left, op);
			} else {
				// :�����������ͨ��ֵ����,������ת��Number
				Number leftNum = Utils.objectToNumber(left, true);
				Number rightNum = Utils.objectToNumber(right, true);
				
				if (leftNum != null) {
					left = leftNum;
				}
				if (rightNum != null) {
					right = rightNum;
				}
				
				return ccRERE(left, right, op);
			}
		}
	}
	
	// :������������������
	protected abstract Object ccPIPI(String op);
	// :�����������븺����
	protected abstract Object ccPINI(String op);
	// :������������������
	protected abstract Object ccPIRE(Object right, String op);
	
	// :���㸺������������
	protected abstract Object ccNIPI(String op);
	// :���㸺�����븺����
	protected abstract Object ccNINI(String op);
	// :���㸺������������
	protected abstract Object ccNIRE(Object right, String op);
	
	// :������������������
	protected abstract Object ccREPI(Object left, String op);
	// :�����������븺����
	protected abstract Object ccRENI(Object left, String op);
	// :������������������
	protected abstract Object ccRERE(Object left, Object right, String op) throws UtilEvalError;
	
	/*
	 * :���Ҷ������ֵ���ѧ����,�˴�ֻ���򵥵�double & int������
	 * ���ӵ����㽻������ȥ��
	 */
	protected Object numberOperation(Object left, Object right, String op) {
		Number leftNum = left instanceof Number ? (Number)left : new Integer(0);
		Number rightNum = right instanceof Number ? (Number)right : new Integer(0);
		
		if (leftNum instanceof BigDecimal || rightNum instanceof BigDecimal || 
				leftNum instanceof BigInteger || rightNum instanceof BigInteger) {
			return decimalOperation(
					leftNum instanceof BigDecimal ? (BigDecimal)leftNum : new BigDecimal(leftNum.toString()),
					rightNum instanceof BigDecimal ? (BigDecimal)rightNum : new BigDecimal(rightNum.toString()),
					op
			);
		} else if (leftNum instanceof Double || rightNum instanceof Double || 
				leftNum instanceof Float || rightNum instanceof Float) {
			return doubleOperation(leftNum.doubleValue(), rightNum.doubleValue(), op);
		} else {
			return intOperation(leftNum.intValue(), rightNum.intValue(), op);
		}
	}
	
	protected abstract Object decimalOperation(BigDecimal B1, BigDecimal B2, String op);
	protected abstract Object doubleOperation(double D1, double D2, String op);
	protected abstract Object intOperation(int I1, int I2, String op);
}
