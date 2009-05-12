package ru.devg.dem.processing;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.stuff.MutableSource;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public abstract class BiConnector<SENDS extends Event, HANDLES extends Event>
        extends MutableSource<SENDS> implements Handler<HANDLES> {

    protected BiConnector() {
    }

    public BiConnector(Handler<? super SENDS> target) {
        super(target);
    }


    public static <A extends Event, B extends Event> void
    bind(BiConnector<A, B> a, BiConnector<B, A> b) {
        a.setHandler(b);
        b.setHandler(a);
    }
}
