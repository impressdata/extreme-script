package org.extreme.script;


import java.util.logging.Level;

import org.extreme.commons.DeathCycleException;
import org.extreme.commons.Formula;
import org.extreme.commons.Parameter;
import org.extreme.commons.util.LogUtil;
import org.extreme.script.parser.UtilEvalError;


public class ParameterMapScope implements Scope {
	protected Calculator nativeCalculator;
	private java.util.Map nativeParameterMap = new java.util.HashMap();
	private static final Object CUR_VAR = new Object();
	
	public static ParameterMapScope create(Parameter[] ps) {
		java.util.Map parameterMap = new java.util.HashMap();
		if (ps != null) {
			for (int i = 0; i < ps.length; i++) {
				parameterMap.put(ps[i].getName(), ps[i].getValue());
			}
		}
		
		return create(parameterMap);
	}
	
	public static ParameterMapScope create(java.util.Map parameterMap) {
		return new ParameterMapScope(parameterMap);
	}

	protected ParameterMapScope(java.util.Map parameterMap) {
		nativeCalculator = Calculator.createCalculator();
		if (parameterMap != null) {
			java.util.Iterator entryIt = parameterMap.entrySet().iterator();
			while (entryIt.hasNext()) {
				java.util.Map.Entry en = (java.util.Map.Entry)entryIt.next();
				key2UpperCase(en.getKey(), en.getValue());
			}
		}
		
		nativeCalculator.pushNameSpace(this);
		nativeCalculator.setAttribute(CUR_VAR, new java.util.HashSet());	// HashSet<String>
	}

	public Object getVariable(Object var, Calculator calculator) {	
		if(var == null) {
			return null;
		}
		
		// 避免死循环
		java.util.Set cur_var_set = (java.util.Set)nativeCalculator.getAttribute(CUR_VAR);
		if(cur_var_set.contains(var.toString())) {
			throw new DeathCycleException("Death cycle exists at calculating " + (String)var);
		}
		cur_var_set.add(var.toString());
		String pName = var.toString();
		if (pName.startsWith(ScriptConstants.DETAIL_TAG)) {
			pName = pName.substring(1);
		}
		
		Object obj = findKey(pName);
		
		if (obj instanceof Formula) {
			String content = ((Formula)obj).getContent();
        	try {
        		// 这边使用另一个calculator, 不支持报表中的变量
        		obj = nativeCalculator.eval(content.substring(1));
    		} catch (UtilEvalError e) {
    			LogUtil.getLogger().log(Level.WARNING,"Attention: Parameter formula getVariable", e);
    			obj = content;
    		}
    		//:这里应该放入的是Parameter的name，没有$
    		key2UpperCase(pName, obj);
		}
		
		cur_var_set.remove(var.toString());
		
		return obj;
	}
	
	/*
	 * :把key转成大写的保存到context里面
	 */
	private void key2UpperCase(Object key, Object value) {
		if (key == null) {
			return;
		}
		
		this.nativeParameterMap.put(key.toString().toUpperCase(), value);
	}
	
	/*
	 * 从context里面找出对应的值
	 */
	private Object findKey(String key) {
		if (key == null) {
			return null;
		}
		
		return this.nativeParameterMap.get(key.toUpperCase());
	}

	public Function getMethod(Object var, Calculator calculator) {
		// TODO Auto-generated method stub
		return null;
	}
}
