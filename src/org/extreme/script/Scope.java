package org.extreme.script;


/**
 * Name Space.
 */
public interface Scope {
    public Object getVariable(Object var, Calculator calculator);
    
    public Function getMethod(Object var, Calculator calculator);
    
}
