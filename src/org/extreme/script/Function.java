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
     * :��������Ŀ��������
     * 1.��FormulaPane����,�����Զ�����,�¼�һ������,����Ҫ��FormulaPane�����һ�¹����
     * 2.�Ǹ���Ҫ��ԭ��,�Ժ����Ҫ�ӷ�ҳ����,���ݼ�����,��ͬ�ĺ��������ʱ��,����ķ������ܲ�ͬ,�Դ˷���֮
     */
    public static final class Type {
    	// :��������Function����ӿ������½�Type
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
