/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.Calculator;
import org.extreme.script.CalculatorEmbeddedFunction;
import org.extreme.script.Primitive;
import org.extreme.script.parser.UtilEvalError;


/**
 * IF Function.
 */
public class IF extends CalculatorEmbeddedFunction {
	
	public Object evpression(Object[] args)
			throws UtilEvalError {
        if (args.length < 3) {
            return Primitive.ERROR_NAME;
        }
        
        Calculator c = this.getCalculator();
        Object boolvalue = c.eval(args[0]);
        if ((boolvalue instanceof Boolean && ((Boolean)boolvalue).booleanValue()) || 
        		// :��Ϊ�п��ܴ������Ĳ���boolean��,������true�������ַ���
        		Boolean.valueOf(Utils.objectToString(boolvalue)).booleanValue()) {
        	return c.eval(args[1]);
        } else {
        	return c.eval(args[2]);
        }
	}
	
	public Type getType() {
		return LOGIC;
	}	public String getCN(){
		return "IF(boolean,number1/string1,number2/string2):�жϺ���,booleanΪtrueʱ���صڶ�������,Ϊfalseʱ���ص�������\n"
		+"boolean: �����жϵĲ���ֵ,true����false��\n"
		+"number1/string1: ��һ�����������booleanΪture,�������ֵ��\n"
		+"number2/string2: �ڶ������������booleanΪfalse,�������ֵ��\n"
		+"ʾ��:\n"
		+"IF(true,2,8)����2\n"
		+"IF(false,\"first\",\"second\")����second\n"
		+"IF(true,\"first\",7)����first";
	}
	public String getEN(){
		return "IF(boolean,number1/string1,number2/string2): Returns number1/striing1 if boolean you specify evaluates to TRUE and number2/string2 if it evaluates to FALSE.\n"
		+"boolean is any value or expression that can be evaluated to TRUE or FALSE.\n"
		+"number1/string1 is the value that is returned if boolean is TRUE.\n"
		+"number2/string2 is the value that is returned if boolean is FALSE.\n"
		+"\n"
		+"Example:\n"
		+"   IF(true,2,8) = 2\n"
		+"   IF(false,\"first\",\"second\") = \"second\"\n"
		+"   IF(true,\"first\",7) = \"first\"";
	}
}