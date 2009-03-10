package test.handlers;

import ru.devg.dem.translating.Translator;
import ru.devg.dem.quanta.Handler;
import test.events.BaseEvent;
import test.events.CollectedEvent;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.0
 */
public class BaseHandler extends Translator<BaseEvent, CollectedEvent> {

    private final char c;

    public BaseHandler( Handler<? super CollectedEvent> target, char c) {
        super(BaseEvent.class, target);
        this.c = c;
    }

    public CollectedEvent translate(BaseEvent event) {
        return new CollectedEvent(c);
    }

}
