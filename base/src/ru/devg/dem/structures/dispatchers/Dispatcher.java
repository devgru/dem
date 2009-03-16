package ru.devg.dem.structures.dispatchers;

import ru.devg.dem.filtering.TypeBoundedHandler;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.16
 */
public final class Dispatcher<E extends Event>
        extends TypeBoundedHandler<E>
//        implements HandlerBundle<TypeBoundedHandler<? extends E>, E>
{

    public static Dispatcher<Event> getCommon() {
        return new Dispatcher<Event>(Event.class);
    }

    public Dispatcher(Class<E> bound) {
        super(bound);
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
        for (TypeBoundedHandler<?> oldOne : handlers) {
            if (isOverlapping(oldOne, newOne)) {
                throw new RuntimeException("handler overlaps another one");
            }
        }
    }


    public void removeHandler(TypeBoundedHandler<? extends E> newOne) {
        handlers.remove(newOne);
    }

    private static boolean isOverlapping(TypeBoundedHandler<?> a, TypeBoundedHandler<?> b) {
        Class<?> alpha = a.getBoundClass();
        Class<?> beta = b.getBoundClass();
        return alpha.isAssignableFrom(beta) || beta.isAssignableFrom(alpha);
    }
}
