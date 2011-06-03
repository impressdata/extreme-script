package org.extreme.script.parser;

import java.util.List;

import org.extreme.script.Calculator;
import org.extreme.script.ExTool;


public class SheetIntervalLiteral extends Atom {
	private String sheetName;
	private Atom atom;
	
	public SheetIntervalLiteral(String sheetName, Atom atom) {
		this.sheetName = sheetName;
		this.atom = atom;
	}
	
	public String getSheetName() {
		return this.sheetName;
	}
	
	public Atom getSheetAtom() {
		return this.atom;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
		// TODO 太乱了
		if (this.atom instanceof ColumnRowRange) {
			return calculator.resolveVariableInCE(this);
		}
		return calculator.resolveVariable(this);
	}
	
	public String exString(Calculator calculator) {
		Object attr = calculator.getAttribute(ExTool.TAG);
		if(attr instanceof ExTool) {
			if (this.getSheetAtom() instanceof ColumnRowRange) {
				String returnStr = ((ExTool)attr).exSIL(calculator, this.getSheetName(), (ColumnRowRange)this.getSheetAtom());
				if (returnStr != null) return returnStr;
			}
		}
		
		return this.toString();
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex,
			int colChanged, boolean isParameter) {
		return this.toString();
	}

	public String[] parserParameter() {
		// TODO ：因为属于另一个sheet中，故需要返回一个特别点的东西
		return null;
	}

	public void traversal4Tiny(TinyHunter hunter) {
		// TODO ：这边暂时不做，假如做的话Hunter以及外部的调用都需要考虑多sheet
	}
	
	public String toString() {
		return "'" + this.sheetName + "'!" + this.atom.toString();
	}

	public boolean delay4PageCal() {
		return false;
	}
	
	public void trav4HuntSIL(List list) {
		list.add(this);
	}
	
	public void trav4HuntBIL(List list) {
		
	}
}
