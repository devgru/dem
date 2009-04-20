package ru.devg.dem.bounding;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

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
public abstract class BoundedHandler<E extends Event>
        implements TypeFilter<E> {

    private final Class<E> bound;

    public BoundedHandler(Class<E> bound) {
        this.bound = bound;
    }

    public final Class<E> getBoundClass() {
        return bound;
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
    BoundedHandler<E> bound(final Handler<E> handler, Class<E> cls) {
        assert handler != null;
        assert cls != null;
        return new BoundedHandler<E>(cls) {
            public void handle(E e) {
                handler.handle(e);
            }
        };
    }

    @SuppressWarnings("unchecked")
    public final boolean handleIfPossible(Event event) {
        boolean canHandle = bound.isInstance(event);
        if (canHandle) {
            handle((E) event);
        }
        return canHandle;
    }


}
