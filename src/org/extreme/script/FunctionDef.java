/*
 * Apache Licence2.0
 */
package org.extreme.script;

import org.extreme.commons.util.StringUtils;
import org.extreme.commons.xml.XMLPrintWriter;
import org.extreme.commons.xml.XMLable;
import org.extreme.commons.xml.XMLableReader;

/**
 * The definition of the function.
 * Function({@link org.extreme.script.Function}) interface.
 */
public class FunctionDef implements XMLable {
    private String name = StringUtils.EMPTY;
    private String description = StringUtils.EMPTY;

    private String className;

    /**
     * Constructor.
     */
    public FunctionDef() {
        this(StringUtils.EMPTY, StringUtils.EMPTY);
    }

    /**
     * Constructor.
     */
    public FunctionDef(String name, String className) {
        this(name, StringUtils.EMPTY, className);
    }

    /**
     * Constructor.
     */
    public FunctionDef(String name, String description, String className) {
        this.setName(name);
        this.setDescription(description);

        this.setClassName(className);
    }

    /**
     * Return function name, the name must be only.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set function name, the name must be only.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the description of function.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of function.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the class name of function.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the class name of function.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Read XML.<br>
     * The method will be invoked when read data from XML file.<br>
     * May override the method to read the data that you saved.
     *
     * @param element the element.
     */
    public void readXML(XMLableReader reader) {

    }

    /**
     * Write XML.<br>
     * The method will be invoked when save data to XML file.<br>
     * May override the method to save your own data.
     *
     * @param writer the PrintWriter.
     */
    public void writeXML(XMLPrintWriter writer) {

    }

    /**
     * Clone.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}