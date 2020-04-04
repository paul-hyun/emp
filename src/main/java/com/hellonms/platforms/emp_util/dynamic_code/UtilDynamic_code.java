/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.dynamic_code;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_util.file.UtilFile;

/**
 * <p>
 * 동적 code loading
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 15.
 * @modified 2016. 1. 15.
 * @author cchyun
 *
 */
public class UtilDynamic_code {

	private static class MemoryJavaSrc extends SimpleJavaFileObject {

		private String src;

		public MemoryJavaSrc(String name, String src) {
			super(URI.create("file:///" + name + ".java"), Kind.SOURCE);
			this.src = src;
		}

		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return src;
		}

		public OutputStream openOutputStream() {
			throw new IllegalStateException();
		}

		public InputStream openInputStream() {
			return new ByteArrayInputStream(src.getBytes());
		}

	}

	private static class MemoryJavaBin extends SimpleJavaFileObject {

		private ByteArrayOutputStream baos;

		public MemoryJavaBin(String name) {
			super(URI.create("byte:///" + name + ".class"), Kind.CLASS);
		}

		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			throw new IllegalStateException();
		}

		public OutputStream openOutputStream() {
			baos = new ByteArrayOutputStream();
			return baos;
		}

		public InputStream openInputStream() {
			throw new IllegalStateException();
		}

		public byte[] getBytes() {
			return baos.toByteArray();
		}
	}

	private static class MemoryJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

		private MemoryClassLoader classLoader;

		public MemoryJavaFileManager(StandardJavaFileManager sjfm, MemoryClassLoader classLoader) {
			super(sjfm);
			this.classLoader = classLoader;
		}

		public JavaFileObject getJavaFileForOutput(Location location, String name, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
			MemoryJavaBin mbc = new MemoryJavaBin(name);
			classLoader.addClass(name, mbc);
			return mbc;
		}

		public ClassLoader getClassLoader(Location location) {
			return classLoader;
		}
	}

	private static class MemoryClassLoader extends ClassLoader {

		private Map<String, MemoryJavaBin> bin_map = new HashMap<String, MemoryJavaBin>();

		protected Class<?> findClass(String name) throws ClassNotFoundException {
			MemoryJavaBin mbc = bin_map.get(name);
			if (mbc == null) {
				mbc = bin_map.get(name.replace(".", "/"));
				if (mbc == null) {
					return UtilDynamic_code.class.getClassLoader().loadClass(name);
				}
			}
			return defineClass(name, mbc.getBytes(), 0, mbc.getBytes().length);
		}

		public void addClass(String name, MemoryJavaBin mbc) {
			bin_map.put(name, mbc);
		}
	}

	public static Class<?> compileClass(File file) throws Exception {
		if (file.getName().endsWith(".java")) {
			String fileName = file.getName();
			String classSource = new String(UtilFile.readFile(file));
			String className = file.getName().substring(0, fileName.length() - 5);
			return compileClass(classSource, className);
		}
		return null;
	}

	public static Class<?> compileClass(String classSource, String className) throws Exception {
		JavaCompiler javac = newJavaCompiler();

		StandardJavaFileManager sjfm = javac.getStandardFileManager(null, null, null);
		MemoryClassLoader classLoader = new MemoryClassLoader();
		MemoryJavaFileManager fileManager = new MemoryJavaFileManager(sjfm, classLoader);

		String path_separator = System.getProperty("path.separator");
		File bin = new File(EmpContext.getEMP_HOME(), "WEB-INF/classes");
		File lib = new File(EmpContext.getEMP_HOME(), "WEB-INF/lib");
		StringBuilder classpath = new StringBuilder();
		if (bin.isDirectory()) {
			classpath.append(bin.getAbsolutePath()).append(path_separator);
		}
		for (File file : lib.listFiles()) {
			if (file.isFile() && file.getName().endsWith(".jar")) {
				classpath.append(file.getAbsolutePath()).append(path_separator);
			}
		}
		List<String> options = new ArrayList<String>();
		options.add("-classpath");
		options.add(classpath.toString());

		List<? extends JavaFileObject> compilationUnits = Arrays.asList(new MemoryJavaSrc(className, classSource));
		DiagnosticListener<? super JavaFileObject> dianosticListener = null;
		Iterable<String> classes = null;
		Writer out = new StringWriter();
		JavaCompiler.CompilationTask compile = javac.getTask(out, fileManager, dianosticListener, options, classes, compilationUnits);
		boolean res = compile.call();
		if (res) {
			return classLoader.findClass(className);
		} else {
			throw new RuntimeException(out.toString());
		}
	}

	private static JavaCompiler newJavaCompiler() {
		if (System.getProperty("dynamic_code") != null) {
			try {
				Class<?> clazz = Class.forName(System.getProperty("dynamic_code"));
				return (JavaCompiler) clazz.getDeclaredConstructor().newInstance();
			} catch (Throwable e) {
			}
		}
		return ToolProvider.getSystemJavaCompiler();
	}

}