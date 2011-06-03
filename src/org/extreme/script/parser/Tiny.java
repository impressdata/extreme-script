package org.extreme.script.parser;

import java.util.List;



/*
 * 语法解析后最小的不可再分的单位
 */
public abstract class Tiny extends Atom {
	
	public void traversal4Tiny(TinyHunter hunter) {
		hunter.hunter4Tiny(this);
	}
	
	public String[] parserParameter() {
		return null;
	}

	public boolean delay4PageCal() {
		return false;
	}
	
	public void trav4HuntSIL(List list) {
	}
	
	public void trav4HuntBIL(List list) {
	}
}
