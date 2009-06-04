package dem.inclass;

import dem.quanta.Event;
import dem.translating.TranslatorStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Handles {

    public Class<? extends Event> value();

    public Class<? extends TranslatorStrategy> translator() default TranslatorStrategy.class;

    public int priority() default 0;
}
