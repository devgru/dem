package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.181
 */
public abstract class TypeFilterImpl<E extends Event> implements TypeFilter<E> {

    public final boolean handleIfPossible(E event) {
        return handleIfPossible((Object) event);
    }
}
