package org.extreme.script.parser;

import org.extreme.script.Calculator;
import org.extreme.script.Function;


public class FunctionCall implements Node {
	private String name;
	private Object[] arguments;
	
	FunctionCall(String name, Node[] arguments){
		this.name = name;
		this.arguments = arguments;
	}
	
	public FunctionCall(String name, Object[] arguments){
		// FIXME
		this.name = name;
		this.arguments = arguments;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
	    Function function = calculator.resolveMethod(name);
        if(function == null) {
            throw new InterpreterError("no function found: " + this.name);
        }

        //:set Calculator since some of functions might need report, cellElement, eval
        function.setCalculator(calculator);
        
        return function.evpression(arguments);
	}
	
	public static class ValueNode extends Tiny {
		public Object value = null;
		
		public ValueNode(Object v) {
			this.value = v;
		}

		public Object eval(Calculator calculator) throws UtilEvalError {
			return value;
		}

		public String getExpression(int rowIndex, int rowChanged,
				int columnIndex, int colChanged, boolean isParameter) {
			return value.toString();
		}
		
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name).append('(');
		for(int i = 0; i < arguments.length; i++) {
			if(i > 0) {
				sb.append(", ");
			}
			if(arguments[i] != null) {				
				sb.append(arguments[i]);
			}
		}
		sb.append(')');
		
		return sb.toString();
	}
}
