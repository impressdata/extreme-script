package org.extreme.script.repl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.extreme.script.Calculator;
import org.extreme.script.compiler.CompileService;

public class REPL {

	/**
	 * @param args
	 * TODO: Ö§³Ö¸³Öµ
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Extreme REPL:");
		System.out.println("Print 'exit' to exit.");
		System.out.println("==> ");
		
		CompileService compileService = CompileService.getInstance();
		Calculator c = Calculator.createCalculator();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		
		while (!"exit".equals(line)) {
			Object value = compileService.eval(c, line);
			System.out.println(value);
			line = br.readLine();
		}
	}

}
