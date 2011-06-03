package org.extreme.commons.xml;

import java.awt.Color;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.extreme.commons.util.Utils;

import com.sun.xml.stream.ZephyrParserFactory;

/**
 * @see XMLReadable
 *
 */
public class XMLableReader {
	private static final int NULL_STATE = -1;
	private static final int ATTRIBUTE = 0;
	private static final int CHILD_NODE = 1;
	private static final int CHARACTERS = 2;
	
	// 当前进行的XMLEventReader
	private XMLEventReader xmlEventReader;
	// 当前的StartElement
	private StartElement startElement;
	
	private XMLEvent currentEvent;
	
	private int deep = 0;
	
	private int state = NULL_STATE;
	
	public static XMLableReader createXMLableReader(String xml) throws XMLStreamException {
		return createXMLableReader(new StringReader(xml));
	}
	
	public static XMLableReader createXMLableReader(Reader inputStreamReader) throws XMLStreamException {
		/*
		 * :原来是用的XMLInputFactory.newInstance(),会根据不一样的classpath加载不一样的类
		 * 遇到两个bug,分别是在weblogic和resin环境下,生成的类不是ZephyrParserFactory,在读CDATA的时候就有问题了
		 * 所以,直接用ZephyrParserFactory,这个我们保存在ThirdTool下面的类
		 * 见bug @0004930
		 */
		XMLInputFactory factory = new ZephyrParserFactory();
		
		if (factory.isPropertySupported("report-cdata-event")) {
			factory.setProperty("report-cdata-event", Boolean.TRUE);
		} else if (factory.isPropertySupported("http://java.sun.com/xml/stream/properties/report-cdata-event")) {
			factory.setProperty("http://java.sun.com/xml/stream/properties/report-cdata-event", Boolean.TRUE);
		}
		
        XMLEventReader reader = factory.createXMLEventReader(inputStreamReader);
        
        XMLableReader xmlReader = new XMLableReader(reader);
        
        if (xmlReader.initRootElement() == null) {
        	return null;
        }
        
        return xmlReader;
	}
	
	private XMLableReader(XMLEventReader xmlEventReader) {
		this.xmlEventReader = xmlEventReader;
	}
	
	private StartElement initRootElement() {
		try {
			while (xmlEventReader.hasNext()) {
			 	currentEvent = xmlEventReader.nextEvent();
	        	if (currentEvent.isStartElement()) {
	        		deep++;
	        		startElement = currentEvent.asStartElement();
	        		break;
	        	}
	        }
		} catch (XMLStreamException e) {
			if (startElement != null) {
				e.printStackTrace();
			}
		}
		
		return startElement;
	}
	
	public XMLableReader(XMLEventReader xmlEventReader, StartElement startElement) {
		this.xmlEventReader = xmlEventReader;
		this.startElement = startElement;
		this.currentEvent = this.startElement;
	}

	public void setXMLEventReader(XMLEventReader xmlEventReader) {
		this.xmlEventReader = xmlEventReader;
	}
	
	public XMLEventReader getXMLEventReader() {
		return this.xmlEventReader;
	}
	
	//////////////////////////////////////////////////
	//	读取XML时会调用的一些方法
	//////////////////////////////////////////////////
	/**
	 * 得到当前节点的名字
	 */
	public String getTagName() {
		if (startElement == null) {
			return null;
		}
		
		return startElement.getName().toString();
	}
	
	protected static String cdataDecode(String data) {
		return XMLEncodeUtils.cdataDecode(data);
	}
	
