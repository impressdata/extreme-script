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
		return "COL()返回当前单元格的列号，必须使用于条件属性中\n"
		+"示例:\n"
		+"如果当前单元格是A5，在A5中写入\"=col()\"则返回1。\n"
		+"如果当前单元格是C6，在C6中写入\"=col()\"则返回3。";
	}
	public String getEN(){
		return "";
	}
}