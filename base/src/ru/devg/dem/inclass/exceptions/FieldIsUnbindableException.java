package ru.devg.dem.inclass.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public class FieldIsUnbindableException extends ElementIsUnbindableException {

    public FieldIsUnbindableException(Throwable cause) {
        super(cause);
    }

    public FieldIsUnbindableException(String message) {
        super(message);
    }

    public FieldIsUnbindableException(String message, Throwable cause) {
        super(message, cause);
    }
}