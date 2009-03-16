package ru.devg.dem.extended;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.18
 */
public final class NoopHandler extends Filter {

    public boolean canHandle(Event event) {
        return false;
    }

    public void handle(Event event) {
    }
}
