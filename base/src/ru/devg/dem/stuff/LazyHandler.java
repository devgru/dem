package ru.devg.dem.stuff;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.182
 */
public class LazyHandler<E extends Event> implements Handler<E> {

    private Handler<? super E> handler = new NoopHandler<E>();

    public LazyHandler() {
    }

    public LazyHandler(Handler<? super E> handler) {
        setHandler(handler);
    }

    public Handler<? super E> getHandler() {
        return handler;
    }

    public void setHandler(Handler<? super E> handler) {
        if (handler == null) {
            this.handler = new NoopHandler<E>();
        } else {
            this.handler = handler;
        }
    }

    public void handle(E event) {
        handler.handle(event);
    }
}
