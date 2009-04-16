package ru.devg.dem.filtering.external;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public final class ExternalFilter<E extends Event>
        extends Source<E> implements Filter<E> {

    private final FilterStrategy filterStrategy;

    public ExternalFilter(Handler<? super E> handler, FilterStrategy filterStrategy) {
        super(handler);
        this.filterStrategy = filterStrategy;
    }

    public void handle(E event) {
        fire(event);
    }

    @SuppressWarnings("unchecked")
    public boolean handleIfPossible(Event event) {
        boolean canHandle = filterStrategy.canHandle(event);
        if (canHandle) {
            fire((E) event);
        }
        return canHandle;
    }
}

