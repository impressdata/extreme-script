/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;

/**
 * CHAR.
 */
public class CHAR extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
    	for(int i = 0; i < args.length; i++) {
    		if (args[i] instanceof Number) {
                return new Character((char) ((Number) args[i]).intValue());
            }
    	}

        return "";
    }
    public Type getType() {
    	return TEXT;
    }	public String getCN(){
		return "CHAR(number): ����ָ�����ַ��ض�Ӧ���ַ���CHAR�����ɽ�������������͵����ִ���ת��Ϊ�ַ���\n"
		+"Number:����ָ���ַ������֣�����1~65535֮�䣨����1��65535����\n"
		+"ʾ��:\n"
		+"CHAR(88)���ڡ�X����\n"
		+"CHAR(45)���ڡ�-����";
	}
	public String getEN(){
		return "CHAR(number): Returns the character specified by a number. Use CHAR to translate code page numbers you    might get from files on other types of computers into characters.\n"
		+"   Number:Number is a number between 1 and 65535 specifying which character you want.(Include 1 and 65535)\n"
		+"Example:\n"
		+"   CHAR(88)=\"X\"\n"
		+"   CHAR(45)=\"-\"";
	}
}