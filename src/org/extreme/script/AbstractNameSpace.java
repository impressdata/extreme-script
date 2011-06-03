package org.extreme.script;

import org.extreme.commons.ColumnRow;

public abstract class AbstractNameSpace implements NameSpace {

	public Object getRelateCellElements(ColumnRow columnrow, Calculator calculator) {
		return null;
	}

	public Function getMethod(Object var, Calculator calculator) {
		return null;
	}

	public Object getVariable(Object var, Calculator calculator) {
		return null;
	}
}