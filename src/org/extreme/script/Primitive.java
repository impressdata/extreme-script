package org.extreme.script;

public class Primitive implements java.io.Serializable {    
    private int idx = 0;
    
	private Primitive(int index) {
	    this.idx = index;
	}
	
	public static final Primitive NULL = new Primitive(0);
	public static final Primitive ERROR_NAME = new Primitive(1);
	public static final Primitive ERROR_VALUE = new Primitive(2);
	// _ ���Ǽ򵥵�ָ�κ�ֵ���������κ�ֵ�ȽϾ�Ϊtrue,�Ӳ����κ�ֵ��Ϊ��������һ�������ֵ
	// �ڲ�����������Ϊ�շ���ȫ���������Ӧ�˶���
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
