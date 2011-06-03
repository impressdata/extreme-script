package org.extreme.commons.json;

/**
 * :������������JSON��,toStringʱ��������д��
 */
public class JSONVariable implements JSONString {
	private String varName;
	
	public JSONVariable(String varName) {
		this.varName = varName;
	}
	
	public String toJSONString() {
		return this.varName;
	}
	
	public String toString() {
		return toJSONString();
	}
}
