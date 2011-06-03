package org.extreme.script.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extreme.script.Calculator;
import org.extreme.script.Function;


public class FunctionCall extends Atom {
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
	
	public void traversal4Tiny(TinyHunter hunter) {
		for (int i = 0; i < arguments.length; i++) {
			// : range(1,2,)这样写的话最后一个arguments是null
			if(arguments[i] != null) {
				if (arguments[i] instanceof Node) {
					((Node)arguments[i]).traversal4Tiny(hunter);
				}
			}
		}
	}
	
	public String exString(Calculator calculator) {
		StringBuffer sb = new StringBuffer();
		sb.append(name).append('(');
		for(int i = 0; i < arguments.length; i++) {
			if(i > 0) {
				sb.append(", ");
			}
			// : range(1,2,)这样写的话最后一个arguments是null
			if(arguments[i] != null) {
				sb.append(arguments[i] instanceof Node ? ((Node)arguments[i]).exString(calculator) : arguments[i]);
			}
		}
		sb.append(')');
		
		return sb.toString();
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

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, boolean isParameter) {
		StringBuffer sb = new StringBuffer();
		sb.append(name).append('(');
		for(int i = 0; i < arguments.length; i++) {
			if(i > 0) {
				sb.append(", ");
			}
			if(arguments[i] != null) {				
				sb.append(arguments[i] instanceof Node ? ((Node)arguments[i]).getExpression(rowIndex, rowChanged, columnIndex, colChanged, isParameter) : arguments[i]);
			}
		}
		sb.append(')');
		
		return sb.toString();
	}

	public String[] parserParameter() {
		List paraList = new ArrayList();
		for (int i = 0, length = arguments.length; i < length; i++) {
			if (arguments[i] instanceof Node) {
				paraList.addAll(Arrays.asList(((Node)arguments[i]).parserParameter()));
			}
			
		}
		return (String[])paraList.toArray(new String[paraList.size()]);
	}
	
	public void trav4HuntSIL(List list) {
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] instanceof Node) {
				((Node)arguments[i]).trav4HuntSIL(list);
			}
		}
	}
	
	public void trav4HuntBIL(List list) {
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] instanceof Node) {
				((Node)arguments[i]).trav4HuntBIL(list);
			}
		}
	}
}
