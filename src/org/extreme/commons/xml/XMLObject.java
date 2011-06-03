package org.extreme.commons.xml;

public abstract class XMLObject implements XMLReadable {

	protected Object obj;
	
	public XMLObject(Object obj) {
		this.obj = obj;
	}
	
	public Object getObject() {
		return this.obj;
	}
}
