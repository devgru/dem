package ru.devg.hmt.tunnel.exceptions;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.0
 */
public class NoResponseCanBeGiven extends Throwable {
    public NoResponseCanBeGiven(Class cls) {
        super("This handler can't give any respond to the request of " + cls.getName() + " type");
    }
}
