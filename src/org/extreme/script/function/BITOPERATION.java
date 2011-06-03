package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class BITOPERATION extends AbstractFunction {
	public Object run(Object[] args) {
		if(args.length < 3 || args.length > 3) {
			return Primitive.ERROR_NAME;
		}
		Object op = args[2];
		
		// :λ����һ��ת��Ϊlong����
		long i1 = Utils.objectToNumber(args[0], false).longValue();
		long i2 = Utils.objectToNumber(args[1], false).longValue();

		if("&".equals(op)) {
			return convert(i1 & i2);
		} else if("|".equals(op)) {
			return convert(i1 | i2);
		} else if("^".equals(op)) {
			return convert(i1 ^ i2);
		} else if("<<".equals(op)) {
			// bug0002213 ���ܻᳬλ������ֱ��תΪlong������
			return convert(i1 << i2);
		} else if(">>".equals(op)) {
			return convert(i1 >> i2);
		}else if(">>>".equals(op)) {
            return convert(i1 >>> i2);
		} else if("^~".equals(op)) {
			//ͬ������Y = A ^~ B = ~(A ^ B)
			return convert(~(i1 ^ i2));
		} else {
			return Primitive.ERROR_NAME;
		}
	}
	
	private Object convert(long l) {
		if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
			return new Long(l);
		} else {
			return new Integer((int)l);
		}
	}
	public Type getType() {
		return LOGIC;
	}
	public String getCN(){
		return "BITOPERATIOIN(int,int,op) λ���㣬����������������op����λ�����Ľ����\n"
		+"int:ʮ����������\n"
		+"op:λ�����������֧��\"&\"(��),\"|\"(��),\"^\"(���),\"<<\"(����),\">>\"(����)��\n"
		+"ʾ����\n"
		+"BITOPERATION(4,2,\"&\")��ʾ4��2����\"��\"����,�������0��\n"
		+"BITOPERATION(4,2,\"|\")��ʾ4��2����\"��\"����,�������6��\n"
		+"BITOPERATION(4,2,\"^\")��ʾ4��2����\"���\"����,�������6��\n"
		+"BITOPERATION(4,2,\"<<\")��ʾ4��λ����2λ���������16��\n"
		+"BITOPERATION(4,2,\">>\")��ʾ4��λ����2λ���������1��\n"
		+"BITOPERATION(4,1,\"^~\")��ʾ4��2����\"ͬ��\"����,���Ϊ-7��";
	}
	public String getEN(){
		return "";
	}
}
