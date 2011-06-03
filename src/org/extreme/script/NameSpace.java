package org.extreme.script;

import org.extreme.commons.ColumnRow;

/**
 * Name Space.
 */
public interface NameSpace {
    public Object getVariable(Object var, Calculator calculator);
    
    public Function getMethod(Object var, Calculator calculator);
    
    public Object getRelateCellElements(ColumnRow columnrow, Calculator calculator);
}
