package org.extreme.commons.xml;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class XMLWriterHelper {
	
	public static XmlStartTag startTag(String tagName) {
		return new XmlStartTag(tagName);
	}
	
	public static XmlStartTag completeTagWithNoChildNode(String tagName) {
		return new XmlStartTag(tagName, true);
	}
	
	public static void startTagWithNoAttribute(PrintWriter writer, String tagName) {
		writer.print("<" + tagName + ">");
	}

	public static void endTag(PrintWriter writer, String tagName) {
		writer.print("</" + tagName + ">");
	}
	
	public static class XmlStartTag {
		private String tagName;
		private java.util.Map attributes;
		private boolean withNoChildNode = false;
		
		private XmlStartTag(String tagName) {
			this.tagName = tagName;
		}
		
		private XmlStartTag(String tagName, boolean withNodChildNode) {
			this(tagName);
			this.withNoChildNode = withNodChildNode;
		}
		
		public XmlStartTag attr(String name, String val) {
			if (val == null) {
				return this;
			}
			
			if(attributes == null) {
				attributes = new java.util.HashMap();
			}
			
			attributes.put(name, val);
			
			return this;
		}
		
		public XmlStartTag attr(String name, String val, String defaultVal) {
			if (val == null || val.equals(defaultVal)) {
				return this;
			}
			
			return attr(name, val);
		}
		
		public XmlStartTag attr(String name, boolean val) {
			return attr(name, Boolean.toString(val));
		}
		
		public XmlStartTag attr(String name, boolean val, boolean defaultVal) {
			if (val == defaultVal) {
				return this;
			}
			
			return attr(name, val);
		}
		
		public XmlStartTag attr(String name, byte val) {
			return attr(name, Byte.toString(val));
		}
		
		public XmlStartTag attr(String name, byte val, byte defaultVal) {
			if (val == defaultVal) {
				return this;
			}
			
			return attr(name, val);
		}
		
		public XmlStartTag attr(String name, int val) {
			return attr(name, Integer.toString(val));
		}
		
		public XmlStartTag attr(String name, int val, int defaultVal) {
			if (val == defaultVal) {
				return this;
			}
			
			return attr(name, val);
		}
		
		public XmlStartTag attr(String name, double val) {
			return attr(name, Double.toString(val));
		}
		
		public XmlStartTag attr(String name, double val, double defaultVal) {
			if (val == defaultVal) {
				return this;
			}
			
			return attr(name, val);
		}
		
		public void write(PrintWriter writer) {
			
			writer.write(new StringBuffer().append('<').append(tagName).toString());
			
			writer.write(attributeBuffer().toString());
			
			if (withNoChildNode) {
				writer.write("/>");
			} else {
				writer.write(">");
			}
		}
		
		private StringBuffer attributeBuffer() {
			StringBuffer sb = new StringBuffer();
			
			if(attributes != null) {
				java.util.Iterator enIt = this.attributes.entrySet().iterator();
				while(enIt.hasNext()) {
					java.util.Map.Entry entry = (java.util.Map.Entry)enIt.next();
					sb.append(' ').append(entry.getKey()).append("=\"").append(XMLPrintWriter.xmlAttrEncode(entry.getValue().toString())).append('"');
				}
			}
			
			return sb;
		}
		
		public String toString() {
			java.io.StringWriter sw = new StringWriter();
			write(new PrintWriter(sw));
			
			return sw.getBuffer().toString();
		}
	}
}
