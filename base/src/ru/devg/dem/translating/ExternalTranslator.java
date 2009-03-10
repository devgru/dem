package ru.devg.dem.translating;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.175
 */
public final class ExternalTranslator<TO extends Event, FROM extends Event>
        extends Translator<TO, FROM> {

    private final TranslatorStrategy<? extends TO, ? super FROM> strategy;

    public ExternalTranslator(Handler<? super TO> target, Class<FROM> bound,
                              TranslatorStrategy<? extends TO, ? super FROM> strategy) {
        super(target, bound);
        this.strategy = strategy;
    }

    public TO translate(FROM event) {
        return strategy.translate(event);
    }
}
