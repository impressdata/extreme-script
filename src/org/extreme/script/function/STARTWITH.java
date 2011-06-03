/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/*
 * function
 */
public class STARTWITH extends AbstractFunction {
	
	/**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
	public Object run(Object[] args) {
		if(args.length != 2) {
			return Primitive.ERROR_VALUE;
		}
		
		String strPara1 = args[0].toString();
		String strPara2 = args[1].toString();
		
		return Boolean.valueOf(strPara1.startsWith(strPara2));
	}
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "STARTWITH(str1��str2):�ж��ַ���str1�Ƿ���str2��ʼ��\n"
		+"��ע:\n"
		+"    str1��str2���Ǵ�Сд���еġ�\n"
		+"ʾ��:\n"
		+"STARTWITH(\"ExtremScript\",\"Fine\")����true��\n"
		+"STARTWITH(\"ExtremScript\",\"Report\")����false��\n"
		+"STARTWITH(\"ExtremScript\"��\"Fine\")����false��";
	}
	public String getEN(){
		return "STARTWITH(string1,string2):Judge whether string1 starts with string2.\n"
		+"If string1 starts with string2,return true,else return false.\n"
		+"\n"
		+"Re:\n"
		+"The two strings are all Case-insensitive.\n"
		+"\n"
		+"Example:\n"
		+"STARTWITH(\"ExtremScript\",\"Fine\") returns true.\n"
		+"STARTWITH(\"ExtremScript\",\"Report\") returns false.\n"
		+"STARTWITH(\"ExtremScript\",\"fine\") returns false. ";
	}
}