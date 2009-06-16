package dem.bundles;

import dem.quanta.Event;
import dem.quanta.Handler;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Broadcaster is a bundle of handlers. On
 * {@link Broadcaster#handle(dem.quanta.Event)}
 * it passes an {@link dem.quanta.Event} to every contained handler.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.17
 */
public final class Broadcaster<E extends Event>
        implements HandlingBundle<E, Handler<? super E>> {

    private final LinkedList<Handler<? super E>> handlers =
            new LinkedList<Handler<? super E>>();

    public Broadcaster(Handler<? super E>... handlers) {
        for (Handler<? super E> handler : handlers) {
            addHandler(handler);
        }
    }

    public Broadcaster(Collection<? extends Handler<? super E>> handlers) {
        for (Handler<? super E> handler : handlers) {
            addHandler(handler);
        }
    }

    public void handle(E event) {
        assert event != null;
        for (Handler<? super E> handler : handlers) {
            handler.handle(event);
        }
    }

    public void addHandler(Handler<? super E> handler) {
        assert handler != null;
        handlers.add(handler);
    }

    public void removeHandler(Handler<? super E> handler) {
        handlers.remove(handler);
    }

    @Override
    public String toString() {
        return "Broadcaster (targets: " + handlers + ")";
    }

}
