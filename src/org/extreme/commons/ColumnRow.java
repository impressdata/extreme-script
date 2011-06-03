package org.extreme.commons;

import org.extreme.commons.util.BaseCoreUtils;

/**
 * Column and Row.
 */
public class ColumnRow implements java.io.Serializable, Cloneable {
    public final int column;
    public final int row;
    
    public static ColumnRow valueOf(int column, int row) {
    	if(column >= 0 && column < 5 && row >= 0 && row < 10) {
    		return ColumnRowCache.cache[column][row];
    	}
    	return new ColumnRow(column, row);
    }
    
    public static ColumnRow valueOf(String str) {
    	ColumnRow columnrow = ColumnRow.valueOf(-1, -1);
    	if (str == null) {
            return columnrow;
        }
        // trim多于的空格.
    	str = str.trim();
        if(!str.matches("^[a-zA-Z]+[1-9]\\d*$")) {
            return columnrow;
        }

        try {
        	columnrow = ColumnRow.valueOf(
        			BaseCoreUtils.convertABCToInt(str.replaceAll("\\d", "")) - 1, 
        			Integer.parseInt(str.replaceAll("[a-zA-Z]", "")) - 1);
        } catch (NumberFormatException numberFormatException) {
            // 解析有错误，do nothing.
        }
        
        return columnrow;
    }
    
    /*
     * 判断这个columnrow是不是一个合法的ColumnRow
     */
    public static boolean validate(ColumnRow columnrow) {
    	return columnrow != null && columnrow.getColumn() >=0 && columnrow.getRow() >= 0;
    }

    /**
     * Constructor.
     * @param column the column
     * @param row the row 
     */
    private ColumnRow(int column, int row) {
        this.column = column;
        this.row = row;
    }
    
    /**
     * Gets the column.
     * @return the column.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets the row.
     * @return the row.
     */
    public int getRow() {
        return row;
    }
    
    public ColumnRow[] getRelatedColumnRows() {
    	return new ColumnRow[] {this};
    }

    /**
     * toString
     */
    public String toString() {
    	return BaseCoreUtils.convertIntToABC(column + 1) + (row + 1);
    }
    
    public int hashCode() {
        int result = 17;
        result = 37 * result + column;
        result = 37 * result + row;
        return result;
    }

    /**
     * Equals
     */
    public boolean equals(Object object) {
        if(!(object instanceof ColumnRow)) {
            return false;
        }

        ColumnRow newColumnRow = (ColumnRow) object;
        return this.row == newColumnRow.row && this.column == newColumnRow.column;
    }

    /**
     * Clone.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    private static class ColumnRowCache {
    	private ColumnRowCache() {}

    	static final ColumnRow[][] cache = new ColumnRow[5][10];

    	static {
    	    for(int i = 0; i < cache.length; i++)
    	    	for(int j = 0, len = cache[i].length; j < len; j++) 
    	    		cache[i][j] = new ColumnRow(i, j);
    	}
    }
}
