package dem.inclass.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@ProcessedBy(PriorityWrapper.class)
public @interface Prioritized {
    int value() default 0;
}
