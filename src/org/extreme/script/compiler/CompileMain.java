package org.extreme.script.compiler;

import java.io.PrintWriter;

import org.extreme.script.Calculator;
import org.extreme.script.Expr;
import org.extreme.script.parser.ParseUtils;




public class CompileMain {
	
	public static void main(String[] args) throws Exception {
		CustomCompiler customCompiler = new CustomCompiler(
				new PrintWriter(System.out), 
				new PrintWriter(System.err), 
				false /* systemExit */, 
				null /* options */, 
				null
				);
		
		customCompiler.pakageSourceMap.put("jdt/Expression.java", ParseUtils.getParsedExpressionJavaSource("(a + b) * c", ""));
		
		customCompiler
//		.compile(org.eclipse.jdt.internal.compiler.batch.Main.tokenize(""));
		.compile(new String[0]);
//		.performCompilation();
		
		try {
			Class clazz = customCompiler.classLoader.loadClass("jdt.Expression");
			Object obj = clazz.newInstance();
			
//			Method method = clazz.getDeclaredMethod("value", new Class[0]);
//			Object value = method.invoke(obj, new Object[]{});
//			
			System.out.println(((Expr)obj).eval(Calculator.createCalculator()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
