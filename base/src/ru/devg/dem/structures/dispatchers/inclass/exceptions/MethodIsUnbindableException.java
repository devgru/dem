package ru.devg.dem.structures.dispatchers.inclass.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.0
 */
public class MethodIsUnbindableException extends Exception{
    public MethodIsUnbindableException() {
        super();
    }

    public MethodIsUnbindableException(Throwable cause) {
        super(cause);
    }

    public MethodIsUnbindableException(String message) {
        super(message);
    }

    public MethodIsUnbindableException(String message, Throwable cause) {
        super(message, cause);
    }
}