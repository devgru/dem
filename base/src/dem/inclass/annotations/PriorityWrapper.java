package dem.inclass.annotations;

import java.lang.reflect.AnnotatedElement;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
final class PriorityWrapper implements Wrapper {
    public void wrap(AnnotatedElement clz, FilterWithPriority filterWithPriority) {
        Prioritized annotation =
                clz.getAnnotation(Prioritized.class);

        if (annotation != null) {
            filterWithPriority.setPriority((long) annotation.value());
        }
    }
}
