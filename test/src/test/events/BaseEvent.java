package test.events;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.0
 */
public class BaseEvent implements Event {
    public BaseEvent() {
        System.out.println("Created " + getClass().getSimpleName());
    }
}
