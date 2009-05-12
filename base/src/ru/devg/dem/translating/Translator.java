package ru.devg.dem.translating;

import ru.devg.dem.bounding.BoundedHandler;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
public abstract class Translator<TO extends Event, FROM extends Event>
        extends BoundedHandler<FROM> implements TranslatorStrategy<TO, FROM> {

    private final Handler<? super TO> target;

    protected Translator(Handler<? super TO> target, Class<FROM> bound) {
        super(bound);
        this.target = target;
    }

    public final void handle(FROM event) {
        target.handle(translate(event));
    }
}