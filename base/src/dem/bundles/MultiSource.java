package dem.bundles;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.181
 */
public abstract class MultiSource<E extends Event, H extends Handler>
        extends Source<E>
        implements HandlerBundle<H> {

    protected MultiSource(HandlingBundle<E, H> target) {
        super(target);
    }

    public final boolean addHandler(H handler) throws IllegalArgumentException {
        return ((HandlingBundle<E, H>) target).addHandler(handler);
    }

    public final boolean removeHandler(H handler) {
        return ((HandlingBundle<E, H>) target).removeHandler(handler);
    }

    @Override
    public String toString() {
        return "Multi-source (targets: " + target + ")";
    }
}
