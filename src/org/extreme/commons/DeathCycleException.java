package org.extreme.commons;

public class DeathCycleException extends RuntimeException {
    /**
     * Constructs a <code>DeathCycleException</code> without a detail message.
     */
    public DeathCycleException() {
        super();
    }

    /**
     * Constructs a <code>DeathCycleException</code> with a detail message.
     *
     * @param s the detail message.
     */
    public DeathCycleException(String s) {
        super(s);
    }
}
