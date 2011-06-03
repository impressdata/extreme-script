package org.extreme.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extreme.commons.Mapper;


/**
 * Base Core Utils
 */
public class BaseCoreUtils {
	private BaseCoreUtils() {
	}
	
	private final static char[] digits = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * ת�����ֵ�ABC
	 */
	public static String convertIntToABC(int index) {
		int k = index;
		int c;
		StringBuffer abcBuf = new StringBuffer();

		if (k == 0) {
			return StringUtils.EMPTY;
		}

		for (; k != 0;) {
			c = k % 26;
			if (c == 0) {
				c = 26;
			}
			abcBuf.insert(0, digits[c - 1]);
			k = (k - c) / 26;
		}

		return abcBuf.toString();
	}

	/**
	 * Convert "ABC" to integer.
	 * 'A' to 1
	 * 'B' to 2
	 * 'AA' to 27
	 */
	public static int convertABCToInt(String abc) {
		int answer = 0;

		Character aCharacter = new Character('A');

		abc = abc.toUpperCase();
		int len = abc.length();
		for (int i = 0; i < len; i++) {
			answer = Character.getNumericValue(abc.charAt(i)) - Character.getNumericValue(aCharacter.charValue()) + 26 * answer + 1;
			/**
			 * daniel���ﴦ��answerֵ������������0���������ݸ��е�����Ϊ-1����ʶ��Ϊsql����
			 * ������������Ϊ��֮�������Ϊ��������ͨ����ģ������������־Ͳ���ȷ
			 */
			if(answer>(Integer.MAX_VALUE-1-Character.getNumericValue(abc.charAt(i)) + Character.getNumericValue(aCharacter.charValue()))/26)
				return 0;
		
		}

		return answer;
	}

	/**
	 * �������飬Encode String.
     * @param string the string will be encoded
     * @param encodeArray encodeArray[0] is the encoded Value, encodeArray[1] is the original Value
	 */
	public static String encodeString(String string, String[][] encodeArray) {
		if (string == null) {
			return "";
		}

		StringBuffer stringBuf = new StringBuffer();

		loop: for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			for (int j = 0; j < encodeArray[1].length; j++) {
				if (ch == encodeArray[1][j].charAt(0)) {
					stringBuf.append(encodeArray[0][j]);
					continue loop;
				}
			}

			stringBuf.append(ch);
		}

