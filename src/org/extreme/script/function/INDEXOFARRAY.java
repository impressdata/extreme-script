package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;

public class INDEXOFARRAY extends AbstractFunction {
	/**
	 * ȡ����array�еĵ�index��Ԫ�أ�index�±��1��ʼ
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
		return "INDEXOFARRAY(array, index):��������array�ĵ�index��Ԫ�ء�\n"
		+"ʾ����\n"
		+"INDEXOFARRAY([\"��һ��\", \"�ڶ���\", \"������\"], 2)����\"�ڶ���\"��";
	}
	public String getEN(){
		return "";
	}
}