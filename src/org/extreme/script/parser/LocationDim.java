package org.extreme.script.parser;

import java.io.Serializable;

import org.extreme.commons.ColumnRow;


public class LocationDim implements Cloneable, Serializable {
	public static final byte ABSOLUTE = 0;
	public static final byte PLUS = 1;
	public static final byte MINUS = 2;
	
	private ColumnRow columnrow;
	private int index;
	private byte op;
	
	public LocationDim(ColumnRow columnrow, byte op, int index) {
		this.columnrow = columnrow;
		this.op = op;
		this.index = index;
	}
	
	public ColumnRow getColumnrow() {
		return columnrow;
	}
	
	public void setIndex(int idx) {
		this.index = idx;
	}

	public int getIndex() {
		return index;
	}

	public byte getOp() {
		return op;
	}

	public String toString() {
		return this.columnrow + ":" + (op == PLUS ? "+" : (op == MINUS ? "-" : "!")) + this.index;
	}
	
	public void changeColumnRow(int rowIndex, int rowChanged, int columnIndex, int colChanged){
		int newColumnIndex = columnrow.getColumn();
        if (columnIndex != -1 && columnrow.getColumn() >= columnIndex) {
        	newColumnIndex += colChanged;
        }

        int newRowIndex = columnrow.getRow();
        if (rowIndex != -1 && columnrow.getRow() >= rowIndex) {
            newRowIndex += rowChanged;
        }
        
        columnrow = ColumnRow.valueOf(newColumnIndex, newRowIndex);
	}
}
