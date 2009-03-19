package ru.devg.dem.inclass;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.translating.TranslatorStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.176
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Handles {
    public Class<? extends Event> value();
    public Class<? extends TranslatorStrategy> translator() default TranslatorStrategy.class;
    public int priority() default 0;
}
