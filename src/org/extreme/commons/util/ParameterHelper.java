package org.extreme.commons.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extreme.commons.Parameter;
import org.extreme.script.Calculator;
import org.extreme.script.ParameterMapScope;
import org.extreme.script.parser.Expression;

import antlr.ANTLRException;

/**
 * ׼������һЩ��̬����.
 */
public class ParameterHelper {

	// TODO �����TemplateUtils�����deprecatedParaPatternһ�����ظ���
    private static final Pattern deprecatedParaPattern = Pattern.compile("\\[\\?[^\\]\\?]*\\?\\]");// [?XXX|XXX?]��ʽ��
    
    public final static List FILTER_PARAMETERNAMELIST = new ArrayList();

    private ParameterHelper() {
    }

    /*
     * :��text�з���������Щ��Ҫ�Ĳ���
     */
    public static Parameter[] analyze4Parameters(String text) {
        Parameter[] array = null;

        if (deprecatedParaPattern.matcher(text).find()) {
            SegmentHandler.ParameterCollector handler = new SegmentHandler.ParameterCollector();
            ParameterHelper.analyzeParametersFromQuery(text, handler);

            array = handler.getParameters();
        } else if (TemplateUtils.ParameterPattern.matcher(text).find()) {
            array = parametersFromQuery(text);
        }

        return array == null ? new Parameter[0] : array;
    }

    /*
     * :��Formula Content�з�������Щ����
     */
    public static Parameter[] analyze4ParametersFromFormula(String formula) {
        Parameter[] array = null;
        if (formula == null) {
            return array;
        }
        if (formula.startsWith("=")) {
            formula = formula.substring(1);
        }
        if (StringUtils.isBlank(formula)) {
            return array;
        }
        try {
            String[] parameters = Calculator.relatedParameters(formula);
            array = new Parameter[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                array[i] = new Parameter(parameters[i].substring(1));
            }
        } catch (ANTLRException e) {
            LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
        }

        return array;
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

    private static Parameter[] parametersFromQuery(String queryText) {
        Matcher matcher = TemplateUtils.ParameterPattern.matcher(queryText);
        List paraList = new ArrayList();
        Set set = new HashSet();
        while (matcher.find()) {
            String group = matcher.group();
            String trueStr = group.substring(2, group.length() - 1);
            if (StringUtils.isBlank(trueStr)) {
                continue;
            }
            Expression ex = null;
            Calculator cu = Calculator.createCalculator();
            try {
                ex = cu.parse(trueStr);
            } catch (ANTLRException e) {
                e.printStackTrace();
            }
            if (ex.parserParameter() != null) {
                for (int i = 0, length = ex.parserParameter().length; i < length; i++) {
                    set.add(ex.parserParameter()[i]);
                }
            }
        }

        Iterator it = set.iterator();
        while (it.hasNext()) {
            Parameter par = new Parameter((String) it.next());
            paraList.add(par);
        }

        return (Parameter[]) paraList.toArray(new Parameter[paraList.size()]);
    }

    public static Parameter[] analyze4Parameters(String[] paramTexts, boolean isFormula) {
        List paramlist = new ArrayList();
        if (paramTexts != null) {
            String text;
            for (int i = 0; i < paramTexts.length; i++) {
                text = paramTexts[i];
                if (StringUtils.isNotBlank(text)) {
                    Parameter[] pparams = isFormula ? ParameterHelper.analyze4ParametersFromFormula(text) : ParameterHelper.analyze4Parameters(text);
                    if (!ArrayUtils.isEmpty(pparams)) {
                        for (int j = 0; j < pparams.length; j++) {
                            if (!paramlist.contains(pparams[j])) {
                                paramlist.add(pparams[j]);
                            }
                        }
                    }
                }
            }
        }

        // :��������£���Ĭ�ϵĲ���ȥ��
        for (int i = paramlist.size() - 1; i >= 0; i--) {
            if (FILTER_PARAMETERNAMELIST.contains(((Parameter) paramlist.get(i)).getName().toLowerCase())) {
                paramlist.remove(i);
            }
        }

        return (Parameter[]) paramlist.toArray(new Parameter[paramlist.size()]);
    }

    /*
     * ��ȡtext�еĲ����������������ϲ���ȥ���ظ���
     */
    public static Parameter[] analyzeAndUnionParameters(String[] paramTexts, Parameter[] ps, boolean isFormula) {
        Parameter[] parameters = analyze4Parameters(paramTexts, isFormula);

        if (ArrayUtils.isEmpty(parameters)) {
            return new Parameter[0];
        }

        if (!ArrayUtils.isEmpty(ps)) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter newParameter = parameters[i];
                Parameter existParameter = null;
                for (int j = 0; j < ps.length; j++) {
                    if (ComparatorUtils.equals(ps[j].getName(), newParameter.getName())) {
                        existParameter = ps[j];
                        break;
                    }
                }
                if (existParameter != null) {
                    newParameter.setValue(existParameter.getValue());
                }
            }
        }

        return parameters;
    }
}
