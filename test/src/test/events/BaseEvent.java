package test.events;

import ru.devg.dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public class BaseEvent implements Event {
    public BaseEvent() {
        System.out.println("Created " + getClass().getSimpleName());
    }
}
