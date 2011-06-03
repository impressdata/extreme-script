package org.extreme.script.function;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.script.Calculator;
import org.extreme.script.CalculatorEmbeddedFunction;
import org.extreme.script.Primitive;
import org.extreme.script.parser.UtilEvalError;


public class SWITCH extends CalculatorEmbeddedFunction {
	public Object evpression(Object[] args)
			throws UtilEvalError {
		if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }
		Calculator c = this.getCalculator();
		
		Object v = c.eval(args[0]);
		for (int i = 1; i < args.length; i+=2) {
			Object caseV = c.eval(args[i]);
			if (ComparatorUtils.equals(v, caseV)) {
				if (i + 1 < args.length) {
					return c.eval(args[i + 1]);
				}
			}
		}
        
        return Primitive.NULL;
	}
	
	public Type getType() {
		return LOGIC;
	}
	public String getCN(){
		return "switch(表达式, 值1, 结果1, 值2, 结果2, ...)\n"
		+ "如果表达式的结果是值1，整个函数返回结果1\n"
		+ "如果表达式的结果是值2，整个函数返回结果2\n"
		+ "如果表达式的结果是值3，整个函数返回结果3\n"
		+ "等等\n";
	};
	public String getEN(){
		return "";
	};	
}
