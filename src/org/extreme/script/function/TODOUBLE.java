/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import java.util.logging.Level;

import org.extreme.commons.util.LogUtil;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


/**
 * Convert String to double.
 */
public class TODOUBLE extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }

        Object param;
        if (args.length > 0) {
            param = args[0];
            if(param instanceof Number) {
                return new Double(((Number) param).doubleValue());
            }

            try {
                return new Double(Double.parseDouble("" + param));
            } catch(NumberFormatException nExp) {
                LogUtil.getLogger().log(Level.WARNING,nExp.getMessage(), nExp);
            }
        }

        return new Double(0);
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "TODOUBLE(text): 将文本转换成Double对象。\n"
		+"Text:需要转换的文本。\n"
		+"示例:\n"
		+"TODOUBLE(\"123.21\")等于 new Double(123.21)。";
	}
	public String getEN(){
		return "TODOUBLE(text): Convert text to Double object.\n"
		+"\n"
		+"Example:\n"
		+"TODOUBLE(\"123.21\") = new Double(123.21)。";
	}
}