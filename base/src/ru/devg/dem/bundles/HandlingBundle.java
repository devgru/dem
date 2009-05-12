package ru.devg.dem.bundles;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.181
 */
public interface HandlingBundle<E extends Event, H extends Handler>
        extends Handler<E>, HandlerBundle<H> {
}
