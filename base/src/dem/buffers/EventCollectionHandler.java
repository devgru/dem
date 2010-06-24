package dem.buffers;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Log;
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
            /**
             * we need this assertion because {@link EventCollection}
             * uses no check of null events
             */
            assert event != null;
            fire(event);
        }
    }

    @Override
    public String toString() {
        return "Event-collection handler\n" + Log.offset("target is " + target);
    }

}
