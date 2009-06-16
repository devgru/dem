package dem.buffers;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;

import java.util.Collection;
import java.util.LinkedList;

/**
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

    public final boolean lock() {
        boolean wasFree = !locked;
        locked = true;
        return wasFree;
    }

    public final boolean isLocked() {
        return locked;
    }

    public final void reset() {
        events.clear();
        locked = false;
    }

    /**
     * Fires all collected events to {@link dem.quanta.Handler target}
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

    public final void export(Collection<? super E> target) {
        target.addAll(events);
    }

    @Override
    public String toString() {
        return "Event buffer (events: " + events + "; target is " + target + ")";
    }

}
