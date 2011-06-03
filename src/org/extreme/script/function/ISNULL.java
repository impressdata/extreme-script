package org.extreme.script.function;

import org.extreme.commons.util.StringUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/*
 * 用于判断对象中所有中的值是否全部都是Primitive.NULL.如：要判断A1扩展出来的数据是不是全部是Primitive.NULL
 */
public class ISNULL extends AbstractFunction {

	public Object run(Object[] args) {
        if (args.length < 1) {
            return Boolean.TRUE;
        }

        Boolean result = Boolean.TRUE;

        Object param = null;
        for(int i = 0; i < args.length; i++) {
			param = args[i];
			if (!(Primitive.NULL == param || (param instanceof String && StringUtils.isEmpty((String)param)))) {
				result = Boolean.FALSE;
				break;
			}
		}
		return result;
	}
	public Type getType() {
		return OTHER;
	}	public String getCN(){
		return "ISNULL(object):判断对象中所有的值是否全部都是NULL或者为空字符串。";
	}
	public String getEN(){
		return "";
	}
}