package dem.mutables;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
public class DelayedInitializationSource<E extends Event>
        extends Source<E> {

    public DelayedInitializationSource() {
        super(new MutableHandler<E>());
    }

    protected void setTarget(Handler<? super E> target) {
        assert target != null;

        MutableHandler<E> mutableHandler = (MutableHandler<E>) this.target;
        if (mutableHandler.getTarget() == null) {
            mutableHandler.setTarget(target);
        } else {
            throw new RuntimeException("This source is already initialized");
        }
    }

    @Override
    public String toString() {
        return "Delayed-initialization " + super.toString();
    }

}
