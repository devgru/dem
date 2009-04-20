package ru.devg.dem.stuff;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.18
 */
public final class NoopHandler<E extends Event>
        implements Handler<E> {

    public void handle(E event) {
    }
}
