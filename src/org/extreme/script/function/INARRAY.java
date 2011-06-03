package org.extreme.script.function;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class INARRAY extends AbstractFunction {
	/**
	 * 查询某个元素v是否在指定的数组array中,如果存在,则返回第一个v在数组array中的位置（从1开始）,如果不存在则返回0.
	 * @author richer
	 */
	public Object run(Object[] args) {
		if (args.length != 2) {
			return Primitive.ERROR_NAME;
		}
		
		Object value = args[0];
		Object array = args[1];
		
		if (!(array instanceof FArray)) {
			return Primitive.ERROR_VALUE;
		}
		
		FArray farray = (FArray)array;
		for (int i = 0; i < farray.length(); i ++) {
			if (ComparatorUtils.equals(farray.elementAt(i), value)) {
				return new Integer(i + 1);
			}
		}	
		return new Integer(0);
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "INARRAY(co, array):返回co在数组array中的位置，如果co不在array中，则返回0.\n"
		+"示例：\n"
		+"String[] arr = {\"a\",\"b\",\"c\",\"d\"}\n"
		+"那么INARRAY(\"b\", arr)等于2.";
	}
	public String getEN(){
		return "INARRAY(co, array):returns the index of co in array, if co isn\'t contained in array, returns -1.\n"
		+"Example：\n"
		+"String[] arr = {\"a\",\"b\",\"c\",\"d\"}\n"
		+"then INARRAY(\"b\", arr) returns 1.";
	}
}