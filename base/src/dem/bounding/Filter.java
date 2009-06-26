package dem.bounding;

import dem.quanta.Event;
import dem.quanta.Handler;

/**
 * Filter is a {@link Handler} that contains one additional method:
 * {@link #handleIfPossible(Event)}.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.181
 */
public interface Filter<E extends Event> extends Handler<E> {

    /**
     * This method adds flexibility to {@link Handler} class, allowing you
     * to use your own {@link Event}-filtering strategies.
     *
     * @param event event that will be handled
     * @return returns <code>true</code> if and only if the event was handled as excepted;<br>
     *         returns <code>false</code> otherwise
     */
    boolean handleIfPossible(Event event);
}
