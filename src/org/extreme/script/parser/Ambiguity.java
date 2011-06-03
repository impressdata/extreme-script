package org.extreme.script.parser;



import org.extreme.commons.ColumnRow;
import org.extreme.commons.util.StringUtils;
import org.extreme.script.Calculator;
import org.extreme.script.Primitive;


public class Ambiguity extends Tiny {
	private String statement;

	public Ambiguity(String statement) {
		this.statement = statement;
	}
	
	public String getStatement() {
		return this.statement;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
        if (statement == null) {
            throw new UtilEvalError("statement in literal is null");
        }

        Object obj = calculator.resolveVariable(statement);

        if (obj == null) {
        	// 不合理的抛错
        	return Primitive.NULL;
//            throw new UtilEvalError("null field resolved: " + statement);
        }
        return obj;
    }
	
	public String toString() {
		return statement;
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		ColumnRow columnrow = ColumnRow.valueOf(statement.substring(1));
		if (!isParameter && statement.startsWith("$") && ColumnRow.validate(columnrow)){
			int newColumnIndex = columnrow.getColumn();
	        if (columnIndex != -1 && columnrow.getColumn() >= columnIndex) {
	        	newColumnIndex += colChanged;
	        }

	        int newRowIndex = columnrow.getRow();
	        if (rowIndex != -1 && columnrow.getRow() >= rowIndex) {
	            newRowIndex += rowChanged;
	        }
	        
	        columnrow = ColumnRow.valueOf(newColumnIndex, newRowIndex);
	        return "$" + columnrow.toString();
		}
		return this.toString();
	}
	
	public String[] parserParameter() {
		return StringUtils.isBlank(statement) ? null : new String[]{statement};
	}
	

	public boolean delay4PageCal() {
		if ("$$totalPage_number".equals(statement) || "$$page_number".equals(statement)) {
			return true;
		}
		
		return false;
	}
}
