/*
 * Apache Licence2.0
 */
package org.extreme.script.function;


import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class DEGREES extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object value = args[0];
        if (value instanceof Number) {
            return FunctionHelper.asNumber(
                    Math.toDegrees(((Number) value).doubleValue()));
        }

        return Primitive.ERROR_NAME;
    }
	public Type getType() {
		return MATH;
	}	public String getCN(){
		return "DEGREES(angle): 将弧度转化为度。\n"
		+"angle:待转换的弧度角。\n"
		+"示例:\n"
		+"DEGREES(PI()/2)等于90。\n"
		+"DEGREES(3.1415926)等于179.9999969。";
	}
	public String getEN(){
		return "DEGREES(angle): Converts radians into degrees.\n"
		+"angle is the angle in radians that you want to convert.\n"
		+"\n"
		+"Exmaple:\n"
		+"   DEGREES(PI()/2)=90\n"
		+"   DEGREES(3.1415926)=179.9999969";
	}
}