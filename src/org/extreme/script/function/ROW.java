package org.extreme.script.function;

import org.extreme.commons.ColumnRow;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Calculator;


public class ROW extends AbstractFunction {

	public Object run(Object[] args) {
		ColumnRow cr = null;
		Calculator cal = this.getCalculator();
		if(cal != null) {
			cr = (ColumnRow)cal.getAttribute(ColumnRow.class);
		}
		
		return cr != null ? new Integer(cr.getRow() + 1) : new Integer(0);
    }
	public Type getType() {
		return REPORT;
	}	public String getCN(){
		return "ROW()���ص�ǰ��Ԫ����кţ�����ʹ��������������\n"
		+"ʾ��:\n"
		+"�����ǰ��Ԫ��ΪA5����A5��д��\"=ROW()\"�򷵻�5��\n"
		+"�����ǰ��Ԫ��ΪB8����B8��д��\"=ROW()\"�򷵻�8��";
	}
	public String getEN(){
		return "";
	}
}