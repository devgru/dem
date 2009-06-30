package dem.inclass;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by IntelliJ IDEA.
 * User: devgru
 * Date: 29.06.2009
 * Time: 16:09:33
 */
public class PriorityWrapper implements Wrapper {
    public void wrap(AnnotatedElement clz, FilterWithPriority filterWithPriority) {
        Prioritized annotation =
                clz.getAnnotation(Prioritized.class);
        if (annotation != null) {
            filterWithPriority.setPriority((long) annotation.value());
        }
    }
}
