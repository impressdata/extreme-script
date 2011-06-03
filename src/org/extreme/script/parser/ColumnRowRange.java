package org.extreme.script.parser;

import org.extreme.commons.ColumnRow;
import org.extreme.script.Calculator;
import org.extreme.script.ExTool;
import org.extreme.script.Primitive;


public class ColumnRowRange extends Tiny {
	private ColumnRowLiteral from;
	private ColumnRowLiteral to;
	
	public static interface IntersectAction {
		public Object intersect(Calculator calculator, int left, int top, int width, int height);
	}

	/*
	 * :发现在做ParameterMapNameSpace解析的时候会调用这个类的toString方法
	 * 一个ColumnRowRange扩展成上万个之后,要调用上万次,挺费时间的
	 * 所以保存下来
	 */
	private transient String toString;
	
	public static ColumnRowRange columnRow2Range(ColumnRow columnrow) {
		ColumnRowLiteral literal = new ColumnRowLiteral(columnrow, null, null, null, null, null);
		return new ColumnRowRange(literal, null);
	}
	
	public ColumnRowRange(ColumnRowLiteral from, ColumnRowLiteral to) {
		this.from = from;
		this.to = to;
	}

	public ColumnRowLiteral getFrom() {
		return from;
	}

	public ColumnRowLiteral getTo() {
		return to;
	}
	
	public Object resolve(Calculator calc, IntersectAction iAction) {
    	ColumnRowLiteral from_literal = this.getFrom();
    	ColumnRowLiteral to_literal = this.getTo();
    	
    	ColumnRow from_columnrow = from_literal == null ? null : from_literal.getTargetColumnRow();
    	ColumnRow to_columnrow = to_literal == null ? null : to_literal.getTargetColumnRow();
    	
    	if(from_columnrow == null) {
    		return Primitive.NULL;
    	}
    	
    	if(to_columnrow == null) {
    		to_columnrow = from_columnrow;
    	}
    	
    	int left = Math.min(from_columnrow.column, to_columnrow.column);
    	int top = Math.min(from_columnrow.row, to_columnrow.row);
    	int width = Math.max(from_columnrow.column, to_columnrow.column) - left + 1;
    	int height = Math.max(from_columnrow.row, to_columnrow.row) - top + 1;
    	
    	return iAction.intersect(calc, left, top, width, height);
	}

	public Object eval(Calculator calculator) throws UtilEvalError {
		return calculator.resolveVariableInCE(this);
	}
	
	public String exString(Calculator calculator) {
		Object attr = calculator.getAttribute(ExTool.TAG);
		if(attr instanceof ExTool) {
			return ((ExTool)attr).ex(calculator, this);
		}
		
		return this.toString();
	}
	
	public String toString() {
		if (toString == null) {
			StringBuffer sb = new StringBuffer().append(from);
			if (to != null) {
				sb.append(':').append(to);
			}
			
			toString = sb.toString();
		}
		return toString;
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		return from.getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter) +  (to != null ? (":" + to.getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter)) : "");
	}

	public String[] parserParameter() {
		if (to == null) {
			return new String[]{from.getParameterName()};
		} else {
			return new String[]{from.getParameterName(), to.getParameterName()};
		}
	}
}
