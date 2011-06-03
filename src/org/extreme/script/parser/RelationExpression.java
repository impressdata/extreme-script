package org.extreme.script.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.script.Calculator;
import org.extreme.script.FArray;


public class RelationExpression extends MathExpression {
    private static final String GT = ">";
    private static final String LT = "<";
    private static final String GE = ">=";
    private static final String LE = "<=";
    private static final String EQ = "=";
    private static final String NE1 = "!=";
    private static final String NE2 = "<>";
    
	Node left;
	String op;
	Node right;
	
	RelationExpression(Node left, String op, Node right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}
	
	private static Boolean onEQ(String op) {
		return Boolean.valueOf(GE.equals(op) || LE.equals(op) || EQ.equals(op));
	}
	
	private static Boolean onLT(String op) {
		return Boolean.valueOf(LT.equals(op) || LE.equals(op) || NE1.equals(op) || NE2.equals(op));
	}
	
	private static Boolean onGT(String op) {
		return Boolean.valueOf(GT.equals(op) || GE.equals(op) || NE1.equals(op) || NE2.equals(op));
	}
	
	protected int sizeOfNodes() {
		int size = 0;
		if (left != null) size++;
		if (right != null) size++;
		
		return size;
	}
	
	protected String emptyNodesException() {
		return "addExpression should not be empty";
	}
	
	protected Node getNodeByIndex(int idx) {
		return idx == 0 ? left : right;
	}
	
	protected String getOpByIndex(int idx) {
		return op;
	}
	
	protected boolean shortcutJudge() {
		return false;
	}
	
	protected Object arrayBinaryOperation(FArray array1, FArray array2, String op) {
		boolean equal = true;
		if (array1.length() == array2.length()) {
			for (int i = 0, len = array1.length(); i < len; i++) {
				// :只要遇到一个不相等的,就可以直接退出
				if (!(equal = ComparatorUtils.equals(array1.elementAt(i), array2.elementAt(i)))) {
					break;
				}
			}
		} else {
			equal = false;
		}
		
		// :如果两个array是相等的
		if (equal) {
			return (GE.equals(op) || LE.equals(op) || EQ.equals(op)) ? Boolean.TRUE : Boolean.FALSE;
		}
		
		// :如果两个array是不相等的,那么相等与不相等的操作符就可以直接返回结果
		if (EQ.equals(op)) {
			return Boolean.FALSE;
		} else if (NE1.equals(op) || NE2.equals(op)) {
			return Boolean.TRUE;
		}
		
		// :在不相等的情况下,从前往后,一个个元素比较,只要一个元素不相等,就可以返回GT,GE,LT,LE对应的结果了
		
		// :先在两个数组的最小长度上比较
		int min = Math.min(array1.length(), array2.length());
		int result = 0; // 0表示相等, >0表示大于, <0表示小于
		for (int i = 0; i < min && result == 0; i++) {
			result = ComparatorUtils.compare(array1.elementAt(i), array2.elementAt(i));
		}
		
		// :如果min长度内所有元素都相等,那么长度大的那个就大
		if (result == 0) {
			result = array1.length() - array2.length();
		}

		if (result > 0) {
			return (GT.equals(op) || GE.equals(op)) ? Boolean.TRUE : Boolean.FALSE;
		} else if (result < 0) {
			return (GT.equals(op) || GE.equals(op)) ? Boolean.FALSE : Boolean.TRUE;
		}
		
		// :理论上不会走到这一步了
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

	public void traversal4Tiny(TinyHunter hunter) {
		if (left != null) {
			left.traversal4Tiny(hunter);
		}
		if (right != null) {
			right.traversal4Tiny(hunter);
		}
	}
	
	public String exString(Calculator calculator) {
		StringBuffer sb = new StringBuffer();
		sb.append(left.exString(calculator));
		if(op != null && right != null) {
			sb.append(' ').append(op).append(' ').append(right.exString(calculator));
		}
		
		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(left);
		if(op != null && right != null) {
			sb.append(' ').append(op).append(' ').append(right);
		}
		
		return sb.toString();
	}
	
	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		StringBuffer sb = new StringBuffer();
		sb.append(left.getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter));
		if(op != null && right != null) {
			sb.append(' ').append(op).append(' ').append(right.getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter));
		}
		
		return sb.toString();
	}

	public String[] parserParameter() {
		List paraList = new ArrayList();

		paraList.addAll(Arrays.asList(left.parserParameter()));
		if(op != null && right != null) {
			paraList.addAll(Arrays.asList(right.parserParameter()));
		}
		return (String[]) paraList.toArray(new String[paraList.size()]);
	}
	
	public void trav4HuntSIL(List list) {
		if (left != null) {
			left.trav4HuntSIL(list);
		}
		if (right != null) {
			right.trav4HuntSIL(list);
		}
	}
	
	public void trav4HuntBIL(List list) {
		if (left != null) {
			left.trav4HuntBIL(list);
		}
		if (right != null) {
			right.trav4HuntBIL(list);
		}
	}
}
