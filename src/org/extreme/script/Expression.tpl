package jdt;

import com.fr.report.script.Calculator;
import com.fr.report.script.core.parser.Ambiguity;
import com.fr.report.script.core.parser.NumberLiteral;
import com.fr.report.script.core.parser.FunctionCall;
import com.fr.report.script.core.parser.ColumnRowLiteral;
import com.fr.report.script.core.parser.ColumnRowRange;
import com.fr.report.script.core.parser.Node;
import com.fr.report.script.core.parser.UtilEvalError;
import com.fr.base.ColumnRow;

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
