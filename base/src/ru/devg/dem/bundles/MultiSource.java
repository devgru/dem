package ru.devg.dem.bundles;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.quanta.Source;

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

    public final void addHandler(H handler) throws IllegalArgumentException {
        ((HandlingBundle<E, H>) target).addHandler(handler);
    }

    public final void removeHandler(H handler) {
        ((HandlingBundle<E, H>) target).removeHandler(handler);
    }
}
