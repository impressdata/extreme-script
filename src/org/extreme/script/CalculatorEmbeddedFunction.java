/*
 * Apache Licence2.0
 */
package org.extreme.script;

/**
 * <!CN[һ����Function�ӿڵĳ���ʵ����,һ�㶼��ͨ���̳��������ʵ��Function�ӿ�.]!>
 * <p/>
 * <!EN[The abstract implementation of Function interface.]!>
 */
public abstract class CalculatorEmbeddedFunction implements Function {
    private transient Calculator calculator = null;

    public Calculator getCalculator() {
        return this.calculator;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }
    
    public Type getType() {
    	return OTHER;
    }
}
