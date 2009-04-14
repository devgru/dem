package ru.devg.dem.excepions;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.sources.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public final class ExceptionCatcher<E extends Event>
        extends Source<E> implements Handler<E> {

    private final Handler<ExceptionEvent> hole;

    public ExceptionCatcher(Handler<? super E> target) {
        this(target, null);
    }

    public ExceptionCatcher(Handler<? super E> target, Handler<ExceptionEvent> hole) {
        super(target);
        this.hole = hole;
    }

    public void handle(E event) {
        try {
            fire(event);
        } catch (Throwable e) {
            if (hole != null) {
                hole.handle(new ExceptionEvent(e));
            }
        }
    }

}
