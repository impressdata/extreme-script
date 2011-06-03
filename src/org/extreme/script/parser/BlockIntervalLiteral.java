/*
 * Apache Licence2.0
 */
package org.extreme.script.parser;

import java.util.List;

import org.extreme.script.Calculator;


/**
 * @author richer
 * @since 6.5.4 创建于2011-4-19
 * 聚合块元
 */
public class BlockIntervalLiteral extends Atom {
	private String blockName;
	private Atom atom;
	
	public BlockIntervalLiteral(String blockName, Atom atom) {
		this.blockName = blockName;
		this.atom = atom;
	}
	
	public String getBlockName() {
		return blockName;
	}
	
	public Atom getBlockAtom() {
		return atom;
	}
	
 	public Object eval(Calculator calculator) throws UtilEvalError {
 		if (this.atom instanceof ColumnRowRange) {
			return calculator.resolveVariableInCE(this);
		}
		return calculator.resolveVariable(this);
	}
	
	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		return this.toString();
	}

	public boolean delay4PageCal() {
		return false;
	}
	
	public String toString() {
		return "'" + this.blockName + "'~" + this.atom.toString();
	}
	
	public String[] parserParameter() {
		return null;
	}
	
	public void traversal4Tiny(TinyHunter hunter) {
		
	}

	public void trav4HuntSIL(List list) {
		
	}
	
	public void trav4HuntBIL(List list) {
		list.add(this);
	}
}
