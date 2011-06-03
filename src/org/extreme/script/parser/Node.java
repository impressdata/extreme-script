package org.extreme.script.parser;

import java.io.Serializable;
import java.util.List;

import org.extreme.script.Calculator;


public interface Node extends Serializable {
	public Object eval(Calculator calculator) throws UtilEvalError;
	
	public void traversal4Tiny(TinyHunter hunter);
	
	public void trav4HuntSIL(List list);
	
	public void trav4HuntBIL(List list);
	
	public String exString(Calculator calculator);
	
	/*isParameter是判断$B2中B2是参数还是单元格,层次公式中单元格参数在插入行时会需要判断*/
	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter);
	
	public String[] parserParameter();
}
