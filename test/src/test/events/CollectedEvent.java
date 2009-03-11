package test.events;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.176
 */
public class CollectedEvent implements Event {
    private final String c;

    public CollectedEvent(String c) {
        this.c = c;
    }

    public String getString() {
        return c;
    }
}
