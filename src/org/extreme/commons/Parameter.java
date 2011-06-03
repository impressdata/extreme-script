package org.extreme.commons;

import java.io.Serializable;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.commons.util.StringUtils;


/**
 * Parameter for report
 */
public class Parameter implements Serializable {
	/**
	 * ParameterдXMLʱ���TAG
	 */
	public static final String XML_TAG = "Parameter";
	public static final String ARRAY_XML_TAG = "Parameters";
	
	/**���������֣��������Ҫ��**/
	protected String name = null;
    /**value��Ҫ֧�ֹ�ʽ������൱����Ĭ��ֵ**/
    protected Object value = null;
    
    /*
     * :���������������ڵ��ò���Ҫ��ʾ�ڱ����Ĭ�ϲ���ҳ��ʱ��һЩ����
     */


    public Parameter() {
    	this(StringUtils.EMPTY);
    }
    
    public Parameter(String name) {
    	this(name, StringUtils.EMPTY);
    }
    
    public Parameter(String name, Object value) {
    	this.setName(name);
    	this.setValue(value);
    }
    
    /**
     * Gets the name of parameter
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of parameter
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets the value of parameter.
     *
     * @return the value of parameter.
     */
    public Object getValue() {
    	return this.value;
    }

    /**
     * Sets the new value of parameter.
     *
     * @param value the new value of parameter.
     */
    public void setValue(Object value) {
    	this.value = value;
    }
    
    public String toString() {
    	return "$" + this.getName();
    }
    
    public int hashCode() {
    	final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		result = prime * result
			+ ((value == null) ? 0 : value.hashCode());
		return result;
    }
    
    public boolean equals(Object obj) {
    	return obj instanceof Parameter && ComparatorUtils.equals(this.name, ((Parameter)obj).name)
    	&& ComparatorUtils.equals(this.value, ((Parameter)obj).value);
    }
}
