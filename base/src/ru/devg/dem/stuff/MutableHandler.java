package ru.devg.dem.stuff;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.182
 */
public class MutableHandler<E extends Event> implements Handler<E> {

    private Handler<? super E> handler = null;

    public MutableHandler() {
    }

    public MutableHandler(Handler<? super E> handler) {
        setHandler(handler);
    }

    public Handler<? super E> getHandler() {
        return handler;
    }

    public void setHandler(Handler<? super E> handler) {
        this.handler = handler;
    }

    public void handle(E event) {
        if (handler != null) {
            handler.handle(event);
        }
    }
}
