package org.extreme.commons.json;

import org.extreme.commons.util.BaseCoreUtils;

/**
 * :加一个JSONFunction用于写JSON格式输出为String
 * 
 * JSONFunction(new String[] {"e"}, "alert(e.target)")
 * 
 * toString ->
 * function(e) {alert(e.target)}
 */
public class JSONFunction implements JSONString {
	private String[] args;
	private String statement;
	
	public JSONFunction(String[] args, String statement) {
		this.args = args;
		this.statement = statement;
	}
	
	public String toJSONString() {
		// ： TODO 测试用
//		if (true) {
		return toJSONString(true);
//		}
//		String str = "function(" + BaseCoreUtils.join(java.util.Arrays.asList(args), ",") + ")";
//		
//		/*
//		 * :把里面的换行符都去掉,这些东西写到dom.attr里面会变成<br>就无法正确还原了
//		 * 但是把换行符去掉,那么一行的注释//就会出现问题
//		 * 所以要注释的话就用多行注释的方式
//		 * 
//		 * 最佳做法是用yuicompressor处理一下
//		 */
//		str += "{" + this.statement.replaceAll("[\\r\\n\\t]", "") + "}";
//		
//		return str;
	}
	
	public String toString() {
		return toJSONString();
	}
	
	public String toJSONString(boolean pretty) {
		if (pretty) {
			String str = "function(" + BaseCoreUtils.join(java.util.Arrays.asList(args), ",") + ")";
			
			str += "{\n" + this.statement + "\n}";
			
			return str;
		} else {
			return toString();
		}
	}
}
