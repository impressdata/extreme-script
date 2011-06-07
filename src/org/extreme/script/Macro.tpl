package org.extreme.script;

import org.extreme.script.Calculator;
import org.extreme.script.parser.Ambiguity;
import org.extreme.script.parser.NumberLiteral;
import org.extreme.script.parser.FunctionCall;
import org.extreme.script.parser.ColumnRowLiteral;
import org.extreme.script.parser.ColumnRowRange;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.UtilEvalError;
import org.extreme.commons.ColumnRow;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.extreme.script.ParameterMapNameSpace;

public class Macro${index} extends CoreOperation implements Macro {
	public static Pattern VariablePattern = Pattern.compile("^[A-Za-z]+\\w*$");
	
	public Object value(Calculator c) throws UtilEvalError {
		${value}
	}
}