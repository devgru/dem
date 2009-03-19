package ru.devg.dem.inclass.binding;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.translating.TranslatorStrategy;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public class BindableElementDescriptor {
    private final Class<? extends Event> bound;
    private final Class<? extends TranslatorStrategy> translatorStrategy;
    private final long priority;

    public BindableElementDescriptor(Class<? extends Event> bound,
                         long priority,
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
