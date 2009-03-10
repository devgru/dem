package ru.devg.dem.bundles.one2many;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.bundles.HandlerBundle;

import java.util.LinkedList;

/**
 * MultiBroadcaster is a bundle of handlers. On
 * {@link MultiBroadcaster#handle(ru.devg.dem.quanta.Event)}
 * it passes an {@link ru.devg.dem.quanta.Event} to every contained handler.
 *
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.17
 */
public final class MultiBroadcaster<E extends Event>
        implements HandlerBundle<Handler<E>,E> {

    private final LinkedList<Handler<E>> handlers =
            new LinkedList<Handler<E>>();

    public static <E extends Event> MultiBroadcaster<E> hey() {
        return new MultiBroadcaster<E>();
    }

    public void handle(E event) {
        for (Handler<? super E> handler : handlers) {
            handler.handle(event);
        }
    }

    public void addHandler(Handler<E> handler) {
        assert handler != null;
        handlers.add(handler);
    }

    public void removeHandler(Handler<E> handler) {
        handlers.remove(handler);
    }
}
