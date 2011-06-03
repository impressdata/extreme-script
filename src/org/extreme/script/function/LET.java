package org.extreme.script.function;

import java.util.regex.Pattern;

import org.extreme.script.Calculator;
import org.extreme.script.CalculatorEmbeddedFunction;
import org.extreme.script.ParameterMapNameSpace;
import org.extreme.script.Primitive;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.UtilEvalError;


public class LET extends CalculatorEmbeddedFunction {
	public static Pattern VariablePattern = Pattern.compile("^[A-Za-z]+\\w*$");
	
	public Object evpression(Object[] args)
	throws UtilEvalError {
		if (args.length % 2 == 0) {
			// �����ĸ���N����Ϊ���������һ���Ǳ��ʽ��ǰ����N-1(ż��)Ϊ�ֲ�������ֵ
            return Primitive.ERROR_NAME;
        }
        
		
        Calculator c = this.getCalculator();
        java.util.Map variableMap = new java.util.HashMap();
        
        for (int i = 0; i < args.length - 1; i += 2) {
        	if (args[i] instanceof Node) {
        		String variableName = ((Node)args[i]).exString(c);
            	if (VariablePattern.matcher(variableName).find()) {
            		variableMap.put(variableName, c.eval(args[i + 1]));
            	}
        	}
        }
        
        ParameterMapNameSpace nameSpace = ParameterMapNameSpace.create(variableMap);
        c.pushNameSpace(nameSpace);
        Object res = c.eval(args[args.length - 1]);
        c.removeNameSpace(nameSpace);
        return res;
	}

	public Type getType() {
		return OTHER;
	}
	
	public String getCN() {
		return "LET(������,����ֵ,������,����ֵ,..., ���ʽ):�ֲ�������ֵ����,�����ĸ���N����Ϊ����, ���һ���Ǳ��ʽ��ǰ����N-1(ż��)Ϊ�ֲ�������ֵ�ԡ�\n"
		+"������: �����ǺϷ��ı�����������ĸ��ͷ�����԰�����ĸ�����ֺ��»���\n"
		+"���ʽ: ����ǰ���N-1��������ֵ���������Ľ������Щ������ֵֻ��������ʽ�ڲ���Ч\n"
		+"ʾ��:\n"
		+"LET(a, 5,b, 6, a+b)����11\n";
	}
	
	

	public String getEN() {
		return "IF(variable,value,variable,value,..., expression):Local varialbe assign function, the length of arguments must be odd, the last arg is a expression��the first N-1(Even) arg loacal variable pairs.\n"
		+"variable: is legal when starts with letter and composite with letters, numbers and underline\n"
		+"expression: calculate with previous (N-1)/2 varialbes.\n"
		+"Example:\n"
		+"LET(a, 5,b, 6, a+b)equals11\n";
	}

}
