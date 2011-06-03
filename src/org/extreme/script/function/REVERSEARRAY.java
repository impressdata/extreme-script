package org.extreme.script.function;

import org.extreme.script.AbstractCellFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;

public class REVERSEARRAY extends AbstractCellFunction {
	/**
	 * 返回一个元素顺序被反转的FArray对象.
	 * @author richer
	 */
	public Object run(Object[] args) {
		if (args.length != 1) {
			return Primitive.ERROR_VALUE;
		}

		Object object = args[0];
		if (!(object instanceof FArray)) {
			return Primitive.ERROR_VALUE;
		}

		FArray oldArray = (FArray) object;
		FArray newArray = new FArray();
		for (int i = oldArray.length(); i > 0; i--) {
			newArray.add(oldArray.elementAt(i - 1));
		}
		return newArray;
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "REVERSEARRAY(array):返回数组array的倒序数组。\n"
		+"示例：\n"
		+"REVERSEARRAY([\"第一个\", \"第二个\", \"第三个\"])返回[\"第三个\", \"第二个\", \"第一个\"].";
	}
	public String getEN(){
		return "";
	}
}