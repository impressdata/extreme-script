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
        
        // :�Ȱ�double���͵�ת�ɾ���ֵ -> ��doubleǿ��ת����longʱ������С�ķ���ת,������������븺����һ�µ����
        double dAbs = Math.abs(d);
        
        /*
         * :��ΪEVEN������ODD���������ھ���ֵ���ķ�����ȡֵ,��doubleǿת��longʱ����С�ķ���
         * �������dAbs��С��λ,����2.5��Ӧ�ñ��3,��2����2
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
