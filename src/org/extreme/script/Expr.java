package org.extreme.script;

import org.extreme.script.parser.UtilEvalError;

public interface Expr {
	public Object eval(Calculator c) throws UtilEvalError ;
}