		return stringBuf.toString();
	}

	/**
	 * :������ת����Ӣ��.
	 */
	public static double convertMillimetersToInches(double millimeters) {
		return millimeters / 25.4;
	}

	/**
	 * :��Ӣ��ת���ɺ���.
	 */
	public static double convertInchesToMillimeters(double inches) {
		//:ת���ɺ��׵�ʱ���С���㶼ȥ��,���������С��λ������ë��ô!
		//: to : ������뱣��С��,��Ϊ�û��״��ʱ��,�������������׵�,
		//������0.�����׵�,��Ȼ���û���ӡ500�ź�,���λ�����ص�.
		// :��׼һ��
		BigDecimal bd1 = new BigDecimal("" + inches);
		BigDecimal bd2 = new BigDecimal("25.4");
		return bd1.multiply(bd2).floatValue();
	}
	
	// :������Ļ����ϱ���һλС��������ֻ���ڽ�������ʾ�ã��ڲ����㻹���������
	public static double convertInchesToMillimeters2Show(double inches) {
		double length = convertInchesToMillimeters(inches);
		return Math.floor(length * 10) / 10;
	}
	
	public static final java.util.List getMacAddresses() throws IOException {
		java.util.List addList = new ArrayList();
		String macAddress = null;

		Process p = null;
		BufferedReader in = null;
		String osname = System.getProperty("os.name", "");

		if (osname.startsWith("Windows")) {
			p = Runtime.getRuntime().exec(new String[] { "ipconfig", "/all" }, null);
		} else if (osname.startsWith("AIX")) {
			p = Runtime.getRuntime().exec(new String[] { "netstat", "-v" }, null);
		}
		// Solaris code must appear before the generic code 
		else if (osname.startsWith("Solaris") || osname.startsWith("SunOS")) {
			String hostName = getFirstLineOfCommand(new String[] { "uname", "-n" });
			if (hostName != null) {
				p = Runtime.getRuntime().exec(new String[] { "/usr/sbin/arp", hostName }, null);
			}
		} else if (new File("/usr/sbin/lanscan").exists()) {
			p = Runtime.getRuntime().exec(new String[] { "/usr/sbin/lanscan" }, null);
		} else if (new File("/sbin/ifconfig").exists()) {
			p = Runtime.getRuntime().exec(new String[] { "/sbin/ifconfig", "-a" }, null);
		}

		if (p != null) {
			InputStream inputStream = p.getInputStream();
			
			in = new BufferedReader(new InputStreamReader(inputStream), 128);
			String l = null;
			while ((l = in.readLine()) != null) {
				//:�������ܴ�ӡ��Ϣ���鿴��¼
				LogUtil.getLogger().info(l);
				macAddress = parseMacAddress(l);
				if (macAddress != null && parseShort(macAddress) != 0xff)
					addList.add(macAddress);
			}
		}
		
		return addList;		
	}
	
	/**
	 * @author 
	 * @return   mac address
	 * @throws IOException 
	 */
	public static String getMacAddress() throws IOException {
		return (String)getMacAddresses().get(0);
	}
    
	/**
	 * Parses a <code>short</code> from a hex encoded number. This method will skip
	 * all characters that are not 0-9 and a-f (the String is lower cased first).
	 * Returns 0 if the String does not contain any interesting characters.
	 * 
	 * @param s the String to extract a <code>short</code> from, may not be <code>null</code>
	 * @return a <code>short</code>
	 * @throws NullPointerException if the String is <code>null</code>
	 */
	private static short parseShort(String s) throws NullPointerException {
		s = s.toLowerCase();
		short out = 0;
		byte shifts = 0;
		char c;
		for (int i = 0; i < s.length() && shifts < 4; i++) {
			c = s.charAt(i);
			if ((c > 47) && (c < 58)) {
				out <<= 4;
				++shifts;
				out |= c - 48;
			} else if ((c > 96) && (c < 103)) {
				++shifts;
				out <<= 4;
				out |= c - 87;
			}
		}
		return out;
	}

	/**
	 * Returns the first line of the shell command.
	 * 
	 * @param commands the commands to run
	 * @return the first line of the command
	 * @throws IOException
	 */
	private static String getFirstLineOfCommand(String[] commands) throws IOException {
		Process p = null;
		BufferedReader reader = null;

		try {
			p = Runtime.getRuntime().exec(commands);
			reader = new BufferedReader(new InputStreamReader(p.getInputStream()), 128);

			return reader.readLine();
		} finally {
			if (p != null) {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ex) {
					}
				}
				try {
					p.getErrorStream().close();
				} catch (IOException ex) {
				}
				try {
					p.getOutputStream().close();
				} catch (IOException ex) {
				}
				p.destroy();
			}
		}
	}

	private static String parseMacAddress(String in) {
		// lanscan        
		int hexStart = in.indexOf("0x");
		if (hexStart != -1 && in.indexOf("ETHER") != -1) {
			int hexEnd = in.indexOf(' ', hexStart);
			if (hexEnd > hexStart + 2) {
				return in.substring(hexStart, hexEnd);
			}
		}

		int octets = 0;
		int lastIndex, old, end;

		if (in.indexOf('-') > -1) {
			in = in.replace('-', ':');
		}

		lastIndex = in.lastIndexOf(':');

		if (lastIndex > in.length() - 2)
			return null;

		end = Math.min(in.length(), lastIndex + 3);

		++octets;
		old = lastIndex;
		while (octets != 5 && lastIndex != -1 && lastIndex > 1) {
			lastIndex = in.lastIndexOf(':', --lastIndex);
			if (old - lastIndex == 3 || old - lastIndex == 2) {
				++octets;
				old = lastIndex;
			}
		}

		if (octets == 5 && lastIndex > 1) {
			return in.substring(lastIndex - 2, end).trim();
		}

		return null;
	}
    
    /*
     * ��'\n'��ôһ��charд��'\\' + 'n'
     * '\\' -> '\\' + '\\'
     * '\t' -> '\\' + 't'
     * '\r' -> '\\' + 'r'
     * '\b' -> '\\' + 'b'
     * '\f' -> '\\' + 'f'
     * '\'' -> '\\' + '\''
     * '\"' -> '\\' + '\"'
     */
    public static String writeESC(String str) {
    	if(str == null) {
    		return "";
    	}
    	
    	StringBuffer sb = new StringBuffer();
    	char c;
    	for (int i = 0, len = str.length(); i < len; i++) {
			switch (c = str.charAt(i)) {
			case '\n':
				sb.append("\\n");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\'':
				sb.append("\\\'");
				break;
			case '\"':
				sb.append("\\\"");
				break;
			default:
				sb.append(c);
			}
		}
    	
    	return sb.toString();
    }
	
	/*
	 * ��"\\n"�������ַ������'\n',Ҳ���ǰ�������ת���ַ����ʵ�ʵ��ַ�
	 */
	public static String readESC(String str) throws Exception {
		if(str == null) {
			return "";
		}
		
		/*
		 * :���BUG0001421,ԭ�����Ǽ򵥵���string.split("\\")��ÿһ����н���
		 * �ᵼ��������\\\\\\\�޷�����
		 */
		
		Pattern pattern = Pattern.compile("\\\\"); // :��б�ܵ�PatternҪд�ĸ�б��...
		
		Matcher matcher = pattern.matcher(str);
		int start = 0, end = 0;
		StringBuffer sb = new StringBuffer();
		while(matcher.find(start)) {
    		start = matcher.start();
    		sb.append(str.substring(end, start)); //��startǰ��һ��д��ȥ
    		
    		end = matcher.end();
    		String tailString = str.substring(end);
    		
    		if (tailString.length() == 0) {
    			throw new Exception('\\' + " can't be parsed.");
    		}
    		
    		char firstChar = tailString.charAt(0);
    		switch(firstChar) {
	    		case 'n':sb.append('\n');end++;break;
	    		case 'r':sb.append('\r');end++;break;
	    		case 't':sb.append('\t');end++;break;
	    		case 'b':sb.append('\b');end++;break;
	    		case 'f':sb.append('\f');end++;break;
	    		case '"':sb.append('\"');end++;break;
	    		case '\'':sb.append('\'');end++;break;
	    		case '\\':sb.append('\\');end++;break;
	    		
	    		// hex -> unicode
	    		case 'u': {
	    			String hexString = str.substring(end);
	    			if (hexString.length() < 4) {
	    				throw new Exception("\\u" + hexString + " can't be parsed.");
	    			}
	    			sb.append(parseHex(hexString.substring(0, 4)));
	    			end = end + 5;
	    			break;
	    		}
	    		// octal -> unicode
	    		default: {
	    			int octal = 0; // octal�ĳ���
	    			if(tailString.matches("[0-3][0-7][0-7].*")) {
	    				octal = 3;
	    			} else if (tailString.matches("[0-7][0-7].*")) {
	    				octal = 2;
	    			} else if (tailString.matches("[0-7].*")) {
	    				octal = 1;
	    			}
	    			
	    			if (octal > 0) {
	    				sb.append(parseOctal(str.substring(end, end + octal)));
	    				end = end + octal;
	    			} else {
	    				throw new Exception("\\" + tailString + " can't be parsed.");
	    			}
	    		}
    		}
    		
    		start = end;
		}
		
		sb.append(str.substring(end));
		
		return sb.toString();
	}
	
	/*
	 * ��745c����ַ���ת��'�'���char,16���� -> char
	 */
	private static char parseHex(String hex_str) {
		return (char)Integer.decode("0x" + hex_str).intValue();
	}
	
	/*
	 * ��101����ַ���ת��'A'���char,�˽��� -> char
	 */
	private static char parseOctal(String octal_str) {
		return (char)Integer.decode('0' + octal_str).intValue();
	}
	
	/*
	 * ����path�е�\��/,��path�ָ������
	 */
	public static String[] pathSplit(String path) {
		return path.split("[/\\\\]");
	}
	
	/*
	 * ·���ڵ������,��File.seperator����ƴ��һ���ַ���
	 */
	public static String pathJoin(String[] nodes) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0, len = nodes == null ? 0 : nodes.length; i < len; i++) {
			String node = nodes[i];
			
			if(node == null) {
				node = "";
			}
			
			// ���i > 0,��ôsb�����һλ�ض���File.separatorChar,�������node�Էָ���Ϊ��ʼ,����ָ�����Ҫ�ӽ�ȥ
			if(i > 0 && (node.startsWith("/") || node.startsWith("\\"))) {
				node = node.substring(1);
			}
			
			sb.append(node);
			
			if(i + 1 < len && !(node.endsWith("/") || node.endsWith("\\"))) {
				//����ط����ոĽ��ģ���Ϊ�еĵط�����"\"�����.
				sb.append('/');
			}
		}
		
		return sb.toString();
	}
	
	/*
	 * ����file�����target�����·��
	 * ����֮��û����Թ�ϵ����null
	 */
	public static String getRelativePath(File file, File target) {
		String filePath = file.getAbsolutePath();
		String targetPath = target.getAbsolutePath();
		if (filePath.length() < targetPath.length()) {
			return null;
		} else if (filePath.equals(targetPath)) {
			return "";
		} else {
			if (filePath.startsWith(targetPath)) {
				String path = filePath.substring(targetPath.length());
				if (!path.startsWith("/") && !path.startsWith("\\")) {
					path = File.separator + path;
				}
				
				return path;
			} else {
				return null;
			}
		}
	}
	
	/*
	 * Collection.join
	 */
	public static String join(java.util.Collection c, String se) {
		StringBuffer sb = new StringBuffer();
		java.util.Iterator it = c.iterator();
		while(it.hasNext()) {
			// ����class
			Object o = it.next();
			// :ΪʲôҪ����������instanceof���ж�?
//			if (o instanceof Class) {
//				o = ((Class)o).getName();
//			}
//			if (o instanceof FieldEditor){
//				o = ((FieldEditor)o).toString();
//			}
			sb.append(o);
			if(it.hasNext()) {
				sb.append(se);
			}
		}
		
		return sb.toString();
	}
	
	public static String join(java.util.Collection c) {
		return join(c, StringUtils.EMPTY);
	}
	
	/*
	 * Array.join
	 */
	public static String join(Object[] array, String se) {
		return join(Arrays.asList(array), se);
	}
	
	public static String join(Object[] array) {
		return join(array, StringUtils.EMPTY);
	}
	
	/*
	 * List.map
	 */
	public static java.util.List map(java.util.List l, Mapper mapper) {
		int len = l.size();
		Object[] res = new Object[len];
		
		for (int i = 0; i < len; i++) {
			res[i] = mapper.map(i, l.get(i), l);
		}

		return java.util.Arrays.asList(res);
	}

	/*
	 * ����Map�����кö࣬��������clone��Ҫ���������.
	 * �����������һ�����Ƶĵط�,�ܶ�������HashMap, Hashtable��ʵ����Cloneable�ӿڣ�
	 * Ϊʲô���ǲ���ֱ�ӵ���clone������.
	 */
	public static Map cloneMap(Map map) throws CloneNotSupportedException {
	    if (map == null) {
	        return null;
	    }
	
	    if (map instanceof HashMap) {
	        return (Map) ((HashMap) map).clone();
	    } else if (map instanceof Hashtable) {
	        return (Map) ((Hashtable) map).clone();
	    } else if (map instanceof ListMap) {
	        return (Map) ((ListMap) map).clone();
	    } else {
	    	// �÷������ʵ��
	        try {
	            Method clipMethod = Utils.getDeclaredMethod(map.getClass(), "clone", new Class[]{});
	            clipMethod.setAccessible(true);
	
	            return (Map) clipMethod.invoke(map, new Object[]{});
	        } catch (Exception exp) {
	        	LogUtil.getLogger().log(Level.WARNING, exp.getMessage(), exp);
	        }
	    }
	
	    return map;
	}
}
