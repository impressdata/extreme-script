package org.extreme.script.function;

import org.extreme.commons.ColumnRow;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Calculator;


public class COL extends AbstractFunction {

	public Object run(Object[] args) {
		ColumnRow cr = null;
		Calculator cal = this.getCalculator();
		if(cal != null) {
			cr = (ColumnRow)cal.getAttribute(ColumnRow.class);
		}
		
		return cr != null ? new Integer(cr.column + 1) : new Integer(0);
    }
	public Type getType() {
		return REPORT;
	}	public String getCN(){
		return "COL()���ص�ǰ��Ԫ����кţ�����ʹ��������������\n"
		+"ʾ��:\n"
		+"�����ǰ��Ԫ����A5����A5��д��\"=col()\"�򷵻�1��\n"
		+"�����ǰ��Ԫ����C6����C6��д��\"=col()\"�򷵻�3��";
	}
	public String getEN(){
		return "";
	}
}