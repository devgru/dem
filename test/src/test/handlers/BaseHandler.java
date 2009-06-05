package test.handlers;

import dem.translating.Translator;
import test.events.BaseEvent;
import test.events.CollectedEvent;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
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
