package dem.mutables;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Log;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public class MutableHandler<E extends Event> implements Handler<E> {

    private Handler<? super E> target = null;

    public MutableHandler() {
    }

    public MutableHandler(Handler<? super E> target) {
        setTarget(target);
    }

    public Handler<? super E> getTarget() {
        return target;
    }

    public void setTarget(Handler<? super E> target) {
        this.target = target;
    }

    public void handle(E event) {
        if (target != null) {
            target.handle(event);
        }
    }

    @Override
    public String toString() {
        return "Mutable handler\n" +
                Log.offset("current target is " + target);
    }
}
