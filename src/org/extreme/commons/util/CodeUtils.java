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
    
    // �����ж�ĳ���ı��ǲ��Ǳ�cjkEncode����,������Pattern�õ���Ƶ�ʱȽϸ�,����static��
    private static Pattern bracketPattern = Pattern.compile("\\[[^\\]]*\\]");
    // :һЩ�����ַ���cjkEncode����2���ַ�����3���ַ�(��),������cjkEncode����4���ַ�
    private static Pattern textPattern = Pattern.compile("[0-9a-f]{2,4}", Pattern.CASE_INSENSITIVE);
    // �����滻�հ��ַ�
    private static Pattern blankPattern = Pattern.compile("\\s+");

	/**
	 * Encode the specific string in javascript
	 * @param str the string will be encoded
	 * @return the encoded string
	 */
	public static String javascriptEncode(String str) {
		//�����������javascript��Encode��ȫ�棬������JSONObject��quote�ȽϺ�Щ�����ǵ�ȫЩ��
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
				//barry: ��Ȩ��Ƿ���ҲҪ����һ��('\u00a9')
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
	 * �����encodeURIComponent��javascript�е�encodeURIComponent�����в�ͬ�ģ�����û�ж�CJK������
	 * ��Ҫ�ǿ��������CJKҲ������һ���鷳�� ���Ǵ�������URL����ָ�����ǵ�Server���Ƕ�ȡURL��ʱ��
	 * ��Ҫ���Ǳ��룬�ǲ�ͬ�ķ���������Ͳ�һ����Ҫ���ǵ�����Ƚ϶�
	 * �������ﲻ��CJK������CJK�Ĵ��������cjkEncode
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
	 * Encode HTML��ǩ������
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
	 * ������ͬ��asp���server.htmlEncode����
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
					 *  ��\n����<br>
					 *  �ڸ������\n������char,����һ��char
					 *  �൱��Utils.replaceAllString(str, "\\n", "<br>")
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
	 * �ѿհ�ת����&nbsp;ͬʱ��֤break-word��Ч
	 * ��ʼ�ͽ���������" "������break-word
	 * �м�Ŀհ�ÿ�������һ��&nbsp;��Ϊ&nbsp;ʵ�ʳ��ȱ�һ���հ״�
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
		
		if (start == 0) {	// �Ǻǣ����û�п��ַ����� ֱ�ӷ���
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
		//�����û��encodeд��ȫ��
		return BaseCoreUtils.encodeString(str,
				new String[][] {
						{ " ", "&", "<", ">", "'", "\"", "\\r\\n" },
						{ "&nbsp;", "&amp;", "&lt;", "&gt;", "&apos;",
								"&quot;", "<br>" } });
	}

	/**
	 * Encode CJK characters to '[0000]".
	 * for example: �� --> [7528],   �� --> [8f6f]
	 */
	public static String cjkEncode(String text) {
		if (text == null) {
			return "";
		}

		StringBuffer newTextBuf = new StringBuffer();
		for (int i = 0, len = text.length(); i < len; i++) {
			char ch = text.charAt(i);
			if (ch >= 128 || ch == 91 || ch == 93) {//:91 is '[', 93 is ']'.ת����ascii + '[' + ']'
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
	 * for example: [7528] --> ��, [8f6f] --> ��
	 */
	public static String cjkDecode(String text) throws Exception {
		if (text == null) {
			return "";
		}

		//����û�� "[", ֱ�ӷ���.
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
					//����Ҫ�ǿ���[CDATA[]]������ֵ�ĳ���
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
			if (string.charAt(i) == '\\') {//���һ��"\\",���˫��.
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
	 * �ж��Ƿ���cjkEncode�����ַ���
	 * �������[]�м������]���ַ�,��ô�⼸���ַ�������5b����5d����[0-9a-fA-F]{4},����Ͳ��Ǳ�cjkEncode����
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
			
			// �������[]�м���5b����5d����[0-9a-fA-F]{4},��ô�п�����encode����,Ҫ�������
			// richer:[0-9a-fA-F]{3}ͬ���п�����encode����,���������ַ����Ϊ[444]
			if(textPattern.matcher(sub_text).matches()) {
				continue;
			}
			
			// ���������,��ô�Ͳ���encode����,ֱ�ӷ���false
			return false;
		}
		
		return b;
	}
	
    /**
	 * :���ַ�������.
	 * 
	 * @param text
	 *            the old text
	 * @return the passworded text.
	 */
    public static String passwordEncode(String text) {
        //�������֧��CJK��
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

            //ȷ������λ�����������ַ���.
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
            passwordText = passwordText.substring(3); //ȥ��ǰ���"___"��

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
