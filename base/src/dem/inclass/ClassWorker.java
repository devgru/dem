package dem.inclass;

import dem.bounding.Filter;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.inclass.exceptions.ElementIsUnbindableException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public final class ClassWorker {

    public static final List<? extends AbstractBinder> DEFAULT_BINDERS;
    public static final List<? extends Wrapper> DEFAULT_WRAPPERS;

    static {
        List<AbstractBinder> binders =
                new LinkedList<AbstractBinder>();
        binders.add(new FieldWorker());
        binders.add(new MethodWorker());

        DEFAULT_BINDERS = Collections.unmodifiableList(binders);

        List<Wrapper> wrappers =
                new LinkedList<Wrapper>();
        wrappers.add(new PriorityWrapper());
        wrappers.add(new TranslatingWrapper());

        DEFAULT_WRAPPERS = Collections.unmodifiableList(wrappers);
    }

    private final Object target;

    private final List<? extends AbstractBinder> binders;

    private final List<? extends Wrapper> wrappers;

    public ClassWorker(Object target, List<? extends AbstractBinder> binders, List<? extends Wrapper> wrappers) {
        this.target = target;
        this.binders = binders;
        this.wrappers = wrappers;
    }

    public List<? extends Filter<?>> bindClassElements() throws ClassIsUnbindableException {
        List<AnnotatedFilter> elements = new LinkedList<AnnotatedFilter>();

        Class targetClass = target.getClass();
        for (AbstractBinder binder : binders) {
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

        for (Wrapper wrapper : wrappers) {
            wrapper.wrap(filterToWrap.getAnnotatedElement(), filterWithPriority);
        }
        return filterWithPriority;

    }

}
