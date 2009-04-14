package ru.devg.dem.sources;

import ru.devg.dem.bundles.HandlerBundle;
import ru.devg.dem.bundles.HandlingBundle;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.181
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
