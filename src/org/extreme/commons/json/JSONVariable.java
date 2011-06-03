package org.extreme.commons.json;

/**
 * :变量名保存于JSON中,toString时不加引号写出
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
