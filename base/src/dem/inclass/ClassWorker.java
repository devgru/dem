package dem.inclass;

import dem.bounding.Filter;
import dem.inclass.exceptions.ClassIsUnbindableException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public final class ClassWorker {

    private static final List<? extends AbstractBinder> defaultBinders;

    static {
        List<AbstractBinder> binders =
                new LinkedList<AbstractBinder>();
        binders.add(new FieldWorker());
        binders.add(new MethodWorker());

        defaultBinders = binders;
    }

    private final Object target;

    private final List<? extends AbstractBinder> binders;

    public ClassWorker(Object target) {
        this(target, defaultBinders);
    }

    public ClassWorker(Object target, List<? extends AbstractBinder> binders) {
        this.target = target;
        this.binders = binders;
    }

    public List<? extends Filter<?>> bindClassElements() throws ClassIsUnbindableException {
        List<FilterWithPriority> elements = new LinkedList<FilterWithPriority>();

        Class targetClass = target.getClass();
        while (targetClass != Object.class) {
            for (AbstractBinder binder : binders) {
                binder.tryBindMembers(target, elements, targetClass);
                /*for (Class oneOfInterfaces : targetClass.getInterfaces()) {
                    todo what about interfaces? still nothing
                    binder.tryBindMembers(elements, oneOfInterfaces);
                }*/
            }
            targetClass = targetClass.getSuperclass();
        }

        Collections.sort(elements);
        return elements;
    }

}
