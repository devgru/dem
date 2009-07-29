package dem.translating.external;

import dem.quanta.Event;

public final class NoopTranslatorStrategy<TO extends Event, FROM extends TO> implements TranslatorStrategy<TO,FROM> {
    public TO translate(FROM from) {
        return from;
    }
}
