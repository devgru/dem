package dem.buffers;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.remote.RemoteEvent;

import java.util.LinkedList;
import java.util.Collection;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.180
 */
public class EventCollection<E extends Event>
        extends LinkedList<E> implements Handler<E>,RemoteEvent {

    public EventCollection() {
    }

    public EventCollection(Collection<? extends E> c) {
        super(c);
    }

    public void handle(E event) {
        add(event);
    }
}
