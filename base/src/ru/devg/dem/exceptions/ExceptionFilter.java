package ru.devg.dem.exceptions;

import ru.devg.dem.filtering.UpperFilter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.181
 */
public final class ExceptionFilter<E extends Event>
        extends UpperFilter<E> {

    public ExceptionFilter(Handler<? super E> target) {
        super(target);
    }

    @SuppressWarnings("unchecked")
    public boolean handleIfPossible(Event event) {
        try {
            fire((E) event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
