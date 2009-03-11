package ru.devg.dem.bundles.one2one.onclass;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
public abstract class InplaceDispatchableAdapter<E extends Event> implements InplaceDispatchable<E> {
    private final Class<E> bound;

    public InplaceDispatchableAdapter(Class<E> bound) {
        this.bound = bound;
    }

    public final Class<E> getBoundClass() {
        return bound;
    }
}
