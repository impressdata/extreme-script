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
		return "REGEXP(str, pattern):�ַ���str�Ƿ���������ʽpattern��ƥ�䡣\n"
		+"ʾ����\n"
		+"REGEXP(\"aaaaac\",\"a*c\")����true��\n"
		+"REGEXP(\"abc\",\"a*c\")����false��\n"
		+"\n"
		+"REGEXP(str, pattern,  intNumber):�ַ���str�Ƿ�����и���ģʽ intNumber��������ʽpattern��ƥ�䡣\n"
		+"ʾ����\n"
		+"CASE_INSENSITIVE = 0         ���ò����ִ�Сд��ƥ�䡣 Ĭ������£������ִ�Сд��ƥ��ٶ���ƥ�� US-ASCII �ַ����е��ַ�������ͨ��ָ                                                         �� UNICODE_CASE ��־��ͬ�˱�־������ Unicode ��֪�ġ������ִ�Сд��ƥ�䡣 \n"
		+"MULTILINE = 1                          ���ö���ģʽ��\n"
		+"DOTALL = 2                               ���� dotall ģʽ���� dotall ģʽ�У����ʽ . ����ƥ���κ��ַ��������н�������Ĭ������£��˱��ʽ��ƥ����                                                         ��������\n"
		+"UNICODE_CASE = 3               ���� Unicode ��֪�Ĵ�Сд�۵���ָ���˱�־���� CASE_INSENSITIVE ��־����ʱ�������ִ�Сд��ƥ�佫��                                                        ���� Unicode Standard �ķ�ʽ��ɡ�\n"
		+"CANON_EQ = 4                         ���ù淶�ȼۡ� ָ���˱�־�󣬵��ҽ����������淶�ֽ�ƥ��ʱ�������ַ��ſ���Ϊƥ�䡣\n"
		+"UNIX_LINES = 5                        ���� Unix ��ģʽ�� �ڴ�ģʽ�У�.��^ �� $ ����Ϊ�н�ʶ�� \'\\n\' �н�������\n"
		+"LITERAL = 6                               ����ģʽ������ֵ������ ָ���˱�־��ָ��ģʽ�������ַ����ͻ���Ϊ����ֵ�ַ��������Դ������������е�                                                          Ԫ�ַ���ת�����в������κ��������塣 ��־ CASE_INSENSITIVE �� UNICODE_CASE ����˱�־һ��ʹ��ʱ��                                                        ��ƥ�����Ӱ�졣������־����ö����ˡ�\n"
		+"COMMENTS = 7                        ģʽ������հ׺�ע�͡� ��ģʽ�����Կհ׺��ڽ�����֮ǰ�� # ��ͷ��Ƕ��ʽע�͡�\n"
		+" \n"
		+"REGEXP(\"Aaaaabbbbc\",\"a*b*c\", 3)����true��\n"
		+"REGEXP(\"Aaaaabbbbc\",\"a*b*c\", 1)����false��\n"
		+"\n"
		+"";
	}
	public String getEN(){
		return "";
	}
}