package org.extreme.script.function;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.script.AbstractCellFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class UNIQUEARRAY extends AbstractCellFunction {
	/**
	 * 去掉数组中的重复元素
	 * @author richer
	 */
	public Object run(Object[] args) {
		if (args.length != 1) {
			return Primitive.ERROR_VALUE;
		}
		
		FArray newArray = new FArray();
		for (int i = 0; i < args.length; i++) {
			Object ob = args[i];
			if (ob instanceof FArray) {
				FArray array = (FArray)ob;
				for (int ai = 0; ai < array.length(); ai++) {
					if (!isExist(newArray, array.elementAt(ai))) {
						newArray.add(array.elementAt(ai));
					}
				}
			}
			
			else if (!isExist(newArray, ob)) {
				newArray.add(ob);
			}
		}

		return newArray;
	}

	/**
	 * 判断一个array里面的第index个元素是否已经在前面出现.
	 */
	private boolean isExist(FArray array, Object ob) {
		for (int i = 0; i < array.length(); i++) {
			if (ComparatorUtils.equals(array.elementAt(i), ob)) {
				return true;
			}
		}
		return false;
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "UNIQUEARRAY(array):去掉数组array中的重复元素。\n"
		+"示例：\n"
		+"UNIQUEARRAY([14, 2, 3, 4, 3, 2, 5, 6, 2, 7, 9, 12, 3])返回[14, 2, 3, 4, 5, 6, 7, 9, 12].";
	}
	public String getEN(){
		return "";
	}
}