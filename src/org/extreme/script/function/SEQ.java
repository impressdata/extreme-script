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
		// :保证key不为null;
		if (key == null) {
			key = NULL;
		}
		
		// :取出保存于Calculator中的MARKERMAP
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
		// :同步一下,免得这个map里面的操作太混乱了
		synchronized (SEQMAP) {
			/*
			 * :取出MARKERMAP中保存的key对应的值
			 * +1返回
			 * 重设map的key
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
		return "SEQ(): 返回数值，在整个报表执行过程中，返回该函数被第几次执行了。\n"
		+"示例:\n"
		+"SEQ()在第一次执行时，结果为1。\n"
		+"SEQ()在第二次执行时，结果为2。";
	}
	public String getEN(){
		return "SEQ(): Returns a value of number which means the times this function is used during the report execution.\n"
		+"Example:\n"
		+"SEQ() returns 1 when used first time.\n"
		+"SEQ() returns 2 when used second time.";
	}
}