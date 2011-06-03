package org.extreme.commons.html;

import java.io.PrintWriter;


public class TextHtml extends Html {
	private String text;
	
	public TextHtml(String text) {
		this.text = text;
	}
	
	public void writeHtml(PrintWriter writer) {
		if (text != null) {
			writer.print(text);
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
