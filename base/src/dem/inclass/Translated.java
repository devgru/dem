package dem.inclass;

import dem.quanta.Event;
import dem.translating.NoopTranslator;
import dem.translating.TranslatorStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: devgru
 * Date: 29.06.2009
 * Time: 12:51:51
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Translated {

    public Class<? extends TranslatorStrategy> value() default NoopTranslator.class;

    public Class<? extends Event> bound() default Event.class;

}
