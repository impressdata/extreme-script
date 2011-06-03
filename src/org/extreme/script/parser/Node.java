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
	
	/*isParameter���ж�$B2��B2�ǲ������ǵ�Ԫ��,��ι�ʽ�е�Ԫ������ڲ�����ʱ����Ҫ�ж�*/
	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter);
	
	public String[] parserParameter();
}
