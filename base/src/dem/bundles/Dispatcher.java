package dem.bundles;

import dem.bounding.Filter;
import dem.quanta.Event;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Dispatcher is a handler bundle that passes
 * every event to one of {@link dem.quanta.Handler handlers} it contain.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.16
 */
public final class Dispatcher<E extends Event>
        implements HandlingBundle<E, Filter<? extends E>> {

    private final List<Filter<? extends E>> handlers =
            new LinkedList<Filter<? extends E>>();

    public Dispatcher(Filter<? extends E>... handlers) {
        for (Filter<? extends E> handler : handlers) {
            addHandler(handler);
        }
    }

    public Dispatcher(Collection<? extends Filter<? extends E>> handlers) {
        for (Filter<? extends E> handler : handlers) {
            addHandler(handler);
        }
    }

    public void handle(E event) {
        assert event != null;
        for (Filter<?> handler : handlers) {
            if (handler.handleIfPossible(event)) return;
        }
    }

    public void addHandler(Filter<? extends E> handler) {
        assert handler != null;
        handlers.add(handler);
    }


    public void removeHandler(Filter<? extends E> handler) {
        handlers.remove(handler);
    }

}
