package dem.processing;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.stuff.MutableSource;

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
        return "Bi-connector (target is " + super.toString() + ")";
    }

}
