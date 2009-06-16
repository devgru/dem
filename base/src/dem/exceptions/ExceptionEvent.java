package dem.exceptions;

import dem.remote.RemoteEvent;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.180
 */
public final class ExceptionEvent implements RemoteEvent {
    private final Throwable exception;

    ExceptionEvent(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "Event, containing exception (" + exception + ")";
    }

}
