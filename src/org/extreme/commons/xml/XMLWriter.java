package org.extreme.commons.xml;


public interface XMLWriter {
	/**
     * Write XML.<br>
     * The method will be invoked when save data to XML file.<br>
     * May override the method to save your own data.
     * ���������濼�ǣ������writer.print(), ������writer.println()
     *
     * @param writer the PrintWriter.
     */
	public void writeXML(XMLPrintWriter writer);
}
