package org.extreme.script.function;

import java.util.logging.Level;

import org.extreme.commons.util.LogUtil;
import org.extreme.script.Calculator;
import org.extreme.script.FArray;
import org.extreme.script.LambdaFunction;
import org.extreme.script.Scope;
import org.extreme.script.ParameterMapScope;
import org.extreme.script.parser.Expression;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.UtilEvalError;

import antlr.ANTLRException;

public class GREPARRAY extends LambdaFunction {
	/**
	 * richer:��������
	 * 
	 * GREPARRAY([3,4,2,3,6,8,7], item != 3)����[4,2,6,8,7]
	 * :Ϊʲô�ڶ������������ַ�����?��Ϊ���ַ����Ļ���û�а취֪������Щ��Ԫ�������,����BS����
	 */
	public Object run(FArray array, Node[] arguments) {
		Calculator cal = this.getCalculator();
		Expression expression;
		try {
			expression = cal.parse(arguments[0]);
		} catch (ANTLRException e) {
			LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
			return array;
		}
		FArray newArray = new FArray();
		for (int i = 0; i < array.length(); i++) {
			// richer:����item�ͽ�����ob;index����������
			java.util.Map m = new java.util.HashMap();
			m.put("item", array.elementAt(i));
			m.put("index", new Integer(i + 1));
			Scope ns = ParameterMapScope.create(m);
			cal.pushNameSpace(ns);
			try {
				Object obj = cal.eval(expression);
				if (obj instanceof Boolean && (Boolean) obj == Boolean.TRUE) {
					newArray.add(array.elementAt(i));
				}
			} catch (UtilEvalError e) {
				LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
			}
			cal.removeNameSpace(ns);
		}
		return newArray;
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "GREPARRAY(array��fn):����(����true����false)�����������˴����飬����γ�һ�������顣\n"
		+"ʾ����\n"
		+"GREPARRAY([3,4,2,3,6,8,7], item != 3)����[4,2,6,8,7].\n";
	}
	public String getEN(){
		return "";
	}
}