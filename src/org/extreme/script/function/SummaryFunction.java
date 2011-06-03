package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

public abstract class SummaryFunction extends AbstractFunction {

	public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        double result = init();

        for(int i = 0; i < args.length; i++) {
        	if(args[i] == null) {
        		continue;
        	}
        	
        	result = operation(result, parseObject(args[i]));
        }

        return FunctionHelper.asNumber(result);
    }
	
	protected abstract double init();
	
	protected abstract double operation(double preview, double nextEl);
	
	protected abstract double parseObject(Object ob);

	public Type getType() {
		return MATH;
	}

}
