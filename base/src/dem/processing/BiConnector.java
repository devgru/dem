package dem.processing;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Log;
import dem.mutables.MutableSource;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public abstract class BiConnector<SENDS extends Event, HANDLES extends Event>
        extends MutableSource<SENDS> implements Handler<HANDLES> {

    public BiConnector() {
    }

    public BiConnector(Handler<? super SENDS> target) {
        super(target);
    }

    @Override
    public String toString() {
        return toString(true);
    }

    public String toString(boolean withTarget) {
        return "Bi-connector\n" +
                Log.offset("real class is " + getClass().getName()) +
                (withTarget ? "\n" + Log.offset("target is " + getTarget()) : "");
    }

}
