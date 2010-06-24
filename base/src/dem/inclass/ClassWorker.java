package dem.inclass;

import dem.bounding.Filter;
import dem.inclass.annotations.FilterWithPriority;
import dem.inclass.annotations.ProcessedBy;
import dem.inclass.annotations.Wrapper;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.inclass.exceptions.ElementIsUnbindableException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public final class ClassWorker {

    public static final List<? extends AbstractBinder> DEFAULT_BINDERS;

    static {
        List<AbstractBinder> binders =
                new LinkedList<AbstractBinder>();
        binders.add(new FieldWorker());
        binders.add(new MethodWorker());

        DEFAULT_BINDERS = Collections.unmodifiableList(binders);

    }

    private final Object target;

    private final List<? extends AbstractBinder> binders;

    public ClassWorker(Object target, List<? extends AbstractBinder> binders) {
        this.target = target;
        this.binders = binders;
    }

    public List<? extends Filter<?>> bindClassElements() throws ClassIsUnbindableException {
        List<AnnotatedFilter> elements = new LinkedList<AnnotatedFilter>();

        for (AbstractBinder binder : binders) {
            Class targetClass = target.getClass();
            while (targetClass != Object.class) {
                binder.tryBindMembers(target, elements, targetClass);
                targetClass = targetClass.getSuperclass();
            }
        }

        List<FilterWithPriority> elementsWithPriority
                = new LinkedList<FilterWithPriority>();

        try {
            for (AnnotatedFilter element : elements) {
                elementsWithPriority.add(wrap(element));
            }
        } catch (ElementIsUnbindableException e) {
            throw new ClassIsUnbindableException(e);
        }

        Collections.sort(elementsWithPriority);
        return elementsWithPriority;
    }


    public FilterWithPriority wrap(AnnotatedFilter filterToWrap) throws ElementIsUnbindableException {

        FilterWithPriority filterWithPriority = new FilterWithPriority(filterToWrap);

        for (Annotation annotation : filterToWrap.getAnnotatedElement().getAnnotations()) {
            ProcessedBy processedBy = annotation.annotationType().getAnnotation(ProcessedBy.class);
            if (processedBy != null) {
                try {
                    Class<? extends Wrapper> wrapperClass = processedBy.value();
                    Constructor<? extends Wrapper> constructor = wrapperClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    constructor.newInstance().wrap(filterToWrap.getAnnotatedElement(), filterWithPriority);
                } catch (InstantiationException e) {
                    throw new ElementIsUnbindableException(e);
                } catch (IllegalAccessException e) {
                    throw new ElementIsUnbindableException(e);
                } catch (NoSuchMethodException e) {
                    throw new ElementIsUnbindableException(e);
                } catch (InvocationTargetException e) {
                    throw new ElementIsUnbindableException(e);
                }
            }
        }

        return filterWithPriority;

    }

}
