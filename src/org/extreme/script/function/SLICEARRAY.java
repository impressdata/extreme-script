package org.extreme.script.function;

import org.extreme.script.AbstractCellFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;

public class SLICEARRAY extends AbstractCellFunction {
	/**
	 * 返回数组里指定区间的元素
	 */
	public Object run(Object[] args) {
		if (args.length < 2 || args.length > 3) {
			return Primitive.ERROR_VALUE;
		}

		Object arrayObject = args[0];
		if (!(arrayObject instanceof FArray)) {
			return Primitive.ERROR_VALUE;
		}
		FArray oldArray = (FArray) arrayObject;

		Object startObject = args[1];
		int start = 0;
		if (!(startObject instanceof Integer)) {
			return Primitive.ERROR_VALUE;
		}
		start = Integer.parseInt(startObject.toString());
		if (args.length == 2) {
			return partArray(oldArray, start);
		} else {
			Object endObject = args[2];
			int end = 0;
			if (!(endObject instanceof Integer)) {
				return Primitive.ERROR_VALUE;
			}
			end = Integer.parseInt(endObject.toString());
			return partArray(oldArray, start, end);
		}
	}

	/**
	 * 返回数组从第start个元素开始到结束的元素.
	 */
	private FArray partArray(FArray array, int start) {
		return this.partArray(array, start, array.length());
	}

	/**
	 * 返回数组从start开始，end结束之间的元素，包括第end个元素.
	 */
	private FArray partArray(FArray array, int start, int end) {
		FArray newArray = new FArray();
		if (start < 1) {
			start = 1;
		}
		if (end > array.length()) {
			end = array.length();
		}

		for (int i = start; i <= end; i++) {
			newArray.add(array.elementAt(i - 1));
		}
		return newArray;
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "SLICEARRAY(array, start, end):返回数组从第start个到第end个元素(包括第end个元素)。\n"
		+"示例：\n"
		+"SLICEARRAY([3, 4, 4, 5, 1, 5, 7], 3, 6)返回[4, 5, 1, 5].\n"
		+"当不使用end参数时，返回从start开始到数组结束之间的元素。\n"
		+"SLICEARRAY([3, 4, 4, 5, 1, 5, 7], 3)返回[4, 5, 1, 5, 7].";
	}
	public String getEN(){
		return "";
	}
}