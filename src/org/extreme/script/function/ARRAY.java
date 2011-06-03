package org.extreme.script.function;

import org.extreme.script.AbstractCellFunction;
import org.extreme.script.FArray;

public class ARRAY extends AbstractCellFunction {

	public Object run(Object[] args) {
		return new FArray(args);
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "ARRAY(arg1,arg2...):返回一个由arg1,arg2,...组成的数组.\n"
		+"arg1,arg2,...:字符串或者数字.\n"
		+"示例:\n"
		+"ARRAY(\"hello\") = [\"hello\"].\n"
		+"ARRAY(\"hello\",\"world\") = [\"hello\",\"world\"].\n"
		+"ARRAY(\"hello\",98) = [\"hello\",98].\n"
		+"ARRAY(67,98) = [67,98].";
	}
	public String getEN(){
		return "ARRAY(arg1,arg2,...): return an array that is the composition of arg1,arg2,.... \n"
		+"arg1,arg2,...: a string or a number.\n"
		+"Example: \n"
		+"ARRAY(\"hello\") = [\"hello\"].\n"
		+"ARRAY(\"hello\",\"world\") = [\"hello\",\"world\"].\n"
		+"ARRAY(\"hello\",98) = [\"hello\",98].\n"
		+"ARRAY(67,98) = [67,98].\n"
		+"";
	}
}