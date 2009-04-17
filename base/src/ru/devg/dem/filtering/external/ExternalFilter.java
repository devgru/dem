package ru.devg.dem.filtering.external;

import ru.devg.dem.filtering.UpperFilter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public final class ExternalFilter<E extends Event>
        extends UpperFilter<E> {

    private final FilterStrategy filterStrategy;

    public ExternalFilter(Handler<? super E> handler, FilterStrategy filterStrategy) {
        super(handler);
        this.filterStrategy = filterStrategy;
    }

    @SuppressWarnings("unchecked")
    public boolean handleIfPossible(E event) {
        boolean canHandle = filterStrategy.canHandle(event);
        if (canHandle) {
            fire( event);
        }
        return canHandle;
    }
}

