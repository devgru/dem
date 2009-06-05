package dem.stuff;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
public class MutableSource<E extends Event> extends Source<E> {

    public MutableSource() {
        super(new MutableHandler<E>());
    }

    public MutableSource(Handler<? super E> target) {
        super(new MutableHandler<E>(target));
    }

    public final void setHandler(Handler<? super E> handler) {
        ((MutableHandler<E>) target).setHandler(handler);
    }

    public final Handler<? super E> getHandler() {
        return ((MutableHandler<E>) target).getHandler();
    }

}
