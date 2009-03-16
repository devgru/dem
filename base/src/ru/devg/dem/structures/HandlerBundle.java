package ru.devg.dem.structures;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * HandlerBundle is a way to use some strategies of bundling handlers.
 * If you want to use your own strategy - you just have
 * to implement this interface.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.15
 */
public interface HandlerBundle<H extends Handler<? super E>, E extends Event>
        extends Handler<E> {

    /**
     * @param handler handler to add
     * @throws IllegalArgumentException if handler == null
     */
    public void addHandler(H handler) throws IllegalArgumentException;

    public void removeHandler(H handler);

}
