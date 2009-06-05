package dem.processing;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.stuff.MutableSource;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
public abstract class MutableBypasser<E extends Event>
        extends MutableSource<E> implements Handler<E> {

    protected MutableBypasser() {
    }

    protected MutableBypasser(Handler<? super E> target) {
        super(target);
    }

    public final void handle(E event) {
        fire(event);
    }
}
