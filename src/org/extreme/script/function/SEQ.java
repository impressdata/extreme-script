package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Calculator;

/*
 * seq(1)
 */
public class SEQ extends AbstractFunction {
	private static final Object SEQMAP = new Object();
	private static final Object NULL = new Object();
	
	public Object run(Object[] args) {
		Object key = null;
		if (args.length > 0) {
			key = args[0];
		}
		// :��֤key��Ϊnull;
		if (key == null) {
			key = NULL;
		}
		
		// :ȡ��������Calculator�е�MARKERMAP
		Calculator c = this.getCalculator();
		Object o = c.getAttribute(SEQMAP);
		java.util.Map map;
		if (o instanceof java.util.Map) {
			map = (java.util.Map)o;
		} else {
			map = new java.util.HashMap();
			c.setAttribute(SEQMAP, map);
		}
		
		Integer ret;
		// :ͬ��һ��,������map����Ĳ���̫������
		synchronized (SEQMAP) {
			/*
			 * :ȡ��MARKERMAP�б����key��Ӧ��ֵ
			 * +1����
			 * ����map��key
			 */
			Object v = map.get(key);
			if (v instanceof Integer) {
				ret = new Integer(((Integer)v).intValue() + 1);
			} else {
				ret = new Integer(1);
			}
			map.put(key, ret);
		}
		
		return ret;
	}
	public Type getType() {
		return OTHER;
	}	public String getCN(){
		return "SEQ(): ������ֵ������������ִ�й����У����ظú������ڼ���ִ���ˡ�\n"
		+"ʾ��:\n"
		+"SEQ()�ڵ�һ��ִ��ʱ�����Ϊ1��\n"
		+"SEQ()�ڵڶ���ִ��ʱ�����Ϊ2��";
	}
	public String getEN(){
		return "SEQ(): Returns a value of number which means the times this function is used during the report execution.\n"
		+"Example:\n"
		+"SEQ() returns 1 when used first time.\n"
		+"SEQ() returns 2 when used second time.";
	}
}