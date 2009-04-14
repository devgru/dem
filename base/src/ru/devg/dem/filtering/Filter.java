package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.17
 */
public interface Filter<E extends Event>
        extends Handler<E> {

    public abstract boolean handleIfPossible(Event event);
}
