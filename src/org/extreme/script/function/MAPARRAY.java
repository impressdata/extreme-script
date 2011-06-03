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
	 * richer:数组可以参加函数运算,item表示数组的元素，从第一个开始，index表示数组的索引.
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
		 * :这里必须要使用当前函数所用的Calculator,不能新建一个Calculator
		 * 因为新建的Calculator里面缺少NameSpace
		 */
		FArray newArray = new FArray();
		while (it.hasNext()) {
			Object ob = it.next();
			//richer:遇到item就解析成ob;index解析成索引
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
		return "MAPARRAY(array, fn):把一个数组中的项目转换到另一个数组中。\n"
		+"array (Array): 要转换的数组 \n"
		+"fn (Function): 处理数组项目的函数 \n"
		+"示例：\n"
		+"MAPARRAY([3,4,2,3,6,8,7], item != 3)等于[false,true,true,false,true,true,true].";
	}
	public String getEN(){
		return "";
	}
}