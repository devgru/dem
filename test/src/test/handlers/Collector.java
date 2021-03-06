package test.handlers;

import dem.quanta.Handler;
import test.events.CollectedEvent;

public class Collector implements Handler<CollectedEvent> {
    private StringBuilder sb = new StringBuilder();

    public void handle(CollectedEvent event) {
        sb.append(event.getString());
        System.out.println(event.getString() + " handled");
    }

    public void clear() {
        sb = new StringBuilder();
    }

    public String getString() {
        return sb.toString();
    }
}
