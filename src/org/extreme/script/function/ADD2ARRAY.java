package org.extreme.script.function;

import org.extreme.script.AbstractCellFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;

public class ADD2ARRAY extends AbstractCellFunction {
	public Object run(Object[] args) {
		if (args.length < 2 || args.length > 3) {
			return Primitive.ERROR_VALUE;
		}

		Object arrayObject = args[0];
		if (!(arrayObject instanceof FArray)) {
			return Primitive.ERROR_VALUE;
		}
		Object insertObject = args[1];
		int start = 0;
		if (args.length == 3) {
			Object startObject = args[2];
			if (startObject instanceof Integer) {
				start = Integer.parseInt(startObject.toString());
			}
		}
		return this.addElement((FArray) arrayObject, insertObject, start);
	}

	/**
	 *�������startλ���������Ԫ�ص�ԭ��������ȥ
	 */
	private FArray addElement(FArray array, Object insertObject, int start) {
		FArray newArray = new FArray();
		if (start < 1) {
			start = 1;
		}
		if (insertObject instanceof FArray) {
			FArray insertArray = (FArray) insertObject;
			for (int i = 0; i < start - 1; i++) {
				newArray.add(array.elementAt(i));
			}
			for (int i = 0; i < insertArray.length(); i++) {
				newArray.add(insertArray.elementAt(i));
			}
			for (int i = start - 1; i < array.length(); i++) {
				newArray.add(array.elementAt(i));
			}
		} else {
			for (int i = 0; i < start - 1; i++) {
				newArray.add(array.elementAt(i));
			}
			newArray.add(insertObject);
			for (int i = start - 1; i < array.length(); i++) {
				newArray.add(array.elementAt(i));
			}
		}
		return newArray;
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "ADDARRAY(array, insertArray, start):�������start��λ�ò���insertArray�е�����Ԫ�أ��ٷ��ظ����顣\n"
		+"ʾ����\n"
		+"ADDARRAY([3, 4, 1, 5, 7], [23, 43, 22], 3)����[3, 4, 23, 43, 22, 1, 5, 7].\n"
		+"ADDARRAY([3, 4, 1, 5, 7], \"����\", 3)����[3, 4, \"����\", 1, 5, 7].\n"
		+"ע�⣺���startΪС��1�������߲�дstart��������Ĭ�ϴ�����ĵ�һλ��ʼ��������Ԫ�ء�";
	}
	public String getEN(){
		return "";
	}
}