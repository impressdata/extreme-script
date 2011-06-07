package org.extreme.script;

import junit.framework.TestCase;

public class CompileServiceTest extends TestCase {
	CompileService compileService;
	Calculator c;
	
	public void setUp() {
		compileService = CompileService.getInstance();
		c = Calculator.createCalculator();
		
		java.util.Map varMap = new java.util.HashMap();
		varMap.put("a", new Integer(2));
		varMap.put("b", new Integer(4));
		varMap.put("c", new Integer(8));
		
		varMap.put("a1", "A1");
		varMap.put("b1", "B1");
		
		c.pushNameSpace(ParameterMapNameSpace.create(varMap));
	}

	public void testEvalMulti0() {
		assertEquals(new Integer("48"), compileService.eval(c, "(a + b) * c"));
	}
	
	public void testEvalMulti() {		
		assertEquals(new Integer("24"), compileService.eval(c, "(1 + 2) * c"));
	}
	
	// Test Lazy??? LET 和 IF
	/*
	 * 遇到LET或者IF的时候，特殊处理?
	 */
	public void testEvalLet() {
		assertEquals("(2+4)", compileService.eval(c, "let(a, 5, b, 4, a+b)") + "");
	}
	
	public void testEvalIf() {
		assertEquals("-2", compileService.eval(c, "IF(false, (a+b), a-b)") + "");
	}
	
	public void testEvalAdd() {
		assertEquals("A1B1", compileService.eval(c, "a1+b1") + "");
	}
	
	public void testEvalAbs() {
		assertEquals(new Integer("6"), compileService.eval(c, "abs(a+b)"));
	}
	
	public void testEvalClosure() {
		assertEquals(new Integer("27"), compileService.eval(c, "(1+2) * (4+ 5)"));
	}
	
	public void testIfPerformance10000() {
		for (int i = 0; i < 10000; i ++) {
			testEvalIf();
		}
	}
}
