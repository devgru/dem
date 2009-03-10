package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
public interface FilterStrategy {
    public boolean canHandle(Event e);
}