package org.extreme.commons;

import org.extreme.commons.util.ComparatorUtils;
import org.extreme.commons.util.StringUtils;
import org.extreme.script.Calculator;


/** 
 * Formula.
 */
public class Formula {

    private String content = StringUtils.EMPTY;
    private transient Object result = null; //The result of Formula.

    /**
     * Constructor
     */
    public Formula() {
    }

    /**
     * Constructor
     */
    public Formula(String content) {
        this.setContent(content);
    }

    public static boolean canBeFormula(String content) {
        return content.trim().startsWith("=");
    }

    /**
     * Gets the content of formula.
     */
    public String getContent() {
        if (this.content.trim().startsWith("=")) {
            return this.content;
        } else {
            return "=" + this.content;
        }
    }

    /**
     * Sets content to formula.
     */
    public void setContent(String content) {
        this.content = content;

        if (this.content.trim().startsWith("=")) {
            this.content = this.content.trim().substring(1);
        }
    }

    /**
     * Gets the result.
     */
    public Object getResult() {
        return result;
    }

    /**
     * Sets the result.
     */
    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isEmpty() {
        return "=".equals(this.getContent());
    }

    public boolean isResult() {
        return false;
    }

    /**
     * Equals.
     */
    public boolean equals(Object obj) {
        return obj instanceof Formula
            && ComparatorUtils.equals(this.getContent(), ((Formula) obj).getContent());
    }

    /**
     * To string.
     */
    public String toString() {
        return this.getContent();
    }

    /**
     * Clone.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
