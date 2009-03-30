package ru.devg.dem.sources;

import ru.devg.dem.bundles.HandlerBundle;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
@SuppressWarnings("unchecked")
public abstract class MultiTargetedSource<H extends Handler<? super E>, E extends Event>
        extends Source<E> implements HandlerBundle<H, E> {

    @Deprecated
    public MultiTargetedSource(HandlerBundle<H, E> target) {
        super(target);
    }

    public MultiTargetedSource(Class<? extends HandlerBundle<H, E>> target)
            throws IllegalAccessException, InstantiationException {
        super(target.newInstance());
    }

    public final void addHandler(H handler) {
        getTargetAsBundle().addHandler(handler);
    }

    public final void removeHandler(H handler) {
        getTargetAsBundle().removeHandler(handler);
    }

    private HandlerBundle<H, E> getTargetAsBundle() {
        return ((HandlerBundle<H,E>)target);
    }
}