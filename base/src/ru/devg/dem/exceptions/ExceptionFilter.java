package ru.devg.dem.exceptions;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.sources.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.181
 */
public abstract class ExceptionFilter<E extends Event>
        extends Source<E> implements Filter<E> {

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
