package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

public class BITNOT extends AbstractFunction {
	public Object run(Object[] args) {
		if(args.length < 1) {
			return Primitive.ERROR_NAME;
		}
		
		Object para = args[0];
		if(para instanceof Integer) {
			int i = ((Integer)para).intValue();
			return new Integer(~i);
		} else {
			return Primitive.ERROR_NAME;
		}
	}
	public Type getType() {
		return LOGIC;
	}	public String getCN(){
		return "BITNOT(int):��һ��ʮ�����������ж�����ȡ�����㡣\n"
		+"int:��Ҫ����ת����ʮ��������\n"
		+"ʾ����\n"
		+"BITNOT(3)����-4��\n"
		+"BITNOT(12)����-13��";
	}
	public String getEN(){
		return "";
	}
}