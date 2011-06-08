package org.extreme.script.compiler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.batch.CompilationUnit;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.CompilerStats;
import org.eclipse.jdt.internal.compiler.util.HashtableOfObject;
import org.eclipse.jdt.internal.compiler.util.Util;

public class CustomCompiler extends Main {
	ClassLoaderImpl classLoader = null;
	java.util.Map pakageSourceMap = new java.util.HashMap();

	public CustomCompiler(PrintWriter outWriter, PrintWriter errWriter,
			boolean systemExitWhenFinished, Map customDefaultOptions,
			CompilationProgress compilationProgress) {
		super(outWriter, errWriter, systemExitWhenFinished, customDefaultOptions,
				compilationProgress);
		classLoader = new ClassLoaderImpl(CustomCompiler.class.getClassLoader());
	}
	
	public void configure(String[] argv) {
		ArrayList bootclasspaths = new ArrayList(DEFAULT_SIZE_CLASSPATH);
		String sourcepathClasspathArg = null;
		ArrayList sourcepathClasspaths = new ArrayList(DEFAULT_SIZE_CLASSPATH);
		ArrayList classpaths = new ArrayList(DEFAULT_SIZE_CLASSPATH);
		ArrayList extdirsClasspaths = null;
		ArrayList endorsedDirClasspaths = null;
		
		String customEncoding = null;
		
		// TODO FIXME
		disableWarnings();
		
		if (this.maxRepetition == 0) {
			this.maxRepetition = 1;
		}
		if (this.maxRepetition >= 3 && (this.timing & TIMING_ENABLED) != 0) {
			this.compilerStats = new CompilerStats[this.maxRepetition];
		}
		
		setPaths(bootclasspaths,
				sourcepathClasspathArg,
				sourcepathClasspaths,
				classpaths,
				extdirsClasspaths,
				endorsedDirClasspaths,
				customEncoding);
	}
	
	// 覆盖，不输出成为.class文件
	public void outputClassFiles(CompilationResult unitResult) {
		if (!((unitResult == null) || (unitResult.hasErrors() && !this.proceedOnError))) {
			ClassFile[] classFiles = unitResult.getClassFiles();
			
			for (int i = 0; i < classFiles.length; i ++) {
				ClassFile classFile = classFiles[i];
				char[] filename = classFile.fileName();
//				int length = filename.length;
				
				CharOperation.replace(filename, '/', '.');
				
//				char[] relativeName = new char[length + 6];
//				
//				System.arraycopy(filename, 0, relativeName, 0, length);
//				System.arraycopy(SuffixConstants.SUFFIX_class, 0, relativeName, length, 6);
//				CharOperation.replace(relativeName, '/', File.separatorChar);
//				String relativeStringName = new String(relativeName);
				
				classLoader.add(new String(filename), classFile);
			}
		}
	}
	
	/*
	 *  Build the set of compilation source units
	 */
	public CompilationUnit[] getCompilationUnits() {
		Iterator iterator = pakageSourceMap.entrySet().iterator();
		
		int classCount = pakageSourceMap.size();
		CompilationUnit[] units = new CompilationUnit[classCount];
		
		HashtableOfObject knownFileNames = new HashtableOfObject(classCount);
		String defaultEncoding = (String) this.options.get(CompilerOptions.OPTION_Encoding);
		if (Util.EMPTY_STRING.equals(defaultEncoding))
			defaultEncoding = null;
		String encoding = defaultEncoding;
		
		int i = 0;
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry)iterator.next();
			String filename = (String)entry.getKey();
			String source = (String)entry.getValue();
			
			char[] charName = filename.toCharArray();
			if (knownFileNames.get(charName) != null)
				throw new IllegalArgumentException(this.bind("unit.more", filename)); //$NON-NLS-1$
			knownFileNames.put(charName, charName);

			units[i] = new CompilationUnit(source.toCharArray(), filename, encoding,
					null);
		}
		return units;
	}
}
