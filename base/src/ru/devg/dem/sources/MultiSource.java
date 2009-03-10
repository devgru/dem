package ru.devg.dem.sources;

import ru.devg.dem.bundles.HandlerBundle;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
@SuppressWarnings("unchecked")
public abstract class MultiSource<H extends Handler<?>, E extends Event>
        extends Source<E> implements HandlerBundle<H,E> {

    private final HandlerBundle<H,E> target;

    public MultiSource(HandlerBundle<H, E> target) {
        super(target);
        this.target = target;
    }

    public final void addHandler(H handler) {
        target.addHandler(handler);
    }

    public void removeHandler(H handler) {
        target.addHandler(handler);
    }
}
