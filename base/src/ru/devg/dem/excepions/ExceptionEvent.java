package ru.devg.dem.excepions;

import ru.devg.dem.pipes.api.RemoteEvent;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.180
 */
public final class ExceptionEvent implements RemoteEvent {
    private final Throwable exception;

    ExceptionEvent(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }
}
