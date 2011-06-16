package org.extreme.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extreme.commons.Parameter;
import org.extreme.script.Calculator;
import org.extreme.script.ParameterMapScope;

/**
 * 准备参数一些静态方法.
 */
public class ParameterHelper {

	// TODO 这个和TemplateUtils里面的deprecatedParaPattern一样，重复了
    private static final Pattern deprecatedParaPattern = Pattern.compile("\\[\\?[^\\]\\?]*\\?\\]");// [?XXX|XXX?]格式的

    private ParameterHelper() {
    }

    /*
     * 根据parameters对text做template处理,变成一个新的text
     */
    public static String analyze4Templatee(String text, Parameter[] parameters) {
        // :parameters如果没有，就不需要做什么处理了
        if (parameters == null || parameters.length == 0) {
            return text;
        }

        Calculator c = Calculator.createCalculator();

        ParameterMapScope ns = ParameterMapScope.create(parameters);
        c.pushNameSpace(ns);
        String str = TemplateUtils.renderTpl(c, text);

        c.removeNameSpace(ns);

        return str;
    }

    /*
     * 分析一段文字中的参数
     */
    public static void analyzeParametersFromQuery(String queryText, SegmentHandler handler) {
        Matcher matcher = deprecatedParaPattern.matcher(queryText);
        int start = 0;
        int end = 0;
        while (matcher.find()) {
            // 不是[??]的其他文字
            end = matcher.start();
            String tmpText = queryText.substring(start, end);
            if (StringUtils.isNotEmpty(tmpText)) {
                handler.stringSegment(tmpText);
            }

            start = matcher.end();
            // 是[??]的文字
            String group = matcher.group();
            String trueStr = group.replaceAll("[\\[\\?\\?\\]]", "");// 将[?、?]全部剔除
            String[] paraStrs = trueStr.split("\\|");// 获取参数名称和值

            /**
             * 取到\n,\r\n，这样就支持 [? Para|Value ?] 的形式的啦，不过不建议用户这样用，还是写成
             * [?Para|Value?]好，考虑的全面些没坏处啊
             */
            String paraName = paraStrs[0].replaceAll("[\\r\\n, \\n]*", "");
            handler.parameterSegment(paraName, paraStrs.length > 1 ? paraStrs[1] : null);
        }

        // :剩下来的字符串
        String leftText = queryText.substring(start);
        if (StringUtils.isNotBlank(leftText)) {
            handler.stringSegment(leftText);
        }
    }
}
