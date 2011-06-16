package org.extreme.script.parser;

import java.math.BigDecimal;

import org.extreme.script.Calculator;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class UnaryExpression implements Node {
	String op;
	Node atom;
	
	UnaryExpression(String op, Node atom) {
		this.op = op;
		this.atom = atom;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
		if (atom == null) {
			throw new InterpreterError("atom should not be null");
		}

		Object result = atom.eval(calculator);
		if (op != null) {
			result = primitiveWrapperUnaryOperation(result, op);
		}

		return result;
	}

    private Object primitiveWrapperUnaryOperation(Object val, String op) throws UtilEvalError {
    	if (val == Primitive.ERROR_NAME || val == Primitive.ERROR_NAME) {
			return Primitive.ERROR_NAME;
		}
    	
    	if(val instanceof FArray) {
    		FArray array = (FArray)val;
    		Object[] res_array = new Object[array.length()];
    		for(int i = 0; i < res_array.length; i++) {
    			res_array[i] = primitiveWrapperUnaryOperation(array.elementAt(i), op);
    		}
    		
    		return new FArray(res_array);
    	}
    	
		// : Byte&Short -> Integer;Character -> String
		Object operand = OperationUtils.promoteCharByteShort(val);

		if (operand instanceof Boolean)
			return new Boolean(OperationUtils.booleanUnaryOperation((Boolean) operand, op));
		else if (operand instanceof Integer) 
			return new Integer(OperationUtils.intUnaryOperation((Integer) operand, op));
		else if (operand instanceof Long)
			return new Long(OperationUtils.longUnaryOperation((Long) operand,op));
		else if (operand instanceof Float)
			return new Float(OperationUtils.floatUnaryOperation((Float) operand, op));
		else if (operand instanceof Double)
			return new Double(OperationUtils.doubleUnaryOperation((Double) operand, op));
		else if (operand instanceof BigDecimal) 
			return OperationUtils.bigDecimalUnaryOperation((BigDecimal)operand, op);
		else
			throw new InterpreterError("error exists: " + op + atom);
    }
	
	public String toString() {
		return (op != null ? op : "") + atom.toString(); 
	}
}
