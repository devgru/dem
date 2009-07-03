package dem.buffers;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Log;
import dem.quanta.Source;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Event buffer is class, designed to collect events and manage this collections.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public final class EventBuffer<E extends Event>
        extends Source<E> implements Handler<E> {

    private final Collection<E> events
            = new LinkedList<E>();

    private boolean locked = false;

    public EventBuffer(Handler<? super E> target) {
        super(target);
    }

    /**
     * Locks buffer.
     *
     * @return <code>true</code> if buffer was free and it is locked;<br>
     *         <code>false</code> if buffer already was locked
     */
    public final boolean lock() {
        boolean wasFree = !locked;
        locked = true;
        return wasFree;
    }

    /**
     * Checks if buffer is locked.
     *
     * @return <code>true</code> if buffer is locked, <code>false</code> otherwise
     */
    public final boolean isLocked() {
        return locked;
    }

    /**
     * Removes all events from buffer and unlocks it.
     */
    public final void reset() {
        events.clear();
        locked = false;
    }

    /**
     * Fires all collected events to {@link Handler target}
     * you provide.
     * Also, it calls {@link EventBuffer#reset()}, so it
     * isn't applicable for broadcasting to multiple handlers.
     * If you want to broadcast events, use any
     * {@link dem.bundles handler bundle}.
     *
     * @see dem.bundles.Broadcaster
     * @see dem.bundles.Dispatcher
     */
    public final void release() {
        lock();
        for (E event : events) {
            fire(event);
        }
        reset();
    }

    public final void handle(E event) {
        if (!locked) {
            assert event != null;
            events.add(event);
        }
    }

    /**
     * Adds all collected events to target.
     *
     * @param target collection, that will contain all collected events.
     */
    public final void export(Collection<? super E> target) {
        target.addAll(events);
    }

    @Override
    public String toString() {
        return "Event buffer\n" + Log.offset("events: " + events + "\n" + " target is " + target);
    }

}
