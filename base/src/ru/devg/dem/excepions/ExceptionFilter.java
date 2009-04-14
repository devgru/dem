package ru.devg.dem.excepions;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.sources.Source;
import ru.devg.dem.filtering.Filter;

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
