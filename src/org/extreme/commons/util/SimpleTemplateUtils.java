package org.extreme.commons.util;

import java.util.logging.Level;
import java.util.regex.Matcher;

import org.extreme.script.Calculator;
import org.extreme.script.ParameterMapNameSpace;

public class SimpleTemplateUtils {
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
	
	/*
	 * :用context处理tpl
	 */
	public static String render(String tpl, java.util.Map context) throws Exception {
        // 匹配${.*}
        Matcher matcher = TemplateUtils.ParameterPattern.matcher(tpl);
        StringBuffer sb = new StringBuffer();
        int start = 0, end = 0;
        while (matcher.find()) {
            end = matcher.start();
            String st = tpl.substring(start, end);
            start = matcher.end();

            sb.append(st);

            String group = matcher.group();
            String trueStr = group.substring(2, group.length() - 1);

            String res = context.get(trueStr).toString();
            if (res != null) {
                sb.append(res);
            }
        }
        sb.append(tpl.substring(start));
        return sb.toString();
	}
}
