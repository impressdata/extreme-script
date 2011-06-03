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
	 * ����InputStream��dirĿ¼����fileName��Ϊ�ļ������ļ�����
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
	 * �����ļ���ĳĿ¼��
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
		// :�������������Ǹ�����
		if (OperationUtils.POSITIVE_INFINITY.equals(number)) {
			return "��";
		} else if (OperationUtils.NEGATIVE_INFINITY.equals(number)) {
			return "-��";
		}
		
		String numberString = number.toString();
		
		/**
		 * ���������Ҫ��������ǿjava��Number.toString()�ķ�����java����������ط��Ĵ�����Bug.
		 * ���ǲ���һЩ�ر𳤵�java���֡�
		 */
		int dotIndex = numberString.indexOf('.');
		if (dotIndex < 0) {// ֻ��С������Ҫ����.
			return numberString;
		}

		// ���һ���Ƿ�����'E'����ʾ������.
		int eIndex = numberString.indexOf('E');
		if ((dotIndex == 1 && eIndex > 0) 
				|| (dotIndex == 2 && eIndex > 0 && (numberString.startsWith("-") || numberString.startsWith("+")))) {
			String ePreString = numberString.substring(0, eIndex);
			StringBuffer dotStringBuf = new StringBuffer(ePreString.substring(dotIndex + 1)); // ɾ��".".

			dotStringBuf.insert(0, ePreString.substring(0, dotIndex));
            
            // :1.24125E+20,��Ҫȡ��20��Ϊ��,+20�޷���ȷInteger.parseInt,Ҫ��+ȥ��
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

		// ��������ͨ����
		String decimalString = numberString.substring(dotIndex + 1);
		int cIndex;
		if ((cIndex = decimalString.indexOf("9999")) >= 0) {
			double value = Double.parseDouble(numberString);

			if (cIndex == 0) {
				return Long.toString(Math.round(value));
			}

			if (Math.abs(value) > Long.MAX_VALUE) {// ������������.
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

			value = Math.abs(value);// ȡ������ֵ.
			long longValue = Math.round(value * a);
			String longString = negativeString + Long.toString(longValue / a);
			long rLong = (a == 0) ? 0 : longValue % a;

			/*
			 * CPUbug convertNumberStringToString long���ͼ���Խ��10^23 * 10 = -10
			 * long a1 = 2^23; a1 * 10 = -10
			 */
			if (rLong < 0) { // ˵��Խ��
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

		if (numberString.endsWith(".0")) {// ȥ����β��.0
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
	 * :���Ӧ����д�ķ���,��inputstream����ַ���,ȱ��һ���������� TODO
	 */
	public static String inputStream2String(InputStream is, String charset) throws UnsupportedEncodingException {
		return new String(inputStream2Bytes(is), charset);
	}
	
	/*
	 * :��InputStream�е������ֽڶ����������һ��byte[]
	 * 
	 * ԭ����д����
	 * byte[] b = new byte[in.avalable];
	 * in.read(b);
	 * ������CipherInputStream���Ե�ʱ��,�����õ���byte[]����Ϊ0
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
	 * ��Objectת��Number
	 * 
	 * returnNull : ��object�޷���ȷת����Numberʱ,����returnNull,true -> null, false -> new Integer(0)
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
     * ��Stringת��Number
     * 
     * �����������ʽ��,ֱ�ӷ���null
     * ��������ʽ���ַ�������
     * 0123
     * �Լ���ȫ���ֺ�����.������ʽ��������ʽ���ַ���
     */
    public static Number string2Number(String statement) {
    	if (statement == null) return null;
    	
    	if(statement.endsWith("%")) {// �жϰٷֱȺ�. ������������ 12% 
    		Number number = string2Number(statement.substring(0, statement.length() - 1));
    		return number == null ? null : new Double(number.doubleValue()/100.0);
    	}
    	
		if (statement.startsWith("+")) {
			statement = statement.substring(1, statement.length());
		}
    	// :�������0Ϊʼ,�Ҳ�����0.Ϊʼ��,��ô�Ͳ�������
    	if (statement.startsWith("0") && statement.length() > 1 && !statement.startsWith("0E") && !statement.startsWith("0.")) {
    		return null;
    	}
    	
    	if (statement.matches("[\\-]?\\d+")) {
    		// ��������BigInteger
    		try {
				return Integer.valueOf(statement);
			} catch (NumberFormatException e) {
				return new BigInteger(statement);
			}
    	} else if (statement.matches("[\\-]?\\d+([\\.]\\d+)?([Ee][\\-\\+]?\\d+)?")) {
    		// :��ת��double����ʾ�ַ���ͬԭ��һ�£���ת��double
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
     * ��Objectת��String
     */
	public static String objectToString(Object obj) {
		/**
		 * ���������Ҫ��������ǿjava��Number.toString()�ķ�����java����������ط��Ĵ�����Bug.
		 * ���ǲ���һЩ�ر𳤵�java���֡� TODO: ���bug��û�н�����Կ�Utils.testDoubleToStringBug()
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
	 * �滻���е�oldTexts���newTexts
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
		if (allString == null) {// ��allStringҲ������NULL��
			return null;
		}
		return allString.replaceAll("\\Q" + oldText + "\\E", filterString(newText));
	}

	/*
	 * �����������String replaceAllString(String allString, String oldText, String newText)�е�newText����������ַ�$�����
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
		 * ��Ҫָ���������Ƶ�ͼ���ļ�.
		 */
		Utils.copyBinaryTo(new FileInputStream(file), output);
	}

	public static void copyBinaryTo(InputStream input, OutputStream output) throws IOException {
		/**
		 * ��Ҫָ���������Ƶ�ͼ���ļ�.
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
	 * ȷ��file�������,���file��������mkdirs
	 */
	public static boolean mkdirs(File file) {
		/**
		 * ���mkdirs,�����ȼ��Ŀ¼�Ƿ��Ѿ�����.
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

	// : �ļ����ֵķǷ���Char
	public static char[] FileNameInvalidChars = { '\"', '\'', '[', ']', '\\', '/', ':', '*', '?', '<', '>', '|' };

	public static boolean deleteFile(File file) {
		/**
		 * ����java���е�ɾ����֧��ɾ�����յ�Ŀ¼��������Ҫ�����������ɾ��.
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
		 * �½�һ���յ��Կո񹹳ɵ��ַ���.
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
		 * ������е�DeclaredMethod,���������,��Ҫ�ڷ����ʱ����.
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
		 * : ������е�DeclaredField,���������,��Ҫ�ڷ����ʱ����.
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
	 * �ж�obj�ǲ�������
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
	 * ����ȡ����ϵͳ�Ļ�������
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
			return prop;// ����Windows���Ȳ�����
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
		// ������ļ��У����ȡ����������ļ�
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (base != null) {
				out.putNextEntry(new ZipEntry(base + "/"));
			}
			base = (base == null || base.length() == 0) ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else { // ������ļ�����ѹ��
			LogUtil.getLogger().info("compress files" + base);
			out.putNextEntry(new ZipEntry(base)); // ������һ��ѹ���ڵ�
			FileInputStream in = new FileInputStream(f); // ��ȡ�ļ�����
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
	 * ����ȥ���߼������ı���ϵͳ����֧�ֵ�����
	 * 
	 * ΪʲôҪȥ���߼�����?��Ϊ�߼����屾������,��Java�ж����
	 * ��jre/lib/font.properties�ﶨ���߼���������Ӧ��ʵ������,��ȡ��������
	 * ������ͬ��Locale�Ϳ��Զ��岻ͬ������,���Ǳ�����,��û�б�Ҫ��,�ѵ���ͬ��jre���в�һ����������?
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
			 Java �����߼�����ӳ�䵽����ƽ̨�϶�Ӧ�������������ַ����������������ʾ
			 Java �����������߼����壺Serif���г��ߣ� ��Sans-serif���޳��ߣ� ��Monospaced���ȿ� ��Dialog���Ի��򣩡�DialogInput���Ի������룩
			 ÿ�������Ӧ������������plain����ƽ����bold�����壩��italic��б�壩��bolditalic����б����
			 */
			java.util.List logicFontList = java.util.Arrays.asList(new String[] {
					"serif", "sansserif", "monospaced", "dialog", "dialoginput"
			});
			
			java.util.List physicsFontList = new java.util.ArrayList();
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			if (ge != null) {
				String[] array = ge.getAvailableFontFamilyNames();
				for (int i = 0; i < array.length; i++) {
					// :������߼�����,���ӵ�FontFamilyNames��
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
	 * :��windows�е��ļ���
	 */
	public static void openWindowsFolder(String file) {
		if (!OperatingSystem.isWindows()) {
			// :�������windows,��֧�ִ��ļ���
			return;
		}
		
		java.io.File f = new java.io.File(file);
		if (!f.exists()) {
			return;
		}
		
		// :������ļ�,ֱ��ѡ���ļ�
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
	 * ��������ȡ������11��11.5��12��ֵ
	 */
	public static float round5(float number){
		return (float)(Math.round(number*2))/2;
	}
	
	/**
     * kunsnat: ����Formula�еĲ�����. ת��Ϊ���յ�value
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
