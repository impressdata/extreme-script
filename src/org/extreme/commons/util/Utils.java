package org.extreme.commons.util;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.extreme.commons.Formula;
import org.extreme.script.Calculator;
import org.extreme.script.parser.OperationUtils;
import org.extreme.script.parser.UtilEvalError;


public class Utils {
	
	private Utils() {
	}
	
	/*
	 * 拷贝InputStream到dir目录下以fileName作为文件名的文件存在
	 */
	public static void copy(InputStream in, String fileName, File dir) throws IOException {
		File destFile = new File(dir, fileName);
		Utils.makesureFileExist(destFile);

		OutputStream out = new FileOutputStream(destFile);
		Utils.copyBinaryTo(in, out);
		in.close();
		out.close();
	}
	
	/*
	 * 拷贝文件到某目录下
	 */
    public static void copy(File src, File dir) throws IOException {
    	if (src.isFile()) {
    		copy(new java.io.FileInputStream(src), src.getName(), dir);
    	} else if (src.isDirectory()) {
    		File folder = new File(dir, src.getName());
    		if (Utils.mkdirs(folder)) {
    			File[] subFiles = src.listFiles();
    			for (int i = 0; i < subFiles.length; i++) {
    				copy(subFiles[i], folder);
    			}
    		}
    	}
    }

	public static String doubleToString(double d) {
		return convertNumberStringToString(new Double(d));
	}

	public static String convertNumberStringToString(Number number) {
		// :如果是正无穷或是负无穷
		if (OperationUtils.POSITIVE_INFINITY.equals(number)) {
			return "∞";
		} else if (OperationUtils.NEGATIVE_INFINITY.equals(number)) {
			return "-∞";
		}
		
		String numberString = number.toString();
		
		/**
		 * 这个方法主要是用来增强java中Number.toString()的方法，java语言在这个地方的处理有Bug.
		 * 老是产生一些特别长的java数字。
		 */
		int dotIndex = numberString.indexOf('.');
		if (dotIndex < 0) {// 只有小数才需要处理.
			return numberString;
		}

		// 检查一下是否是以'E'来表示的数字.
		int eIndex = numberString.indexOf('E');
		if ((dotIndex == 1 && eIndex > 0) 
				|| (dotIndex == 2 && eIndex > 0 && (numberString.startsWith("-") || numberString.startsWith("+")))) {
			String ePreString = numberString.substring(0, eIndex);
			StringBuffer dotStringBuf = new StringBuffer(ePreString.substring(dotIndex + 1)); // 删除".".

			dotStringBuf.insert(0, ePreString.substring(0, dotIndex));
            
            // :1.24125E+20,需要取出20作为幂,+20无法正确Integer.parseInt,要把+去掉
            String eString = numberString.substring(eIndex + 1);
            if (eString.startsWith("+")) {
            	eString = eString.substring(1);
            }
            int eInteger = Integer.parseInt(eString);

			if (eInteger <= 0) {
				while (dotStringBuf.charAt(dotStringBuf.length() - 1) == '0') {
					dotStringBuf.deleteCharAt(dotStringBuf.length() - 1);
				}

				for (int i = 0; i < -eInteger; i++) {
					dotStringBuf.insert(dotIndex - 1, "0");
				}

				dotStringBuf.insert(dotIndex, ".");
			} else {
				if (dotStringBuf.length() - dotIndex > eInteger) {
					dotStringBuf.insert(eInteger + dotIndex, '.');
				} else {
					while (dotStringBuf.length() - dotIndex < eInteger) {
						dotStringBuf.append('0');
					}
				}
			}

			return dotStringBuf.toString();
		}

		// 其他的普通数字
		String decimalString = numberString.substring(dotIndex + 1);
		int cIndex;
		if ((cIndex = decimalString.indexOf("9999")) >= 0) {
			double value = Double.parseDouble(numberString);

			if (cIndex == 0) {
				return Long.toString(Math.round(value));
			}

			if (Math.abs(value) > Long.MAX_VALUE) {// 处理最大的数字.
				String valueString = Double.toString(value);
				int dotPos = valueString.indexOf(".");

				if (dotPos > 0 && (valueString.length() - dotPos - 1) > cIndex) {
					valueString = valueString.substring(0, dotPos + cIndex + 1);
				}

				if (valueString.endsWith(".0")) {
					return valueString.substring(0, valueString.length() - 2);
				} else {
					return valueString;
				}
			}

			String negativeString = (value < 0) ? "-" : "";
			long a1 = (long) Math.pow(10, cIndex - 1);
			long a = a1 * 10;

			value = Math.abs(value);// 取绝对数值.
			long longValue = Math.round(value * a);
			String longString = negativeString + Long.toString(longValue / a);
			long rLong = (a == 0) ? 0 : longValue % a;

			/*
			 * CPUbug convertNumberStringToString long整型计算越界10^23 * 10 = -10
			 * long a1 = 2^23; a1 * 10 = -10
			 */
			if (rLong < 0) { // 说明越界
				return numberString;
			}

			if (rLong == 0) {
				StringBuffer rString = new StringBuffer("");
				int i = cIndex;
				while (i > 0) {
					rString.insert(0, "0");
					i--;
				}

				return longString + "." + rString;
			} else {
				StringBuffer rString = new StringBuffer(Long.toString(rLong));

				while (rLong < a1) {
					rLong *= 10;
					rString.insert(0, "0");
				}

				return longString + "." + rString;
			}
		} else if ((cIndex = decimalString.indexOf("0000")) >= 0) {
			numberString = numberString.substring(0, dotIndex + Math.max(cIndex, 1) + 1);
		}

		if (numberString.endsWith(".0")) {// 去掉结尾的.0
			numberString = numberString.substring(0, numberString.length() - 2);
		}

		return numberString;
	}
	
