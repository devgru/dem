package dem.translating;

import dem.quanta.Event;
import dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
public final class NoopTranslator<TO extends Event, FROM extends TO>
        extends Translator<TO, FROM> {

    public NoopTranslator(Handler<? super TO> target, Class<FROM> bound) {
        super(target, bound);
    }

    public TO translate(FROM event) {
        return event;
    }

    @Override
    public String toString() {
        return "No-operation " + super.toString();
    }

}
