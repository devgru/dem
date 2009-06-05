package dem.inclass.binding.exceptions;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public class ElementIsUnbindableException extends Exception {

    public ElementIsUnbindableException(Throwable cause) {
        super(cause);
    }

    public ElementIsUnbindableException(String message) {
        super(message);
    }

}