package dem.buffers;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.180
 */
public final class EventCollectionHandler<E extends Event>
        extends Source<E> implements Handler<EventCollection<E>> {

    public EventCollectionHandler(Handler<? super E> target) {
        super(target);
    }

    public void handle(EventCollection<E> eventCollection) {
        assert eventCollection != null;
        for (E event : eventCollection) {
            fire(event);
        }
    }

}
