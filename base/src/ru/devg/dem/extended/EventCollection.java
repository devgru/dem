package ru.devg.dem.extended;

import ru.devg.dem.quanta.Event;

import java.util.LinkedList;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.180
 */
public final class EventCollection<E extends Event>
        extends LinkedList<E> implements Event {

}
