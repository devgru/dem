package test.handlers;

import dem.translating.Translator;
import test.events.BaseEvent;
import test.events.CollectedEvent;

public class BaseHandler<E extends BaseEvent>
        extends Translator<CollectedEvent, E> {

    private final String c;

    public BaseHandler(Collector target, Class<E> bound, String c) {
        super(target, bound);
        this.c = c;
    }

    public CollectedEvent translate(BaseEvent event) {
        return new CollectedEvent(c);
    }

}
