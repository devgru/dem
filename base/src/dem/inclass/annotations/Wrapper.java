package dem.inclass.annotations;

import dem.inclass.exceptions.ElementIsUnbindableException;

import java.lang.reflect.AnnotatedElement;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
public interface Wrapper {
    public void wrap(AnnotatedElement clz, FilterWithPriority filterWithPriority)
            throws ElementIsUnbindableException;
}
