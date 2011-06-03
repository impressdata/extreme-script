package jdt;

import com.fr.report.script.Calculator;

public class X implements Expr {
	
	public Object eval(Calculator c) {
		return 2 + 3 * 4;
	}

}
