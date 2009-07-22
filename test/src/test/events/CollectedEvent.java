package test.events;

import dem.quanta.Event;

public class CollectedEvent implements Event {
    private final String c;

    public CollectedEvent(String c) {
        this.c = c;
    }

    public String getString() {
        return c;
    }
}
