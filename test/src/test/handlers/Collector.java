package test.handlers;

import test.events.CollectedEvent;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.176
 */
public class Collector implements Handler<CollectedEvent> {
    private final StringBuilder sb = new StringBuilder();

    public void handle(CollectedEvent event) {
        sb.append(event.getString());
        System.out.println(event.getString() + " handled");
    }

    public String getString() {
        return sb.toString();
    }
}
