package ru.devg.dem.inclass.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public class ClassIsUnbindableException extends Exception {

    public ClassIsUnbindableException(Throwable cause) {
        super(cause);
    }

    public ClassIsUnbindableException(String message, Throwable cause) {
        super(message, cause);
    }
}
