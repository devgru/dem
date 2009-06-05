package dem.processing;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;
import dem.stuff.MutableSource;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.181
 */
public abstract class MutableProcessor<E extends Event>
        extends Mutable Source<E> implements Handler<E> {

    protected MutableProcessor(Handler<? super E> target) {
        super(target);
    }

    public void handle(E event){
        fire(event);
    }
}