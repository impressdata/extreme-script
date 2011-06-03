package org.extreme.commons.json;

import org.extreme.commons.util.BaseCoreUtils;

/**
 * :��һ��JSONFunction����дJSON��ʽ���ΪString
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
		// �� TODO ������
//		if (true) {
		return toJSONString(true);
//		}
//		String str = "function(" + BaseCoreUtils.join(java.util.Arrays.asList(args), ",") + ")";
//		
//		/*
//		 * :������Ļ��з���ȥ��,��Щ����д��dom.attr�������<br>���޷���ȷ��ԭ��
//		 * ���ǰѻ��з�ȥ��,��ôһ�е�ע��//�ͻ��������
//		 * ����Ҫע�͵Ļ����ö���ע�͵ķ�ʽ
//		 * 
//		 * �����������yuicompressor����һ��
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
