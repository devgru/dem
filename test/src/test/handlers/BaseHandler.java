package test.handlers;

import ru.devg.dem.translating.Translator;
import ru.devg.dem.quanta.Handler;
import test.events.BaseEvent;
import test.events.CollectedEvent;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.0
 */
public class BaseHandler extends Translator<CollectedEvent, BaseEvent> {

    private final char c;

    public BaseHandler( Handler<? super CollectedEvent> target, char c) {
        super(target, BaseEvent.class);
        this.c = c;
    }

    public CollectedEvent translate(BaseEvent event) {
        return new CollectedEvent(c);
    }

}
