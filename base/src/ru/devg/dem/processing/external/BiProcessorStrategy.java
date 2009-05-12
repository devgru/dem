package ru.devg.dem.processing.external;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public abstract class BiProcessorStrategy<LEFT extends Event, RIGHT extends Event> {

    protected abstract boolean processLeft(LEFT event);

    protected abstract boolean processRight(RIGHT event);

}
