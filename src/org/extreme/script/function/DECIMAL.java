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
		return "DECIMAL(number): ����number�Ĵ������ͣ������ھ�ȷ���㡣";
	}
	public String getEN(){
		return "DECIMAL(number): Returns the BigDecimal type of number for precise calculate.\n";
	}
}
