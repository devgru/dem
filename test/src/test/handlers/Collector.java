package test.handlers;

import test.events.CollectedEvent;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.0
 */
public class Collector implements Handler<CollectedEvent> {
    private final StringBuilder sb = new StringBuilder();

    public void handle(CollectedEvent event) {
        sb.append(event.getChar());
    }

    public String getString() {
        return sb.toString();
    }
}
