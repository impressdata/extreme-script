package org.extreme.script.function;

import java.util.regex.Pattern;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public class REGEXP extends AbstractFunction {
	public Object run(Object[] args) {
		if(args.length < 2) {
			return Primitive.ERROR_NAME;
		}
		
		Object str = args[0];
		Object re = args[1];
		int flags = 0;
		if(args.length > 2) {
			Object ob = args[2];
			if(ob instanceof Number) {
				flags = ((Number)ob).intValue();
			}
		}
		
		Pattern pattern = Pattern.compile(re.toString(), flags);
		return Boolean.valueOf(pattern.matcher(str.toString()).matches());
	}
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "REGEXP(str, pattern):字符串str是否与正则表达式pattern相匹配。\n"
		+"示例：\n"
		+"REGEXP(\"aaaaac\",\"a*c\")等于true。\n"
		+"REGEXP(\"abc\",\"a*c\")等于false。\n"
		+"\n"
		+"REGEXP(str, pattern,  intNumber):字符串str是否与具有给定模式 intNumber的正则表达式pattern相匹配。\n"
		+"示例：\n"
		+"CASE_INSENSITIVE = 0         启用不区分大小写的匹配。 默认情况下，不区分大小写的匹配假定仅匹配 US-ASCII 字符集中的字符。可以通过指                                                         定 UNICODE_CASE 标志连同此标志来启用 Unicode 感知的、不区分大小写的匹配。 \n"
		+"MULTILINE = 1                          启用多行模式。\n"
		+"DOTALL = 2                               启用 dotall 模式。在 dotall 模式中，表达式 . 可以匹配任何字符，包括行结束符。默认情况下，此表达式不匹配行                                                         结束符。\n"
		+"UNICODE_CASE = 3               启用 Unicode 感知的大小写折叠。指定此标志后，由 CASE_INSENSITIVE 标志启用时，不区分大小写的匹配将以                                                        符合 Unicode Standard 的方式完成。\n"
		+"CANON_EQ = 4                         启用规范等价。 指定此标志后，当且仅当其完整规范分解匹配时，两个字符才可视为匹配。\n"
		+"UNIX_LINES = 5                        启用 Unix 行模式。 在此模式中，.、^ 和 $ 的行为中仅识别 \'\\n\' 行结束符。\n"
		+"LITERAL = 6                               启用模式的字面值解析。 指定此标志后，指定模式的输入字符串就会作为字面值字符序列来对待。输入序列中的                                                          元字符或转义序列不具有任何特殊意义。 标志 CASE_INSENSITIVE 和 UNICODE_CASE 在与此标志一起使用时将                                                        对匹配产生影响。其他标志都变得多余了。\n"
		+"COMMENTS = 7                        模式中允许空白和注释。 此模式将忽略空白和在结束行之前以 # 开头的嵌入式注释。\n"
		+" \n"
		+"REGEXP(\"Aaaaabbbbc\",\"a*b*c\", 3)等于true。\n"
		+"REGEXP(\"Aaaaabbbbc\",\"a*b*c\", 1)等于false。\n"
		+"\n"
		+"";
	}
	public String getEN(){
		return "";
	}
}