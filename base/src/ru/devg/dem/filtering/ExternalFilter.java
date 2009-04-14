package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public final class ExternalFilter<E extends Event>
        extends CommonFilter<E> {

    private final FilterStrategy filterStrategy;
    private final Handler<? super E> handler;

    public ExternalFilter(Handler<? super E> handler, FilterStrategy filterStrategy) {
        this.handler = handler;
        this.filterStrategy = filterStrategy;
    }

    public boolean canHandle(Event e) {
        return filterStrategy.canHandle(e);
    }

    public void handle(E event) {
        handler.handle(event);
    }
}
