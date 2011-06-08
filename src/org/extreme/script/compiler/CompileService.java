package org.extreme.script.compiler;

import java.io.PrintWriter;

import org.extreme.script.Calculator;
import org.extreme.script.Expr;
import org.extreme.script.Macro;
import org.extreme.script.parser.ParseUtils;
import org.extreme.script.parser.UtilEvalError;



public class CompileService {
	private static CompileService compileService = new CompileService();
	
	private CustomCompiler customCompiler;
	
	private static java.util.Map /*<String, Expr>*/cache = new java.util.HashMap();
	
	private static java.util.Map /*<String, Expr>*/ micro_cache = new java.util.HashMap();
	
	private static int index = 100;
	
	private static int macro_index = 100;
	
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
			customCompiler.pakageSourceMap.put("org/extreme/scirpt/Expression" + index + ".java", ParseUtils.getParsedExpressionJavaSource(formula, "" + index));
			customCompiler.compile(org.eclipse.jdt.internal.compiler.batch.Main.tokenize("-warn:none"));
			
			try {
				Class clazz = customCompiler.classLoader.loadClass("org.extreme.script.Expression" + index);
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
	
	public Macro compileMacro(String macro_str) {
		Macro micro_o = null;
		if (micro_cache.containsKey(macro_str)) {
			micro_o = (Macro)micro_cache.get(macro_str);
		}
		
		if (micro_o == null) {
			customCompiler.pakageSourceMap.put("org/extreme/script/Macro" + macro_index + ".java", ParseUtils.getMacroJavaSource(macro_str, "" + macro_index));
			customCompiler.compile(org.eclipse.jdt.internal.compiler.batch.Main.tokenize("-warn:none"));
			
			try {
				Class clazz = customCompiler.classLoader.loadClass("org.extreme.script.Macro" + macro_index);
				micro_o = (Macro)clazz.newInstance();
				
				micro_cache.put(macro_str, micro_o);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			customCompiler.pakageSourceMap.clear();
			
			macro_index ++;
		}
		
		return micro_o;
	}
}
