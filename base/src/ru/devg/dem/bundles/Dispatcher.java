package ru.devg.dem.bundles;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.quanta.Event;

import java.util.LinkedList;
import java.util.List;
import java.util.Collection;

/**
 * Dispatcher is a handler bundle that passes
 * every event to one of {@link ru.devg.dem.quanta.Handler handlers} it contain.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.16
 */
public final class Dispatcher<E extends Event>
        implements HandlingBundle<E, TypeFilter<? extends E>> {

    public Dispatcher(TypeFilter<? extends E>... handlers) {
        for (TypeFilter<? extends E> handler : handlers) {
            addHandler(handler);
        }
    }

    public Dispatcher(Collection<? extends TypeFilter<? extends E>> handlers) {
        for (TypeFilter<? extends E> handler : handlers) {
            addHandler(handler);
        }
    }

    // fields

    private final List<TypeFilter<? extends E>> handlers =
            new LinkedList<TypeFilter<? extends E>>();

    //vv

    public void handle(E event) {
        for (TypeFilter<?> binder : handlers) {
            if (binder.handleIfPossible(event)) return;
        }
    }

    //adding

    public void addHandler(TypeFilter<? extends E> newOne) {
        assert newOne != null;
        handlers.add(newOne);
    }


    public void removeHandler(TypeFilter<? extends E> newOne) {
        handlers.remove(newOne);
    }

}
