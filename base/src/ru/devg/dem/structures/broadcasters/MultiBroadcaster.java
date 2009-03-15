package ru.devg.dem.structures.broadcasters;

import ru.devg.dem.structures.HandlerBundle;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

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
        implements HandlerBundle<Handler<? super E>, E> {

    private final LinkedList<Handler<? super E>> handlers =
            new LinkedList<Handler<? super E>>();

    public MultiBroadcaster() {
    }

    public MultiBroadcaster(Handler<? super E>... handlers) {
        for(Handler<? super E> handler : handlers){
            addHandler(handler);
        }
    }

    public static <E extends Event> MultiBroadcaster<E> one() {
        return new MultiBroadcaster<E>();
    }

    public void handle(E event) {
        for (Handler<? super E> handler : handlers) {
            handler.handle(event);
        }
    }

    public void addHandler(Handler<? super E> handler) {
        assert handler != null;
        handlers.add(handler);
    }

    public void removeHandler(Handler<? super E> handler) {
        handlers.remove(handler);
    }
}
