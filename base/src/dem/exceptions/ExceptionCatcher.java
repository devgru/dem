package dem.exceptions;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;
import dem.bounding.Filter;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public final class ExceptionCatcher<E extends Event>
        extends Source<E> implements Filter<E> {

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

    @SuppressWarnings("unchecked")
    public boolean handleIfPossible(Event event) {
        try {
            fire((E) event);
            return true;
        } catch (Throwable e) {
            if (hole != null) {
                hole.handle(new ExceptionEvent(e));
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return "Exception catcher (target is " + target + "; exception hole is" + hole + ")";
    }
}
