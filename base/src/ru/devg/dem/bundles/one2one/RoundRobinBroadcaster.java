package ru.devg.dem.bundles.one2one;

import ru.devg.dem.bundles.HandlerBundle;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * RoundRobinBroadcaster is a sophisticated broadcaster, that uses round-robin algorythm.
 * You must remember few things about this broadcaster:
 * <ul>
 * <li>sending event to empty RoundRobinBroadcaster will have no result;</li>
 * <li>when you add a Handler to RoundRobinBroadcaster it is added <i>before</i> the next Handler;</li>
 * </ul>
 * <p/>
 * <h2>Handling algorithm</h2>
 * For example, we have RoundRobinBroadcaster that contains
 * 3 {@link Handler handlers}, <code>a</code>, <code>b</code> and <code>c</code>.
 * <p/>
 * If iterator is between <code>b</code> and <code>c</code>
 * we can say that the event will be transferred to <code>c</code>
 * and the next one will be given to <code>a</code>.
 * <p/>
 * <pre>|a|b|c|      -->       |a|b|c|      -->       |a|b|c|</pre>
 * <pre>    ^     handle to c        ^   autorewind   ^</pre>
 *
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.15
 */
public final class RoundRobinBroadcaster<E extends Event>
        implements HandlerBundle<Handler<E>, E>, Handler<E> {

    private boolean empty = true;

    /**
     * It works, just believe me.
     */
    private final ListIterator<Handler<E>> storedIterator
            = new LinkedList<Handler<E>>().listIterator();

    private final LinkedList<Handler<E>> handlersToRemove
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

    private void rewindIfNeeded() {
        if (!storedIterator.hasNext()) {
            rewind();
        }
    }

    /**
     * If you add iterator is between <code>b</code> and <code>c</code>
     * we can say that the event will be transferred to <code>c</code>
     * and the next one will be given to <code>a</code>.
     * <p/>
     * <pre>|a|b|c|      -->       |a|b|c|      -->       |a|b|c|</pre>
     * <pre>    ^     handle to c        ^  after handle  ^</pre>
     */
    public synchronized void addHandler(Handler<E> h) {
        assert h != null;
        storedIterator.add(h);
        storedIterator.previous();
        empty = false;
    }

    public synchronized void removeHandler(Handler<E> h) {
        if (h == null) return;
        if (handlersToRemove.contains(h)) return;
        handlersToRemove.add(h);
    }

    private synchronized void rewind() {
        while (storedIterator.hasPrevious()) {
            storedIterator.previous();
        }
    }

    public boolean isEmpty() {
        return empty;
    }
}
