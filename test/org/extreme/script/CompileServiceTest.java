package org.extreme.script;

import org.extreme.script.compiler.CompileService;

import junit.framework.TestCase;

/*
 * TODO: exString, parseExpressions...的实现
 */
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
		
		c.pushNameSpace(ParameterMapScope.create(varMap));
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
		assertEquals(new Integer("9"), compileService.eval(c, "let(a, 5, b, 4, a+b)"));
	}
	
	public void testEvalLet2() {
		assertEquals(new Integer("11"), compileService.eval(c, "let(a, 5 + a, b, 4, a+b)"));
	}
	
	public void testEvalIf() {
		assertEquals(new Integer("-2"), compileService.eval(c, "IF(false, (a+b), a-b)"));
	}
	
	public void testEvalAdd() {
		assertEquals("A1B1", compileService.eval(c, "a1+b1"));
	}
	
	public void testEvalAbs() {
		assertEquals(new Integer("6"), compileService.eval(c, "abs(a+b)"));
	}
	
	public void testEvalClosure() {
		assertEquals(new Integer("27"), compileService.eval(c, "(1+2) * (4+ 5)"));
	}
	
	public void testEvalMulti0_() {
		assertEquals(new Integer("48"), compileService.eval(c, "(a + b) * c"));
	}
	
	public void testEvalMulti_() {
		assertEquals(new Integer("24"), compileService.eval(c, "(1 + 2) * c"));
	}
	
	// Test Lazy??? LET 和 IF
	/*
	 * 遇到LET或者IF的时候，特殊处理?
	 */
	public void testEvalLet_() {
		assertEquals(new Integer("9"), compileService.eval(c, "let(a, 5, b, 4, a+b)"));
	}
	
	public void testEvalLet2_() {
		assertEquals(new Integer("11"), compileService.eval(c, "let(a, 5 + a, b, 4, a+b)"));
	}
	
	public void testEvalIf_() {
		assertEquals(new Integer("-2"), compileService.eval(c, "IF(false, (a+b), a-b)"));
	}
	
	public void testEvalAdd_() {
		assertEquals("A1B1", compileService.eval(c, "a1+b1"));
	}
	
	public void testEvalAbs_() {
		assertEquals(new Integer("6"), compileService.eval(c, "abs(a+b)"));
	}
	
	public void testEvalClosure_() {
		assertEquals(new Integer("27"), compileService.eval(c, "(1+2) * (4+ 5)"));
	}
	
	public void testIfPerformance10000() {
		for (int i = 0; i < 10000; i ++) {
			testEvalIf();
		}
	}
	
	public void testLetPerformance10000() {
		for (int i = 0; i < 10000; i ++) {
			testEvalLet();
		}
	}
	
	public void testMulti0Performance10000() {
		for (int i = 0; i < 10000; i ++) {
			testEvalMulti0();
		}
	}
}
