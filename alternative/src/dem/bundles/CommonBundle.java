package dem.bundles;

import dem.quanta.*;

import java.util.*;

/**
 * @author devgru &lt;java@devg.ru&gt;
 * @since 0.190
 */
public abstract class CommonBundle<E extends Event, H extends Handler>
    implements HandlingBundle<E, H>{

    private final Collection<Handler<? super E>> handlers =
        new LinkedList<Handler<? super E>>();

    public void handle(E event) {
        
    }

    public final boolean addHandler(H handler) throws IllegalArgumentException {
        return false;
    }

    public final boolean removeHandler(H handler) {
        return false;
    }
}
