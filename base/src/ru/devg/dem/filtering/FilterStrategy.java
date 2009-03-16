package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public interface FilterStrategy {
    public boolean canHandle(Event e);
}