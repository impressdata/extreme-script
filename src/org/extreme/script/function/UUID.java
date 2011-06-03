package org.extreme.script.function;

import org.extreme.script.AbstractFunction;

public class UUID extends AbstractFunction {
	public Object run(Object[] args) {
		if (args.length == 0) {
			return org.extreme.commons.UUID.randomUUID().toString();
		} else if (args.length > 0) {
			Object digit = args[0];
			if (digit instanceof Integer) {
				if (((Integer)digit).equals(new Integer(32))) {
					return org.extreme.commons.UUID.randomUUID().toString().replaceAll("-", "");
				}
			} else if (digit instanceof String) {
				if (digit.toString().equals("32")) {
					return org.extreme.commons.UUID.randomUUID().toString().replaceAll("-", "");
				}
			} else {
				return org.extreme.commons.UUID.randomUUID().toString();
			}
		}
		return org.extreme.commons.UUID.randomUUID().toString();
	}
	public Type getType() {
		return OTHER;
	}	public String getCN(){
		return "UUID():���������UUID��\n"
		+"ʾ��:UUID()����36λ�����������\n"
		+"       UUID(32)����32λ�����������";
	}
	public String getEN(){
		return "";
	}
}