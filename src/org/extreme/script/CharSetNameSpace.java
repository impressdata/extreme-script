package org.extreme.script;

import java.util.HashMap;

public class CharSetNameSpace extends AbstractNameSpace {
	public static final String NEWCHAR = "$$_NEWCHAR";
	public static final String ORIGINALCHAR = "$$_ORIGINALCHAR";
	private HashMap charMap = new HashMap();
	
	public CharSetNameSpace(String newChar, String originalChar) {
		this.charMap.put(NEWCHAR, newChar);
		this.charMap.put(ORIGINALCHAR, originalChar);
	}

	public Object getVariable(Object var, Calculator calculator) {
		return charMap.get(var);
	}

}
