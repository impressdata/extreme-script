package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;

public class INDEXOFARRAY extends AbstractFunction {
	/**
	 * 取数组array中的第index个元素，index下表从1开始
	 */
	public Object run(Object[] args) {
		if (args.length != 2) {
			return Primitive.ERROR_VALUE;
		}
		Object indexObject = args[1];
		Object arrayObject = args[0];
		if (!(indexObject instanceof Integer)
				|| !(arrayObject instanceof FArray)) {
			return Primitive.ERROR_VALUE;
		}

		int index = Integer.parseInt(indexObject.toString());
		FArray array = (FArray) arrayObject;
		if (index < 1 || index > array.length()) {
			return Primitive.NULL;
		}
		return array.elementAt(index - 1);
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "INDEXOFARRAY(array, index):返回数组array的第index个元素。\n"
		+"示例：\n"
		+"INDEXOFARRAY([\"第一个\", \"第二个\", \"第三个\"], 2)返回\"第二个\"。";
	}
	public String getEN(){
		return "";
	}
}