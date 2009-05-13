package ru.devg.dem.inclass.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public class MethodIsUnbindableException extends ElementIsUnbindableException {

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