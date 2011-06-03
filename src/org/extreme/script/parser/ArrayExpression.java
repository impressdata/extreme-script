package org.extreme.script.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extreme.script.Calculator;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class ArrayExpression extends Atom {
	private Node[] exp_array;
	
	ArrayExpression(Node[] array) {
		exp_array = array;
	}

	public Object eval(Calculator calculator) throws UtilEvalError {
		if (exp_array == null) {
			return Primitive.NULL;
		}
		
		Object[] res_array = new Object[exp_array.length];
		java.util.Arrays.fill(res_array, Primitive.NULL);
		for (int i = 0; i < exp_array.length; i++) {
			if (exp_array[i] != null) {
				res_array[i] = calculator.eval(exp_array[i]);
			}
		}
		
		return new FArray(res_array);
	}
	
	public void traversal4Tiny(TinyHunter hunter) {
		for (int i = 0, len = exp_array == null ? 0 : exp_array.length; i < len; i++) {
			if (exp_array[i] != null) {
				exp_array[i].traversal4Tiny(hunter);
			}
		}
	}
	
	public String exString(Calculator calculator) {
		final Calculator fc = calculator;
		return gen_string(new StringGen() {
			public String gen_string(Node exp) {
				return exp.exString(fc);
			}
		});
	}
	
	public String toString() {
		return gen_string(new StringGen() {
			public String gen_string(Node exp) {
				return exp.toString();
			}
		});
	}
	
	private String gen_string(StringGen gen) {
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		for (int i = 0, len = exp_array == null ? 0 : exp_array.length; i < len; i++) {
			if (i > 0) {
				sb.append(',');
			}
			
			sb.append(gen.gen_string(exp_array[i]));
		}
		sb.append(']');
		
		return sb.toString();
	}

	private static interface StringGen {
		public String gen_string(Node exp);
	}

	public String getExpression(int rowIndex, int rowChanged, int columnIndex, int colChanged, final boolean isParameter) {
		final int rowIn = rowIndex;
		final int rowChange = rowChanged;
		final int columnIn = columnIndex;
		final int colChange = colChanged;
		return gen_string(new StringGen() {
			public String gen_string(Node exp) {
				return exp.getExpression(rowIn, rowChange, columnIn, colChange, isParameter);
			}
		});
	}

	
	public String[] parserParameter() {
		List paraList = new ArrayList();
		
		for (int i = 0, length = exp_array.length; i < length; i++) {
			paraList.addAll(Arrays.asList(exp_array[i].parserParameter()));
		}
		return (String[])paraList.toArray(new String[paraList.size()]);
	}
	
	public void trav4HuntSIL(List list) {
		for (int i = 0, len = exp_array == null ? 0 : exp_array.length; i < len; i++) {
			if (exp_array[i] != null) {
				exp_array[i].trav4HuntSIL(list);
			}
		}
	}
	
	public void trav4HuntBIL(List list) {
		for (int i = 0, len = exp_array == null ? 0 : exp_array.length; i < len; i++) {
			if (exp_array[i] != null) {
				exp_array[i].trav4HuntBIL(list);
			}
		}
	}
}
