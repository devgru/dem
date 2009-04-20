package ru.devg.dem.processing.external;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.182
 */
public interface ProcessorStrategy<E extends Event> {
    public boolean process(E event);
}
