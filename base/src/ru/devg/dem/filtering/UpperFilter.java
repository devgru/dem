package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.181
 */
public abstract class UpperFilter<E extends Event> extends Source<E> implements Filter<E> {

    protected UpperFilter(Handler<? super E> target) {
        super(target);
    }

    public final void handle(E e) {
        handleIfPossible(e);
    }


}
