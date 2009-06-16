package dem.exceptions;

import dem.processing.Processor;
import dem.quanta.Event;
import dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public final class ExceptionCatcher<E extends Event>
        extends Processor<E> {

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