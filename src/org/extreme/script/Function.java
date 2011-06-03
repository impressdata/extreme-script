/*
 * Apache Licence2.0
 */
package org.extreme.script;

import org.extreme.script.parser.UtilEvalError;


/**
 * Function interface.
 */
public interface Function extends java.io.Serializable {
    /**
     * Gets Calculator
     */
    public Calculator getCalculator();

    /**
     * Sets Calculator
     */
    public void setCalculator(Calculator calculator);
    
    /**
     * The Chinese description of the function
     */
    public String getCN();
    
    /**
     * The English description of the function
     */
    public String getEN();
    
    public Function.Type getType();
    
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object evpression(Object[] args) throws UtilEvalError;
    
    /*
     * :加这个类的目的有两个
     * 1.在FormulaPane里面,可以自动分类,新加一个函数,不需要在FormulaPane里面加一下归个类
     * 2.是更重要的原因,以后可能要加分页函数,数据集函数,不同的函数计算的时间,计算的方法可能不同,以此分类之
     */
    public static final class Type {
    	// :不允许在Function这个接口外面新建Type
    	private Type() {};
    }
    
    public static final Type MATH = new Type();
    public static final Type TEXT = new Type();
    public static final Type DATETIME = new Type();
    public static final Type LOGIC = new Type();
    public static final Type ARRAY = new Type();
    public static final Type REPORT = new Type();
    public static final Type OTHER = new Type();
}
