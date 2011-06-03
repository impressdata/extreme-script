package org.extreme.script;

import java.util.List;

import org.extreme.script.parser.ColumnRowRange;


public interface ExTool {
	// tag
	public static Object TAG = new Object();
	
	public String ex(Calculator calculator, ColumnRowRange range);
	
	public void setCreateRelation(boolean createRelation);
	
	public List exBoxs(ColumnRowRange range);
	
	public String exSIL(Calculator calculator, String sheetName, ColumnRowRange range);
}
