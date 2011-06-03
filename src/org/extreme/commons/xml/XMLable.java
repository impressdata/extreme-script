package org.extreme.commons.xml;


/**
 * This class can be saved to XML file.
 */
public interface XMLable extends XMLReadable, XMLWriter, java.io.Serializable {
	public static final String XML_TAG = "XMLable";
}