package ru.devg.dem.extended;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.filtering.TypeBoundedHandler;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.1755
 */
public final class EventBuffer<E extends Event>
        extends TypeBoundedHandler<E> {

    private final Handler<? super E> target;

    private final Collection<E> events
            = new LinkedList<E>();

    private boolean locked = false;

    //Constructors

    public EventBuffer(Handler<? super E> target, Class<E> bound) {
        super(bound);
        assert target != null;
        this.target = target;
    }

    //Locking

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

    //Specific methods

    /**
     * Fires all collected events to {@link ru.devg.dem.quanta.Handler target}
     * you provide.
     * Also, it calls {@link EventBuffer#reset()}, so it
     * isn't applicable for broadcasting to multiple handlers.
     * If you want to broadcast events, use any
     * {@link ru.devg.dem.bundles handler from multi}.
     *
     * @see ru.devg.dem.bundles.one2many.MultiBroadcaster
     * @see ru.devg.dem.bundles.one2one.Dispatcher
     */
    public final void release() {
        lock();
        for (E event : events) {
            target.handle(event);
        }
        reset();
    }

    //Handling

    public final void handle(E boundedEvent) {
        if (!locked) {
            assert boundedEvent != null;
            events.add(boundedEvent);
        }
    }

    //Collection functionality

    public final void export(Collection<? super E> target) {
        target.addAll(events);
    }

}