	/*
	 * b:get reader's string
	 */
	public static String reader2String(Reader reader) {
        if (reader == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        try {
            Utils.copyCharTo(reader, stringWriter);
            StringBuffer stringBuffer = stringWriter.getBuffer();
            reader.close();
            stringWriter.close();
            return stringBuffer.toString();
        } catch (IOException ioe) {
        	try {
				reader.close();
			} catch (IOException e) {
				LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
			}
            try {
				stringWriter.close();
			} catch (IOException e) {
				LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
			}
            return "";
        }
	}
	
	/*
	 * :这个应该是写的方法,把inputstream变成字符串,缺少一个编码设置 TODO
	 */
	public static String inputStream2String(InputStream is, String charset) throws UnsupportedEncodingException {
		return new String(inputStream2Bytes(is), charset);
	}
	
	/*
	 * :把InputStream中的所有字节都读出来变成一个byte[]
	 * 
	 * 原来的写法是
	 * byte[] b = new byte[in.avalable];
	 * in.read(b);
	 * 但是在CipherInputStream测试的时候,发现拿到的byte[]长度为0
	 */
	public static byte[] inputStream2Bytes(InputStream in) {
		byte[] temp = new byte[32*1024];
		byte[] b = new byte[0];
		try {
			int count;
			while ((count = in.read(temp)) > 0) {
				byte[] b4Add;
				if (temp.length == count) {
					b4Add = temp;
				} else {
					b4Add = ArrayUtils.subarray(temp, 0, count);
				}
				b = ArrayUtils.addAll(b, b4Add);
			}
		} catch (IOException e) {
			LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
			}
		}
		
		return b;
	}
    
	/*
	 * 把Object转成Number
	 * 
	 * returnNull : 当object无法正确转换成Number时,根据returnNull,true -> null, false -> new Integer(0)
	 */
    public static Number objectToNumber(Object obj, boolean returnNull) {
    	if (obj == null) return returnNull ? null : new Integer(0);
    	
    	if (obj instanceof Number) return (Number)obj;
    	
    	String obj2string = objectToString(obj);
    	
    	if(obj2string != null) obj2string = obj2string.trim();
    	
    	Number ret = string2Number(obj2string);
    	if (ret == null && !returnNull) {
    		ret = new Integer(0);
    	}
    	
    	return ret;
    }
    
