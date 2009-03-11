package ru.devg.dem.bundles.one2one.onclass;

import ru.devg.dem.quanta.Event;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.15
 */
public interface InplaceDispatchable<E extends Event> {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD,ElementType.FIELD})
    public @interface Handles {
        public Class<? extends Event> value();
        public int priority() default 0;
    }

    public Class<E> getBoundClass();

}
