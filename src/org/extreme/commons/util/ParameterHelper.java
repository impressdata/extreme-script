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
 * 准备参数一些静态方法.
 */
public class ParameterHelper {

	// TODO 这个和TemplateUtils里面的deprecatedParaPattern一样，重复了
    private static final Pattern deprecatedParaPattern = Pattern.compile("\\[\\?[^\\]\\?]*\\?\\]");// [?XXX|XXX?]格式的
    
    public final static List FILTER_PARAMETERNAMELIST = new ArrayList();

    private ParameterHelper() {
    }

    /*
     * :从text中分析中有哪些需要的参数
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
     * :从Formula Content中分许有哪些参数
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

        // :这里过滤下，把默认的参数去掉
        for (int i = paramlist.size() - 1; i >= 0; i--) {
            if (FILTER_PARAMETERNAMELIST.contains(((Parameter) paramlist.get(i)).getName().toLowerCase())) {
                paramlist.remove(i);
            }
        }

        return (Parameter[]) paramlist.toArray(new Parameter[paramlist.size()]);
    }

    /*
     * 获取text中的参数，并与后续数组合并，去除重复项
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
