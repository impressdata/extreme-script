package org.extreme.script;

public class ErrorValueException extends Exception {
    public ErrorValueException() {
        super();
    }
    
    public ErrorValueException(String msg) {
        super(msg);
    }
    
    public ErrorValueException(Throwable cause) {
        super(cause);
    }
    
    public ErrorValueException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
