package ru.devg.dem.sources;

import ru.devg.dem.quanta.Handler;
import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
public interface AbstractMultiSource<H extends Handler<?>, E extends Event> extends AbstractSource<E> {
    void addTarget(H handler);
    void removeTarget(H handler);
}
