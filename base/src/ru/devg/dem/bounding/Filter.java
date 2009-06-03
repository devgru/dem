package ru.devg.dem.bounding;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.181
 */
public interface Filter<E extends Event> extends Handler<E> {
    boolean handleIfPossible(Event event);
}
