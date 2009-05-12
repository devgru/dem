package ru.devg.dem.processing;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.quanta.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.181
 */
public abstract class Processor<E extends Event>
        extends Source<E> implements Handler<E> {

    protected Processor(Handler<? super E> target) {
        super(target);
    }
}
