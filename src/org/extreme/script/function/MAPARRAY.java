package org.extreme.script.function;

import java.util.Iterator;
import java.util.logging.Level;

import org.extreme.commons.util.LogUtil;
import org.extreme.script.Calculator;
import org.extreme.script.FArray;
import org.extreme.script.LambdaFunction;
import org.extreme.script.NameSpace;
import org.extreme.script.ParameterMapNameSpace;
import org.extreme.script.parser.Expression;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.UtilEvalError;

import antlr.ANTLRException;

public class MAPARRAY extends LambdaFunction {
	/**
	 * richer:������ԲμӺ�������,item��ʾ�����Ԫ�أ��ӵ�һ����ʼ��index��ʾ���������.
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
		Iterator it = array.iterator();
		int index = 0;
		/*
		 * :�������Ҫʹ�õ�ǰ�������õ�Calculator,�����½�һ��Calculator
		 * ��Ϊ�½���Calculator����ȱ��NameSpace
		 */
		FArray newArray = new FArray();
		while (it.hasNext()) {
			Object ob = it.next();
			//richer:����item�ͽ�����ob;index����������
			java.util.Map m = new java.util.HashMap();
			m.put("item", ob);
			m.put("index", new Integer(index + 1));
			NameSpace ns = ParameterMapNameSpace.create(m);
			cal.pushNameSpace(ns);
			try {
				Object obj = cal.eval(expression);
				newArray.add(obj);
			} catch (UtilEvalError e) {
				LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
			}
			cal.removeNameSpace(ns);
			index++;
		}
		return newArray;
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "MAPARRAY(array, fn):��һ�������е���Ŀת������һ�������С�\n"
		+"array (Array): Ҫת�������� \n"
		+"fn (Function): ����������Ŀ�ĺ��� \n"
		+"ʾ����\n"
		+"MAPARRAY([3,4,2,3,6,8,7], item != 3)����[false,true,true,false,true,true,true].";
	}
	public String getEN(){
		return "";
	}
}