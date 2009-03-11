package test.events;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.176
 */
public class BaseEvent implements Event {
    public BaseEvent() {
        System.out.println("Created " + getClass().getSimpleName());
    }
}
