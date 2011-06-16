package org.extreme.script.parser;

import org.extreme.script.Calculator;

public abstract class Atom implements Node {
	public String exString(Calculator calculator) {
		return this.toString();
	}
}
