package ru.devg.dem.translating;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.filtering.TypeBoundedHandler;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.17
 */
public abstract class Translator<FROM extends Event,TO extends Event>
    extends TypeBoundedHandler<FROM> implements TranslatorStrategy<FROM,TO> {

    private final Handler<? super TO> target;

    protected Translator(Class<FROM> bound, Handler<? super TO> target) {
        super(bound);
        this.target = target;
    }

    public final void handle(FROM event) {
        target.handle(translate(event));
    }
}