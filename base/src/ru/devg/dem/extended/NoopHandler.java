package ru.devg.dem.extended;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.filtering.Filter;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.18
 */
public final class NoopHandler extends Filter {
    private static final NoopHandler instance = new NoopHandler();

    public static NoopHandler getInstance() {
        return instance;
    }

    private NoopHandler(){
    }

    public boolean canHandle(Event event) {
        return false;
    }

    public void handle(Event event) {
    }
}
