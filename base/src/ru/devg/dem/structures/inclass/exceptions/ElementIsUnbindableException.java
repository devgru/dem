package ru.devg.dem.structures.inclass.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public class ElementIsUnbindableException extends Exception{
    public ElementIsUnbindableException() {
        super();
    }

    public ElementIsUnbindableException(Throwable cause) {
        super(cause);
    }

    public ElementIsUnbindableException(String message) {
        super(message);
    }

    public ElementIsUnbindableException(String message, Throwable cause) {
        super(message, cause);
    }
}