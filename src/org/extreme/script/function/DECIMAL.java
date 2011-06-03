package org.extreme.script.function;

import java.math.BigDecimal;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class DECIMAL extends AbstractFunction {
	public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        return new BigDecimal(Utils.objectToNumber(args[0], false).doubleValue());
    }
	public Type getType() {
		return MATH;
	}
	public String getCN(){
		return "DECIMAL(number): 返回number的大数类型，常用于精确计算。";
	}
	public String getEN(){
		return "DECIMAL(number): Returns the BigDecimal type of number for precise calculate.\n";
	}
}
