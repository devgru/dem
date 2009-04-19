package ru.devg.dem.buffers;

import ru.devg.dem.remote.RemoteEvent;
import ru.devg.dem.quanta.Event;

import java.util.LinkedList;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.180
 */
public class EventCollection<E extends Event>
        extends LinkedList<E> implements RemoteEvent {

}
