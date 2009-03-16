package test.handlers;

import ru.devg.dem.translating.Translator;
import ru.devg.dem.quanta.Handler;
import test.events.BaseEvent;
import test.events.CollectedEvent;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.176
 */
public class BaseHandler<E extends BaseEvent>
        extends Translator<CollectedEvent, E> {

    private final String c;

    public BaseHandler(Handler<? super CollectedEvent> target, Class<E> bound, String c) {
        super(target, bound);
        this.c = c;
    }

    public CollectedEvent translate(BaseEvent event) {
        return new CollectedEvent(c);
    }

}
