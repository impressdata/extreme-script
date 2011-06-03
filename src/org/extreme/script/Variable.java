package org.extreme.script;

import java.io.Serializable;

public class Variable implements Serializable, Cloneable {
    Object value;

    //TODO :这个Variable,没看出有多大的意义,以后加这些类时要写清注释啊!
    //a:好像是在分组报表的时候,比如分组眉的汇总值,会不断地变,那么汇总值以Variable保存,在变的时候就改变Variable.value,这样其引用就不会变了
    public Variable(Object value) {
        setValue(value);
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        if(value == null) {
            this.value = Primitive.NULL;
        } else {
            this.value = value;
        }
    }
    
    public String toString() {
        return value.toString();
    }
}
