package ru.devg.dem.buffers;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.remote.RemoteEvent;

import java.util.LinkedList;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.180
 */
public class EventCollection<E extends Event>
        extends LinkedList<E> implements RemoteEvent {

}
