package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.181
 */
public interface TypeFilter<E extends Event> extends Filter<E> {
    boolean handleIfPossible(Object event);
}
