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

    private final HandlerBundle<H, E> target;

    public MultiTargetedSource(HandlerBundle<H, E> target) {
        super(target);
        this.target = target;
    }

    public final void addHandler(H handler) {
        target.addHandler(handler);
    }

    public final void removeHandler(H handler) {
        target.removeHandler(handler);
    }
}