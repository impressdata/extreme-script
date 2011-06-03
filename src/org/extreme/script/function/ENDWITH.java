/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/*
 * function
 */
public class ENDWITH extends AbstractFunction {
	
	/**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
	public Object run(Object[] args) {
		if(args.length < 2 || args.length > 2) {
			return Primitive.ERROR_VALUE;
		}
		
		String strPara1 = args[0].toString();
		String strPara2 = args[1].toString();
		
		return Boolean.valueOf(strPara1.endsWith(strPara2));
	}
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "ENDWITH(str1��str2):�ж��ַ���str1�Ƿ���str2������\n"
		+"��ע:\n"
		+"    str1��str2���Ǵ�Сд���еġ�\n"
		+"ʾ��:\n"
		+"ENDWITH(\"ExtremScript\",\"Report\")����true��\n"
		+"ENDWITH(\"ExtremScript\",\"Fine\")����false��\n"
		+"ENDWITH(\"ExtremScript\"��\"report\")����false��";
	}
	public String getEN(){
		return "ENDWITH(string1,string2):Judge whether string1 ends with string2.\n"
		+"If string1 ends with string2,return true,else return false.\n"
		+"\n"
		+"Re:\n"
		+"The two strings are all Case-insensitive.\n"
		+"\n"
		+"Example:\n"
		+"ENDWITH(\"ExtremScript\",\"Report\") returns true.\n"
		+"ENDWITH(\"ExtremScript\",\"Fine\") returns false.\n"
		+"ENDWITH(\"ExtremScript\",\"report\") returns false. ";
	}
}