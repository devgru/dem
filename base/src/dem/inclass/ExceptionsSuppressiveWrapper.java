package dem.inclass;

import dem.inclass.exceptions.ElementIsUnbindableException;
import dem.bounding.Filter;
import dem.exceptions.ExceptionCatcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.annotation.Annotation;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
final class ExceptionsSuppressiveWrapper implements Wrapper{

    @SuppressWarnings("unchecked")
    public void wrap(AnnotatedElement clz, FilterWithPriority filterWithPriority) throws ElementIsUnbindableException {
        Annotation annotation = clz.getAnnotation(SuppressExceptions.class);
        if(annotation != null){
            Filter f = new ExceptionCatcher(filterWithPriority.getFilter());
            filterWithPriority.setFilter(f);
        }
    }
}
