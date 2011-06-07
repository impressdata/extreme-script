package org.extreme.script;

import org.extreme.script.parser.AddExpression;
import org.extreme.script.parser.MathExpression;
import org.extreme.script.parser.MultiExpression;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.PowerExpression;
import org.extreme.script.parser.RelationExpression;
import org.extreme.script.parser.UtilEvalError;


public abstract class CoreOperation {
	public static java.util.Map op_map = new java.util.HashMap(10);
	{
		op_map.put(AddExpression.PLUS, new AddExpression());
		op_map.put(AddExpression.MINUS, new AddExpression());
		op_map.put(MultiExpression.MULTI, new MultiExpression());
		op_map.put(MultiExpression.DIV, new MultiExpression());
		op_map.put(MultiExpression.MOD, new MultiExpression());
		op_map.put(PowerExpression.POW, new PowerExpression());
		op_map.put(RelationExpression.EQ, new RelationExpression());
		op_map.put(RelationExpression.GE, new RelationExpression());
		op_map.put(RelationExpression.GT, new RelationExpression());
		op_map.put(RelationExpression.LE, new RelationExpression());
		op_map.put(RelationExpression.LT, new RelationExpression());
		op_map.put(RelationExpression.NE1, new RelationExpression());
		op_map.put(RelationExpression.NE2, new RelationExpression());
	}
	
	/*
	 * (a+b): binaryOperation(new Ambiguity("a"), new Ambiguity("b"), "+")
	 * resolve, resolve, add
	 * int a = c.get("a");
	 * int b = c.get("b");
	 * 
	 */
	public Object binaryOperation(Object left, Object right, String op, Calculator c) throws UtilEvalError {
		
		if (left instanceof Node) {
			left = c.eval((Node)left);
		}
		
		if (right instanceof Node) {
			right = c.eval((Node)right);
		}
		
		return ((MathExpression)op_map.get(op)).objectBinaryOperation(left, right, op);
	}
}
