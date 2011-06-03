package org.extreme.script.function;

import java.math.BigDecimal;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;


public class ENNUMBER extends AbstractFunction {
	public Object run(Object[] args) {
		String lsValue = "";
		if (args.length < 1) {
			return Primitive.ERROR_NAME;
		}

		Object param;
		for (int i = 0; i < args.length; i++) {
			param = args[i];

			if (param instanceof BigDecimal) {
				try {
					lsValue = FunctionHelper.NumberToEnglish((BigDecimal) param);
				} catch (Exception ex) {
					lsValue = "ERROR!";
				}
			}
		}

		return lsValue;
	}

	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "ENNUMBER(value):将给定的BigDecimal类型的数字转化成英文金额的字符串。";
	}
	public String getEN(){
		return "";
	}
}