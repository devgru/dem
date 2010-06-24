package dem.inclass.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.185
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface ProcessedBy {
    Class<? extends Wrapper> value();
}
