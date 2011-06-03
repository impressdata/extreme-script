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
		return "ROW()返回当前单元格的行号，必须使用于条件属性中\n"
		+"示例:\n"
		+"如果当前单元格为A5，在A5中写入\"=ROW()\"则返回5。\n"
		+"如果当前单元格为B8，在B8中写入\"=ROW()\"则返回8。";
	}
	public String getEN(){
		return "";
	}
}