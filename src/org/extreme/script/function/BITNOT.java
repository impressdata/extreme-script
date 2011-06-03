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
		return "BITNOT(int):将一个十进制整数进行二进制取反运算。\n"
		+"int:需要进行转换的十进制数。\n"
		+"示例：\n"
		+"BITNOT(3)等于-4。\n"
		+"BITNOT(12)等于-13。";
	}
	public String getEN(){
		return "";
	}
}