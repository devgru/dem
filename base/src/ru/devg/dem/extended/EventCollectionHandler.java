package ru.devg.dem.extended;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.sources.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.180
 */
public final class EventCollectionHandler<E extends Event>
        extends Source<E> implements Handler<EventCollection<E>> {

    public EventCollectionHandler(Handler<? super E> target) {
        super(target);
    }

    public void handle(EventCollection<E> event) {
        for(E e : event){
            fire(e);
        }
    }

}
