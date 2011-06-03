/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/*
 * function
 */
public class INDEXOF extends AbstractFunction {

	/**
	 * Run the function on the stack. Pops the arguments from the stack,
	 * then return the result.
	 */
	public Object run(Object[] args) {
		if (args.length < 2 || args.length > 2) {
			return Primitive.ERROR_VALUE;
		}
		Object objectPara = args[0];
		Object objectIndex = args[1];
		String strPara = objectPara.toString();

		if (objectIndex instanceof Integer) {
			int index = ((Integer) objectIndex).intValue();

			char[] charPara = strPara.toCharArray();
			if (index < 0 || index >= charPara.length) {
				return Primitive.ERROR_VALUE;
			} else {
				char result = charPara[index];
				return new Character(result);
			}

		} else {
			return Primitive.ERROR_VALUE;
		}
	}
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "INDEXOF(str1��index):�����ַ���str1��indexλ���ϵ��ַ���\n"
		+"��ע:\n"
		+"    index�Ǵ�0��ʼ�����ġ�\n"
		+"ʾ��:\n"
		+"INDEXOF(\"ExtremScript\",0)����\'F\'��\n"
		+"INDEXOF(\"ExtremScript\",2)����\'n\'��\n"
		+"INDEXOF(\"ExtremScript\"��9)����\'t\'��\n"
		+"\n"
		+"INDEXOF(array, index):����������indexλ���ϵ�Ԫ�ء�\n"
		+"��ע��\n"
		+"    index�Ǵ�1��ʼ�����ġ�\n"
		+"ʾ����\n"
		+"String[] array = {\"a\", \"b\", \"c\"}\n"
		+"INDEXOF(array, 1)����\"a\".\n"
		+"";
	}
	public String getEN(){
		return "INDEXOF(string1,index): Returns the char at the index of string1.\n"
		+"\n"
		+"Re:\n"
		+"index count from 0.\n"
		+"\n"
		+"Example:\n"
		+"INDEXOF(\"ExtremScript\",0) returns \'F\'.\n"
		+"INDEXOF(\"ExtremScript\",2) returns \'n\'.\n"
		+"INDEXOF(\"ExtremScript\"��9) returns \'t\'.\n"
		+"\n"
		+"INDEXOF(array, index):Returns the element at the index of array.\n"
		+"Re��\n"
		+"    index count from 1.\n"
		+"Example��\n"
		+"String[] array = {\"a\", \"b\", \"c\"}\n"
		+"INDEXOF(array, 1) returns \"a\".";
	}
}