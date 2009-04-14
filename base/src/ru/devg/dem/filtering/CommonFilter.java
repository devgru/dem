package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.181
 */
public abstract class CommonFilter<E extends Event>
        implements Filter<E>, FilterStrategy {
    @SuppressWarnings("unchecked")

    public boolean handleIfPossible(Event event) {
        boolean canHandle = canHandle(event);
        if (canHandle) {
            handle((E) event);
        }
        return canHandle;
    }
}
