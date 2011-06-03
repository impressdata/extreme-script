package org.extreme.script;

public class ErrorNameException extends Exception {
    public ErrorNameException() {
        super();
    }
    
    public ErrorNameException(String msg) {
        super(msg);
    }
    
    public ErrorNameException(Throwable cause) {
        super(cause);
    }
    
    public ErrorNameException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
