package ru.devg.dem.bundles;

import ru.devg.dem.filtering.TypeBoundedHandler;
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
        extends TypeBoundedHandler<E>
        implements HandlerBundle<TypeBoundedHandler<? extends E>>,Handler<E> {

    public static Dispatcher<Event> getCommon() {
        return new Dispatcher<Event>(Event.class);
    }

    public Dispatcher(Class<E> bound) {
        super(bound);
    }

    public Dispatcher(Class<E> bound, TypeBoundedHandler<? extends E>... handlers) {
        super(bound);
        for (TypeBoundedHandler<? extends E> handler : handlers) {
            addHandler(handler);
        }
    }

    // fields

    private final List<TypeBoundedHandler<? extends E>> handlers =
            new LinkedList<TypeBoundedHandler<? extends E>>();

    //orphans

    private Handler<Event> handlerForOrphans = null;

    public void setHandlerForOrphans(Handler<Event> handlerForOrphans) {
        this.handlerForOrphans = handlerForOrphans;
    }

    //vv

    public final void handle(E event) {
        for (TypeBoundedHandler<?> binder : handlers) {
            if (binder.handleIfPossible(event)) return;
        }
        if (handlerForOrphans != null) {
            handlerForOrphans.handle(event);
        }
    }

    //adding

    public void addHandler(TypeBoundedHandler<? extends E> newOne) {
        assert newOne != null;
        handlers.add(newOne);
    }


    public void removeHandler(TypeBoundedHandler<? extends E> newOne) {
        handlers.remove(newOne);
    }

}
