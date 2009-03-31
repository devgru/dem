package ru.devg.dem.bundles;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * RoundRobin is a handler structure, that uses round-robin algorythm.
 * You have to remember few things about it:
 * <ul>
 * <li>sending event to empty RoundRobin will have no result;</li>
 * <li>when you add a Handler to RoundRobin it is added <i>before</i> the next Handler;</li>
 * </ul>
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.15
 */
public final class RoundRobin<E extends Event>
        implements HandlingBundle<Handler<E>, E> {


    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
    private final List<Handler<E>> handlers
            = new LinkedList<Handler<E>>();

    private final ListIterator<Handler<E>> storedIterator
            = handlers.listIterator();

    private final List<Handler<E>> handlersToRemove
            = new LinkedList<Handler<E>>();


    public RoundRobin() {
    }

    public RoundRobin(Handler<E>... handlers) {
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

    private synchronized Handler<E> next() {
        Handler<E> h = null;
        while (handlers.size() > 0 && h == null) {
            h = storedIterator.next();
            if (handlersToRemove.contains(h)) {
                storedIterator.remove();
                handlersToRemove.remove(h);
                h = null;
            }
            rewindIfNeeded();
        }
        return h;
    }

    private void rewindIfNeeded() {
        if (handlers.size() != 0 && !storedIterator.hasNext()) {
//            storedIterator = handlers.listIterator();
            while (storedIterator.hasPrevious()) {
                storedIterator.previous();
            }
        }
    }

    public synchronized void addHandler(Handler<E> h) {
        assert h != null;
        storedIterator.add(h);
        storedIterator.previous();
    }

    public synchronized void removeHandler(Handler<E> h) {
        if (h == null) return;
        if (!handlers.contains(h)) return;
        if (handlersToRemove.contains(h)) return;
        handlersToRemove.add(h);
    }
}
