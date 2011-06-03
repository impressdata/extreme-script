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
		 * :如果第一个参数不是FArray类型，返回自己
		 */
		if(!(args[0] instanceof FArray)){
			return args[0];
		}
		StringBuffer sb = new StringBuffer();
		java.util.Iterator it = ((FArray)args[0]).iterator();
		/*
		 * :强制将第二个参数转换成string
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
		return "JOINARRAY(array,sepa):返回一个由sepa作为分隔符的字符串.\n"
		+"array:[arg1,arg2...]格式的数组;\n"
		+"sepa:分隔符。\n"
		+"示例:\n"
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