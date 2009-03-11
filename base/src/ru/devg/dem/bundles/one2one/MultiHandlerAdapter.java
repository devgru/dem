package ru.devg.dem.bundles.one2one;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
public abstract class MultiHandlerAdapter<E extends Event> implements MultiHandler<E> {
    private final Class<E> bound;

    public MultiHandlerAdapter(Class<E> bound) {
        this.bound = bound;
    }

    public final Class<E> getBoundClass() {
        return bound;
    }
}
