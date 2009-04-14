package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.18
 */
public final class NoopFilter<E extends Event>
        implements Filter<E> {

    public boolean handleIfPossible(Event event) {
        return false;
    }

    public void handle(E event) {
    }
}
