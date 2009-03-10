package ru.devg.hmt.tunnel.exceptions;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.0
 */
public class NoAssociatedHandlerFound extends Throwable {
    public NoAssociatedHandlerFound(String className) {
        super("No handler that can respond to request of " + className + " type found");
    }
}
