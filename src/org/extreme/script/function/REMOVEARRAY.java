package org.extreme.script.function;

import org.extreme.script.AbstractCellFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;

public class REMOVEARRAY extends AbstractCellFunction {
	/**
	 * 删除array中从第start个元素开始的deleteCount个元素
	 */
	public Object run(Object[] args) {
		if (args.length < 3) {
			return Primitive.ERROR_VALUE;
		}
		Object arrayObject = args[0];
		Object startObject = args[1];
		Object deleteCountObject = args[2];
		if (!(arrayObject instanceof FArray)
				|| !(deleteCountObject instanceof Integer)
				|| !(startObject instanceof Integer)) {
			return Primitive.ERROR_VALUE;
		}

		FArray array = (FArray) arrayObject;
		int start = Integer.parseInt(startObject.toString());
		int deleteCount = Integer.parseInt(deleteCountObject.toString());

		return splice(array, start, deleteCount);

	}

	/**
	 * 删除array中从第start个元素开始的deleteCount个元素
	 */
	private FArray splice(FArray array, int start, int deleteCount) {
		FArray newArray = new FArray();
		if (start < 1) {
			start = 1;
		}
        
		if (deleteCount < 0) {
			deleteCount = 0;
		}
		
		for (int i = 0; i < start - 1; i++) {
			newArray.add(array.elementAt(i));
		}
		for (int i = start + deleteCount - 1; i < array.length(); i++) {
			newArray.add(array.elementAt(i));
		}

		return newArray;
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "REMOVEARRAY(array, start, deleteCount):从数组array中删除从第start个元素开始的deleteCount个数组元素，并返回删除后的数组。\n"
		+"示例：\n"
		+"REMOVEARRAY([3, 4, 4, 2, 6, 7, 87], 4, 2)返回[3, 4, 4, 7, 87].\n"
		+"";
	}
	public String getEN(){
		return "";
	}
}