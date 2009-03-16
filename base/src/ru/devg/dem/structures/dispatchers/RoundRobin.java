package ru.devg.dem.structures.dispatchers;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.structures.HandlerBundle;

/**
 * RoundRobinBroadcaster is a sophisticated broadcaster, that uses round-robin algorythm.
 * You must remember few things about this broadcaster:
 * <ul>
 * <li>sending event to empty RoundRobinBroadcaster will have no result;</li>
 * <li>when you add a Handler to RoundRobinBroadcaster it is added <i>before</i> the next Handler;</li>
 * </ul>
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.15
 */
public final class RoundRobin<E extends Event>
        implements HandlerBundle<Handler<E>, E> {

    private LoopedIterator<Handler<E>> iterator = new LoopedIterator<Handler<E>>();

    /**
     * This method won't have any effect if broadcaster is
     * empty.
     * <p/>
     * If broadcaster is not empty, the event will be passed
     * to next {@link Handler}.
     *
     * @param event event we transfer
     */
    public void handle(E event) {
        assert event != null;
        Handler<E> handler = iterator.next();
        if (handler != null) {
            handler.handle(event);
        }
    }

    public synchronized void addHandler(Handler<E> h) {
        iterator.add(h);
    }

    public synchronized void removeHandler(Handler<E> h) {
        iterator.remove(h);
    }
}
