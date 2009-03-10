package test.events;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.0
 */
public class CollectedEvent implements Event {
    private final char c;

    public CollectedEvent(char c) {
        this.c = c;
    }

    public char getChar() {
        return c;
    }
}