    /*
     * 把String转成Number
     * 
     * 如果非数字形式的,直接返回null
     * 非数字形式的字符串包括
     * 0123
     * 以及除全数字和数字.数字形式的其它形式的字符串
     */
    public static Number string2Number(String statement) {
    	if (statement == null) return null;
    	
    	if(statement.endsWith("%")) {// 判断百分比号. 对于类似数字 12% 
    		Number number = string2Number(statement.substring(0, statement.length() - 1));
    		return number == null ? null : new Double(number.doubleValue()/100.0);
    	}
    	
		if (statement.startsWith("+")) {
			statement = statement.substring(1, statement.length());
		}
    	// :如果是以0为始,且不是以0.为始的,那么就不是数字
    	if (statement.startsWith("0") && statement.length() > 1 && !statement.startsWith("0E") && !statement.startsWith("0.")) {
    		return null;
    	}
    	
    	if (statement.matches("[\\-]?\\d+")) {
    		// 过长就用BigInteger
    		try {
				return Integer.valueOf(statement);
			} catch (NumberFormatException e) {
				return new BigInteger(statement);
			}
    	} else if (statement.matches("[\\-]?\\d+([\\.]\\d+)?([Ee][\\-\\+]?\\d+)?")) {
    		// :当转成double后，显示字符串同原来一致，则转成double
    		try {
				Double transferDouble = Double.valueOf(statement);
				if (transferDouble.toString().equals(statement)) {
					return transferDouble;
				}
			} catch (NumberFormatException e) {
				// do nothing
			}
    		return new BigDecimal(statement);
    	}
    	
    	return null;
    }

    /*
     * 把Object转成String
     */
	public static String objectToString(Object obj) {
		/**
		 * 这个方法主要是用来增强java中Number.toString()的方法，java语言在这个地方的处理有Bug.
		 * 老是产生一些特别长的java数字。 TODO: 这个bug还没有解决可以看Utils.testDoubleToStringBug()
		 */
		if (obj == null)
			obj = "";
		
		if (obj instanceof String) {
			return (String)obj;
		}

		if (obj instanceof Number) {
			return convertNumberStringToString((Number)obj);
		}

		if (obj instanceof Image) {
			return "";
		}

		return obj.toString();
	}

	/*
	 * XOR Color
	 */
	public static Color getXORColor(Color color) {
		if (color == null) {
			return null;
		}

		return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
	}

	public static String[] splitString(String string, char delimiter) {
		return Utils.splitString(string, "" + delimiter);
	}

	public static String[] splitString(String string, String delimiter) {
		if (string == null || string.length() == 0) {
			return new String[] {};
		}

		return string.split("\\Q" + delimiter + "\\E");
	}

	public static int[] splitStringToIntArray(String string, String delimiter) {
		String[] stringArray = Utils.splitString(string, delimiter);
		int[] intArray = new int[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			try {
				intArray[i] = Integer.parseInt(stringArray[i]);
			} catch (NumberFormatException numberFormatException) {
				// do nothing.
			}
		}

		return intArray;
	}

	/**
	 * Swaps the two specified elements in the specified list.
	 */
	public static void swap(List a, int i, int j) {
		Object tmp = a.get(i);
		a.set(i, a.get(j));
		a.set(j, tmp);
	}

	/**
	 * 替换所有的oldTexts变成newTexts
	 */
	public static String replaceAllString(String allString, String[] oldTexts, String[] newTexts) {
		for (int i = 0; i < oldTexts.length; i++) {
			allString = replaceAllString(allString, oldTexts[i], newTexts[i]);
		}
		return allString;
	}

	/*
	 * Utils.replaceAllString("abc/bb/hh/t.txt", "/", ".")
	 */
	public static String replaceAllString(String allString, String oldText, String newText) {
		if (allString == null) {// ：allString也可能是NULL的
			return null;
		}
		return allString.replaceAll("\\Q" + oldText + "\\E", filterString(newText));
	}

