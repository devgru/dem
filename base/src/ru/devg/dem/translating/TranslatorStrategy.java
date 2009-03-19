package ru.devg.dem.translating;

import ru.devg.dem.quanta.Event;

/**
 * Translator strategies are the simpliest way to use Translating.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public interface TranslatorStrategy<TO extends Event, FROM extends Event> {

    /**
     * Implementation of this method should return Event of class {@link FROM}.
     * This method should not throw exceptions.
     *
     * @param from given event
     * @return created event
     */
    public abstract TO translate(FROM from);
}
