package ru.devg.dem.sources;

import ru.devg.dem.bundles.HandlerBundle;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.15
 */
@SuppressWarnings("unchecked")
public class MultiSource<H extends Handler<?>, E extends Event>
        extends Source<E> implements AbstractMultiSource<H, E> {

    public MultiSource(HandlerBundle<H, E> target) {
        super(target);
    }

    public final void addTarget(H handler) {
        ((HandlerBundle<H, E>) target).addHandler(handler);
    }


    /**
     * In fact, this method if checked of cource.
     *
     * @param handler target to remove
     */
    public void removeTarget(H handler) {
        ((HandlerBundle<H, E>) target).addHandler(handler);
    }
}
