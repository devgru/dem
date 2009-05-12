package ru.devg.dem.inclass.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public class ClassNotExtendsSourceException extends Exception {
    public ClassNotExtendsSourceException() {
        super();
    }

    public ClassNotExtendsSourceException(Throwable cause) {
        super(cause);
    }

    public ClassNotExtendsSourceException(String message) {
        super(message);
    }

    public ClassNotExtendsSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
