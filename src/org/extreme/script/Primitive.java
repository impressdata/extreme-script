package org.extreme.script;

public class Primitive implements java.io.Serializable {    
    private int idx = 0;
    
	private Primitive(int index) {
	    this.idx = index;
	}
	
	public static final Primitive NULL = new Primitive(0);
	public static final Primitive ERROR_NAME = new Primitive(1);
	public static final Primitive ERROR_VALUE = new Primitive(2);
	// _ 并非简单的指任何值，而是与任何值比较均为true,加操作任何值均为本身这样一个特殊的值
	// 在参数部分输入为空返回全部这个功能应运而生
	public static final Primitive NOFILTER = new Primitive(3);
	
	private static final Primitive[] ps = {NULL, ERROR_NAME, ERROR_VALUE, NOFILTER};
	
	private Object readResolve() {
	    return ps[this.idx];
	}
    
    public String toString() {
        if(this == ERROR_NAME) {
            return "#NAME?";
        } else if (this == ERROR_VALUE) {
            return "#VALUE?";
        } else {
            return "";
        }
    }
}
