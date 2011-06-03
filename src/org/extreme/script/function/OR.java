package org.extreme.script.function;

import org.extreme.script.Calculator;
import org.extreme.script.CalculatorEmbeddedFunction;
import org.extreme.script.parser.UtilEvalError;

public class OR extends CalculatorEmbeddedFunction {
	
	public Object evpression(Object[] args)
			throws UtilEvalError {
		boolean result = false;
		
		Calculator c = this.getCalculator();

		for (int i = 0; i < args.length; i++) {
			Object o = c.eval(args[i]);
			if (o instanceof Boolean) {
				result |= ((Boolean)o).booleanValue();
				if(result) {
					break;
				}
			}
		}

		return Boolean.valueOf(result);
	}

	public Type getType() {
		return LOGIC;
	}
	public String getCN(){
		return "OR(logical1,logical2,��): �����в�����ֵΪ��ʱ������FALSE�������������ֵΪ��ʱ������TRUE��\n"
		+"Logical1,logical2,��:ָ1��30����Ҫ����TRUE��FALSE������ֵ��\n"
		+"��ע:\n"
		+"    �����������߼�ֵ�����Ǻ����߼�ֵ����������á�\n"
		+"    �������������к����ı���յĵ�Ԫ���������ֵ��\n"
		+"    �����ָ���ĵ�Ԫ��������û���߼�ֵ��AND���������ش�����Ϣ*NAME?��\n"
		+"ʾ��:\n"
		+"OR(1+7=9,5+7=11)����FALSE��\n"
		+"OR(1+7=8,5+7=11)����TRUE��";
	}
	public String getEN(){
		return "OR(logical1,logical2,��): Returns FALSE if all its arguments are FALSE; returns TRUE if one or more argument is TRUE.\n"
		+"Logical1, logical2, ... are 1 to 30 conditions you want to test that can be either TRUE or FALSE.\n"
		+"\n"
		+"Re:\n"
		+"1. The arguments must evaluate to logical values such as TRUE or FALSE, or the arguments must be arrays or references that contain logical values. \n"
		+"2. If an array or reference argument contains text or empty cells, those values are ignored. \n"
		+"3. If the specified range contains no logical values, AND returns the *NAME? error value. \n"
		+"\n"
		+"Example:\n"
		+"   OR(1+7=8,5+7=11) = TRUE��\n"
		+"   OR(1+7=9,5+7=11) = FALSE��";
	}
}
