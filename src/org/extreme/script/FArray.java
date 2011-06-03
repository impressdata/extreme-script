/*
 * Apache Licence2.0
 */
package org.extreme.script;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.extreme.commons.util.BaseCoreUtils;


/**
 * The list of CellElement.
 */
public class FArray implements Serializable, Cloneable {
    private List list = new java.util.ArrayList();
    private static final String SEPARATOR = ",";
    public static final String XML_TAG = "FARRAY";
    public static final String XML_VALUE = "FARRAYVALUE";
    
    public FArray() {
    }

    public FArray(Object[] array) {
    	this(java.util.Arrays.asList(array));
    }
    
    public FArray(FArray array) {
    	this(array.list);
    }
    
    public FArray(java.util.Collection collection) {
    	this.list.addAll(collection);
    }
    
    public Object[] asObjects() {
    	return this.list.toArray();
    }

    public Object elementAt(int index) {
        return this.list.get(index);
    }

    public int length() {
        return this.list.size();
    }
    
    public Iterator iterator() {
    	return this.list.iterator();
    }
    
    public FArray add(Object obj) {
    	if(obj instanceof FArray) {
    		this.list.addAll(((FArray)obj).list);
    	} else {
    		this.list.add(obj);
    	}
    	
    	return this;
    }
    
    public List toList() {
    	return java.util.Collections.unmodifiableList(this.list);
    }
    
    public String toString() {
        return BaseCoreUtils.join(this.list, SEPARATOR);
    }
    
    public boolean equals(Object object) {
    	if (!(object instanceof FArray)) {
    		return false;
    	}
    	
    	FArray fArray = (FArray)object;
    	if (this.length() != fArray.length()) {
    		return false;
    	}
    	
    	for (int i = 0; i < this.length(); i ++) {
    		if (!(this.elementAt(i).equals(fArray.elementAt(i)))) {
    			return false;
    		}
    	}
    	
    	return true;
    }
}
