package ru.devg.dem.filtering.external;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.filtering.Filter;
import ru.devg.dem.filtering.UpperFilter;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.181
 */
public abstract class CommonFilter<E extends Event>
        extends UpperFilter<E> implements Filter<E>, FilterStrategy {

    protected CommonFilter(Handler<? super E> target) {
        super(target);
    }

    @SuppressWarnings("unchecked")
    public boolean handleIfPossible(E event) {
        boolean canHandle = canHandle(event);
        if (canHandle) {
            handle(event);
        }
        return canHandle;
    }
}
