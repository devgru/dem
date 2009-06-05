package dem.processing;

import dem.quanta.Event;
import dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.183
 */
public class Bypasser<E extends Event> extends Processor<E> {

    public Bypasser(Handler<? super E> target) {
        super(target);
    }

    public void handle(E event) {
        fire(event);
    }
}
