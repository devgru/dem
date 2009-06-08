package dem.processing;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.181
 */
public abstract class Processor<E extends Event>
        extends Source<E> implements Handler<E> {

    protected Processor(Handler<? super E> target) {
        super(target);
    }

    public void handle(E event) {
        fire(event);
    }
}
