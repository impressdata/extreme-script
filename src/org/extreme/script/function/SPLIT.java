package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;

public class SPLIT extends AbstractFunction {

	public Object run(Object[] args) {
		if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }
		
		String str = args[0].toString();
		String re = args[1].toString();
		
		return new FArray(str.split(re));
	}

	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "SPLIT(String1,String2)��������String2�ָ�String1��ɵ��ַ������顣\n"
		+"String1����˫���ű�ʾ���ַ�����\n"
		+"String2����˫���ű�ʾ�ķָ��������綺��\",\"\n"
		+"ʾ��:\n"
		+"SPLIT(\"hello,world,yes\",\",\") = [\"hello\",\"world\",\"yes\"]��\n"
		+"SPLIT(\"this is very good\",\" \") = [\"this\",\"is\",\"very\",\"good\"]��\n"
		+"��ע��\n"
		+"���ֻ��һ���������򷵻�һ������\n"
		+"����ж����������ֻ��ǰ���������á�";
	}
	public String getEN(){
		return "SPLIT(String1,String2)��return an array that is the composition of the result that String1 is split by String2. \n"
		+"String1: a string of characters that is between double quotes.\n"
		+"String2: a string of characters that is between double quotes.\n"
		+"Example: \n"
		+"SPLIT(\"hello,world\",\",\") = [\"hello\",\"world\"].\n"
		+"SPLIT(\"this is very good\",\" \") = [\"this\",\"is\",\"very\",\"good\"].\n"
		+"Re:\n"
		+"1. if there is only one parameter, then SPLIT will return an error.\n"
		+"2. If there are more than two parameters, only the first two works.";
	}
}