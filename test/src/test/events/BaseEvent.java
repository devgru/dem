package test.events;

import dem.quanta.Event;

public class BaseEvent implements Event {
    public BaseEvent() {
        System.out.println("Created " + getClass().getSimpleName());
    }
}
