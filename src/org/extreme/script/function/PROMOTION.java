package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;
import org.extreme.script.parser.OperationUtils;


public class PROMOTION extends AbstractFunction {
	public Object run(Object[] args) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }

        double value1 = Utils.objectToNumber(args[0], false).doubleValue();
        double value2 = Utils.objectToNumber(args[1], false).doubleValue();

        if(value1 > 0) {
        	return new Double((value2 - value1) / value1);
        } else if(value1 < 0) { 
        	return new Double(-(value2 - value1) / value1);
        } else {
        	return OperationUtils.POSITIVE_INFINITY;
        }
	}
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "PROMOTION(value1,value2):返回value2在value1上提升的比例。\n"
		+"示例：\n"
		+"PROMOTION(12, 14)等于0.166666666，即提升了16.6666666%.\n"
		+"PROMOTION(-12, 14)等于2.166666666，即提升了216.6666666%.";
	}
	public String getEN(){
		return "";
	}
}