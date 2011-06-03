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
		
		// :位运算一律转换为long计算
		long i1 = Utils.objectToNumber(args[0], false).longValue();
		long i2 = Utils.objectToNumber(args[1], false).longValue();

		if("&".equals(op)) {
			return convert(i1 & i2);
		} else if("|".equals(op)) {
			return convert(i1 | i2);
		} else if("^".equals(op)) {
			return convert(i1 ^ i2);
		} else if("<<".equals(op)) {
			// bug0002213 可能会超位，这里直接转为long型运算
			return convert(i1 << i2);
		} else if(">>".equals(op)) {
			return convert(i1 >> i2);
		}else if(">>>".equals(op)) {
            return convert(i1 >>> i2);
		} else if("^~".equals(op)) {
			//同或运算Y = A ^~ B = ~(A ^ B)
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
		return "BITOPERATIOIN(int,int,op) 位运算，返回两个整数根据op进行位运算后的结果。\n"
		+"int:十进制整数。\n"
		+"op:位运算操作符，支持\"&\"(与),\"|\"(或),\"^\"(异或),\"<<\"(左移),\">>\"(右移)。\n"
		+"示例：\n"
		+"BITOPERATION(4,2,\"&\")表示4与2进行\"与\"运算,结果等于0。\n"
		+"BITOPERATION(4,2,\"|\")表示4与2进行\"或\"运算,结果等于6。\n"
		+"BITOPERATION(4,2,\"^\")表示4与2进行\"异或\"运算,结果等于6。\n"
		+"BITOPERATION(4,2,\"<<\")表示4按位左移2位，结果等于16。\n"
		+"BITOPERATION(4,2,\">>\")表示4按位右移2位，结果等于1。\n"
		+"BITOPERATION(4,1,\"^~\")表示4与2进行\"同或\"运算,结果为-7。";
	}
	public String getEN(){
		return "";
	}
}
