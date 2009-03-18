package ru.devg.dem.structures.dispatchers.inclass.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.0
 */
public class FieldIsUnbindableException extends Exception{
    public FieldIsUnbindableException() {
        super();
    }

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