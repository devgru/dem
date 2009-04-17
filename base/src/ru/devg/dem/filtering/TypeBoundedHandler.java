package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;

/**
 * Bounded handlers are required when we must have
 * some info about the bound class {@link E}. For example, it's used
 * a lot in the {@link ru.devg.dem.bundles Structures} section.
 * <p/>
 * Bounded handlers are strong filters.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.15
 */
public abstract class TypeBoundedHandler<E extends Event>
        extends TypeFilterImpl<E> {

    private final Class<E> bound;

    public TypeBoundedHandler(Class<E> bound) {
        this.bound = bound;
    }

    public final Class<E> getBoundClass() {
        return bound;
    }

    @SuppressWarnings("unchecked")
    public final boolean handleIfPossible(Object event) {
        boolean canHandle = bound.isInstance(event);
        if (canHandle) {
            handle((E) event);
        }
        return canHandle;
    }

}
