package ru.devg.dem.bundles;

import ru.devg.dem.quanta.Handler;

/**
 * HandlerBundle is a way to use some strategies of bundling handlers.
 * If you want to use your own strategy - you just have
 * to implement this interface.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.15
 */
public interface HandlerBundle<H extends Handler>
        {

    /**
     * @param handler handler to add
     * @throws IllegalArgumentException may be thrown if handler is null
     */
    public void addHandler(H handler) throws IllegalArgumentException;

    /**
     * @param handler handler to add
     */
    public void removeHandler(H handler);

}
