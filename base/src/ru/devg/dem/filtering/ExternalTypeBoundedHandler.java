package ru.devg.dem.filtering;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.180
 */
public final class ExternalTypeBoundedHandler<E extends Event> extends TypeBoundedHandler<E> {
    private final Handler<? super E> target;

    public ExternalTypeBoundedHandler(Handler<? super E> target, Class<E> bound) {
        super(bound);
        this.target = target;
    }

    public void handle(E event) {
        target.handle(event);
    }
}
