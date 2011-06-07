package org.extreme.script;

import org.extreme.script.parser.UtilEvalError;

public interface Macro {
	public Object value(Calculator c) throws UtilEvalError ;
}
