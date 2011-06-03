package org.extreme.commons.util;

import java.text.SimpleDateFormat;
import java.util.logging.Level;

import org.extreme.commons.Formula;
import org.extreme.commons.Parameter;
import org.extreme.script.Calculator;
import org.extreme.script.parser.UtilEvalError;


public interface SegmentHandler {
	public void stringSegment(String str);
	
	public void parameterSegment(String name, String defaultValue);
	
	public static final class ParameterCollector implements SegmentHandler {
		java.util.List parameterList = new java.util.ArrayList();
		
		public Parameter[] getParameters() {
			return (Parameter[])parameterList.toArray(new Parameter[parameterList.size()]);
		}

		public void parameterSegment(String name, String defaultValue) {
			parameterList.add(new Parameter(name, defaultValue));
		}

		public void stringSegment(String str) {
		}
	}
	
	public static class QueryCreator implements SegmentHandler {
		private Calculator c;
		private StringBuffer sb = new StringBuffer();
		
		public QueryCreator(Calculator c) {
			if (c == null) {
				c = Calculator.createCalculator();
			}
			this.c = c;
		}
		
		public String getNewQuery() {
			return sb.toString();
		}
		
		public void parameterSegment(String name, String defaultValue) {
			Object v = this.c.resolveVariable(name);
			if (v == null) {
				v = defaultValue;
			}
			
			// :如果defaultValue是一个以=开头的String
			if (v instanceof String && ((String)v).startsWith("=")) {
				v = new Formula(((String)v));
			}
			
			if (v instanceof Formula) {
				try {
					v = this.c.eval(((Formula)v).getContent());
				} catch (UtilEvalError e) {
					LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
				}
			}
			
			// 此处拼sql是作字符串拼接,如果v是Date类型的,应该format成一个String
			if (v instanceof java.util.Date) {
				String format = null;			
				if (StringUtils.isBlank(format)) {
					format = "yyyy-MM-dd"; // 默认的format格式
				}
				
				v = new SimpleDateFormat(format).format((java.util.Date)v);
			}
			
			String appendString = null;
			if (v instanceof String) {
				appendString = (String)v; 
			} else if (v != null) {
				appendString = v.toString();
			}
			
			if (StringUtils.isNotEmpty(appendString)) {
				sb.append(appendString);
			}
		}

		public void stringSegment(String str) {
			sb.append(str);
		}
		
	}
}