	/*
	 * 用来处理如果String replaceAllString(String allString, String oldText, String newText)中的newText如果有特殊字符$的情况
	 */
	private static String filterString(String s) {
		if (s.indexOf('$') == -1) {
			return s;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '$') {
				sb.append("\\$");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static void copyBinaryTo(File file, OutputStream output) throws IOException {
		/**
		 * 主要指拷贝二进制的图像文件.
		 */
		Utils.copyBinaryTo(new FileInputStream(file), output);
	}

	public static void copyBinaryTo(InputStream input, OutputStream output) throws IOException {
		/**
		 * 主要指拷贝二进制的图像文件.
		 */
		byte[] bufByte = new byte[10240];

		int count;
		while ((count = input.read(bufByte)) >= 0) {
			output.write(bufByte, 0, count);
		}

		output.flush();
	}

	public static void copyCharTo(Reader reader, Writer writer) throws IOException {
		char[] buff = new char[32*1024];
		int count;
		while ((count = reader.read(buff)) != -1) {
			writer.write(buff, 0, count);
		}
		writer.flush();
	}

	/**
	 * 确保file必须存在,如果file不存在则mkdirs
	 */
	public static boolean mkdirs(File file) {
		/**
		 * 这个mkdirs,将会先检测目录是否已经存在.
		 */
		if (!file.exists()) {
			return file.mkdirs();
		}

		return true;
	}

	/**
	 * file.mkfile
	 */
	public static boolean makesureFileExist(File file) throws IOException {
		if (file == null) {
			return false;
		}

		if (file.exists()) {
			return true;
		}

		Utils.mkdirs(file.getParentFile());
		file.createNewFile();

		return true;
	}

	/**
	 * Convert java color to css string.
	 */
	public static String javaColorToCSSColor(Color c) {
		if(c == null) return "";
		
		StringBuffer cssBuf = new StringBuffer("rgb(");
		cssBuf.append(c.getRed());
		cssBuf.append(',');
		cssBuf.append(c.getGreen());
		cssBuf.append(',');
		cssBuf.append(c.getBlue());
		cssBuf.append(')');

		return cssBuf.toString();
	}

	/**
	 * Check file name valid char.
	 */
	public static boolean isFileNameInvalidChar(char ch) {
		for (int i = 0; i < FileNameInvalidChars.length; i++) {
			if (ch == FileNameInvalidChars[i]) {
				return true;
			}
		}

		return false;
	}

	// : 文件名字的非法的Char
	public static char[] FileNameInvalidChars = { '\"', '\'', '[', ']', '\\', '/', ':', '*', '?', '<', '>', '|' };

	public static boolean deleteFile(File file) {
		/**
		 * 由于java当中的删除不支持删除不空的目录，所以需要用这个方法来删除.
		 */
		if ((file == null) || (!file.exists())) {
			return true;
		}

		boolean delete_success = true;
		
		if (file.isDirectory()) {
			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (!deleteFile(files[i]) && delete_success) {
					delete_success = false;
				}
			}
		}
		
		delete_success = file.delete() && delete_success; 

		return delete_success;
	}

	public static String createWhiteSpaceString(int whiteSpaceNumber) {
		/**
		 * 新建一个空的以空格构成的字符串.
		 */
		StringBuffer stringBuf = new StringBuffer();

		for (int i = 0; i < whiteSpaceNumber; i++) {
			stringBuf.append(' ');
		}

		return stringBuf.toString();
	}
	
	public static Field[] getInstanceFields(Class cls) {
		return getInstanceFields(cls, Object.class);
	}
	
	public static Field[] getInstanceFields(Class cls, Class superTo) {
		Class _cls = cls;
		java.util.List fieldList = new java.util.ArrayList();
		do {
			Field[] fs = _cls.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				if (!Modifier.isStatic(f.getModifiers())) {
					fieldList.add(f);
				}
			}
			
			if (_cls == superTo) {
				break;
			}
		} while ((_cls = _cls.getSuperclass()) != Object.class);
		
		return (Field[])fieldList.toArray(new Field[0]);
	}

