package org.extreme.script.parser;



import org.extreme.script.Calculator;
import org.extreme.script.Primitive;


public class Ambiguity extends Tiny {
	private String statement;

	public Ambiguity(String statement) {
		this.statement = statement;
	}
	
	public String getStatement() {
		return this.statement;
	}
	
	public Object eval(Calculator calculator) throws UtilEvalError {
        if (statement == null) {
            throw new UtilEvalError("statement in literal is null");
        }

        Object obj = calculator.resolveVariable(statement);

        if (obj == null) {
        	// 不合理的抛错
        	return Primitive.NULL;
//            throw new UtilEvalError("null field resolved: " + statement);
        }
        return obj;
    }
	
	public String toString() {
		return statement;
	}
}
