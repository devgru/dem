package ru.devg.dem.bundles.one2one;

import ru.devg.dem.bundles.HandlerBundle;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.List;

/**
 * RoundRobinBroadcaster is a sophisticated broadcaster, that uses round-robin algorythm.
 * You must remember few things about this broadcaster:
 * <ul>
 * <li>sending event to empty RoundRobinBroadcaster will have no result;</li>
 * <li>when you add a Handler to RoundRobinBroadcaster it is added <i>before</i> the next Handler;</li>
 * </ul>
 *
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.15
         */
        public final class RoundRobinBroadcaster<E extends Event>
                implements HandlerBundle<Handler<E>, E>, Handler<E> {

    private boolean empty = true;

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
    private final List<Handler<E>> handlers
            = new LinkedList<Handler<E>>();

    /**
     * It works, just believe me.
     */
    private ListIterator<Handler<E>> storedIterator
            = handlers.listIterator();

    private final List<Handler<E>> handlersToRemove
            = new LinkedList<Handler<E>>();

    /**
     * This method won't have any effect if broadcaster is
     * {@link RoundRobinBroadcaster#isEmpty empty}.
     * <p/>
     * If broadcaster is not empty, the event will be passed
     * to next {@link Handler}.
     *
     * @param event event we transfer
     */
    public void handle(E event) {
        assert event != null;
        Handler<E> handler = storedIterator.next();
        if(!empty){
            while(handlersToRemove.contains(handler)){
                storedIterator.remove();
                handler = storedIterator.next();
                rewindIfNeeded();
            }
        }
        if (!empty) {
            handler.handle(event);
            rewindIfNeeded();
        }
    }

    public synchronized void addHandler(Handler<E> h) {
        assert h != null;
        storedIterator.add(h);
        storedIterator.previous();
        empty = false;
    }

    public synchronized void removeHandler(Handler<E> h) {
        if (h == null) return;
        if (!handlers.contains(h)) return;
        if (handlersToRemove.contains(h)) return;
        handlersToRemove.add(h);
    }

    private void rewindIfNeeded() {
        if (!storedIterator.hasNext()) {
            rewind();
        }
    }

    private void rewind() {
        storedIterator = handlers.listIterator();
    }

    public boolean isEmpty() {
        return empty;
    }
}
