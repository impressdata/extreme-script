package org.extreme.script.function;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class INDEX extends AbstractFunction {

	public Object run(Object[] args) {
		if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
		
		Object key = args[0];
		int idx = 1;
		for(int i = 1; i < args.length; i++) {
			if(ComparatorUtils.equals(key, args[i])) {
				break;
			}
			idx++;
		}
		
		return new Integer(idx);
	}

	public Type getType() {
		return OTHER;
	}	public String getCN(){
		return "INDEX(key,val1,val2,...):返回key在val1,val2,...所组成的序列中的位置,不存在于序列中则返回参数的个数.\n"
		+"备注:\n"
		+"    key和valn可以是任意类型\n"
		+"示例:\n"
		+"INDEX(2,2)等于1。\n"
		+"INDEX(2,1,2)等于2。\n"
		+"INDEX(2,4,5,6)等于4。\n"
		+"INDEX(\"b\",\"b\",\"o\",\"y\")等于1。";
	}
	public String getEN(){
		return "INDEX(key,val1,val2,...):return the position that key in val1,val2,..., if not exist, return the length of arguments\n"
		+"key and valn can be of any type.\n"
		+"\n"
		+"Examples:\n"
		+"INDEX(2,2) = 1。\n"
		+"INDEX(2,1,2) = 2。\n"
		+"INDEX(2,4,5,6) = 4。\n"
		+"INDEX(\"b\",\"b\",\"o\",\"y\") = 1。";
	}
}