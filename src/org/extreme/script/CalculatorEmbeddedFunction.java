/*
 * Apache Licence2.0
 */
package org.extreme.script;

/**
 * <!CN[一个对Function接口的抽象实现类,一般都是通过继承这个类来实现Function接口.]!>
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
