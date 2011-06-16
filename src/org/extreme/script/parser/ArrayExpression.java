package org.extreme.script.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extreme.script.Calculator;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class ArrayExpression implements Node {
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
}
