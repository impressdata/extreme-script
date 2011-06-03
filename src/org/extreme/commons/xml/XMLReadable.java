package org.extreme.commons.xml;

/**
 * 可以读取XML的那些应该实现这个接口, 从某种程度上来说像一个Listener
 * 
 * @author  Wang
 * @since 6.5
 *
 */
public interface XMLReadable {
	/**
	 * 读取子节点，应该会被XMLableReader.readXMLObject()调用多次
	 * @param reader
	 * @see XMLableReader
	 */
	public void readXML(XMLableReader reader);
}
