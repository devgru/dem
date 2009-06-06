package dem.stuff;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.183
 */
public class LateInitSource<E extends Event>
        extends Source<E> {

    public LateInitSource() {
        super(new MutableHandler<E>());
    }

    protected void setHandler(Handler<? super E> handler) {
        MutableHandler<E> mutableHandler = (MutableHandler<E>) this.target;
        if (mutableHandler.getHandler() == null) {
            mutableHandler.setHandler(handler);
        } else {
            throw new RuntimeException("This source is already initiated");
        }
    }
}
