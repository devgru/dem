package ru.devg.dem.filtering.external;

import ru.devg.dem.filtering.Bypasser;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.181
 */
public abstract class CommonFilter<E extends Event>
        extends Bypasser<E> implements FilterStrategy {

    protected CommonFilter(Handler<? super E> target) {
        super(target);
    }

    @SuppressWarnings("unchecked")
    public void handle(E event) {
        boolean canHandle = canHandle(event);
        if (canHandle) {
            handle(event);
        }
    }
}