	public static Method getDeclaredMethod(Class cls, String name, Class[] parameterTypes) {
		/**
		 * 获得所有的DeclaredMethod,包括父类的,主要在反射的时候用.
		 */
		try {
			return cls.getDeclaredMethod(name, parameterTypes);
		} catch (NoSuchMethodException noSuchMethodException) {
			cls = cls.getSuperclass();

			if (cls != null) {
				return getDeclaredMethod(cls, name, parameterTypes);
			}
		}

		return null;
	}

	public static Field getDeclaredField(Class cls, String name) {
		/**
		 * : 获得所有的DeclaredField,包括父类的,主要在反射的时候用.
		 */
		try {
			return cls.getDeclaredField(name);
		} catch (NoSuchFieldException noSuchFieldException) {
			cls = cls.getSuperclass();

			if (cls != null) {
				return getDeclaredField(cls, name);
			}
		}

		return null;
	}

	/**
	 * Class instanceof.
	 */
	public static boolean classInstanceOf(Class childClass, Class parentClass) {
		if (childClass == null || parentClass == null) {
			return false;
		}

		if (childClass.equals(parentClass)) {
			return true;
		}

		Class[] interfaceClassArray = childClass.getInterfaces();
		if (interfaceClassArray != null) {
			for (int i = 0; i < interfaceClassArray.length; i++) {
				if (classInstanceOf(interfaceClassArray[i], parentClass)) {
					return true;
				}
			}
		}

		return classInstanceOf(childClass.getSuperclass(), parentClass);
	}
	
	/*
	 * 判断obj是不是数组
	 */
	public static boolean isArray(Object obj) {
		return obj != null && obj.getClass().isArray();
	}

	/*
	 * convert object to byte
	 */
	public static byte[] objectToByte(java.lang.Object obj) {
		byte[] bytes = new byte[0];
		try {
			// object to bytearray
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);

			bytes = bo.toByteArray();

			bo.close();
			oo.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * ：获取操作系统的环境变量
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Properties getSystemEnvs() throws Exception {
		if (prop == null) {
			prop = new Properties();
		} else {
			return prop;
		}
		Process p = null;
		if (OperatingSystem.isWindows()) {
			p = Runtime.getRuntime().exec("cmd /c set");
		} else {
			return prop;// ：非Windows的先不考虑
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = br.readLine()) != null) {
			int i = line.indexOf("=");
			if (i > -1) {
				String key = line.substring(0, i);
				String value = line.substring(i + 1);
				prop.setProperty(key, value);
			}
		}

		return prop;
	}

	private static Properties prop = null;
	
	/**
	 * :compress file
	 */
	public static void zip(ZipOutputStream out, File f) throws Exception {
		zip(out, f, null);
	}
	
	private static void zip(ZipOutputStream out, File f, String base) throws Exception {
		// 如果是文件夹，则获取下面的所有文件
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (base != null) {
				out.putNextEntry(new ZipEntry(base + "/"));
			}
			base = (base == null || base.length() == 0) ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else { // 如果是文件，则压缩
			LogUtil.getLogger().info("compress files" + base);
			out.putNextEntry(new ZipEntry(base)); // 生成下一个压缩节点
			FileInputStream in = new FileInputStream(f); // 读取文件内容
			int len = 0;
			byte[] buf = new byte[1024];
			while ( (len = in.read(buf)) > 0 ) {
				out.write(buf, 0, len);
			}
			in.close();
			out.flush();
		}
    }
	
	private static String[] availableFontFamilyNames4Report = null;
	
