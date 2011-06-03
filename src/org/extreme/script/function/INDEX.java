package org.extreme.script.function;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class INDEX extends AbstractFunction {

	public Object run(Object[] args) {
		if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
		
		Object key = args[0];
		int idx = 1;
		for(int i = 1; i < args.length; i++) {
			if(ComparatorUtils.equals(key, args[i])) {
				break;
			}
			idx++;
		}
		
		return new Integer(idx);
	}

	public Type getType() {
		return OTHER;
	}	public String getCN(){
		return "INDEX(key,val1,val2,...):����key��val1,val2,...����ɵ������е�λ��,���������������򷵻ز����ĸ���.\n"
		+"��ע:\n"
		+"    key��valn��������������\n"
		+"ʾ��:\n"
		+"INDEX(2,2)����1��\n"
		+"INDEX(2,1,2)����2��\n"
		+"INDEX(2,4,5,6)����4��\n"
		+"INDEX(\"b\",\"b\",\"o\",\"y\")����1��";
	}
	public String getEN(){
		return "INDEX(key,val1,val2,...):return the position that key in val1,val2,..., if not exist, return the length of arguments\n"
		+"key and valn can be of any type.\n"
		+"\n"
		+"Examples:\n"
		+"INDEX(2,2) = 1��\n"
		+"INDEX(2,1,2) = 2��\n"
		+"INDEX(2,4,5,6) = 4��\n"
		+"INDEX(\"b\",\"b\",\"o\",\"y\") = 1��";
	}
}