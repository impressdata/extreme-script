package org.extreme.script.parser;

public class UtilEvalError extends Exception {
	String fcontent = null;
	
	protected UtilEvalError() {
	}

	public UtilEvalError( String s ) {
		super(s);
	}
	
	public void addFormulaContent(String fcontent) {
		this.fcontent = fcontent;
	}
	
	public String getMessage() {
		return super.getMessage() + " in formula : " + fcontent;
	}
	
	public UtilEvalError( Throwable t) {
		super(t);
	}
}
