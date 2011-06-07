package org.extreme.script.parser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.extreme.commons.ColumnRow;
import org.extreme.commons.util.SimpleTemplateUtils;
import org.extreme.commons.util.TemplateUtils;
import org.extreme.commons.util.Utils;


public class ParseUtils {
	
	public static String addString(java.util.List multi_list, java.util.List op_list) {
		int size = multi_list.size();
		
		String left = null;
		for (int i = 0; i < size; i++) {
			String node = (String)multi_list.get(i);
			if (i == 0) {
				left = node;
			} else {
				String right = node;

				left = fn("binaryOperation", new String[] {left, right, Utils.quote((String)op_list.get(i-1)), "c"});
			}
		}
		
		return left;
	}
	
	public static String fnString(String fn) {
		return fn;
	}
	
	public static String unary(String op, String atom) {
		return fn("primitiveWrapperUnaryOperation", new String[] {op, atom});
	}
	
	public static String fn(String fnName, String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append(fnName);
		sb.append('(');
		
		for (int i = 0; i < args.length; i ++) {
			if (i != 0) {
				sb.append(",");
			}
			
			sb.append(args[i]);
		}
		
		sb.append(')');
		
		return sb.toString();
	}
	
	public static String newInstance(String fnName, String[] args) {
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("(new ");
		
		sb.append(fnName);
		sb.append('(');
		
		for (int i = 0; i < args.length; i ++) {
			if (i != 0) {
				sb.append(",");
			}
			
			sb.append(args[i]);
		}
		
		sb.append(')');
		
		sb.append(")");
		
		return sb.toString();
	}

	public static String multiString(List power_list, List op_list) {
		return addString(power_list, op_list);
	}
	
	public static String orString(java.util.List list) {
		int size = list.size();
		
		String left = null;
		for (int i = 0; i < size; i++) {
			String node = (String)list.get(i);
			if (i == 0) {
				left = node;
			} else {
				String right = node;

				left = fn("binaryOperation", new String[] {left, right, "||", "c"});
			}
		}
		
		return left;
	}
	
	public static String andString(java.util.List list) {
		int size = list.size();
		
		String left = null;
		for (int i = 0; i < size; i++) {
			String node = (String)list.get(i);
			if (i == 0) {
				left = node;
			} else {
				String right = node;

				left = fn("binaryOperation", new String[] {left, right, "&&", "c"});
			}
		}
		
		return left;
	}
	
	public static String relationString(String left, String op, String right) {
		return fn("binaryOperation", new String[] {left, right, op, "c"});
	}
	
	public static String powerString(java.util.List list) {
		int size = list.size();
		
		String left = null;
		for (int i = 0; i < size; i++) {
			String node = (String)list.get(i);
			if (i == 0) {
				left = node;
			} else {
				String right = node;

				left = fn("binaryOperation", new String[] {left, right, "^", "c"});
			}
		}
		
		return left;
	}
	
	public static String getParsedExpressionJavaSource(String formula, String index) {
		try {
			String tpl = Utils.inputStream2String(ParseUtils.class.getResourceAsStream("/org/extreme/script/Expression.tpl"), "utf-8");
			
			StringReader in = new StringReader(formula);
			XLexer lexer = new XLexer(in);
			XParser parser = new XParser(lexer);
			String res = parser.parse();
			
			return SimpleTemplateUtils.render(tpl, new String[] {"parsed_value", "index"}, new Object[] {res, index});
		} catch (Exception e) {
			try {
				return Utils.inputStream2String(ParseUtils.class.getResourceAsStream("/org/extreme/script/ErrorExpression.tpl"), "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static String getMacroJavaSource(String macro, String index) {
		try {
			String tpl = Utils.inputStream2String(ParseUtils.class.getResourceAsStream("/org/extreme/script/Macro.tpl"), "utf-8");
			
			return SimpleTemplateUtils.render(tpl, new String[] {"value", "index"}, new Object[] {macro, index});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static String newInstance(String fnName, String quote) {
		return newInstance(fnName, new String[] {quote});
	}
	
	public static String toObjectArrayString(String[] objExps) {
		return toTypeArrayString(objExps, "Object");
	}
	
	public static String toTypeArrayString(String[] objExps, String type) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("new " + type + "[] {");
		
		for (int i = 0; i < objExps.length; i ++) {
			if (i != 0) {
				sb.append(',');
			}
			
			sb.append(objExps[i]);
		}
		
		sb.append("}");
		
		return sb.toString();
	}
	
	public static String cr(ColumnRow cr) {
		return cr(cr.toString());
	}
	
	public static String cr(String cr) {
		return "ColumnRow.valueOf(" + Utils.quote(cr) + ")";
	}
	
	public static String Int(int i) {
		return new Integer(i).toString();
	}
}
