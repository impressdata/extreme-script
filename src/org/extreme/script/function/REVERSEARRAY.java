package org.extreme.script.function;

import org.extreme.script.AbstractCellFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;

public class REVERSEARRAY extends AbstractCellFunction {
	/**
	 * ����һ��Ԫ��˳�򱻷�ת��FArray����.
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
		return "REVERSEARRAY(array):��������array�ĵ������顣\n"
		+"ʾ����\n"
		+"REVERSEARRAY([\"��һ��\", \"�ڶ���\", \"������\"])����[\"������\", \"�ڶ���\", \"��һ��\"].";
	}
	public String getEN(){
		return "";
	}
}