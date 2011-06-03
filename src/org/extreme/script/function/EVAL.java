package org.extreme.script.function;

import java.util.logging.Level;

import org.extreme.commons.util.LogUtil;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Calculator;
import org.extreme.script.Primitive;
import org.extreme.script.parser.UtilEvalError;


public class EVAL extends AbstractFunction {

    public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        Object exp = args[0];

        Calculator cal = this.getCalculator();
        Object result = null;
        if(cal != null) {
            try {
                result = cal.eval(exp.toString());
            } catch (UtilEvalError e) {
                LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
            }
        }

        return result == null ? Primitive.NULL : result;
    }
	public Type getType() {
		return OTHER;
	}	public String getCN(){
		return "EVAL(exp)返回表达式exp计算后的结果。\n"
		+"exp:一个表达式形式字符串。\n"
		+"备注:\n"
		+"    只要EVAL中的参数exp最终可以转化成一表达式形式的字符串，比如\"sum(2,4)\",\"2+7\"等等，那么它就可以被计算。\n"
		+"EVAL(\"2+5\")等于7。\n"
		+"EVAL(\"count(2,3)\")等于5。\n"
		+"EVAL(\"sum\"+\"(2,3,5)\")等于10。\n"
		+"EVAL(IF(true, \"sum\", \"count\") + \"(1,2,3,4)\")等于10。\n"
		+"EVAL(IF(false, \"sum\", \"count\") + \"(1,2,3,4)\")等于4。";
	}
	public String getEN(){
		return "";
	}
}