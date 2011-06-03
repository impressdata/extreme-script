package org.extreme.commons.xml;

public interface XMLFileReader {
	/*
	 * 根据xml文件的Root,返回结果
	 */
	public Object readFileContent(XMLableReader reader);
	
	/*
	 * 当xml文件无法解析时返回什么?
	 * 最常出现的情况是xml文件没有root导致xml文件无法解析
	 */
	public Object errorHandler();
}
