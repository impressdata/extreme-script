package org.extreme.script;

import org.extreme.script.Calculator;
import org.extreme.script.parser.Ambiguity;
import org.extreme.script.parser.NumberLiteral;
import org.extreme.script.parser.FunctionCall;
import org.extreme.script.parser.ColumnRowLiteral;
import org.extreme.script.parser.ColumnRowRange;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.UtilEvalError;
import org.extreme.script.compiler.CompileService;
import org.extreme.commons.ColumnRow;

/*
 * TODO: 自动引入import 类的问题
 */
public class Expression${index} extends CoreOperation implements Expr {
	
	public Object eval(Calculator c) throws UtilEvalError {
		Object o = ${parsed_value};
		
		if (o instanceof Node) {
			o = c.eval((Node)o);
		}
		
		return o;
	}
}
