package org.extreme.script.compiler;
import java.io.PrintWriter;

import org.eclipse.jdt.core.compiler.batch.BatchCompiler;


public class JDTCompilerMain {

	/**
	 * @param args
	 * http://dev.eclipse.org/viewcvs/viewvc.cgi/jdt-core-home/howto/batch%20compile/batchCompile.html?view=co&revision=1.4
	 * http://help.eclipse.org/helios/index.jsp?topic=/org.eclipse.jdt.doc.isv/guide/jdt_api_compile.htm
	 * http://help.eclipse.org/help33/index.jsp?topic=/org.eclipse.jdt.doc.isv/reference/api/org/eclipse/jdt/core/compiler/package-use.html
	 */
	public static void main(String[] args) {
		boolean success = BatchCompiler.compile(
				"E:\\sources\\X.java -d E:\\output", 
				new PrintWriter(System.out),
				new PrintWriter(System.err), 
				null);
		
		System.out.println(success ? "success" : "fail");
	}

}
