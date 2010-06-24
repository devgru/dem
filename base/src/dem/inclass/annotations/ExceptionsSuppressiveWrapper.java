package dem.inclass.annotations;

import dem.exceptions.ExceptionCatcher;
import dem.inclass.exceptions.ElementIsUnbindableException;

import java.lang.reflect.AnnotatedElement;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
final class ExceptionsSuppressiveWrapper implements Wrapper {

    @SuppressWarnings("unchecked")
    public void wrap(AnnotatedElement clz, FilterWithPriority filterWithPriority) throws ElementIsUnbindableException {
        if (clz.isAnnotationPresent(SuppressExceptions.class)) {
            filterWithPriority.setFilter(
                    new ExceptionCatcher(filterWithPriority.getFilter())
            );
        }
    }
}
