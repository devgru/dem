package dem.bundles;

import dem.bounding.Filter;
import dem.quanta.Event;
import dem.stuff.Log;

import java.util.*;

/**
 * Dispatcher is a handler bundle that passes
 * every event to one of {@link dem.quanta.Handler handlers} it contain.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.16
 */
public final class Dispatcher<E extends Event>
        implements HandlingBundle<E, Filter<? extends E>> {

    private final Set<Filter<? extends E>> handlers =
            new LinkedHashSet<Filter<? extends E>>();

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

    public boolean addHandler(Filter<? extends E> handler) {
        assert handler != null;
        return handlers.add(handler);
    }


    public boolean removeHandler(Filter<? extends E> handler) {
        return handlers.remove(handler);
    }

    @Override
    public String toString() {
        return "Dispatcher\n" +
                Log.offset("targets:")+"\n" + Log.offset(Log.describe(handlers),2);
    }

}
