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
		return "EVAL(exp)���ر��ʽexp�����Ľ����\n"
		+"exp:һ�����ʽ��ʽ�ַ�����\n"
		+"��ע:\n"
		+"    ֻҪEVAL�еĲ���exp���տ���ת����һ���ʽ��ʽ���ַ���������\"sum(2,4)\",\"2+7\"�ȵȣ���ô���Ϳ��Ա����㡣\n"
		+"EVAL(\"2+5\")����7��\n"
		+"EVAL(\"count(2,3)\")����5��\n"
		+"EVAL(\"sum\"+\"(2,3,5)\")����10��\n"
		+"EVAL(IF(true, \"sum\", \"count\") + \"(1,2,3,4)\")����10��\n"
		+"EVAL(IF(false, \"sum\", \"count\") + \"(1,2,3,4)\")����4��";
	}
	public String getEN(){
		return "";
	}
}