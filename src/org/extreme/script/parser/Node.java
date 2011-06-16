package org.extreme.script.parser;

import java.io.Serializable;

import org.extreme.script.Calculator;


public interface Node extends Serializable {
	public Object eval(Calculator calculator) throws UtilEvalError;
}