	/*
	 * 这是去除逻辑字体后的报表系统可以支持的字体
	 * 
	 * 为什么要去除逻辑字体?因为逻辑字体本不存在,是Java中定义的
	 * 在jre/lib/font.properties里定义逻辑字体所对应的实际字体,截取部分内容
	 * 这样不同的Locale就可以定义不同的字体,但是报表中,就没有必要了,难道不同的jre运行不一样的字体吗?
		dialog.0=Arial,ANSI_CHARSET
		dialog.1=WingDings,SYMBOL_CHARSET
		dialog.2=Symbol,SYMBOL_CHARSET
		
		dialog.bold.0=Arial Bold,ANSI_CHARSET
		dialog.bold.1=WingDings,SYMBOL_CHARSET
		dialog.bold.2=Symbol,SYMBOL_CHARSET
		
		dialog.italic.0=Arial Italic,ANSI_CHARSET
		dialog.italic.1=WingDings,SYMBOL_CHARSET
		dialog.italic.2=Symbol,SYMBOL_CHARSET
		
		dialog.bolditalic.0=Arial Bold Italic,ANSI_CHARSET
		dialog.bolditalic.1=WingDings,SYMBOL_CHARSET
		dialog.bolditalic.2=Symbol,SYMBOL_CHARSET
	 */
	public static String[] getAvailableFontFamilyNames4Report() {
		if (availableFontFamilyNames4Report == null) {
			/*
			 Java 采用逻辑字体映射到运行平台上对应的物理字体这种方法来处理字体的显示
			 Java 定义了五种逻辑字体：Serif（有衬线） 、Sans-serif（无衬线） 、Monospaced（等宽） 、Dialog（对话框）、DialogInput（对话框输入）
			 每种字体对应有四种字体风格：plain（扁平）、bold（粗体）、italic（斜体）、bolditalic（粗斜）。
			 */
			java.util.List logicFontList = java.util.Arrays.asList(new String[] {
					"serif", "sansserif", "monospaced", "dialog", "dialoginput"
			});
			
			java.util.List physicsFontList = new java.util.ArrayList();
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			if (ge != null) {
				String[] array = ge.getAvailableFontFamilyNames();
				for (int i = 0; i < array.length; i++) {
					// :如果是逻辑字体,不加到FontFamilyNames中
					if (logicFontList.indexOf(array[i].toLowerCase()) < 0) {
						physicsFontList.add(array[i]);
					}
				}
			}
			
			availableFontFamilyNames4Report = (String[])physicsFontList.toArray(new String[physicsFontList.size()]);
		}
		
		return availableFontFamilyNames4Report;
	}

	/*
	 * :打开windows中的文件夹
	 */
	public static void openWindowsFolder(String file) {
		if (!OperatingSystem.isWindows()) {
			// :如果不是windows,不支持打开文件夹
			return;
		}
		
		java.io.File f = new java.io.File(file);
		if (!f.exists()) {
			return;
		}
		
		// :如果是文件,直接选中文件
		if (f.isFile()) {
			try {
				Runtime.getRuntime().exec("explorer /select, " + f.getAbsolutePath());
			} catch (IOException e) {
			}
		} else if (f.isDirectory()) {
			try {
				Runtime.getRuntime().exec("explorer " + f.getAbsolutePath());
			} catch (IOException e) {
			}
		}
	}
	
	public static String i18nQuote(String key) {
		return "i18n(" + quote(key) + ")";
	}
	
	public static String quote(String key) {
		return "\"" + key + "\"";
	}
	
	/**
	 * 四舍五入取靠近如11、11.5、12的值
	 */
	public static float round5(float number){
		return (float)(Math.round(number*2))/2;
	}
	
	/**
     * kunsnat: 处理Formula中的参数等. 转换为最终的value
     */
    public static void dealFormulaValue(Object object, Calculator calculator) {
    	Object result = null;
    	if(object instanceof Formula) { 

    		Formula formula = (Formula)object;
    		String content = (formula).getContent();
    		if(content.startsWith("=")) {
    			content = content.substring(1);
    		}
    		if(StringUtils.isEmpty(content) || StringUtils.isBlank(content))return;
    		
    		try {
    			result = calculator.eval(content);
    		} catch (UtilEvalError e) {
    			String message = "Formula Error";
    			LogUtil.getLogger().log(Level.WARNING,message, e);
    			result = content;
    		}
    		
    		formula.setResult(result);
    	} 
    }
}
