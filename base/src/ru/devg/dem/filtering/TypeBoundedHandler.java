package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * Bounded handlers are required when we must have
 * some info about the bound class {@link E}. For example, it's used
 * a lot in the {@link ru.devg.dem.structures} section.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.15
 */
public abstract class TypeBoundedHandler<E extends Event> extends Filter<E> {
    private final Class<E> bound;

    protected TypeBoundedHandler(Class<E> bound) {
        this.bound = bound;
    }

    public final Class<E> getBoundClass() {
        return bound;
    }

    public final boolean canHandle(Event event) {
        return bound.isInstance(event);
    }

    /**
     * This function returns a simple bounded instance of your handler.
     * No, it's not a brainfuck, I claim! 01:31, 09.12.2008
     *
     * @param handler handler we will bound
     * @param cls     our bound class
     * @param <E>     bound type
     * @return bounded handler
     */
    public static <E extends Event>
    TypeBoundedHandler<E> bound(final Handler<E> handler, Class<E> cls) {
        assert handler != null;
        assert cls != null;
        return new TypeBoundedHandler<E>(cls) {
            public void handle(E e) {
                handler.handle(e);
            }
        };
    }
}
