package ru.devg.dem.inclass.binding;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.translating.TranslatorStrategy;
import ru.devg.dem.inclass.Handles;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
final class BindableElement {

    static final Class<TranslatorStrategy> WITHOUT_TRANSLATOR = TranslatorStrategy.class;
    private static final long UNREACHABLE_NEGATIVE_PRIORITY = (long) Integer.MIN_VALUE - 1;


    public static final BindableElement ORPHAN_HANDLER =
            new BindableElement(Event.class, UNREACHABLE_NEGATIVE_PRIORITY, WITHOUT_TRANSLATOR);

    private final Class<? extends Event> bound;
    private final Class<? extends TranslatorStrategy> translatorStrategy;
    private final long priority;

    public BindableElement(Handles annotation) {
        this(annotation.value(), annotation.priority(), annotation.translator());
    }

    public BindableElement(Class<? extends Event> bound, long priority,
                           Class<? extends TranslatorStrategy> translatorStrategy) {
        this.bound = bound;
        this.priority = priority;
        this.translatorStrategy = translatorStrategy;
    }

    public Class<? extends Event> getBound() {
        return bound;
    }

    public long getPriority() {
        return priority;
    }

    public Class<? extends TranslatorStrategy> getTranslatorStrategy() {
        return translatorStrategy;
    }
}
