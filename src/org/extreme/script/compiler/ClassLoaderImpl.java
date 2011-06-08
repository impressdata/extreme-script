package org.extreme.script.compiler;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.ClassFile;


/**
 * A custom ClassLoader which maps class names to JavaFileObjectImpl instances.
 */
public final class ClassLoaderImpl extends ClassLoader {
	private final Map/*<String, ClassFile>*/ classes = new HashMap();

	ClassLoaderImpl(final ClassLoader parentClassLoader) {
		super(parentClassLoader);
	}

	/**
	 * @return An collection of JavaFileObject instances for the classes in the
	 *         class loader.
	 */
	Collection/*<ClassFile>*/ files() {
		return Collections.unmodifiableCollection(classes.values());
	}

	protected Class findClass(final String qualifiedClassName) throws ClassNotFoundException {
		ClassFile file = (ClassFile)classes.get(qualifiedClassName);
		if (file != null) {
			byte[] bytes = file.getBytes();
			return defineClass(qualifiedClassName, bytes, 0, bytes.length);
		}
		// Workaround for "feature" in Java 6
		// see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6434149
		try {
			Class c = Class.forName(qualifiedClassName);
			return c;
		} catch (ClassNotFoundException nf) {
			// Ignore and fall through
		}
		return super.findClass(qualifiedClassName);
	}

	void add(final String qualifiedClassName, final ClassFile classFile) {
		classes.put(qualifiedClassName, classFile);
	}

	protected synchronized Class loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		return super.loadClass(name, resolve);
	}

	public InputStream getResourceAsStream(final String name) {
		if (name.endsWith(".class")) {
			String qualifiedClassName = name.substring(0, name.length() - ".class".length()).replace('/', '.');
			ClassFile file = (ClassFile) classes.get(qualifiedClassName);
			if (file != null) {
				return new ByteArrayInputStream(file.getBytes());
			}
		}
		return super.getResourceAsStream(name);
	}
}

