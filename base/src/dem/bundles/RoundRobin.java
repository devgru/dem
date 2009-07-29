package dem.bundles;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Log;

import java.util.*;

/**
 * RoundRobin is a handler structure, that uses round-robin algorithm.
 * You have to remember few things about it:
 * <ul>
 * <li>sending event to empty RoundRobin will have no result;</li>
 * <li>when you add a Handler to RoundRobin it is added <i>before</i> the next Handler;</li>
 * </ul>
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.15
 */
public final class RoundRobin<E extends Event>
        implements HandlingBundle<E, Handler<E>> {

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
    private final List<Handler<E>> handlers
            = new LinkedList<Handler<E>>();

    private ListIterator<Handler<E>> storedIterator
            = handlers.listIterator();

    private final Set<Handler<E>> handlersToRemove
            = new HashSet<Handler<E>>();

    public RoundRobin(Handler<E>... handlers) {
        for (Handler<E> handler : handlers) {
            addHandler(handler);
        }
    }

    public RoundRobin(Collection<? extends Handler<E>> handlers) {
        assert handlers != null;
        for (Handler<E> handler : handlers) {
            addHandler(handler);
        }
    }

    /**
     * This method won't have any effect if broadcaster is
     * empty.
     * <p/>
     * If broadcaster is not empty, the event will be passed
     * to next {@link Handler}.
     *
     * @param event event we transfer
     */
    public synchronized void handle(E event) {
        assert event != null;
        Handler<E> handler = next();
        if (handler != null) {
            handler.handle(event);
        }
    }

    private Handler<E> next() {
        Handler<E> handler = null;
        while (handlers.size() > 0 && handler == null) {
            handler = storedIterator.next();
            if (handlersToRemove.contains(handler)) {
                storedIterator.remove();
                handlersToRemove.remove(handler);
                handler = null;
            }
            rewindIfNeeded();
        }
        return handler;
    }

    private void rewindIfNeeded() {
        if (handlers.size() != 0 && !storedIterator.hasNext()) {
            storedIterator = handlers.listIterator();
        }
    }

     public synchronized boolean addHandler(Handler<E> handler) {
        assert handler != null;
        storedIterator.add(handler);
        storedIterator.previous();
        return true;
    }

    public synchronized boolean removeHandler(Handler<E> h) {
        if (h == null) return false;
        if (!handlers.contains(h)) return false;
        if (handlersToRemove.contains(h)) return false;

        return handlersToRemove.add(h);
    }

    @Override
    public String toString() {
        return "Round-robin dispatcher\n" + Log.offset("handlers:" + Log.offset(Log.inline(handlers)));
    }

}
