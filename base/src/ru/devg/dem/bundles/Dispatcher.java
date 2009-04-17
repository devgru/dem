package ru.devg.dem.bundles;

import ru.devg.dem.bounding.BoundedHandler;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.LinkedList;
import java.util.List;

/**
 * Dispatcher is a handler bundle that passes
 * every event to one of {@link ru.devg.dem.quanta.Handler handlers} it contain.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.16
 */
public final class Dispatcher<E extends Event>
        extends BoundedHandler<E>
        implements HandlingBundle<E, BoundedHandler<? extends E>> {

    public Dispatcher(Class<E> bound) {
        super(bound);
    }

    public Dispatcher(Class<E> bound, BoundedHandler<? extends E>... handlers) {
        super(bound);
        for (BoundedHandler<? extends E> handler : handlers) {
            addHandler(handler);
        }
    }

    // fields

    private final List<BoundedHandler<? extends E>> handlers =
            new LinkedList<BoundedHandler<? extends E>>();

    //orphans

    private Handler<Event> handlerForOrphans = null;

    public void setHandlerForOrphans(Handler<Event> handlerForOrphans) {
        this.handlerForOrphans = handlerForOrphans;
    }

    //vv

    public void handle(E event) {
        for (BoundedHandler<?> binder : handlers) {
            if (binder.handleIfPossible(event)) return;
        }
        if (handlerForOrphans != null) {
            handlerForOrphans.handle(event);
        }
    }

    //adding

    public void addHandler(BoundedHandler<? extends E> newOne) {
        assert newOne != null;
        handlers.add(newOne);
        for (BoundedHandler<?> oldOne : handlers) {
            if (isOverlapping(oldOne, newOne)) {
                throw new IllegalArgumentException("handler " + newOne + " overlaps " + oldOne);
            }
        }
    }


    public void removeHandler(BoundedHandler<? extends E> newOne) {
        handlers.remove(newOne);
    }

    private static boolean isOverlapping(BoundedHandler<?> a, BoundedHandler<?> b) {
        Class<?> alpha = a.getBoundClass();
        Class<?> beta = b.getBoundClass();
        return alpha.isAssignableFrom(beta) || beta.isAssignableFrom(alpha);
    }
}
