package ru.devg.dem.inclass.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
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