	/**
	 * 当一个元素的子节点中没有元素，只有文本时，可以调用这个方法
	 * @see XMLEventReader#getElementText
	 * @return
	 */
	public String getElementValue() {
		if (! currentEvent.isStartElement()) {
			throw new IllegalStateException("current state must be StartElement");
		}
		
		// 这边没有考虑多个的情况
		try {
			StringBuffer content = new StringBuffer();
			currentEvent = xmlEventReader.nextEvent();
			int eventType = currentEvent.getEventType();
			while(eventType != XMLStreamConstants.END_ELEMENT ) {
	            if(currentEvent.isCharacters() && currentEvent.asCharacters().isCData()) {
	            	content.append(cdataDecode(currentEvent.asCharacters().getData()));
	            } else if (eventType == XMLStreamConstants.CDATA || eventType == XMLStreamConstants.CHARACTERS || eventType == XMLStreamConstants.SPACE
	    	            || eventType == XMLStreamConstants.ENTITY_REFERENCE) {
	            	String data = currentEvent.asCharacters().getData();
	            	if(data.matches("\\s+")) {
	            		// skipping
	            	} else {
	            		content.append(data.trim());
	            	}
	            } else if(eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
	            || eventType == XMLStreamConstants.COMMENT) {
	                // skipping
	            } else if(eventType == XMLStreamConstants.END_DOCUMENT) {
	                throw new XMLStreamException("unexpected end of document when reading element text content");
	            } else if(eventType == XMLStreamConstants.START_ELEMENT) {
	                throw new XMLStreamException(
	                "elementGetText() function expects text only elment but START_ELEMENT was encountered.", currentEvent.getLocation());
	            } else {
	                throw new XMLStreamException(
	                "Unexpected event type "+ eventType, currentEvent.getLocation());
	            }
	            currentEvent = xmlEventReader.nextEvent();
	            eventType = currentEvent.getEventType();
	        }
			
			deep --;
			
			if(content.length() == 0) {
				return null;
			} else {
				return content.toString();
			}
		} catch (XMLStreamException xml_e) {
			xml_e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * 读取Attribute，同时还可以做一些预处理
	 * @return
	 */
	public boolean isAttr() {
		return state == ATTRIBUTE;
	}
	
	/**
	 * 判断某个子节点是否是文本
	 * @return
	 */
	public boolean isCharacters() {
		return state == CHARACTERS;
	}
	
	/**
	 * 判断某个子节点是否是Element
	 * @return
	 */
	public boolean isChildNode() {
		return state == CHILD_NODE;
	}
	
	/**
	 * 得到当前节点的某个属性
	 * @param attrName
	 * @return
	 */
	public String getAttrAsString(String attrName, String defaultValue) {
		String value = getAttr(attrName);
		return value == null ? defaultValue : value;
	}
	
	public int getAttrAsInt(String attrName, int defaultValue) {
		String value = getAttr(attrName);
		return value == null ? defaultValue : Utils.string2Number(value).intValue();
	}
	
	public long getAttrAsLong(String attrName, long defaultValue) {
		String value = getAttr(attrName);
		return value == null ? defaultValue : Utils.string2Number(value).longValue();
	}
	
	public float getAttrAsFloat(String attrName, float defaultValue) {
		String value = getAttr(attrName);
		return value == null ? defaultValue : Utils.string2Number(value).floatValue();
	}
	
	public double getAttrAsDouble(String attrName, double defaultValue) {
		String value = getAttr(attrName);
		return value == null ? defaultValue : Utils.string2Number(value).doubleValue();
	}
	
	public byte getAttrAsByte(String attrName, byte defaultValue) {
		String value = getAttr(attrName);
		return value == null ? defaultValue : Byte.parseByte(value);
	}
	
	public boolean getAttrAsBoolean(String attrName, boolean defaultValue) {
		String value = getAttr(attrName);
		return value == null ? defaultValue : value.equalsIgnoreCase("true");
	}
	
	public Color getAttrAsColor(String attrName, Color defaultValue) {
		String value = getAttr(attrName);
		return value == null ? defaultValue : new Color(Utils.string2Number(value).intValue(), true);
	}
	
	private String getAttr(String attrName) {
		if (startElement == null) {
			throw new IllegalStateException("StartElement should not be null...");
		}
		
		javax.xml.stream.events.Attribute attr = startElement.getAttributeByName(QName.valueOf(attrName));
		return attr == null ? null : attr.getValue();
	}
	
	/**
	 * 配合isCharacters()使用，用来读取文本
	 * @return
	 */
	public String getContent() {
		if (state != CHARACTERS) {
			throw new IllegalStateException("Current State should be Characters...");
		}
		
		return currentEvent.asCharacters().getData();
	}
	
	
	/**
	 * 读取XML生成某个对象XMLObject
	 * 这个方法在调用前应该触发event : StartElement，同时用XML_TAG来判断
	 * 该用哪个XMLObject作为参数传进来，从某种程度上来说XMLObject像个Visitor.
	 * 同时将XMLObject作为返回值传出去，用来连接
	 * @param xmlObject
	 * @return
	 */
	public Object readXMLObject(XMLReadable xmlObject) {
		if (xmlObject == null) {
			throw new NullPointerException();
		}
		
		int cur_deep = deep;
		
		// 读当前节点的属性
		this.state = ATTRIBUTE;
		xmlObject.readXML(this);	// read attr
		this.state = NULL_STATE;
		
		if (deep < cur_deep) {	// isAttr时可能又读过一遍，导致deep < cur_deep, over 
			return xmlObject;
		}
		
		try {
			while(xmlEventReader.hasNext()) {
	    		currentEvent = xmlEventReader.nextEvent();
	    		
	    		if (currentEvent.isStartElement()) {	// 开始读子节点
	    			deep ++;
	    			startElement = currentEvent.asStartElement();
	    			
	    			// 子节点应该在这个方法内部就会结束
	    			this.state = CHILD_NODE;
	    			xmlObject.readXML(this);	// read child_node
	    			this.state = NULL_STATE;
	    		} else if (currentEvent.isEndElement()) {
	    			if (deep <= cur_deep) {
	    				deep --;	// 结束读当前节点
	    				break;
	    			} else {
	    				deep --;
	    			}
	    		} else if (currentEvent.isCharacters()  && 
	    				! (! currentEvent.asCharacters().isCData() && currentEvent.asCharacters().getData().equals("\n"))) {	// 说明当前是文本节点
	    			this.state = CHARACTERS;
	    			xmlObject.readXML(this);
	    			this.state = NULL_STATE;
	    		}
	    	}
		} catch (XMLStreamException xml_e) {
			throw new RuntimeException(xml_e);
		}
		
		return xmlObject;
	}
	
	public void close() throws XMLStreamException {
		this.xmlEventReader.close();
	}
}