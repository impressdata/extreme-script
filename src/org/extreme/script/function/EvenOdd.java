package org.extreme.script.function;

import org.extreme.commons.util.Utils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;


public abstract class EvenOdd extends AbstractFunction {
	public Object run(Object[] args) {
        if (args.length < 1) {
            return Primitive.ERROR_NAME;
        }
        
        Number num = Utils.objectToNumber(args[0], false);
        double d = num.doubleValue();
        
        // :先把double类型的转成绝对值 -> 把double强制转换成long时都是往小的方向转,不会出现正数与负数不一致的情况
        double dAbs = Math.abs(d);
        
        /*
         * :因为EVEN函数和ODD函数都是在绝对值变大的方向上取值,而double强转成long时是往小的方向
         * 所以如果dAbs有小数位,比如2.5就应该变成3,而2还是2
         */
        long lAbs = (long)dAbs;
        if (dAbs > lAbs) {
        	lAbs++;
        }
        
        while (findMatch(lAbs)) {
        	lAbs++;
        }
        
        long lRet = d >= 0 ? lAbs : -lAbs;
        
        return FunctionHelper.asNumber(lRet);
    }
	
	protected abstract boolean findMatch(long l);
}
