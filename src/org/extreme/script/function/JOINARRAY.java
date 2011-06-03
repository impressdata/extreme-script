package org.extreme.script.function;

import org.extreme.commons.util.StringUtils;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class JOINARRAY extends AbstractFunction {
	
	public Object run(Object[] args){
		if (args.length < 1) {
			return Primitive.ERROR_NAME;
		}
		/*
		 * :�����һ����������FArray���ͣ������Լ�
		 */
		if(!(args[0] instanceof FArray)){
			return args[0];
		}
		StringBuffer sb = new StringBuffer();
		java.util.Iterator it = ((FArray)args[0]).iterator();
		/*
		 * :ǿ�ƽ��ڶ�������ת����string
		 */
		String se = Utils.objectToString(args.length > 1 ? args[1] : StringUtils.EMPTY);
		while(it.hasNext()) {
			sb.append(it.next());
			if(it.hasNext()) {
				sb.append(se);
			}
		}
		return sb.toString();
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "JOINARRAY(array,sepa):����һ����sepa��Ϊ�ָ������ַ���.\n"
		+"array:[arg1,arg2...]��ʽ������;\n"
		+"sepa:�ָ�����\n"
		+"ʾ��:\n"
		+"JOINARRAY([1,2],\";\") = [1;2].\n"
		+"JOINARRAY([hello,world],\"-\") = [hello-world].";
	}
	public String getEN(){
		return "JOINARRAY(array,separator):return an array with sepa as the separator.\n"
		+"array:array of [arg1,arg2...] type;\n"
		+"sepa:separator.\n"
		+"Examples:\n"
		+"JOINARRAY([1,2],\";\") = [1;2].\n"
		+"JOINARRAY([hello,world],\"-\") = [hello-world].";
	}
}