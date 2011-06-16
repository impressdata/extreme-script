package org.extreme.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extreme.commons.Parameter;
import org.extreme.script.Calculator;
import org.extreme.script.ParameterMapScope;

/**
 * ׼������һЩ��̬����.
 */
public class ParameterHelper {

	// TODO �����TemplateUtils�����deprecatedParaPatternһ�����ظ���
    private static final Pattern deprecatedParaPattern = Pattern.compile("\\[\\?[^\\]\\?]*\\?\\]");// [?XXX|XXX?]��ʽ��

    private ParameterHelper() {
    }

    /*
     * ����parameters��text��template����,���һ���µ�text
     */
    public static String analyze4Templatee(String text, Parameter[] parameters) {
        // :parameters���û�У��Ͳ���Ҫ��ʲô������
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
     * ����һ�������еĲ���
     */
    public static void analyzeParametersFromQuery(String queryText, SegmentHandler handler) {
        Matcher matcher = deprecatedParaPattern.matcher(queryText);
        int start = 0;
        int end = 0;
        while (matcher.find()) {
            // ����[??]����������
            end = matcher.start();
            String tmpText = queryText.substring(start, end);
            if (StringUtils.isNotEmpty(tmpText)) {
                handler.stringSegment(tmpText);
            }

            start = matcher.end();
            // ��[??]������
            String group = matcher.group();
            String trueStr = group.replaceAll("[\\[\\?\\?\\]]", "");// ��[?��?]ȫ���޳�
            String[] paraStrs = trueStr.split("\\|");// ��ȡ�������ƺ�ֵ

            /**
             * ȡ��\n,\r\n��������֧�� [? Para|Value ?] ����ʽ�����������������û������ã�����д��
             * [?Para|Value?]�ã����ǵ�ȫ��Щû������
             */
            String paraName = paraStrs[0].replaceAll("[\\r\\n, \\n]*", "");
            handler.parameterSegment(paraName, paraStrs.length > 1 ? paraStrs[1] : null);
        }

        // :ʣ�������ַ���
        String leftText = queryText.substring(start);
        if (StringUtils.isNotBlank(leftText)) {
            handler.stringSegment(leftText);
        }
    }
}
