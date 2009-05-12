package ru.devg.dem.stuff;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Source;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public class MutableSource<E extends Event> extends Source<E> {

    public MutableSource() {
        super(new MutableHandler<E>());
    }

    public MutableSource(Handler<? super E> target) {
        super(new MutableHandler<E>(target));
    }

    public void setHandler(Handler<? super E> handler) {
        ((MutableHandler<E>)target).setHandler(handler);
    }

    public Handler<? super E> getHandler() {
        return ((MutableHandler<E>)target).getHandler();
    }

}
