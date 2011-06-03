package org.extreme.script.function;

import org.extreme.script.FArray;

public abstract class MinAndMaxAndSUM extends SummaryFunction {
	
	protected double parseObject(Object ob) {
    	if (ob instanceof Number) {
        	return ((Number)ob).doubleValue();
        } else if (ob instanceof Boolean) {
            return ((Boolean)ob).booleanValue() ? 1 : 0;
        } else if (ob instanceof FArray) {
        	FArray array = (FArray)ob;
        	double v = init();
        	for(int i = 0, len = array.length(); i < len; i++) {
        		v = operation(v, parseObject(array.elementAt(i)));
        	}
        	return v;
        } else if(ob != null) {
        	try {
        		return Double.parseDouble(ob.toString());
        	} catch(NumberFormatException exp) {
        		return 0;
        	}
        }
    	
    	return 0;
	}
}
