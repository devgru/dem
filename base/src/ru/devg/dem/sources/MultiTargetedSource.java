package ru.devg.dem.sources;

import ru.devg.dem.bundles.HandlingBundle;
import ru.devg.dem.bundles.Bundle;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
@SuppressWarnings("unchecked")
public abstract class MultiTargetedSource<H extends Handler<? super E>, E extends Event>
        extends Source<E> implements Bundle<H> {

    public MultiTargetedSource(HandlingBundle<? super H, E> target) {
        super(target);
    }

    public MultiTargetedSource(Class<? extends HandlingBundle> target)
            throws IllegalAccessException, InstantiationException {
        this(target.newInstance());
    }

    public final void addHandler(H handler) {
        getTargetAsBundle().addHandler(handler);
    }

    public final void removeHandler(H handler) {
        getTargetAsBundle().removeHandler(handler);
    }

    private HandlingBundle<? super H, E> getTargetAsBundle() {
        return ((HandlingBundle< ? super H,E>)target);
    }
}