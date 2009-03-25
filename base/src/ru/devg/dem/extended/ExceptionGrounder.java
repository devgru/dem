package ru.devg.dem.extended;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.sources.Source;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public final class ExceptionGrounder<E extends Event>
        extends Source<E> implements Handler<E>{
    private final Handler<ExceptionEvent> hole;

    public ExceptionGrounder(Handler<? super E> target) {
        this(target, null);
    }

    public ExceptionGrounder(Handler<? super E> target, Handler<ExceptionEvent> h) {
        super(target);
        this.hole = h;
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

    public final class ExceptionEvent implements Event {
        private final Throwable exception;

        private ExceptionEvent(Throwable exception) {
            this.exception = exception;
        }

        public Throwable getException() {
            return exception;
        }
    }

}
