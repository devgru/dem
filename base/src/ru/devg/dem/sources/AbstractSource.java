package ru.devg.dem.sources;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
public interface AbstractSource<E extends Event> {

    public void fire(E event);
}
