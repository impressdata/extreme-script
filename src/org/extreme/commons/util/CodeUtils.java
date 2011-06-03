package org.extreme.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extreme.commons.json.JSONArray;
import org.extreme.commons.json.JSONException;
import org.extreme.commons.json.JSONObject;


public class CodeUtils {
	private CodeUtils() {
	}
    
    // 用于判断某个文本是不是被cjkEncode过的,这两个Pattern用到的频率比较高,所以static化
    private static Pattern bracketPattern = Pattern.compile("\\[[^\\]]*\\]");
    // :一些特殊字符在cjkEncode后是2个字符或是3个字符(α),汉字在cjkEncode后是4个字符
    private static Pattern textPattern = Pattern.compile("[0-9a-f]{2,4}", Pattern.CASE_INSENSITIVE);
    // 用于替换空白字符
    private static Pattern blankPattern = Pattern.compile("\\s+");

	/**
	 * Encode the specific string in javascript
	 * @param str the string will be encoded
	 * @return the encoded string
	 */
	public static String javascriptEncode(String str) {
		//：本来的这个javascript的Encode不全面，还是用JSONObject的quote比较好些，考虑的全些吧
		if (StringUtils.isEmpty(str)) {
			return "";
		}

		char b;
		char c = 0;
		int i;
		int len = str.length();
		StringBuffer sb = new StringBuffer();
		String t;

		for (i = 0; i < len; i += 1) {
			b = c;
			c = str.charAt(i);
			switch (c) {
			case '\\':
			case '"':
				sb.append('\\');
				sb.append(c);
				break;
			case '/':
				if (b == '<') {
					sb.append('\\');
				}
				sb.append(c);
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			default:
				//barry: 版权标记符号也要处理一下('\u00a9')
				if (c < ' ' || (c >= '\u0080' && c < '\u00a0')
						|| (c >= '\u2000' && c < '\u2100')
						|| (c == '\u00a9')
						|| (c == '\u00ae')) {
					t = "000" + Integer.toHexString(c);
					sb.append("\\u" + t.substring(t.length() - 4));
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Decode the specific string in javascript
	 *
	 * @param str the string will be decoded
	 * @return the decoded string
	 */
	public static String javascriptDecode(String str) {
		return BaseCoreUtils.encodeString(str, new String[][] {
				{ "\\", "'", "\"" }, { "\\\\", "\\'", "\\\"" } });
	}

	/**
	 * Encode the URI Component, refer to the encodeURIComponent method in javascript
	 * 这里的encodeURIComponent跟javascript中的encodeURIComponent还是有不同的，这里没有对CJK作处理
	 * 主要是考虑如果对CJK也作处理，一是麻烦， 二是处理后，如果URL还是指向我们的Server，那读取URL的时候
	 * 需要考虑编码，那不同的服务器处理就不一样，要考虑的情况比较多
	 * 所以这里不对CJK作处理，CJK的处理请调用cjkEncode
	 * @param uriCmp the URI Component will be encoded
	 * @return the encoded URI Component
	 */
	public static String encodeURIComponent(String uriCmp) {
		try {
			return URLEncoder.encode(uriCmp, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return BaseCoreUtils.encodeString(uriCmp, new String[][] {
					{ "%20", "%23", "%24", "%26", "%2B", "%2C", "%2F", "%3A",
							"%3B", "%3D", "%3F", "%40", "%25" },
					{ " ", "#", "$", "&", "+", ",", "/", ":", ";", "=", "?", "@",
							"%" } });
		}
	}
	
	/*
	 * Encode HTML标签的属性
	 */
	public static String attributeHtmlEncode(CharSequence rawValue) {
		if (rawValue == null) {
			return "";
		}
		
		int len = rawValue.length();
        StringBuffer out = new StringBuffer((int)(len*1.5));

        // Allow: a-z A-Z 0-9
        // Allow (dec): 97-122 65-90 48-57

        for(int cnt = 0; cnt < len; cnt++)
        {
                char c = rawValue.charAt(cnt);
                if( (c >= 97 && c <= 122) ||
                        (c >= 65 && c <= 90 ) ||
                        (c >= 48 && c <= 57 ) )
                {
                        out.append(c);
                }
                else
                {
                        out.append("&#").append((int)c).append(';');
                }
        }

        return out.toString();
	}

	/**
	 * 功能类同于asp里的server.htmlEncode方法
	 *
	 * @param rawValue String
	 * @return String
	 */
	public static String htmlEncode(CharSequence rawValue) {
		if (rawValue == null) {
			return "";
		}
		
		int len = rawValue.length();
		StringBuffer sb = new StringBuffer(len);

		for (int i = 0; i < len; i++) {
			char c = rawValue.charAt(i);

			switch (c) {
				case '<': {
					sb.append("&lt;");
					break;
				}
				case '>': {
					sb.append("&gt;");
					break;
				}
				case '&': {
					sb.append("&amp;");
					break;
				}
				case '"': {
					sb.append("&quot;");
					break;
				}
				case '\r': {
					if (i + 1 < len && rawValue.charAt(i + 1) == '\n') {
						i++;
					}
					sb.append("<br>");
					break;
				}

				case '\\': {
					/*
					 *  将\n换成<br>
					 *  在格子里的\n是两个char,不是一个char
					 *  相当于Utils.replaceAllString(str, "\\n", "<br>")
					 */
					if (i + 1 < len && rawValue.charAt(i + 1) == 'n') {
						i ++;
						sb.append("<br>");				
					} else {
						sb.append(c);
					}
					break;
				}
				
				case '\n': {
					sb.append("<br>");
					break;
				}
				default: {
					sb.append(c);
					break;
				}
			}
		}

		String str = replaceBlankToHtmlBlank(sb.toString());
		
		return str;
	}

	/*
	 * 把空白转换成&nbsp;同时保证break-word有效
	 * 起始和结束必须是" "以满足break-word
	 * 中间的空白每两个变成一个&nbsp;因为&nbsp;实际长度比一个空白大
	 */
	private static String replaceBlankToHtmlBlank(String str) {
		Matcher matcher = blankPattern.matcher(str);
		int start = 0, end = 0;
		StringBuffer sb = new StringBuffer(str.length());
		while (matcher.find()) {
			end = matcher.start();
			sb.append(str.substring(start, end));
			start = matcher.end();
			
			String group = matcher.group();
			
			sb.append(' ');
			int psize = group.length() - 1;
			if (psize > 1) {
				for (int pi = psize; pi > 1; pi -= 2) {
					sb.append("&nbsp;");
				}
				sb.append(' ');
			}
		}
		
		if (start == 0) {	// 呵呵，如果没有空字符串， 直接返回
			return str;
		}
		
		sb.append(str.substring(start));

		return sb.toString();
	}

	/**
	 * Decode the specific string in HTML/XML
	 *
	 * @param str
	 * @return decoded string
	 */
	public static String htmlDecode(String str) {
		//：这个没有encode写的全面
		return BaseCoreUtils.encodeString(str,
				new String[][] {
						{ " ", "&", "<", ">", "'", "\"", "\\r\\n" },
						{ "&nbsp;", "&amp;", "&lt;", "&gt;", "&apos;",
								"&quot;", "<br>" } });
	}

	/**
	 * Encode CJK characters to '[0000]".
	 * for example: 用 --> [7528],   软 --> [8f6f]
	 */
	public static String cjkEncode(String text) {
		if (text == null) {
			return "";
		}

		StringBuffer newTextBuf = new StringBuffer();
		for (int i = 0, len = text.length(); i < len; i++) {
			char ch = text.charAt(i);
			if (ch >= 128 || ch == 91 || ch == 93) {//:91 is '[', 93 is ']'.转换非ascii + '[' + ']'
				newTextBuf.append('[');
				newTextBuf.append(Integer.toString(ch, 16));
				newTextBuf.append(']');
			} else {
				newTextBuf.append(ch);
			}
		}

		return newTextBuf.toString();
	}

	/**
	 * decode '[0000]" to CJK characters.
	 * for example: [7528] --> 用, [8f6f] --> 软
	 */
	public static String cjkDecode(String text) throws Exception {
		if (text == null) {
			return "";
		}

		//查找没有 "[", 直接返回.
		if (text.indexOf('[') == -1) {
			return text;
		}

		StringBuffer newTextBuf = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch == '[') {
				int rightIdx = text.indexOf(']', i + 1);
				if (rightIdx > i + 1) {
					String subText = text.substring(i + 1, rightIdx);
					//：主要是考虑[CDATA[]]这样的值的出现
					if (subText.length() > 0) {
						ch = (char) Integer.parseInt(subText, 16);
					}

					i = rightIdx;
				}
			}

			newTextBuf.append(ch);
		}

		return newTextBuf.toString();
	}
	
	/*
	 * JSON Encode
	 */
	public static String jsonEncode(Object o) throws JSONException {
		return JSONObject.valueToString(o);
	}
	
	/*
	 * JSON Decode
	 */
	public static Object jsonDecode(String str) throws JSONException {
		JSONArray ja = new JSONArray("[" + str + "]");
		return (ja.length() == 0) ? null : ja.get(0);
	}

	/**
	 * Evade the backslash
	 *
	 * @param string 
	 * @return
	 */
	public static String evadeBackslash(String string) {
		StringBuffer stringBuffer = new StringBuffer();

		int length = string.length();
		for (int i = 0; i < length; i++) {
			if (string.charAt(i) == '\\') {//多加一个"\\",变成双的.
				stringBuffer.append("\\\\");
			} else {
				stringBuffer.append(string.charAt(i));
			}
		}

		return stringBuffer.toString();
	}
	
	/**
	 * JavaScript escape
	 * @param src
	 * @return 
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append('%');
				if (j < 16)
					tmp.append('0');
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
	
	/**
	 * JavaScript unescape
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/*
	 * 判断是否是cjkEncode过的字符串
	 * 如果符合[]中间包含非]的字符,那么这几个字符必须是5b或是5d或是[0-9a-fA-F]{4},否则就不是被cjkEncode过的
	 */
	public static boolean isCJKEncoded(String text) {
		boolean b = false;
		if(text == null) {
			return b;
		}
		
		Matcher matcher = bracketPattern.matcher(text);
		while(matcher.find()) {
			b = true;
			
			int start = matcher.start();
			int end = matcher.end();
			String sub_text = text.substring(start + 1, end - 1);
			
			// 如果符合[]中间是5b或是5d或是[0-9a-fA-F]{4},那么有可能是encode过的,要继续检查
			// richer:[0-9a-fA-F]{3}同样有可能是encode过的,比如特殊字符ф就为[444]
			if(textPattern.matcher(sub_text).matches()) {
				continue;
			}
			
			// 如果不符合,那么就不是encode过的,直接返回false
			return false;
		}
		
		return b;
	}
	
    /**
	 * :给字符串加密.
	 * 
	 * @param text
	 *            the old text
	 * @return the passworded text.
	 */
    public static String passwordEncode(String text) {
        //这个方法支持CJK。
        StringBuffer passwordTextBuf = new StringBuffer();
        passwordTextBuf.append("___");
        if (text == null) {
            return passwordTextBuf.toString();
        }

        int pmIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            if (pmIndex == PasswordMaskArray.length) {
                pmIndex = 0;
            }

            int intValue = ((int) text.charAt(i)) ^ PasswordMaskArray[pmIndex];
            String passwordText = Integer.toHexString(intValue);

            //确保用四位数字来保存字符串.
            int pLength = passwordText.length();
            for (int j = 0; j < 4 - pLength; j++) {
                passwordText = "0" + passwordText;
            }

            passwordTextBuf.append(passwordText);
            pmIndex++;
        }

        return passwordTextBuf.toString();
    }

    public static String passwordDecode(String passwordText) {
        if (passwordText != null && passwordText.startsWith("___")) {
            passwordText = passwordText.substring(3); //去掉前面的"___"。

            StringBuffer textBuf = new StringBuffer();

            int pmIndex = 0;
            for (int i = 0; i <= (passwordText.length() - 4); i += 4) {
                if (pmIndex == PasswordMaskArray.length) {
                    pmIndex = 0;
                }

                String hexText = passwordText.substring(i, i + 4);
                int hexInt = Integer.parseInt(hexText, 16) ^ PasswordMaskArray[pmIndex];

                textBuf.append((char) hexInt);
                pmIndex++;
            }

            passwordText = textBuf.toString();
        }

        return passwordText;
    }

    private static final int[] PasswordMaskArray = {19, 78, 10, 15, 100, 213, 43, 23};
}
