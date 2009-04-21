package ru.devg.dem.stuff;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Source;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public class LazySource<E extends Event> extends Source<E> {

    public LazySource() {
        super(new LazyHandler<E>());
    }

    public LazySource(Handler<? super E> target) {
        super(new LazyHandler<E>(target));
    }

    public void setHandler(Handler<? super E> handler) {
        ((LazyHandler<E>)target).setHandler(handler);
    }

    public Handler<? super E> getHandler() {
        return ((LazyHandler<E>)target).getHandler();
    }

}
