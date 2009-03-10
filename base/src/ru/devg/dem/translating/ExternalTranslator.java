package ru.devg.dem.translating;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
public final class ExternalTranslator<FROM extends Event, TO extends Event> extends Translator<FROM, TO> {

    private final TranslatorStrategy<? super FROM, ? extends TO> strategy;

    public ExternalTranslator(Class<FROM> bound, Handler<? super TO> target, TranslatorStrategy<? super FROM, ? extends TO> strategy) {
        super(bound, target);
        this.strategy = strategy;
    }

    public TO translate(FROM event) {
        return strategy.translate(event);
    }
}
