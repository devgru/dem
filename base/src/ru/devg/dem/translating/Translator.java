package ru.devg.dem.translating;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.filtering.TypeBoundedHandler;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.175
 */
public abstract class Translator<TO extends Event, FROM extends Event>
    extends TypeBoundedHandler<FROM> implements TranslatorStrategy<TO, FROM> {

    private final Handler<? super TO> target;

    protected Translator(Handler<? super TO> target, Class<FROM> bound) {
        super(bound);
        this.target = target;
    }

    public final void handle(FROM event) {
        target.handle(translate(event));
    }
}