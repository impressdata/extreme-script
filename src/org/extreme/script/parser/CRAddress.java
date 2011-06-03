package org.extreme.script.parser;

import org.extreme.commons.ColumnRow;
import org.extreme.script.Calculator;


public class CRAddress extends Tiny {
	private ColumnRow target; // 所求的地址的单元格
	
	CRAddress(ColumnRow target) {
		this.target = target;
	}
	
	public ColumnRow getTarget() {
		return target;
	}

	public Object eval(Calculator calculator) throws UtilEvalError {
		return calculator.resolveVariable(this);
	}

	public String toString() {
		return "&" + target;
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		int newColumnIndex = target.getColumn();
        if (columnIndex != -1 && target.getColumn() >= columnIndex) {
        	newColumnIndex += colChanged;
        }
        int newRowIndex = target.getRow();
        if (rowIndex != -1 && target.getRow() >= rowIndex) {
            newRowIndex += rowChanged;
        }
		return "&" +  ColumnRow.valueOf(newColumnIndex, newRowIndex).toString();
	}

	
	//可能不对
	public String[] parserParameter() {
		return target == null ? null : new String[]{new String(target.toString())};
	}
}
