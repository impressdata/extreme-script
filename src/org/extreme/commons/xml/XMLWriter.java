package org.extreme.commons.xml;


public interface XMLWriter {
	/**
     * Write XML.<br>
     * The method will be invoked when save data to XML file.<br>
     * May override the method to save your own data.
     * 从性能上面考虑，大家用writer.print(), 而不是writer.println()
     *
     * @param writer the PrintWriter.
     */
	public void writeXML(XMLPrintWriter writer);
}
