package org.extreme.script.parser;

import org.extreme.commons.ColumnRow;

public class ColumnRowLiteral {
	private ColumnRow targetColumnRow;
	private ColumnRowLocation leftLocation; //[]中的左父定位
	private ColumnRowLocation upLocation; //[]中的上父定位
	private Node condition; //{}中的条件
	
	// 前面四个参数得到CE后再根据[start, end]闭区间范围内的,-1表示倒数第1个,也就是最后一个
	private Node start;
	private Node end;
	
	public ColumnRowLiteral(ColumnRow targetColumnRow, ColumnRowLocation left, ColumnRowLocation up, 
			Node condition, 
			Node start, Node end) {
		this.targetColumnRow = targetColumnRow;
		this.leftLocation = left;
		this.upLocation = up;
		this.condition = condition;
		
		this.start = start;
		this.end = end;
	}
	/*
	 * :判断一下是不是纯粹的ColumnRow的形式
	 */
	public boolean isPureColumnRow() {
		return targetColumnRow != null && leftLocation == null && upLocation == null
		&& condition == null && start == null && end == null;
	}
	
	
	public ColumnRow getTargetColumnRow() {
		return targetColumnRow;
	}

	public ColumnRowLocation getLeftLocation() {
		return leftLocation;
	}

	public ColumnRowLocation getUpLocation() {
		return upLocation;
	}

	public Node getCondition() {
		return condition;
	}

	public Node getStart() {
		return start;
	}

	public Node getEnd() {
		return end;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(targetColumnRow);
		if(leftLocation != null || upLocation != null) {
			sb.append('[');
			if(leftLocation != null) {
				sb.append(leftLocation);
			}
			if(upLocation != null) {
				sb.append(';').append(upLocation);
			}
			sb.append(']');
		}
		
		if(condition != null) {
			sb.append('{').append(condition).append('}');
		}
		
		if(start != null || end != null) {
			if(condition == null) {
				sb.append("{}");
			}
			sb.append('[').append(start).append(':').append(end).append(']');
		}
		
		return sb.toString();
	}
	
	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		int newColumnIndex = targetColumnRow.getColumn();
        if (columnIndex != -1 && targetColumnRow.getColumn() >= columnIndex) {
        	newColumnIndex += colChanged;
        }

        // 判断RowIndex.
        int newRowIndex = targetColumnRow.getRow();
        if (rowIndex != -1 && targetColumnRow.getRow() >= rowIndex) {
            newRowIndex += rowChanged;
        }
        
        StringBuffer sb = new StringBuffer();
		sb.append(ColumnRow.valueOf(newColumnIndex, newRowIndex).toString());
		if(leftLocation != null || upLocation != null) {
			sb.append('[');
			if(leftLocation != null) {
				leftLocation.changeColumnRow(rowIndex, rowChanged, columnIndex, colChanged);
				sb.append(leftLocation);
			}
			if(upLocation != null) {
				upLocation.changeColumnRow(rowIndex, rowChanged, columnIndex, colChanged);
				sb.append(';').append(upLocation);
			}
			sb.append(']');
		}
		
		if(condition != null) {
			sb.append('{').append(condition.getExpression(rowIndex, rowChanged, columnIndex, colChanged, false)).append('}');
		}
		
		if(start != null || end != null) {
			if(condition == null) {
				sb.append("{}");
			}
			sb.append('[').append(start.getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter)).append(':').append(end.getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter)).append(']');
		}
		
		return sb.toString();
	}
	
	public String getParameterName() {
		return targetColumnRow.toString();
	}
}
