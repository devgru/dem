package ru.devg.dem.bundles.one2many;

import ru.devg.dem.bundles.HandlerBundle;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.filtering.TypeBoundedHandler;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version     0.175
 */
public final class SelectiveBroadcaster<E extends Event>
        implements HandlerBundle<TypeBoundedHandler<? extends E>,E> {

    private final Collection<TypeBoundedHandler<? extends E>> handlers =
            new LinkedList<TypeBoundedHandler<? extends E>>();


    public void handle(E event) {
        for (TypeBoundedHandler handler : handlers) {
            handler.handleIfPossible(event);
        }
    }

    public void addHandler(TypeBoundedHandler<? extends E> handler) {
        assert handler != null;
        handlers.add(handler);
    }

    /**
     * @param handler handler to remove.
     */
    public void removeHandler(TypeBoundedHandler<? extends E> handler) {
        handlers.remove(handler);
    }

}