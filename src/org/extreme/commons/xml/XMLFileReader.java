package org.extreme.commons.xml;

public interface XMLFileReader {
	/*
	 * ����xml�ļ���Root,���ؽ��
	 */
	public Object readFileContent(XMLableReader reader);
	
	/*
	 * ��xml�ļ��޷�����ʱ����ʲô?
	 * ����ֵ������xml�ļ�û��root����xml�ļ��޷�����
	 */
	public Object errorHandler();
}
