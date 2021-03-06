package dem.translating.external;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.translating.Translator;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
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

    @Override
    public String toString() {
        return "External " + super.toString();
    }
}
