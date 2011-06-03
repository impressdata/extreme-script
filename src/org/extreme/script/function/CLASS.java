/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Return the class of parameter
 */
public class CLASS extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        return args[0].getClass();
    }
    public Type getType() {
    	return OTHER;
    }	public String getCN(){
		return "CLASS(object):返回object对象的所属的类。";
	}
	public String getEN(){
		return "get the class type.";
	}
}