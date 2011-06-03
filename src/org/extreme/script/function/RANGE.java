package org.extreme.script.function;

import java.util.Date;
import java.util.logging.Level;

import org.extreme.commons.collections.IntList;
import org.extreme.commons.util.DateUtils;
import org.extreme.commons.util.LogUtil;
import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class RANGE extends AbstractFunction {
	/**
     * range(4) returns [0, 1, 2, 3]
     * range(-1) returns []
     * range(1, 4) returns [1, 2, 3]
     * range(4, 1) returns []
     * range(-1, 4, 2) returns [-1, 1, 3]
     * range(4, 1, -1) returns [4, 3, 2]
     */
	public Object run(Object[] args) {
		if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }	
		if (args[0] instanceof Date) {
			return rangeDate(args);
		} else {
			return rangeNumber(args);
		}
	}
	
	private Object rangeNumber(Object[] args) {
		int from = 1;
		int to = 1;
		int step = 1;
		
		try {
			if (args.length == 1) {
				to = Utils.string2Number(args[0].toString()).intValue() + 1;
			} else if (args.length >= 2) {
				if (args.length > 2) {
					step = Utils.string2Number(args[2].toString()).intValue();
				}
				
				from = Utils.string2Number(args[0].toString()).intValue();
				to = Utils.string2Number(args[1].toString()).intValue() + (step > 0 ? 1 : -1);
			}
		} catch (NumberFormatException e) {
			return Primitive.ERROR_VALUE;
		}
		
		if (step == 0) {
			// step�ǲ�����Ϊ0��,�������û��û��
			return Primitive.ERROR_VALUE;
		}

		int[] array = IntList.range(from, to, step);
		Integer[] int_array = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			int_array[i] = new Integer(array[i]);
		}

		return new FArray(int_array);
	}
	
	private Object rangeDate(Object[] args) {
		if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
		
		Date from = DateUtils.createDate(2009,10,23);
		Date to = DateUtils.createDate(2009,10,23);
		int step = 1;
		
		try {
			if (args.length == 1) {
				Date[] dateArray = new Date[1];
				dateArray[0] = (Date)args[0];
				return new FArray(dateArray);
			} else if (args.length >= 2) {
				if (args.length > 2) {
					step = Utils.string2Number(args[2].toString()).intValue();
				}
				
				from = (Date)args[0];
				to = (Date)args[1];
			}
		} catch (NumberFormatException e) {
			LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
			return Primitive.ERROR_VALUE;
		}
		
		if (step == 0) {
			// :step�ǲ�����Ϊ0��,�������û��û��
			return Primitive.ERROR_VALUE;
		}
		
		int delta = (int) DateUtils.subtractDate(to, from, "d");
		int[] times = IntList.range(0, delta + (step > 0 ? 1 : -1), step);
		Date[] dates = new Date[times.length];
		for (int i = 0; i < times.length; i++) {
			dates[i] = DateUtils.datePlusInteger(from, times[i]);
		}
		return new FArray(dates);
	}

	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "RANGE(from��to��step)������ʾ������from��ʼ����stepΪÿһ���Ĵ�С��ֱ������to��һ���������С�\n"
		+"���磺\n"
		+"RANGE(1,5,1)��ʾ��1��ʼ��ֱ��5(����5)��ÿһ����СΪ1����ô������һ����������Ϊ[1,2,3,4,5]��\n"
		+"RANGE(-1,6,2)��ʾ��-1��ʼ��ֱ��6(����6)��ÿһ����СΪ2����ô������һ����������Ϊ[-1,1,3,5]��\n"
		+"��ע��RANGE���������ֲ�����ʽ��\n"
		+"1 RANGE(to)��Ĭ�ϵ�fromΪ1��stepΪ1�����磺\n"
		+"  RANGE(4)����[1,2,3,4]��\n"
		+"  RANGE(-5)����[]��\n"
		+"2 RANGE(from,to)��Ĭ�ϵ�stepΪ1������:\n"
		+"  RANGE(-1,3)����[-1,0,1,2,3]��\n"
		+"  RANGE(0,5)����[0,1,2,3,4,5]��\n"
		+"3 RANGE(from,to,step)������������������������ע�ͣ����磺\n"
		+"  RANGE(6,-1,-2)����[6,4,2,0]��\n"
		+"  RANGE(4,1,1)����[]��";
	}
	public String getEN(){
		return "RANGE(from,to,step)returns a serial of numbers from \"from\" to \"to\" and the step is \"step\".For example:\n"
		+"RANGE(1,5,1)from 1,to 5,and step is1,it returns[1,2,3,4,5].\n"
		+"RANGE(-1,6,2)from -1,to 6, and step is 2,it returns[-1,1,3,5].\n"
		+"\n"
		+"BTW:RANGE function has three parameter formats:\n"
		+"1 RANGE(to) the default from is 0,step is 1, e.g:\n"
		+"  RANGE(4)returns[1,2,3,4].\n"
		+"  RANGE(-5)returns[]\n"
		+"2 RANGE(from,to) the default step is 1, e.g:\n"
		+"  RANGE(-1,3)returns[-1,0,1,2,3].\n"
		+"  RANGE(0,5)returns[0,1,2,3,4,5].\n"
		+"3 RANGE(from,to,step), e.g:\n"
		+"  RANGE(6,-1,-2)returns[6,4,2,0].\n"
		+"  RANGE(4,1,1)returns[].";
	}
}