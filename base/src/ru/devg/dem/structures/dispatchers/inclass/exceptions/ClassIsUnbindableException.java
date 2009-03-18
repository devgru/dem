package ru.devg.dem.structures.dispatchers.inclass.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.0
 */
public class ClassIsUnbindableException extends Exception{
    public ClassIsUnbindableException() {
        super();
    }

    public ClassIsUnbindableException(Throwable cause) {
        super(cause);
    }

    public ClassIsUnbindableException(String message) {
        super(message);
    }

    public ClassIsUnbindableException(String message, Throwable cause) {
        super(message, cause);
    }
}
