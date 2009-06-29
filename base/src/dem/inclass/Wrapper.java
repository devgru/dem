package dem.inclass;

import dem.inclass.exceptions.ElementIsUnbindableException;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by IntelliJ IDEA.
 * User: devgru
 * Date: 29.06.2009
 * Time: 16:06:56
 */
public interface Wrapper {
    public void wrap(AnnotatedElement clz, FilterWithPriority filterWithPriority) throws ElementIsUnbindableException;
}
