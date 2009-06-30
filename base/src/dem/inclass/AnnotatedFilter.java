package dem.inclass;

import dem.bounding.Filter;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by IntelliJ IDEA.
 * User: devgru
 * Date: 29.06.2009
 * Time: 17:16:21
 */
public interface AnnotatedFilter extends Filter {
    public AnnotatedElement getAnnotatedElement();
}
