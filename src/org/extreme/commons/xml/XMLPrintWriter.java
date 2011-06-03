package org.extreme.commons.xml;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import org.extreme.commons.util.BaseCoreUtils;
import org.extreme.commons.util.LogUtil;
import org.extreme.commons.util.StringUtils;


public class XMLPrintWriter {
	//XML Encode String Array.
    private static final String[][] decodeArray = {
        {"&amp;", "&lt;", "&gt;", "&apos;", "&quot;"}, {"&", "<", ">", "'", "\""}};
	
	public static final String XML_ENCODER = "UTF-8";
	protected static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"" + XML_ENCODER + "\"?>";
	protected static final String NEW_LINE = "\n";
	
	// ��ʶ�Ƿ��Ѿ���ӡ��Root
	private boolean rootPrinted;
	private java.util.LinkedList tagChain = new LinkedList();
	/*
	 * tagChain�е����һ��tag�ǲ���open״̬
	 * <tag:open
	 * <tag>:close
	 */
	private boolean isLastTagOpened = false;
	
	private PrintWriter writer;
	
	private boolean debug = false;
	
	/*
	 * :дxml��ʱ���Ƿ���Ҫ����
	 * Ϊ�˷������,д��cpt��xml����Ҫ���е�
	 * �������xml����,д�ɵ��ַ�����Ϊhtml dom������ֵ,�ͱ����<br>,����������,������writeAsString��ʱ��Ͳ�����
	 */
	private boolean wellFormatted = true;
	
	public static XMLPrintWriter create(java.io.OutputStream out) {
		return create(out, true);
	}
	
	public static XMLPrintWriter create(java.io.OutputStream out, boolean wellFormatted) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(new OutputStreamWriter(out, XML_ENCODER));
        } catch (UnsupportedEncodingException exp) {
            writer = new PrintWriter(new OutputStreamWriter(out));
        }
        
        return create(writer, wellFormatted);
	}
	
	public static XMLPrintWriter create(java.io.Writer writer) {
		return create(writer, true);
	}
	
	public static XMLPrintWriter create(java.io.Writer writer, boolean wellFormatted) {
		return create(new PrintWriter(writer), wellFormatted);
	}
	
	public static XMLPrintWriter create(java.io.PrintWriter writer) {
		return create(writer, true);
	}
	
	public static XMLPrintWriter create(PrintWriter writer, boolean wellFormatted) {
		return new XMLPrintWriter(writer, wellFormatted);
	}
	
	private XMLPrintWriter(PrintWriter writer, boolean wellFormatted) {
		this.writer = writer;
		this.wellFormatted = wellFormatted;
	}
	
	private void export(String str) {
		writer.print(str);
		if (debug) {
			LogUtil.getLogger().info(str);
		}
	}
	
	public XMLPrintWriter startTAG(String tagName) {
		if (!rootPrinted) {
			return startRoot(tagName);
		} else {
			closeLastTagOfChain();
			
			export("<" + tagName);
			tagChain.add(tagName);
			
			isLastTagOpened = true;
			return this;
		}
	}
	
	private XMLPrintWriter startRoot(String rootName) {
		// :������println,��Ϊprintlnд����\r\n,����\n
		export(XML_HEADER);
		if (wellFormatted) {export(NEW_LINE);}
		rootPrinted = true;
		
		return this;
	}
	
	public XMLPrintWriter textNode(String data) {
		closeLastTagOfChain();
		
		export(BaseCoreUtils.join(new String[] {
			"<![CDATA[", XMLEncodeUtils.cdataEncode(data), "]]>"
		}));
		
		return this;
	}
	
	private void closeLastTagOfChain() {
		if (isLastTagOpened) {
			export(">");
			if (wellFormatted) {export(NEW_LINE);}
			isLastTagOpened = false;
		}
	}
	
	public XMLPrintWriter attr(String name, long value) {
		return attr(name, String.valueOf(value));
	}
	
	public XMLPrintWriter attr(String name, boolean b) {
		return attr(name, String.valueOf(b));
	}
	
	public XMLPrintWriter attr(String name, int i) {
		return attr(name, String.valueOf(i));
	}
	
	public XMLPrintWriter attr(String name, float f) {
		return attr(name, String.valueOf(f));
	}
	
	public XMLPrintWriter attr(String name, double d) {
		return attr(name, String.valueOf(d));
	}
	
	public XMLPrintWriter attr(String name, String value) {
		if(value == null) return this;
		
		export(BaseCoreUtils.join(new String[] {
			StringUtils.BLANK, name, "=\"", xmlAttrEncode(value), "\""
		}));
		
		return this;
	}
	
	public static String xmlAttrEncode(String str) {
        /**
         * ȥ��XML�ļ����Ե��е������ַ�.
         */
        return BaseCoreUtils.encodeString(str, decodeArray);
    }
	
	public XMLPrintWriter end() {
		String lastTag = (String)tagChain.removeLast();
		if (isLastTagOpened) {
			export("/>");
			if (wellFormatted) {export(NEW_LINE);}
		} else {
			export("</" + lastTag + ">");
			if (wellFormatted) {export(NEW_LINE);}
		}
		
		isLastTagOpened = false;
		
		return this;
	}
	
	public void flush() {
		writer.flush();
	}
	
	/*
	 * ��xml����ʱ�����������,�������ж�д��xml�ļ��Ƿ�Ϸ�
	 */
	public void close() {
		if (tagChain.size() > 0) {
			String[] unclosedTAGs = (String[])this.tagChain.toArray(new String[this.tagChain.size()]);
			throw new RuntimeException(BaseCoreUtils.join(new String[] {
					BaseCoreUtils.join(unclosedTAGs, ","),
					unclosedTAGs.length > 1 ? "are" : "is",
					"not closed."
			}, StringUtils.BLANK));
		}
		
		writer.close();
	}
}
