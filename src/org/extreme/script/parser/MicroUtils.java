package org.extreme.script.parser;

import java.io.UnsupportedEncodingException;

import org.extreme.commons.json.JSONObject;
import org.extreme.commons.util.TemplateUtils;
import org.extreme.commons.util.Utils;


/*
 * ��.
 * ��ͺ������������ڣ�������ÿ�������ڴ���֮ǰ����Eval, ����Ĳ�������Eval������ת���������һ�δ���
 * ����IF��LET
 */
public class MicroUtils {	
	private static java.util.Map/*<String, MicroProcessor>*/ MICROS = new java.util.HashMap();
	
	public static final MicroProcessor IF = new MicroProcessor() {
		
		public String process(String[] args) {
			if (args.length < 3) {
				return null;
			}
			
			StringBuffer buffer = new StringBuffer();
			
			try {
				String if_micro = TemplateUtils.render(
						Utils.inputStream2String(MicroUtils.class.getResourceAsStream("/jdt/parser/IF.tpl"), "utf-8"), 
						new String[] {"condition", "true_expr", "false_expr"}, args);
				
				buffer.append("CompileService.getInstance().compileMicro(" + JSONObject.quote(if_micro) + ").value(c)");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return buffer.toString();
		}
	};
	
	public static final MicroProcessor LET = new MicroProcessor() {
		
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
		MICROS.put("IF", IF);
		MICROS.put("LET", LET);
	}

	public static boolean isMicro(String fnName) {		
		return MICROS.containsKey(fnName.toUpperCase());
	}
	
	public static String toMicro(String fnName, String[] args) {
		return ((MicroProcessor)MICROS.get(fnName.toUpperCase())).process(args);
	}
	
	public static interface MicroProcessor {
		public String process(String[] args);	
	}
}
