package ru.devg.dem.sources;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public abstract class Source<E extends Event> {
    protected final Handler<? super E> target;

    public Source(Handler<? super E> target) {
        assert target != null;
        this.target = target;
    }

    public final void fire(E event) {
        target.handle(event);
    }
}
