package ru.devg.dem.extended;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.180
 */
public final class ExceptionEvent implements Event {
    private final Throwable exception;

    ExceptionEvent(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }
}
