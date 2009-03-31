package ru.devg.dem.bundles;

import ru.devg.dem.quanta.Handler;
import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public interface HandlingBundle<H extends Handler, E extends Event>
        extends Bundle<H>,Handler<E>{
}
