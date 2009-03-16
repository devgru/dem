package ru.devg.dem.sources;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public abstract class Source<E extends Event> {
    private final Handler<E> target;

    public Source(Handler<E> target) {
        this.target = target;
    }

    protected final void sendUp(E event) {
        target.handle(event);
    }
}
