package ru.devg.dem.filtering.external;

import ru.devg.dem.quanta.Event;

/**
 * This interface should be implemented in order to provide filtering capabilities.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public interface FilterStrategy<E extends Event> {

    /**
     * Simple boolean method.
     * Returns true if handler can handle given event.
     * Should not throw exceptions in any implemntation.
     *
     * @param e event that will be analyzed.
     * @return returns true if and only if
     *         any {@link ru.devg.dem.quanta.Handler Handler} using this strategy
     *         will handle given event.
     */
    public boolean canHandle(E e);
}