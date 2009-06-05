package dem.bounding;

import dem.quanta.Event;
import dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.181
 */
public interface Filter<E extends Event> extends Handler<E> {
    boolean handleIfPossible(Event event);
}
