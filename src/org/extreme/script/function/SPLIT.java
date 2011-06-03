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
		return "SPLIT(String1,String2)：返回由String2分割String1组成的字符串数组。\n"
		+"String1：以双引号表示的字符串。\n"
		+"String2：以双引号表示的分隔符。例如逗号\",\"\n"
		+"示例:\n"
		+"SPLIT(\"hello,world,yes\",\",\") = [\"hello\",\"world\",\"yes\"]。\n"
		+"SPLIT(\"this is very good\",\" \") = [\"this\",\"is\",\"very\",\"good\"]。\n"
		+"备注：\n"
		+"如果只有一个参数，则返回一个错误。\n"
		+"如果有多个参数，则只有前两个起作用。";
	}
	public String getEN(){
		return "SPLIT(String1,String2)：return an array that is the composition of the result that String1 is split by String2. \n"
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