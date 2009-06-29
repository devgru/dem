package dem.inclass;

import dem.inclass.exceptions.ClassIsUnbindableException;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.177
 */
public interface AbstractBinder {

    public void tryBindMembers(Object target, List<AnnotatedFilter> listToFill, Class<?> clz)
            throws ClassIsUnbindableException;

}
