package org.extreme.commons.html;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public abstract class Html implements Serializable, Cloneable {
	public String toHtml() {
		java.io.StringWriter sw = new StringWriter();
		writeHtml(new PrintWriter(sw));
		
		return sw.getBuffer().toString();
	}
	
	public abstract void writeHtml(PrintWriter writer);
	
	public String toString() {
		return toHtml();
	}
}
