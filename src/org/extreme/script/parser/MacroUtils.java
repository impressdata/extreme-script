package org.extreme.script.parser;

import java.io.UnsupportedEncodingException;

import org.extreme.commons.json.JSONObject;
import org.extreme.commons.util.SimpleTemplateUtils;
import org.extreme.commons.util.Utils;



/*
 * 宏.
 * 宏和函数的区别在于，函数的每个参数在传入之前都会Eval, 但宏的参数不会Eval，而是转化成另外的一段代码
 * 比如IF和LET
 */
public class MacroUtils {	
	private static java.util.Map/*<String, MacroProcessor>*/ MACROS = new java.util.HashMap();
	
	public static final MacroProcessor IF = new MacroProcessor() {
		
		public String process(String[] args) {
			if (args.length < 3) {
				return null;
			}
			
			StringBuffer buffer = new StringBuffer();
			
			try {
				String if_macro = SimpleTemplateUtils.render(
						Utils.inputStream2String(MacroUtils.class.getResourceAsStream("/org/extreme/script/parser/IF.tpl"), "utf-8"), 
						new String[] {"condition", "true_expr", "false_expr"}, args);
				
				buffer.append("CompileService.getInstance().compileMacro(" + JSONObject.quote(if_macro) + ").value(c)");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return buffer.toString();
		}
	};
	
	public static final MacroProcessor LET = new MacroProcessor() {
		
		public String process(String[] args) {
			if (args.length % 2 == 0) {
				return null;
			}
			
			StringBuffer buffer = new StringBuffer();
			
			StringBuffer args_param = new StringBuffer();
			for (int i = 0; i < args.length - 1; i ++) {
				if (i != 0) {
					args_param.append(",");
				}
				args_param.append(args[i]);
			}

			try {
				String let_macro = SimpleTemplateUtils.render(
						Utils.inputStream2String(MacroUtils.class.getResourceAsStream("/org/extreme/script/parser/LET.tpl"), "utf-8"), 
						new String[] {"args", "expr"}, new String[] {args_param.toString(), args[args.length - 1]});
				
				buffer.append("CompileService.getInstance().compileMacro(" + JSONObject.quote(let_macro) + ").value(c)");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return buffer.toString();
		}
	};
	
	static {
		MACROS.put("IF", IF);
		MACROS.put("LET", LET);
	}

	public static boolean isMacro(String fnName) {		
		return MACROS.containsKey(fnName.toUpperCase());
	}
	
	public static String toMacro(String fnName, String[] args) {
		return ((MacroProcessor)MACROS.get(fnName.toUpperCase())).process(args);
	}
	
	public static interface MacroProcessor {
		public String process(String[] args);	
	}
}
