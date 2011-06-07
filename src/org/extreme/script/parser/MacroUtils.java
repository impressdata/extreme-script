package org.extreme.script.parser;

import java.io.UnsupportedEncodingException;

import org.extreme.commons.json.JSONObject;
import org.extreme.commons.util.SimpleTemplateUtils;
import org.extreme.commons.util.Utils;


/*
 * ��.
 * ��ͺ������������ڣ�������ÿ�������ڴ���֮ǰ����Eval, ����Ĳ�������Eval������ת���������һ�δ���
 * ����IF��LET
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
			if (args.length < 3) {
				return null;
			}
			
			StringBuffer buffer = new StringBuffer();
			
			// TODO FIXME
			buffer.append(Utils.quote("Not Supportted"));
			
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
