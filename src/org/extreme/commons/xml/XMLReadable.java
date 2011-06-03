package org.extreme.commons.xml;

/**
 * ���Զ�ȡXML����ЩӦ��ʵ������ӿ�, ��ĳ�̶ֳ�����˵��һ��Listener
 * 
 * @author  Wang
 * @since 6.5
 *
 */
public interface XMLReadable {
	/**
	 * ��ȡ�ӽڵ㣬Ӧ�ûᱻXMLableReader.readXMLObject()���ö��
	 * @param reader
	 * @see XMLableReader
	 */
	public void readXML(XMLableReader reader);
}
