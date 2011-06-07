package org.extreme.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extreme.script.Calculator;
import org.extreme.script.NameSpace;
import org.extreme.script.ParameterMapNameSpace;
import org.extreme.script.parser.UtilEvalError;


public class TemplateUtils {
	public static final Pattern ParameterPattern = Pattern.compile("\\$\\{((\\\"[^(\\$\\{)]*\\}[^(\\$\\{)]*\\\")|(\\\"[^(\\$\\{)]*[^\\$]?\\{[^(\\$\\{)]*\\\")|[^\\{\\}])*+\\}");
	public static final Pattern deprecatedParaPattern = Pattern.compile("\\[\\?[^\\]\\?]*\\?\\]");// [?XXX|XXX?]格式的
	
	private TemplateUtils() {
	}
	
	/*
	 * 简单的key, value处理tpl
	 * 
	 * 不throw Exception
	 */
	public static String render(String tpl, String key, Object value) {
		return render(tpl, new String[] {key}, new Object[] {value});
	}
	
	/*
	 * :keys:String[]与values:Object[]处理tpl
	 * 
	 * 不throw Exception
	 */
	public static String render(String tpl, String[] keys, Object[] values) {
		java.util.Map context = new java.util.HashMap();
		for (int ki = 0; ki < keys.length; ki++) {
			context.put(keys[ki], values[ki]);
		}
		
		try {
			return render(tpl, context);
		} catch (Exception e) {
			LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
			return tpl;
		}
	}
	
	public static String render(String tpl) throws Exception {
		return render(tpl, java.util.Collections.EMPTY_MAP);
	}
	
	public static String render(String tpl, Calculator c) throws Exception {
		return render(tpl, java.util.Collections.EMPTY_MAP, c);
	}
	
	/*
	 * :用context处理tpl
	 */
	public static String render(String tpl, java.util.Map context) throws Exception {
		Calculator c = Calculator.createCalculator();
		
		String str = TemplateUtils.renderTpl(c, tpl, ParameterMapNameSpace.create(context));
		
		return str;
	}
	
	public static String render(String tpl, java.util.Map context, Calculator c) throws Exception {
		String str = TemplateUtils.renderTpl(c, tpl, ParameterMapNameSpace.create(context));
		
		return str;
	}
	
	/*
	 * :用context处理StringReader
	 */
	public static String render(StringReader sr, java.util.Map context) throws IOException {
		BufferedReader br = new BufferedReader(sr);
		StringBuffer sb = new StringBuffer();
    	while(br.readLine() != null) {
    		sb.append(br.readLine());
    	}
    	String s = StringUtils.EMPTY;
    	try {
			s = TemplateUtils.render(sb.toString(), context);
		} catch (Exception e) {
			LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
		}
		return s;
	}
    
    public static void dealWithTemplate(InputStream input, String encoder, PrintWriter writer, Map map) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, encoder));
        dealWithTemplate(reader, writer, map);
        reader.close();
    }
    
    public static void dealWithTemplate(java.io.Reader reader, PrintWriter writer) throws IOException {
    	dealWithTemplate(reader, writer, java.util.Collections.EMPTY_MAP);
    }
    
    public static void dealWithTemplate(java.io.Reader reader, PrintWriter writer, Map map) throws IOException {
    	BufferedReader read = new BufferedReader(reader);
    	StringBuffer sb = new StringBuffer();
    	String line = StringUtils.EMPTY;
		while ((line = read.readLine()) != null) {
			if (sb.length() > 0) {
				sb.append('\n');
			}
			sb.append(line);
		}
    	String s = StringUtils.EMPTY;
    	try {
			s = TemplateUtils.render(sb.toString(), map);
		} catch (Exception e1) {
			LogUtil.getLogger().log(Level.WARNING,e1.getMessage(), e1);
		}
    	writer.write(s);
    }

    public static String renderTpl(Calculator c, String tpl) {
        return renderTpl(c, tpl, Eval_RenderAction);
    }

    public static String renderTpl(Calculator c, String tpl, NameSpace ns) {
        c.pushNameSpace(ns);
        String str = renderTpl(c, tpl);
        c.removeNameSpace(ns);

        return str;
    }

    /*
     * 通过Calculator处理tpl
     */
    public static String renderTpl(Calculator c, String tpl, RenderAction renderAction) {
        if (deprecatedParaPattern.matcher(tpl).find()) {
            SegmentHandler.QueryCreator handler = new SegmentHandler.QueryCreator(c);
            ParameterHelper.analyzeParametersFromQuery(tpl, handler);

            return handler.getNewQuery();
        }

        // 匹配${.*}
        Matcher matcher = ParameterPattern.matcher(tpl);
        StringBuffer sb = new StringBuffer();
        int start = 0, end = 0;
        while (matcher.find()) {
            end = matcher.start();
            String st = tpl.substring(start, end);
            start = matcher.end();

            sb.append(st);

            String group = matcher.group();
            String trueStr = group.substring(2, group.length() - 1);

            String res = renderAction.render(trueStr, c);
            if (res != null) {
                sb.append(res);
            }
        }
        sb.append(tpl.substring(start));
        return sb.toString();
    }

    public static interface RenderAction {

        public String render(String para, Calculator calculator);
    }
    private static RenderAction Eval_RenderAction = new RenderAction() {

        public String render(String para, Calculator calculator) {
            Object o = null;
            try {
                o = calculator.eval(para);
            } catch (UtilEvalError e) {
                // :此处不打印,因为这里经常用于文本替换,很可能在context里面找不到对应的值
            }
            return o == null ? null : Utils.objectToString(o);
        }
    };
    
    /**
     * 浏览器国际模板Util
     * @param key
     * @return
     */
    public static String i18nTpl(String key) {
    	return "${i18n(\"" + key +"\")}";
    }
}
