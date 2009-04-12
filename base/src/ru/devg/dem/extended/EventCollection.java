package ru.devg.dem.extended;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.pipes.api.RemoteEvent;

import java.util.LinkedList;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.180
 */
public class EventCollection<E extends Event>
        extends LinkedList<E> implements RemoteEvent {

}
