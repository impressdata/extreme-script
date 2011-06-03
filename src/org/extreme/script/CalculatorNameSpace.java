package org.extreme.script;

import org.extreme.commons.ColumnRow;

public class CalculatorNameSpace implements NameSpace {
	private Calculator ca;
	
	public CalculatorNameSpace(Calculator ca) {
		this.ca = ca;
	}
	
	public Function getMethod(Object var, Calculator calculator) {
		return this.ca.resolveMethod(var);
	}
	
	public Object getVariable(Object var, Calculator calculator) {
		return this.ca.resolveVariable(var);
	}
	
	public Object getRelateCellElements(ColumnRow columnrow, Calculator calculator) {
		return this.ca.resolveRelateCellElements(columnrow);
	}
}
