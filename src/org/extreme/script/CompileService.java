package org.extreme.script;

import java.io.PrintWriter;

import org.extreme.script.parser.ParseUtils;
import org.extreme.script.parser.UtilEvalError;



public class CompileService {
	private static CompileService compileService = new CompileService();
	
	private CustomCompiler customCompiler;
	
	private static java.util.Map /*<String, Expr>*/cache = new java.util.HashMap();
	
	private static java.util.Map /*<String, Expr>*/ micro_cache = new java.util.HashMap();
	
	private static int index = 100;
	
	private static int micro_index = 100;
	
	private CompileService() {
		customCompiler = new CustomCompiler(
				new PrintWriter(System.out), 
				new PrintWriter(System.err), 
				false /* systemExit */, 
				null /* options */, 
				null
				);
	}
	
	public static CompileService getInstance() {
		return compileService;
	}
	
	public Object eval(Calculator c, String formula) {
		Expr expr = compileExpr(formula);
		
		if (expr != null) {
			try {
				return expr.eval(c);
			} catch (UtilEvalError e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public Expr compileExpr(String formula) {
		Expr expr = null;
		if (cache.containsKey(formula)) {
			expr = (Expr)cache.get(formula);
		}
		
		if (expr == null) {
			customCompiler.pakageSourceMap.put("jdt/Expression" + index + ".java", ParseUtils.getParsedExpressionJavaSource(formula, "" + index));
			customCompiler.compile(org.eclipse.jdt.internal.compiler.batch.Main.tokenize("-warn:none"));
			
			try {
				Class clazz = customCompiler.classLoader.loadClass("jdt.Expression" + index);
				expr = (Expr)clazz.newInstance();
				
				cache.put(formula, expr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			customCompiler.pakageSourceMap.clear();
			
			index ++;
		}
		
		return expr;
	}
	
	public Micro compileMicro(String micro_str) {
		Micro micro_o = null;
		if (micro_cache.containsKey(micro_str)) {
			micro_o = (Micro)micro_cache.get(micro_str);
		}
		
		if (micro_o == null) {
			customCompiler.pakageSourceMap.put("jdt/Micro" + micro_index + ".java", ParseUtils.getMicroJavaSource(micro_str, "" + micro_index));
			customCompiler.compile(org.eclipse.jdt.internal.compiler.batch.Main.tokenize("-warn:none"));
			
			try {
				Class clazz = customCompiler.classLoader.loadClass("jdt.Micro" + micro_index);
				micro_o = (Micro)clazz.newInstance();
				
				micro_cache.put(micro_str, micro_o);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			customCompiler.pakageSourceMap.clear();
			
			micro_index ++;
		}
		
		return micro_o;
	}
}
