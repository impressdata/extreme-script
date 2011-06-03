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

public class Micro${index} extends CoreOperation implements Micro {	
	public Object value(Calculator c) throws UtilEvalError {
		${value}
	}
}