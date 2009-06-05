package dem.stuff;

import dem.quanta.Event;
import dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.18
 */
public final class NoopHandler<E extends Event>
        implements Handler<E> {

    public void handle(E event) {
    }
}
