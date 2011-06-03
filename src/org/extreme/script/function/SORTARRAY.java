package org.extreme.script.function;

import java.util.logging.Level;

import org.extreme.commons.util.LogUtil;
import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class SORTARRAY extends AbstractFunction {
	/**
	 * ��������
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
		java.util.List aList = new java.util.ArrayList();
		for (int i = 0; i < oldArray.length(); i++) {
			aList.add(oldArray.elementAt(i));
		}
		try {
			java.util.Collections.sort(aList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldArray = new FArray(aList);

		return oldArray;
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "SORTARRAY(array):��������array�Ź�������顣\n"
		+"ʾ����\n"
		+"SORTARRAY([3, 4, 4, 5, 1, 5, 7])����[1, 3, 4, 4, 5, 5, 7].\n"
		+"ע�⣺����array��Ԫ�����ͱ���һ��������Ҫ�ɱȽϡ�";
	}
	public String getEN(){
		return "";
	}
}