package org.extreme.script.function;

import org.extreme.commons.util.StringUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/*
 * �����ж϶����������е�ֵ�Ƿ�ȫ������Primitive.NULL.�磺Ҫ�ж�A1��չ�����������ǲ���ȫ����Primitive.NULL
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
		return "ISNULL(object):�ж϶��������е�ֵ�Ƿ�ȫ������NULL����Ϊ���ַ�����";
	}
	public String getEN(){
		return "";
	}
}