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
	
	// 标识是否已经打印了Root
	private boolean rootPrinted;
	private java.util.LinkedList tagChain = new LinkedList();
	/*
	 * tagChain中的最后一个tag是不是open状态
	 * <tag:open
	 * <tag>:close
	 */
	private boolean isLastTagOpened = false;
	
	private PrintWriter writer;
	
	private boolean debug = false;
	
	/*
	 * :写xml的时候是否需要换行
	 * 为了方便调试,写成cpt的xml是需要换行的
	 * 但是如果xml换行,写成的字符串作为html dom的属性值,就变成了<br>,就有问题了,所以在writeAsString的时候就不换行
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
		// :不能用println,因为println写的是\r\n,不是\n
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
         * 去掉XML文件属性当中的特殊字符.
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
	 * 在xml结束时调用这个方法,有助于判断写的xml文件是否合法
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
