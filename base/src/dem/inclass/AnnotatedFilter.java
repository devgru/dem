package dem.inclass;

import dem.bounding.Filter;

import java.lang.reflect.AnnotatedElement;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
interface AnnotatedFilter extends Filter {
    public AnnotatedElement getAnnotatedElement();
}
