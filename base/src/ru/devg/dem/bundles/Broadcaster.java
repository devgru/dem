package ru.devg.dem.bundles;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.LinkedList;

/**
 * Broadcaster is a bundle of handlers. On
 * {@link Broadcaster#handle(ru.devg.dem.quanta.Event)}
 * it passes an {@link ru.devg.dem.quanta.Event} to every contained handler.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.17
 */
public final class Broadcaster<E extends Event>
        implements HandlerBundle<Handler<? super E>, E> {

    private final LinkedList<Handler<? super E>> handlers =
            new LinkedList<Handler<? super E>>();

    public Broadcaster() {
    }

    public Broadcaster(Handler<? super E>... handlers) {
        for (Handler<? super E> handler : handlers) {
            addHandler(handler);
        }
    }

    public static <E extends Event> Broadcaster<E> one() {
        return new Broadcaster<E>();
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
