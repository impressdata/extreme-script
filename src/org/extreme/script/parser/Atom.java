package org.extreme.script.parser;

import org.extreme.script.Calculator;

public abstract class Atom extends AbstractNode {
	public String exString(Calculator calculator) {
		return this.toString();
	}
}
