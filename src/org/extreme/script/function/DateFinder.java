package org.extreme.script.function;

import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public abstract class DateFinder extends AbstractFunction {
	
	public Object run(Object[] args) {
		if (args.length < 1) {
			return Primitive.ERROR_NAME;
		}
		
		Date scaleDate = null; // 基准日期
		int delta = 0; // 偏移
		
		// 如果只有一个参数且类型为数值
		if (args.length == 1 && args[0] instanceof Number) {
			scaleDate = new Date();
			delta = ((Number)args[0]).intValue();
		} 
		// 如果有两个参数且第二个参数类型为数值
		else if (args.length >= 2 && args[1] instanceof Number) {
			delta = ((Number)args[1]).intValue();
			
			// 如果第一个参数是日期型
			if (args[0] instanceof Date) {
				scaleDate = (Date)args[0];
			} 
			// 如果第一个参数是字符串
			else if (args[0] instanceof String) {
				// 把字符串转换成日期型的
				scaleDate = DateUtils.object2Date((String)args[0], false);
			}
			// 如果第一个参数是数值,就当成是年份吧(因为在DATEINYEAR那边这么支持)
			else if (args[0] instanceof Number) {
				scaleDate = new Date(((Number)args[0]).intValue() - 1900, 0, 1);
			}
		}
		
		// 如果经过上面的处理,scaleDate还是null,就取当天
		if (scaleDate == null) {
			scaleDate = new Date();
		}
		
		// 如果delta是0,就返回scaleDate
		if (delta == 0) {
			return scaleDate;
		}
		
		return findDate(scaleDate, delta);
	}
	
	protected abstract Date findDate(Date scaleDate, int delta);

}
