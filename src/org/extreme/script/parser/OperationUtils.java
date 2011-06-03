package org.extreme.script.parser;

import java.math.BigDecimal;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.commons.util.StringUtils;
import org.extreme.script.Primitive;


public class OperationUtils {
    // Richie:分别表示正负无穷大
	public static final Double POSITIVE_INFINITY = new Double(Double.POSITIVE_INFINITY);

	public static final Double NEGATIVE_INFINITY = new Double(Double.NEGATIVE_INFINITY);

	private OperationUtils() {
    }

    private static final String BOOL = "!";
    
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    
    public static final Integer ZERO = new Integer(0);
	public static final BigDecimal BZERO = new BigDecimal(0);
    
    /*
     * 对象是否false类型
     */
    public static boolean isFalse(Object obj) {
    	return obj == null || obj == Primitive.NULL || 
    	ComparatorUtils.equals(obj, Boolean.FALSE) ||
    	ComparatorUtils.equals(obj, new Integer(0)) ||
    	ComparatorUtils.equals(obj, StringUtils.EMPTY);
    }
    
    static boolean booleanUnaryOperation(Boolean B, String kind)
            throws UtilEvalError {
        boolean operand = B.booleanValue();
        
        if(BOOL.equals(kind)) {
            return !operand;
        } else {
            throw new UtilEvalError("Operator inappropriate for boolean:\n" + kind + B);
        }
    }

    static int intUnaryOperation(Integer I, String kind) {
        int operand = I.intValue();

        if(PLUS.equals(kind)) {
            return operand;
        } else if (MINUS.equals(kind)) {
            return -operand;
        } else {
            throw new InterpreterError("bad integer unaryOperation:\n" + kind + I);
        }
    }

    static long longUnaryOperation(Long L, String kind) {
        long operand = L.longValue();

        if(PLUS.equals(kind)) {
            return operand;
        } else if (MINUS.equals(kind)) {
            return -operand;
        } else {
            throw new InterpreterError("bad long unaryOperation:\n" + kind + L);
        }
    }

    static float floatUnaryOperation(Float F, String kind) {
        float operand = F.floatValue();

        if(PLUS.equals(kind)) {
            return operand;
        } else if (MINUS.equals(kind)) {
            return -operand;
        } else {
            throw new InterpreterError("bad float unaryOperation:\n" + kind + F);
        }
    }

    static double doubleUnaryOperation(Double D, String kind) {
        double operand = D.doubleValue();

        if(PLUS.equals(kind)) {
            return operand;
        } else if (MINUS.equals(kind)) {
            return -operand;
        } else {
            throw new InterpreterError("bad double unaryOperation:\n" + kind + D);
        }
    }
    
    static BigDecimal bigDecimalUnaryOperation(BigDecimal B, String kind) {
    	if(PLUS.equals(kind)) {
    		return B;
    	} else if(MINUS.equals(kind)) {
    		return B.negate();
    	} else {
    		throw new InterpreterError("bad bigDecimal unaryOperation:\n" + kind + B);
    	}
    }
    
    /*
     * Promote primitive wrapper type to to Integer wrapper type
     */
    static Object promoteCharByteShort(Object wrapper) {
        if (wrapper instanceof Character)
            return wrapper.toString();
        else if ((wrapper instanceof Byte) || (wrapper instanceof Short))
            return new Integer(((Number) wrapper).intValue());

        return wrapper;
    }
}
